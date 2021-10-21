// code by jph
package ch.alpine.java.ref;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import ch.alpine.javax.swing.SpinnerLabel;
import ch.alpine.javax.swing.StandardMenu;

/* package */ class MenuPanel extends StringPanel {
  private final JButton jButton = new JButton("?");

  public MenuPanel(FieldWrap fieldWrap, Object object, String[] strings) {
    super(fieldWrap, object);
    SpinnerLabel.updatePreferredSize(jTextField, Arrays.asList(strings));
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        StandardMenu standardMenu = new StandardMenu() {
          @Override
          protected void design(JPopupMenu jPopupMenu) {
            for (String string : strings) {
              JMenuItem jMenuItem = new JMenuItem(string);
              jMenuItem.setFont(jTextField.getFont());
              jMenuItem.addActionListener(event -> {
                jTextField.setText(string);
                indicateGui();
                nofifyIfValid(string);
              });
              jPopupMenu.add(jMenuItem);
            }
          }
        };
        standardMenu.south(jButton);
      }
    });
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(BorderLayout.CENTER, jTextField);
    jPanel.add(BorderLayout.EAST, jButton);
    return jPanel;
  }
}
