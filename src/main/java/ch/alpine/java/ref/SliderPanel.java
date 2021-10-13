// code by jph
package ch.alpine.java.ref;

import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.alpine.java.ref.ann.FieldClip;
import ch.alpine.java.ref.ann.FieldClips;
import ch.alpine.java.ref.ann.FieldInteger;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.sca.Clip;

/* package */ class SliderPanel extends FieldPanel {
  private static final int RESOLUTION = 1000;
  private static final int TICKS_MAX = 20;
  // ---
  private final Clip clip;
  private final int resolution;
  private final JSlider jSlider;
  private final ChangeListener changeListener = new ChangeListener() {
    @Override
    public void stateChanged(ChangeEvent changeEvent) {
      int value = jSlider.getValue();
      if (index != value) // prevent notifications if slider value hasn't changed
        notifyListeners(interp(index = value).toString());
    }

    private Scalar interp(int count) {
      return clip.min().multiply(RationalScalar.of(resolution - count, resolution)) //
          .add(clip.max().multiply(RationalScalar.of(count, resolution)));
    }
  };
  private int index;

  public SliderPanel(FieldWrap fieldWrap, FieldClip fieldClip, Object value) {
    super(fieldWrap);
    clip = FieldClips.of(fieldClip);
    if (Objects.nonNull(fieldWrap.getField().getAnnotation(FieldInteger.class))) {
      int max = Scalars.intValueExact(clip.max());
      int min = Scalars.intValueExact(clip.min());
      resolution = max - min;
    } else
      resolution = RESOLUTION;
    index = Objects.isNull(value) //
        ? 0
        : clip.rescale((Scalar) value).multiply(RealScalar.of(resolution)).number().intValue();
    jSlider = new JSlider(0, resolution, index);
    jSlider.setOpaque(false);
    jSlider.setPaintTicks(resolution <= TICKS_MAX);
    jSlider.setMinorTickSpacing(1);
    jSlider.addChangeListener(changeListener);
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jSlider;
  }
}
