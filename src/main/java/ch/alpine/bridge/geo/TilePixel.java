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

public record TilePixel(Tile tile, int pix, int piy) {
  private static final int xFF = 255;
  private static final IntUnaryOperator CLIP = Integers.clip(0, xFF);

  public static TilePixel of(int z, long nx, long ny) {
    int tx = (int) (nx / 256);
    int ty = (int) (ny / 256);
    return new TilePixel(new Tile(z, tx, ty), (int) (nx & xFF), (int) (ny & xFF));
  }

  public static final Clip CLIP2 = Clips.absolute(ArcTan.FUNCTION.apply(Sinh.FUNCTION.apply(Pi.VALUE)));

  /** 38.343373, -0.762800
   * 
   * @param z
   * @param lat
   * @param lon
   * @return */
  public static TilePixel from(int z, Tensor lat_lon) {
    return from(z, lat_lon.Get(0), lat_lon.Get(1));
  }

  public static TilePixel from(int z, Scalar lat, Scalar lon) {
    lat = CLIP2.apply(UnitSystem.SI().apply(lat));
    lon = UnitSystem.SI().apply(lon);
    Scalar ny = RealScalar.ONE.subtract(ArcTanh.FUNCTION.apply(Sin.FUNCTION.apply(lat)).divide(Pi.VALUE)).multiply(RealScalar.of(1 << z + 7));
    Scalar nx = lon.add(Pi.VALUE).divide(Pi.TWO).multiply(RealScalar.of(1 << z + 8));
    return TilePixel.of(z, Floor.longValueExact(nx), Floor.longValueExact(ny));
  }

  public TilePixel {
    Integers.requireEquals(CLIP.applyAsInt(pix), pix);
    Integers.requireEquals(CLIP.applyAsInt(piy), piy);
  }

  public long absx() {
    return tile.x() * 256 + pix;
  }

  public long absy() {
    return tile.y() * 256 + piy;
  }

  /** @param dx pixel level
   * @param dy pixel level
   * @return */
  public TilePixel shift(long dx, long dy) {
    int z = tile.z();
    long mask = (1 << (z + 8)) - 1;
    long nx = (absx() + dx) & mask;
    long ny = (absy() + dy) & mask;
    return of(z, nx, ny);
  }

  public TilePixel zoom(int delta) {
    int z = tile.z();
    int nz = Math.min(Math.max(0, z + delta), 19);
    delta = nz - z;
    long mask = (1 << z + 8) - 1;
    long nx = absx() & mask;
    long ny = absy() & mask;
    if (0 <= delta) {
      nx <<= delta;
      ny <<= delta;
    } else {
      nx >>= -delta;
      ny >>= -delta;
    }
    return of(z + delta, nx, ny);
  }

  /** @return {lat, lon} */
  public Tensor lat_lon() {
    int z = tile().z();
    int ymax = 1 << z + 8;
    Scalar ang = Pi.VALUE.subtract(Rational.of(absy(), ymax).multiply(Pi.TWO));
    Scalar lat = ArcTan.FUNCTION.apply(Sinh.FUNCTION.apply(ang));
    // ---
    int xmax = 1 << z + 8;
    Scalar lon = Rational.of(absx(), xmax).subtract(Rational.HALF).multiply(Pi.TWO);
    return Tensors.of(lat, lon);
  }
}
