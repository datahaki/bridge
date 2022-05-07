// code by jph, gjoel
package ch.alpine.bridge.ref;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/** based on JList */
/* package */ class ListPanel extends FieldPanel {
  public static final int MAX_HEIGHT = 160;
  public static final int SCROLL_THRESHOLD = 8;
  // ---
  private final JList<Object> jList;
  private final JComponent jComponent;

  public ListPanel(FieldWrap fieldWrap, Object[] objects, Object object) {
    super(fieldWrap);
    jList = new JList<>(objects);
    // jList.setFont(FieldsEditorManager.getFont(FieldsEditorKey.FONT_TEXTFIELD));
    jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jList.setSelectedValue(object, true);
    jList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent listSelectionEvent) {
        // jList.setSelectedValue(value, true); invokes this function but with valueIsAdjusting == false
        if (listSelectionEvent.getValueIsAdjusting()) {
          String string = fieldWrap.toString(jList.getSelectedValue());
          notifyListeners(string);
        }
      }
    });
    jComponent = createJComponent();
  }

  private JComponent createJComponent() {
    if (SCROLL_THRESHOLD < jList.getModel().getSize()) {
      JScrollPane jScrollPane = new JScrollPane(jList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      Dimension dimension = jList.getPreferredSize();
      dimension.height = MAX_HEIGHT;
      jScrollPane.setPreferredSize(dimension);
      return jScrollPane;
    }
    return jList;
  }

  @Override
  public JComponent getJComponent() {
    return jComponent;
  }

  @Override // from FieldPanel
  public void updateJComponent(Object value) {
    jList.setSelectedValue(value, true);
  }
}
