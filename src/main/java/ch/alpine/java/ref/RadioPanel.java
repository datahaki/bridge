// code by jph, gjoel
package ch.alpine.java.ref;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/* package */ class RadioPanel extends FieldPanel {
  private final JScrollPane jScrollPane;

  public RadioPanel(FieldWrap fieldWrap, Object[] objects, Object object) {
    super(fieldWrap);
    JPanel jPanel = new JPanel(new GridLayout(objects.length, 1));
    ButtonGroup buttonGroup = new ButtonGroup();
    for (Object v : objects) {
      JRadioButton jRadioButton = new JRadioButton(v.toString());
      jRadioButton.setSelected(v.equals(object));
      buttonGroup.add(jRadioButton);
      jRadioButton.addActionListener(e -> {
        System.out.println(e);
      });
      jPanel.add(jRadioButton);
    }
    jScrollPane = new JScrollPane(jPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPane.setPreferredSize(new Dimension(200, 200));
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jScrollPane;
  }

  @Override // from FieldPanel
  public void updateJComponent(Object value) {
    // spinnerLabel.setValue(value);
  }
}
