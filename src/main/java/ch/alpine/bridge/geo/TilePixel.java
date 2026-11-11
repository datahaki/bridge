// code by jph
package ch.alpine.bridge.geo;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Optional;

import ch.alpine.bridge.lang.IntQuotientRemainder;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.UnitSystem;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.Floor;
import ch.alpine.tensor.sca.tri.ArcTan;
import ch.alpine.tensor.sca.tri.ArcTanh;
import ch.alpine.tensor.sca.tri.Sin;
import ch.alpine.tensor.sca.tri.Sinh;

/** address combination of tile and pixel on that tile
 * 
 * @param tile
 * @param wid
 * @param pix between 0 and (wid-1)
 * @param piy between 0 and (wid-1) */
public record TilePixel(Tile tile, int wid, int pix, int piy) implements Serializable {
  /** value of latitude outside of this domain cannot be mapped
   * meaningfully to a tile pixel coordinate */
  public static final Clip LAT_DOMAIN = Clips.absolute(ArcTan.FUNCTION.apply(Sinh.FUNCTION.apply(Pi.VALUE)));

  /** function decomposes pixel in world map
   * to tile and pixel on that tile address
   * 
   * @param z
   * @param wid
   * @param nx
   * @param ny
   * @return */
  public static TilePixel of(int z, int wid, long nx, long ny) {
    IntQuotientRemainder iqrx = IntQuotientRemainder.from(nx, wid);
    IntQuotientRemainder iqry = IntQuotientRemainder.from(ny, wid);
    Tile tile = new Tile(z, iqrx.div(), iqry.div());
    return new TilePixel(tile, wid, iqrx.rem(), iqry.rem());
  }

  /** formula taken from gemini
   * 
   * @param z
   * @param wid
   * @param lon
   * @param lat
   * @return */
  public static TilePixel from(int z, int wid, Scalar lon, Scalar lat) {
    lon = UnitSystem.SI().apply(lon);
    lat = LAT_DOMAIN.apply(UnitSystem.SI().apply(lat));
    int base = 1 << z;
    Scalar factor = RealScalar.of(base * wid);
    Scalar nx = lon.add(Pi.VALUE).divide(Pi.TWO).multiply(factor);
    Scalar ny = Rational.HALF.subtract(ArcTanh.FUNCTION.apply(Sin.FUNCTION.apply(lat)).divide(Pi.TWO)) //
        .multiply(factor);
    return TilePixel.of(z, wid, Floor.longValueExact(nx), Floor.longValueExact(ny));
  }

  /** @param z
   * @param wid
   * @param lon_lat for instance {-0.762800[deg], 38.343373[deg]}
   * @return */
  public static TilePixel from(int z, int wid, Tensor lon_lat) {
    return from(z, wid, lon_lat.Get(0), lon_lat.Get(1));
  }

  public static TilePixel geo(Rectangle rectangle, CoordinateBoundingBox cbb) {
    Clip clip0 = cbb.clip(0);
    Clip clip1 = cbb.clip(1);
    for (int z = 0; z < 19; ++z) {
      TilePixel tp0 = TilePixel.from(z + 1, 256, Tensors.of(clip0.min(), clip1.max()));
      TilePixel tp1 = TilePixel.from(z + 1, 256, Tensors.of(clip0.max(), clip1.min()));
      int dx = (int) (tp1.absX() - tp0.absX());
      if (rectangle.width < dx) {
        return TilePixel.from(z, 256, Tensors.of(clip0.min(), clip1.max()));
      }
    }
    return null;
  }

  /** @param lon_lat for instance {-0.762800[deg], 38.343373[deg]}
   * @return */
  public TilePixel from(Tensor lon_lat) {
    return from(tile.z(), wid, lon_lat);
  }

  /** @return */
  public long absX() {
    return (tile.x() * wid) + pix;
  }

  /** @return */
  public long absY() {
    return (tile.y() * wid) + piy;
  }

  /** @param dx pixel level
   * @param dy pixel level
   * @return */
  public TilePixel shift(long dx, long dy) {
    int z = tile.z();
    long base = (1 << z) * wid;
    long nx = Math.floorMod(absX() + dx, base);
    long ny = Math.floorMod(absY() + dy, base);
    return of(z, wid, nx, ny);
  }

  /** @param wid_delta increment of width, or decrement when negative
   * @return */
  public Optional<TilePixel> widIncr(final int wid_delta, int z_max) {
    Tensor lon_lat = lon_lat();
    IntQuotientRemainder iqr = IntQuotientRemainder.from(wid + wid_delta, 256);
    int z = tile.z() + iqr.div() - 1;
    if (0 <= z && z <= z_max)
      return Optional.of(from(z, 256 + iqr.rem(), lon_lat));
    if (z == -1 && 0 < iqr.rem())
      return Optional.of(from(0, iqr.rem(), lon_lat));
    return Optional.empty();
  }

  /** formula adapted from gemini
   * 
   * @return {lon, lat} in radians */
  public Tensor lon_lat() {
    int z = tile().z();
    int ymax = (1 << z) * wid;
    Scalar ang = Rational.of(ymax - absY() * 2, ymax).multiply(Pi.VALUE);
    return Tensors.of( //
        Rational.of(absX() * 2 - ymax, ymax).multiply(Pi.VALUE), //
        ArcTan.FUNCTION.apply(Sinh.FUNCTION.apply(ang)) //
    );
  }

  public Point offsetTo(TilePixel tilePixel) {
    return new Point( //
        (int) (tilePixel.absX() - absX()), //
        (int) (tilePixel.absY() - absY()));
  }

  public Tensor encode() {
    return Tensors.vector(tile.z(), wid, absX(), absY());
  }

  public Scalar fractionalResolution() {
    return RealScalar.of(tile.z()).add(Rational.of(wid - 256, 256));
  }
}
