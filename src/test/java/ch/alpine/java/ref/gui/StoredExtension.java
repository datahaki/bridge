// code by jph
package ch.alpine.java.ref.gui;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ch.alpine.java.ref.FieldClip;
import ch.alpine.java.ref.FieldExistingDirectory;
import ch.alpine.java.ref.FieldExistingFile;
import ch.alpine.java.ref.FieldIntegerQ;
import ch.alpine.java.ref.FieldLabel;
import ch.alpine.java.ref.NameString;
import ch.alpine.java.ref.ObjectProperties;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.mat.re.Pivots;
import ch.alpine.tensor.qty.Quantity;

public class StoredExtension {
  private static final File FILE = HomeDirectory.Downloads(StoredExtension.class.getSimpleName() + ".properties");
  public static final StoredExtension INSTANCE = //
      ObjectProperties.wrap(new StoredExtension()) //
          .tryLoad(FILE);
  @FieldLabel(text = "some")
  public String string = "abc";
  public Boolean status = true;
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
    JComponent jComponent = ConfigPanel.of(INSTANCE).getFieldsAndTextarea();
    // ---
    JFrame jFrame = new JFrame();
    // File root = GrzSettings.file("GuiExtension");
    // root.mkdirs();
    // WindowConfiguration.attach(jFrame, new File(root, "WindowConfiguration.properties"));
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setContentPane(jComponent);
    jFrame.setBounds(500, 200, 500, 700);
    jFrame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        ObjectProperties.wrap(INSTANCE).trySave(FILE);
      }
    });
    jFrame.setVisible(true);
  }
}