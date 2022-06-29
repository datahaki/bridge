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
import ch.alpine.bridge.ref.ann.FieldInteger;
import ch.alpine.bridge.ref.ann.FieldSlider;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.itp.LinearInterpolation;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Round;

/* package */ class SliderPanel extends FieldPanel {
  private static final int RESOLUTION = 1000;
  private static final int TICKS_MAX = 20;
  // ---
  private final Clip clip;
  /** operator maps a scalar to a {@link Quantity} with unit as clip.min and clip.max */
  private final ScalarUnaryOperator scalarUnaryOperator;
  private final int resolution;
  private final JLabel jLabel;
  private final JSlider jSlider;
  private final JComponent jComponent;
  private int index;

  /** @param fieldWrap
   * @param clip
   * @param value
   * @param fieldSlider */
  public SliderPanel(FieldWrap fieldWrap, Clip clip, Object value, FieldSlider fieldSlider) {
    super(fieldWrap);
    this.clip = clip;
    scalarUnaryOperator = UnitConvert.SI().to(QuantityUnit.of(clip));
    // determine resolution
    if (Objects.nonNull(fieldWrap.getField().getAnnotation(FieldInteger.class))) {
      int max = Scalars.intValueExact(clip.max());
      int min = Scalars.intValueExact(clip.min());
      resolution = max - min;
    } else
      resolution = RESOLUTION;
    if (Objects.isNull(value))
      jLabel = null;
    else {
      Scalar scalar = (Scalar) value;
      jLabel = fieldSlider.showValue() //
          ? new JLabel(Unicode.valueOf(scalar), SwingConstants.CENTER)
          : null;
      index = indexOf(scalar);
    }
    jSlider = new JSlider(0, resolution, index);
    {
      FieldsEditorManager.establish(FieldsEditorKey.INT_SLIDER_HEIGHT, jSlider);
    }
    jSlider.setOpaque(false); // for use in toolbar
    jSlider.setPaintTicks(resolution <= TICKS_MAX);
    jSlider.setMinorTickSpacing(1);
    jSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent changeEvent) {
        int value = jSlider.getValue();
        if (index != value) { // prevent notifications if slider value hasn't changed
          Scalar scalar = interp(index = value);
          if (Objects.nonNull(jLabel))
            jLabel.setText(Unicode.valueOf(scalar));
          notifyListeners(scalar.toString());
        }
      }

      private Scalar interp(int count) {
        return scalarUnaryOperator.apply(LinearInterpolation.of(clip).At(RationalScalar.of(count, resolution)));
      }
    });
    JComponent jComponent = jSlider;
    if (fieldSlider.showRange())
      jComponent = addRangeLabels(jComponent);
    if (Objects.nonNull(jLabel))
      jComponent = addValueLabel(jComponent);
    this.jComponent = jComponent;
  }

  private int indexOf(Scalar scalar) {
    Scalar rescale = clip.rescale(scalarUnaryOperator.apply(scalar));
    return Round.FUNCTION.apply(rescale.multiply(RealScalar.of(resolution))).number().intValue();
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jComponent;
  }

  private JPanel addRangeLabels(JComponent jComponent) {
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(new JLabel(Unicode.valueOf(clip.min())), BorderLayout.WEST);
    jPanel.add(jComponent, BorderLayout.CENTER);
    jPanel.add(new JLabel(Unicode.valueOf(clip.max())), BorderLayout.EAST);
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
    index = indexOf((Scalar) value);
    /* Quote from JSlider:
     * "If the new value is different from the previous value, all change listeners are notified."
     * In case the value is not different from the previous value the function returns immediately
     * and no change listeners are notified. */
    jSlider.setValue(index);
  }
}
