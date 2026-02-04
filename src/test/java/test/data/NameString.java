// code by jph
package test.data;

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
