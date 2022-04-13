// code by jph, gjoel
package ch.alpine.java.ref;

import java.awt.BorderLayout;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.alpine.java.lang.PrettyUnit;
import ch.alpine.java.ref.ann.FieldClip;
import ch.alpine.java.ref.ann.FieldClips;
import ch.alpine.java.ref.ann.FieldInteger;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.qty.UnitSystem;
import ch.alpine.tensor.sca.Clip;

/* package */ class SliderPanel extends FieldPanel {
  private static final int RESOLUTION = 1000;
  private static final int TICKS_MAX = 20;
  // ---
  private final boolean showRange;
  private final Clip clip;
  private final int resolution;
  private final ScalarUnaryOperator scalarUnaryOperator;
  private final JSlider jSlider;
  private int index;

  /** @param fieldWrap
   * @param fieldClip non-null
   * @param value */
  public SliderPanel(FieldWrap fieldWrap, FieldClip fieldClip, Object value, boolean showRange) {
    super(fieldWrap);
    this.showRange = showRange;
    clip = FieldClips.of(fieldClip); // si-units
    if (Objects.nonNull(fieldWrap.getField().getAnnotation(FieldInteger.class))) {
      int max = Scalars.intValueExact(clip.max());
      int min = Scalars.intValueExact(clip.min());
      resolution = max - min;
    } else
      resolution = RESOLUTION;
    if (Objects.isNull(value)) {
      scalarUnaryOperator = i -> i;
      index = 0; //
    } else {
      Scalar scalar = (Scalar) value;
      scalarUnaryOperator = UnitConvert.SI().to(QuantityUnit.of(scalar));
      index = indexOf(scalar);
    }
    jSlider = new JSlider(0, resolution, index);
    {
      FieldsEditorManager.establish(FieldsEditorKey.INT_SLIDER_HEIGHT, jSlider);
    }
    jSlider.setOpaque(false);
    jSlider.setPaintTicks(resolution <= TICKS_MAX);
    jSlider.setMinorTickSpacing(1);
    jSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent changeEvent) {
        int value = jSlider.getValue();
        if (index != value) // prevent notifications if slider value hasn't changed
          notifyListeners(interp(index = value).toString());
      }

      private Scalar interp(int count) {
        return scalarUnaryOperator.apply( //
            clip.min().multiply(RationalScalar.of(resolution - count, resolution)) //
                .add(clip.max().multiply(RationalScalar.of(count, resolution))));
      }
    });
  }

  private int indexOf(Scalar scalar) {
    return clip.rescale(UnitSystem.SI().apply(scalar)).multiply(RealScalar.of(resolution)).number().intValue();
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    JComponent jComponent = jSlider;
    if (showRange)
      jComponent = addRangeLabels(jComponent);
    return jComponent;
  }

  private JPanel addRangeLabels(JComponent jComponent) {
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(new JLabel(PrettyUnit.of(clip.min())), BorderLayout.WEST);
    jPanel.add(jComponent, BorderLayout.CENTER);
    jPanel.add(new JLabel(PrettyUnit.of(clip.max())), BorderLayout.EAST);
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
