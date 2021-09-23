// code by jph
package ch.alpine.java.ref;

import java.awt.Color;
import java.io.File;

import ch.alpine.java.ref.ann.FieldFuse;
import ch.alpine.java.ref.ann.FieldLabel;
import ch.alpine.java.ref.ann.FieldSelection;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.mat.re.Pivots;

public class SimpleParam extends BaseParam {
  private final int ignore_int = 2;
  private final Integer ignore_Integer = 2;
  private final String ignore_final_String = "asd";
  @FieldSelection(array = { "a", "b", "c" })
  public String string = "abc";
  @FieldLabel(text = "Emergency Off")
  public Boolean flag = false;
  @FieldLabel(text = "Choose Pivot")
  public Pivots pivot = Pivots.ARGMAX_ABS;
  // public Scalar[] scalars = { Pi.VALUE, RealScalar.ZERO, ComplexScalar.I };
  @FieldLabel(text = "Another Parameter ASDGHJ")
  public final AnotherParam anotherParam = new AnotherParam();
  @FieldLabel(text = "Nested %a")
  public final NestedParam[] nestedParams = { new NestedParam(), new NestedParam() };

  public static class AnotherParam {
    public File file = HomeDirectory.file();
    public Color color = Color.RED;
  }

  public static class NestedParam extends BaseParam {
    @FieldFuse(text = "fuse")
    public Boolean some = true;
    public String text = "grolley";
    @FieldLabel(text = "Again Another")
    public final AnotherParam anotherParam = new AnotherParam();
  }
}
