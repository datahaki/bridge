// code by jph
package ch.alpine.bridge.geo;

import java.util.function.IntUnaryOperator;

import ch.alpine.tensor.Rational;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.ext.Integers;
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
 * @param pix between 0 and 255
 * @param piy between 0 and 255 */
public record TilePixel(Tile tile, int pix, int piy) {
  /** value of latitude outside of this domain cannot be mapped
   * meaningfully to a tile pixel coordinate */
  public static final Clip LAT_DOMAIN = Clips.absolute(ArcTan.FUNCTION.apply(Sinh.FUNCTION.apply(Pi.VALUE)));
  private static final int xFF = 255;
  private static final IntUnaryOperator PIXEL_CLIP = Integers.clip(0, xFF);

  /** function decomposes pixel in world map
   * to tile and pixel on that tile address
   * 
   * @param z
   * @param nx
   * @param ny
   * @return */
  public static TilePixel of(int z, long nx, long ny) {
    int mask = Tile.maxInclusive(z);
    int tx = (int) (nx >> 8) & mask;
    int ty = (int) (ny >> 8) & mask;
    return new TilePixel(new Tile(z, tx, ty), (int) (nx & xFF), (int) (ny & xFF));
  }

  /** @param z
   * @param lat_lon for instance {38.343373[deg], -0.762800[deg]}n
   * @return */
  public static TilePixel from(int z, Tensor lat_lon) {
    return from(z, lat_lon.Get(0), lat_lon.Get(1));
  }

  /** formula taken from gemini
   * 
   * @param z
   * @param lat
   * @param lon
   * @return */
  public static TilePixel from(int z, Scalar lat, Scalar lon) {
    lat = LAT_DOMAIN.apply(UnitSystem.SI().apply(lat));
    lon = UnitSystem.SI().apply(lon);
    Scalar ny = RealScalar.ONE.subtract(ArcTanh.FUNCTION.apply(Sin.FUNCTION.apply(lat)).divide(Pi.VALUE)).multiply(RealScalar.of(1 << z + 7));
    Scalar nx = lon.add(Pi.VALUE).divide(Pi.TWO).multiply(RealScalar.of(1 << z + 8));
    return TilePixel.of(z, //
        Floor.longValueExact(nx), //
        Floor.longValueExact(ny));
  }

  /** @param lat_lon for instance {38.343373[deg], -0.762800[deg]}
   * @return */
  public TilePixel from(Tensor lat_lon) {
    return from(tile.z(), lat_lon);
  }

  public TilePixel {
    Integers.requireEquals(PIXEL_CLIP.applyAsInt(pix), pix);
    Integers.requireEquals(PIXEL_CLIP.applyAsInt(piy), piy);
  }

  /** @return */
  public long absX() {
    return (tile.x() << 8) + pix;
  }

  /** @return */
  public long absY() {
    return (tile.y() << 8) + piy;
  }

  /** @param dx pixel level
   * @param dy pixel level
   * @return */
  public TilePixel shift(long dx, long dy) {
    int z = tile.z();
    long mask = (1 << (z + 8)) - 1;
    long nx = (absX() + dx) & mask;
    long ny = (absY() + dy) & mask;
    return of(z, nx, ny);
  }

  /** @param delta
   * @return */
  public TilePixel zoom(int delta) {
    int z = tile.z();
    // TODO BRIDGE max z should depend on tileServer
    int nz = Math.min(Math.max(0, z + delta), 19);
    delta = nz - z;
    long mask = (1 << z + 8) - 1;
    long nx = absX() & mask;
    long ny = absY() & mask;
    if (0 <= delta) {
      nx <<= delta;
      ny <<= delta;
    } else {
      nx >>= -delta;
      ny >>= -delta;
    }
    return of(z + delta, nx, ny);
  }

  /** formula adapted from gemini
   * 
   * @return {lat, lon} */
  public Tensor lat_lon() {
    int z = tile().z();
    int ymax = 1 << z + 7;
    Scalar ang = Rational.of(ymax - absY(), ymax).multiply(Pi.VALUE);
    return Tensors.of( //
        ArcTan.FUNCTION.apply(Sinh.FUNCTION.apply(ang)), //
        Rational.of(absX() - ymax, ymax).multiply(Pi.VALUE));
  }
}
