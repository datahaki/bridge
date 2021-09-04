// code by jph
package ch.alpine.java.ref;

import java.awt.Color;
import java.io.File;

import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.mat.re.Pivots;

public class SimpleParam extends BaseParam {
  private final int ignore_int = 2;
  private final Integer ignore_Integer = 2;
  private final String ignore_final_String = "asd";
  @FieldSelection(list = "a|b|c")
  public String string = "abc";
  public Boolean flag = false;
  public Pivots pivot = Pivots.ARGMAX_ABS;
  // public Scalar[] scalars = { Pi.VALUE, RealScalar.ZERO, ComplexScalar.I };
  public final AnotherParam anotherParam = new AnotherParam();
  public final NestedParam[] nestedParams = { new NestedParam(), new NestedParam() };

  public static class AnotherParam {
    public File file = HomeDirectory.file();
    public Color color = Color.RED;
  }

  public static class NestedParam extends BaseParam {
    public Boolean some = true;
    public String text = "grolley";
    public final AnotherParam anotherParam = new AnotherParam();
  }
}
