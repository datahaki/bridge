// code by jph
package ch.ethz.idsc.tensor.ref;

public enum NameString {
  FIRST, SECOND, THIRD;

  @Override
  public String toString() {
    return "unrelated to name() " + ordinal();
  }
}
