// code by jph
package sys.gui;

import javax.swing.JOptionPane;

public enum ConfirmDialog {
  ;
  public static boolean typeYes(String doWhat) {
    String string = JOptionPane.showInputDialog(null, "type 'yes' to " + doWhat, "");
    return "yes".equals(string);
  }
}
