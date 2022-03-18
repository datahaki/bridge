// code by jph
package ch.alpine.java.ref;

import java.awt.Color;
import java.io.File;

import ch.alpine.java.ref.ann.FieldFuse;
import ch.alpine.java.ref.ann.FieldLabel;
import ch.alpine.java.ref.ann.FieldList;
import ch.alpine.java.ref.ann.FieldListType;
import ch.alpine.java.ref.ann.FieldSelectionArray;
import ch.alpine.java.ref.ann.ReflectionMarker;
import ch.alpine.javax.swing.LookAndFeels;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.mat.re.Pivots;

@ReflectionMarker
public class SimpleParam extends BaseParam {
  public LookAndFeels lookAndFeels = LookAndFeels.DEFAULT;
  @SuppressWarnings("unused")
  private final int ignore_int = 2;
  @SuppressWarnings("unused")
  private final Integer ignore_Integer = 2;
  @SuppressWarnings("unused")
  private final String ignore_final_String = "asd";
  @FieldSelectionArray(value = { "a", "b", "c" })
  public String string = "abc";
  @FieldLabel("Emergency Off")
  public Boolean flag = false;
  @FieldLabel("Choose Pivot")
  @FieldList(FieldListType.RADIO)
  public Pivots pivot = Pivots.ARGMAX_ABS;
  @FieldLabel("Color Gradients")
  @FieldList
  public ColorDataGradients cdg = ColorDataGradients.DEEP_SEA;
  // public Scalar[] scalars = { Pi.VALUE, RealScalar.ZERO, ComplexScalar.I };
  @FieldLabel("Another Parameter ASDGHJ")
  public final AnotherParam anotherParam = new AnotherParam();
  @FieldLabel("Nested %d")
  public final NestedParam[] nestedParams = { new NestedParam(), new NestedParam() };

  public static class AnotherParam {
    public File file = HomeDirectory.file();
    public Color color = Color.RED;
  }

  public static class NestedParam extends BaseParam {
    @FieldFuse("fuse")
    public Boolean some = true;
    public String text = "grolley";
    @FieldLabel("Again Another")
    public final AnotherParam anotherParam = new AnotherParam();
  }
}
