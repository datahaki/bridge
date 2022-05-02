// code by jph, gjoel
package ch.alpine.bridge.ref;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/* package */ class RadioPanel extends FieldPanel {
  private final JPanel jPanel;
  private final Map<Object, JRadioButton> map = new HashMap<>();
  private String previous = null;

  public RadioPanel(FieldWrap fieldWrap, Object[] objects, Object object) {
    super(fieldWrap);
    jPanel = new JPanel(new GridLayout(objects.length, 1));
    previous = fieldWrap.toString(object);
    ButtonGroup buttonGroup = new ButtonGroup();
    for (Object value : objects) {
      JRadioButton jRadioButton = new JRadioButton(value.toString());
      jRadioButton.setSelected(value.equals(object));
      buttonGroup.add(jRadioButton);
      ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          if (jRadioButton.isSelected()) {
            String string = fieldWrap.toString(value);
            if (!string.equals(previous))
              notifyListeners(previous = string);
          }
        }
      };
      jRadioButton.addActionListener(actionListener);
      jPanel.add(jRadioButton);
      map.put(value, jRadioButton);
    }
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    if (ListPanel.SCROLL_THRESHOLD < map.size()) {
      JScrollPane jScrollPane = new JScrollPane(jPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      Dimension dimension = jScrollPane.getPreferredSize();
      dimension.height = ListPanel.MAX_HEIGHT;
      jScrollPane.setPreferredSize(dimension);
      return jScrollPane;
    }
    return jPanel;
  }

  @Override // from FieldPanel
  public void updateJComponent(Object value) {
    JRadioButton jRadioButton = map.get(value);
    if (Objects.nonNull(jRadioButton) && !jRadioButton.isSelected()) {
      jRadioButton.setSelected(true);
    }
  }
}
