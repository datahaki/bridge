// code by jph, gjoel
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.alpine.bridge.lang.Unicode;
import ch.alpine.bridge.ref.ann.FieldClips;
import ch.alpine.bridge.ref.ann.FieldInteger;
import ch.alpine.bridge.ref.ann.FieldSlider;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;

/* package */ class SliderPanel extends FieldPanel {
  private static final int RESOLUTION = 1000;
  private static final int TICKS_MAX = 20;
  // ---
  private final FieldClips fieldClips;
  private final int resolution;
  private final JLabel jLabel;
  private final JSlider jSlider;
  private final JComponent jComponent;
  private int index;

  /** @param fieldWrap
   * @param fieldClips
   * @param value
   * @param fieldSlider
   * @throws Exception if fieldClips defines an infinite range */
  public SliderPanel(FieldWrap fieldWrap, FieldClips fieldClips, Object value, FieldSlider fieldSlider) {
    super(fieldWrap);
    this.fieldClips = fieldClips;
    // determine resolution
    resolution = Objects.nonNull(fieldWrap.getField().getAnnotation(FieldInteger.class))//
        ? fieldClips.getIntegerResolution()
        : RESOLUTION;
    if (Objects.isNull(value))
      jLabel = null;
    else {
      Scalar scalar = (Scalar) value;
      jLabel = fieldSlider.showValue() //
          ? new JLabel(Unicode.valueOf(scalar), SwingConstants.CENTER)
          : null;
      index = fieldClips.indexOf(scalar, resolution);
    }
    jSlider = new JSlider(0, resolution, index);
    {
      FieldsEditorParam.setHeight(FieldsEditorParam.GLOBAL.sliderHeight, jSlider);
    }
    jSlider.setOpaque(false); // for use in toolbar
    jSlider.setPaintTicks(resolution <= TICKS_MAX);
    jSlider.setMinorTickSpacing(1);
    jSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent changeEvent) {
        int value = jSlider.getValue();
        if (index != value) { // prevent notifications if slider value hasn't changed
          Scalar scalar = fieldClips.interp(RationalScalar.of(index = value, resolution));
          if (Objects.nonNull(jLabel))
            jLabel.setText(Unicode.valueOf(scalar));
          notifyListeners(scalar.toString());
        }
      }
    });
    JComponent jComponent = jSlider;
    if (fieldSlider.showRange())
      jComponent = addRangeLabels(jComponent);
    if (Objects.nonNull(jLabel))
      jComponent = addValueLabel(jComponent);
    this.jComponent = jComponent;
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jComponent;
  }

  private JPanel addRangeLabels(JComponent jComponent) {
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(new JLabel(Unicode.valueOf(fieldClips.min())), BorderLayout.WEST);
    jPanel.add(jComponent, BorderLayout.CENTER);
    jPanel.add(new JLabel(Unicode.valueOf(fieldClips.max())), BorderLayout.EAST);
    return jPanel;
  }

  private JPanel addValueLabel(JComponent jComponent) {
    JPanel jPanel = new JPanel(new GridLayout(2, 1));
    jPanel.add(jLabel);
    jPanel.add(jComponent);
    return jPanel;
  }

  @Override // from FieldPanel
  public void updateJComponent(Object value) {
    index = fieldClips.indexOf((Scalar) value, resolution);
    /* Quote from JSlider:
     * "If the new value is different from the previous value, all change listeners are notified."
     * In case the value is not different from the previous value the function returns immediately
     * and no change listeners are notified. */
    jSlider.setValue(index);
  }
}
