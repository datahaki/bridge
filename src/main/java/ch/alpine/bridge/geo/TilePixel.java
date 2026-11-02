// code by jph
package ch.alpine.bridge.geo;

import java.io.Serializable;
import java.util.Optional;

import ch.alpine.bridge.lang.IntQuotientRemainder;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.num.Pi;
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
   * @param nx
   * @param ny
   * @return */
  public static TilePixel of(int z, int wid, long nx, long ny) {
    IntQuotientRemainder iqrx = IntQuotientRemainder.from(nx, wid);
    IntQuotientRemainder iqry = IntQuotientRemainder.from(ny, wid);
    Tile tile = new Tile(z, iqrx.div(), iqry.div());
    return new TilePixel(tile, wid, iqrx.rem(), iqry.rem());
  }

  /** @param z
   * @param lon_lat for instance {-0.762800[deg], 38.343373[deg]}
   * @return */
  public static TilePixel from(int z, int wid, Tensor lon_lat) {
    return from(z, wid, lon_lat.Get(0), lon_lat.Get(1));
  }

  /** formula taken from gemini
   * 
   * @param z
   * @param lat
   * @param lon
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

  /** @param lon_lat for instance {-0.762800[deg], 38.343373[deg]}
   * @return */
  public TilePixel from(Tensor lon_lat) {
    return from(tile.z(), wid, lon_lat);
  }
  // public TilePixel {
  // Integers.requireEquals(PIXEL_CLIP.applyAsInt(pix), pix);
  // Integers.requireEquals(PIXEL_CLIP.applyAsInt(piy), piy);
  // }

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

  /** @param delta zoom increment, or decrement when negative
   * @return */
  public Optional<TilePixel> zoomIncr(final int wid_delta, int z_max) {
    Tensor lon_lat = lon_lat();
    IntQuotientRemainder iqr = IntQuotientRemainder.from(wid + wid_delta, 256);
    int z = tile.z() + iqr.div() - 1;
    if (0 <= z && z <= z_max)
      return Optional.of(from(z, 256 + iqr.rem(), lon_lat));
    return Optional.empty();
  }

  /** formula adapted from gemini
   * 
   * @return {lon, lat} */
  public Tensor lon_lat() {
    int z = tile().z();
    int ymax = (1 << z) * wid;
    Scalar ang = Rational.of(ymax - absY() * 2, ymax).multiply(Pi.VALUE);
    return Tensors.of( //
        Rational.of(absX() * 2 - ymax, ymax).multiply(Pi.VALUE), //
        ArcTan.FUNCTION.apply(Sinh.FUNCTION.apply(ang)) //
    );
  }

  public Tensor encode() {
    return Tensors.vector(tile.z(), wid, absX(), absY());
  }
}
