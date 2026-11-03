// code by jph
package ch.alpine.bridge.lang;

public record IntQuotientRemainder(int div, int rem) {
  public static IntQuotientRemainder from(long x, int y) {
    long div = Math.floorDivExact(x, y);
    return new IntQuotientRemainder(Math.toIntExact(div), Math.floorMod(x, y));
  }
}
