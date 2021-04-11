// code by jph
package ch.ethz.idsc.tensor.ref;

public enum NameString implements IfForTesting {
  FIRST {
    @Override
    public String here() {
      return "1";
    }
  },
  SECOND {
    @Override
    public String here() {
      return "2";
    }
  },
  THIRD {
    @Override
    public String here() {
      return "3";
    }
  };

  @Override
  public String toString() {
    return "unrelated to name() " + ordinal();
  }
}
