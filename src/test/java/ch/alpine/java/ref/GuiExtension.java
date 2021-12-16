// code by jph
package ch.alpine.java.ref;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import ch.alpine.java.ref.Container.NestedEnum;
import ch.alpine.java.ref.ann.FieldClip;
import ch.alpine.java.ref.ann.FieldExistingDirectory;
import ch.alpine.java.ref.ann.FieldExistingFile;
import ch.alpine.java.ref.ann.FieldFuse;
import ch.alpine.java.ref.ann.FieldLabel;
import ch.alpine.java.ref.ann.FieldSelectionArray;
import ch.alpine.java.ref.ann.ReflectionMarker;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.mat.re.Pivots;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

@ReflectionMarker
public class GuiExtension {
  public Scalar[] scalars = { Pi.VALUE, RealScalar.ONE };
  public String string = "abc";
  @FieldSelectionArray(values = { "/dev/ttyS0", "/dev/ttyS1", "/dev/ttyS2", "/dev/ttyS3", "/dev/ttyUSB0", "/dev/ttyUSB1" })
  public String selectable = "/dev/ttyS0";
  public Boolean status = true;
  @FieldLabel(text = "Big Fuse")
  @FieldFuse(text = "press to restart")
  public Boolean fuse = false;
  @FieldSelectionArray(values = { "{0, 3}", "{10, 11}" })
  public Clip clip = Clips.absolute(3);
  @FieldFuse
  public Boolean defaultFuse = false;
  public Pivots pivots = Pivots.ARGMAX_ABS;
  public Boolean status2 = true;
  public Pivots pivots2 = Pivots.ARGMAX_ABS;
  @FieldExistingDirectory
  public File folder = HomeDirectory.file();
  @FieldExistingFile
  public File file = HomeDirectory.file();
  public File anyFile = HomeDirectory.file();
  @FieldSelectionArray(values = { "1[%]", "2[%]", "3[%]" })
  public Tensor tensor = Tensors.fromString("{1, 2}");
  public final ScalarUnion[] scalarUnion = { new ScalarUnion() };
  public Color foreground = new Color(100, 200, 150, 128);
  public Color background = new Color(200, 100, 150, 128);
  public NameString nameString = NameString.SECOND;
  public NestedEnum nestedEnum = NestedEnum.SECOND;
  @FieldClip(min = "-10[L*min^-1]", max = "20[L*min^-1]")
  public Scalar volumeFlow = Quantity.of(20, "L*min^-1");
  // ---
  Scalar packsc = Quantity.of(3, "m*s^-1");

  public List<String> getStrings() {
    return Arrays.asList("a", "b", string, pivots.toString());
  }

  public List<String> getStringsThrows() {
    throw new RuntimeException();
  }
}
