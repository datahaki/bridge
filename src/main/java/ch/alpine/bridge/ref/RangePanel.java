// code by jph, gjoel
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ch.alpine.bridge.lang.Unicode;
import ch.alpine.bridge.ref.ann.FieldClipInteger;
import ch.alpine.bridge.ref.ann.FieldClips;
import ch.alpine.bridge.ref.ann.FieldSlider;
import ch.alpine.bridge.swing.rs.RangeSlider;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

// TODO BRIDGE implement properly
/* package */ class RangePanel extends FieldPanel {
  private static final int RESOLUTION = 1000;
  private static final int TICKS_MAX = 20;
  // ---
  private final FieldClips fieldClips;
  private final int resolution;
  private final JLabel jLabel;
  private final RangeSlider rangeSlider;
  private final JComponent jComponent;
  private int index_min;
  private int index_max;

  /** @param fieldWrap
   * @param fieldClips
   * @param value
   * @param fieldSlider
   * @throws Exception if fieldClips defines an infinite range */
  public RangePanel(FieldWrap fieldWrap, FieldClips fieldClips, Object value, FieldSlider fieldSlider) {
    super(fieldWrap);
    this.fieldClips = fieldClips;
    // determine resolution
    resolution = Objects.nonNull(fieldWrap.getField().getAnnotation(FieldClipInteger.class))//
        ? fieldClips.getIntegerResolution()
        : RESOLUTION;
    jLabel = new JLabel("", SwingConstants.CENTER);
    if (Objects.nonNull(value)) {
      Clip clip = (Clip) value;
      setLabel(clip);
      index_min = fieldClips.indexOf(clip.min(), resolution);
      index_max = fieldClips.indexOf(clip.max(), resolution);
    }
    rangeSlider = new RangeSlider(0, resolution, () -> {
      // System.out.println("here");
    });
    rangeSlider.setValue(index_min);
    rangeSlider.setUpperValue(index_max);
    rangeSlider.setOpaque(false); // for use in toolbar
    rangeSlider.setPaintTicks(resolution <= TICKS_MAX);
    rangeSlider.setMinorTickSpacing(1);
    rangeSlider.addChangeListener(changeEvent -> {
      int value_min = rangeSlider.getValue();
      int value_max = rangeSlider.getUpperValue();
      if (value_min != index_min || value_max != index_max) {
        // prevent notifications if slider value hasn't changed
        Scalar min = fieldClips.interp(RationalScalar.of(index_min = value_min, resolution));
        Scalar max = fieldClips.interp(RationalScalar.of(index_max = value_max, resolution));
        Clip clip = Clips.interval(min, max);
        setLabel(clip);
        notifyListeners(fieldWrap.toString(clip));
      }
    });
    if (fieldSlider.showRange() || fieldSlider.showValue()) {
      JPanel jPanel = new JPanel(new BorderLayout());
      if (fieldSlider.showRange()) {
        jPanel.add(new JLabel(Unicode.valueOf(fieldClips.min())), BorderLayout.WEST);
        jPanel.add(new JLabel(Unicode.valueOf(fieldClips.max())), BorderLayout.EAST);
      }
      if (fieldSlider.showValue())
        jPanel.add(jLabel, BorderLayout.NORTH);
      jPanel.add(rangeSlider, BorderLayout.CENTER);
      jComponent = jPanel;
    } else
      jComponent = rangeSlider;
  }

  private void setLabel(Clip clip) {
    jLabel.setText(Unicode.valueOf(clip.min()) + " \u2026 " + Unicode.valueOf(clip.max()));
  }

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jComponent;
  }

  @Override // from FieldPanel
  public void updateJComponent(Object value) {
    Clip clip = (Clip) value;
    index_min = fieldClips.indexOf(clip.min(), resolution);
    index_max = fieldClips.indexOf(clip.max(), resolution);
    /* Quote from JSlider:
     * "If the new value is different from the previous value, all change listeners are notified."
     * In case the value is not different from the previous value the function returns immediately
     * and no change listeners are notified. */
    rangeSlider.setValue(index_min);
    rangeSlider.setUpperValue(index_max);
  }
}
