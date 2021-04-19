// code by jph
package ch.ethz.idsc.tensor.ref.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ext.HomeDirectory;
import ch.ethz.idsc.tensor.mat.re.Pivots;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.ref.FieldClip;
import ch.ethz.idsc.tensor.ref.FieldExistingDirectory;
import ch.ethz.idsc.tensor.ref.FieldExistingFile;
import ch.ethz.idsc.tensor.ref.FieldFuse;
import ch.ethz.idsc.tensor.ref.FieldIntegerQ;
import ch.ethz.idsc.tensor.ref.FieldLabel;
import ch.ethz.idsc.tensor.ref.NameString;

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
  @FieldClip(min = "1000[W]", max = "10000[Wa]")
  // TODO handle error in default value
  public Scalar quantity = Quantity.of(3, "kW");
  @FieldIntegerQ
  @FieldClip(min = "10", max = "20")
  public Scalar integer = RealScalar.of(12);
  public Color color = Color.RED;
  public NameString nameString = NameString.SECOND;
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
