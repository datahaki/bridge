// code by jph
package ch.alpine.java.ref.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.java.ref.FieldClip;
import ch.alpine.java.ref.FieldExistingDirectory;
import ch.alpine.java.ref.FieldExistingFile;
import ch.alpine.java.ref.FieldFuse;
import ch.alpine.java.ref.FieldIntegerQ;
import ch.alpine.java.ref.FieldLabel;
import ch.alpine.java.ref.FieldSelection;
import ch.alpine.java.ref.FieldToolTip;
import ch.alpine.java.ref.NameString;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.mat.re.Pivots;
import ch.alpine.tensor.qty.Quantity;

public class GuiExtension {
  public String string = "abc";
  public Boolean status = true;
  @FieldLabel(text = "Big Fuse")
  @FieldFuse(text = "press to restart")
  public Boolean fuse = false;
  public Pivots pivots = Pivots.ARGMAX_ABS;
  @FieldExistingDirectory
  public File folder = HomeDirectory.file();
  @FieldExistingFile
  public File file = HomeDirectory.file();
  public File anyFile = HomeDirectory.file();
  public Tensor tensor = Tensors.fromString("{1, 2}");
  @FieldClip(min = "1[m*s^-1]", max = "10[m*s^-1]")
  public Scalar scalar = Quantity.of(3, "m*s^-1");
  public Scalar scalar1 = Quantity.of(3, "m*s^-1");
  public Scalar scalar2 = Quantity.of(3, "m*s^-1");
  @FieldClip(min = "1000[W]", max = "10000[Wa]")
  // TODO handle error in default value
  public Scalar quantity = Quantity.of(3, "kW");
  // @FieldSubdivide(start = "-4[m*s^-1]", end = "10[m*s^-1]", intervals = 7)
  @FieldToolTip(text = "asd")
  @FieldSelection(list = "{1[%], 2[%], 3[%]}")
  public Scalar subdiv = Quantity.of(3, "kW");
  @FieldIntegerQ
  @FieldClip(min = "10", max = "20")
  public Scalar integer = RealScalar.of(12);
  public Color color = Color.RED;
  public NameString nameString = NameString.SECOND;
  @FieldClip(min = "-10[L*min^-1]", max = "20[L*min^-1]")
  public Scalar volumeFlow = Quantity.of(20, "L*min^-1");
  // ---
  Scalar packsc = Quantity.of(3, "m*s^-1");

  public static String[] stringValues() {
    System.out.println("generating values");
    return new String[] { "a", "b", "c" };
  }

  public static void main(String[] args) {
    GuiExtension guiExtension = new GuiExtension();
    JComponent jComponent = ConfigPanel.of(guiExtension).getFieldsAndTextarea();
    // ---
    JFrame jFrame = new JFrame();
    // File root = GrzSettings.file("GuiExtension");
    // root.mkdirs();
    // WindowConfiguration.attach(jFrame, new File(root, "WindowConfiguration.properties"));
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add("Center", jComponent);
    {
      JButton jButton = new JButton("reset fuse");
      jButton.addActionListener(l -> {
        guiExtension.fuse = false;
      });
      jPanel.add("South", jButton);
    }
    jFrame.setContentPane(jPanel);
    jFrame.setBounds(500, 200, 500, 700);
    jFrame.setVisible(true);
  }
}
