// code by jph
package ch.alpine.java.ref;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import ch.alpine.java.awt.StandardMenu;
import ch.alpine.tensor.Tensor;

/* package */ class MenuPanel extends StringPanel {
  private final JButton jButton = new JButton("?");

  public MenuPanel(FieldWrap fieldWrap, Object object, Tensor tensor) {
    super(fieldWrap, object);
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        StandardMenu standardMenu = new StandardMenu() {
          @Override
          protected void design(JPopupMenu jPopupMenu) {
            for (Tensor x : tensor) {
              JMenuItem jMenuItem = new JMenuItem(x.toString());
              jMenuItem.setFont(jTextField.getFont());
              jMenuItem.addActionListener(l -> {
                // TODO this shows that API is not yet streamlined
                jTextField.setText(x.toString());
                indicateGui();
                nofifyIfValid(x.toString());
              });
              jPopupMenu.add(jMenuItem);
            }
          }
        };
        standardMenu.south(jButton);
      }
    });
  }

  @Override
  public JComponent getJComponent() {
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add("Center", jTextField);
    jPanel.add("East", jButton);
    return jPanel;
  }
}
