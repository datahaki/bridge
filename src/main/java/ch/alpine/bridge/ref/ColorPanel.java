// code by jph
package ch.alpine.bridge.ref;

import java.awt.Color;
import java.awt.Component;
import java.util.Objects;

import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

/* package */ class ColorPanel extends DialogPanel {
  private static final Color FALLBACK = Color.WHITE;

  public ColorPanel(FieldWrap fieldWrap, Object value) {
    super(fieldWrap, value);
    if (Objects.nonNull(value))
      setButtonBackground(value);
  }

  @Override // from StringPanel
  public void updateJComponent(Object value) {
    super.updateJComponent(value);
    setButtonBackground(value);
  }

  private void setButtonBackground(Object value) {
    Color color = (Color) value;
    // background color modification does not work for all l&f, for instance GTK_PLUS
    jButton().setBackground(color);
  }

  @Override // from StringPanel
  protected boolean isEditable() {
    return false;
  }

  @Override // from DialogPanel
  protected JDialog createDialog(Component component, Object value) {
    Color fallback = Objects.isNull(value) ? FALLBACK : (Color) value;
    JColorChooser jColorChooser = new JColorChooser(fallback);
    jColorChooser.getSelectionModel().addChangeListener(changeEvent -> updateAndNotify(jColorChooser.getColor()));
    JDialog jDialog = JColorChooser.createDialog( //
        component, "color selection: " + fieldWrap().getField().getName(), //
        false, jColorChooser, //
        actionEvent -> { // ok listener
          dispose();
          updateAndNotify(jColorChooser.getColor());
        }, //
        actionEvent -> { // cancel listener
          dispose();
          updateAndNotify(fallback);
        });
    jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    return jDialog;
  }
}
