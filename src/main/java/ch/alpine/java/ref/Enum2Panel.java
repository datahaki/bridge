// code by jph, gjoel
package ch.alpine.java.ref;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/* package */ class Enum2Panel extends FieldPanel {
  private final JScrollPane jScrollPane;

  public Enum2Panel(FieldWrap fieldWrap, Object[] objects, Object object) {
    super(fieldWrap);
    JList<Object> jList = new JList<>(objects);
    jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jList.setFont(FieldsEditorManager.getFont(FieldsEditorKey.FONT_TEXTFIELD));
    // spinnerLabel.updatePreferredSize();
    // TODO scrollbar
    // TODO listener similar to slider (only notify when value effectively changes)
    jList.setSelectedValue(object, true);
    jList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        System.out.println(e);
      }
    });
    jScrollPane = new JScrollPane(jList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPane.setPreferredSize(new Dimension(200, 200));
    // SpinnerListener(value -> notifyListeners(fieldWrap.toString(value)));
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
