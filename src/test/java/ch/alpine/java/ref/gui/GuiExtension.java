// code by jph
package ch.alpine.java.ref.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.java.ref.Container.NestedEnum;
import ch.alpine.java.ref.NameString;
import ch.alpine.java.ref.ann.FieldClip;
import ch.alpine.java.ref.ann.FieldExistingDirectory;
import ch.alpine.java.ref.ann.FieldExistingFile;
import ch.alpine.java.ref.ann.FieldFuse;
import ch.alpine.java.ref.ann.FieldInteger;
import ch.alpine.java.ref.ann.FieldLabel;
import ch.alpine.java.ref.ann.FieldSelection;
import ch.alpine.java.ref.ann.FieldSlider;
import ch.alpine.java.ref.ann.ReflectionMarker;
import ch.alpine.javax.swing.LookAndFeels;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.mat.re.Pivots;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.qty.Quantity;

@ReflectionMarker
public class GuiExtension {
  public Scalar[] scalars = { Pi.VALUE, RealScalar.ONE };
  public String string = "abc";
  @FieldSelection(array = { "/dev/ttyS0", "/dev/ttyS1", "/dev/ttyS2", "/dev/ttyS3", "/dev/ttyUSB0", "/dev/ttyUSB1" })
  public String selectable = "/dev/ttyS0";
  public Boolean status = true;
  @FieldLabel(text = "Big Fuse")
  @FieldFuse(text = "press to restart")
  public Boolean fuse = false;
  @FieldFuse
  public Boolean defaultFuse = false;
  public Pivots pivots = Pivots.ARGMAX_ABS;
  @FieldExistingDirectory
  public File folder = HomeDirectory.file();
  @FieldExistingFile
  public File file = HomeDirectory.file();
  public File anyFile = HomeDirectory.file();
  @FieldSelection(array = { "1[%]", "2[%]", "3[%]" })
  public Tensor tensor = Tensors.fromString("{1, 2}");
  @FieldSlider
  @FieldClip(min = "1[m*s^-1]", max = "10[m*s^-1]")
  public Scalar scalar = Quantity.of(3, "m*s^-1");
  @FieldSlider
  @FieldClip(min = "0[%]", max = "100[%]")
  public Scalar percent = Quantity.of(10, "%");
  @FieldSlider
  @FieldInteger
  @FieldClip(min = "1", max = "10")
  public Scalar scalarInt = Quantity.of(10, "");
  public Scalar scalar1 = Quantity.of(3, "m*s^-1");
  public Scalar scalar2 = Quantity.of(3, "m*s^-1");
  @FieldClip(min = "1000[W]", max = "10000[W]")
  public Scalar quantity = Quantity.of(3, "kW");
  // @FieldSubdivide(start = "-4[m*s^-1]", end = "10[m*s^-1]", intervals = 7)
  // @FieldToolTip(text = "asd")
  @FieldClip(min = "0", max = "20")
  @FieldSelection(array = { "1[W]", "2[%]", "3[]" })
  public Scalar subdiv = Quantity.of(3, "");
  @FieldInteger
  @FieldClip(min = "10", max = "20")
  public Scalar integer = RealScalar.of(12);
  public Color color = Color.RED;
  public NameString nameString = NameString.SECOND;
  public NestedEnum nestedEnum = NestedEnum.SECOND;
  @FieldClip(min = "-10[L*min^-1]", max = "20[L*min^-1]")
  public Scalar volumeFlow = Quantity.of(20, "L*min^-1");
  // ---
  Scalar packsc = Quantity.of(3, "m*s^-1");

  public static String[] stringValues() {
    System.out.println("generating values");
    return new String[] { "a", "b", "c" };
  }

  public static void main(String[] args) {
    LookAndFeels.INTELLI_J.updateUI();
    GuiExtension guiExtension = new GuiExtension();
    PanelFieldsEditor fieldsEditor = new PanelFieldsEditor(guiExtension);
    fieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    TestHelper testHelper = new TestHelper(fieldsEditor, guiExtension);
    // ---
    JFrame jFrame = new JFrame();
    // File root = GrzSettings.file("GuiExtension");
    // root.mkdirs();
    // WindowConfiguration.attach(jFrame, new File(root, "WindowConfiguration.properties"));
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add("Center", testHelper.jPanel);
    {
      JButton jButton = new JButton("reset fuse");
      jButton.addActionListener(l -> {
        guiExtension.fuse = false;
        testHelper.runnable.run();
        // fieldsEditor.list().forEach(fp->fp.notifyListeners(""));
      });
      jPanel.add("South", jButton);
    }
    jFrame.setContentPane(jPanel);
    jFrame.setBounds(500, 200, 500, 700);
    jFrame.setVisible(true);
  }
}
