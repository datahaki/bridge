// code by jph, gjoel
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.alpine.bridge.lang.Unicode;
import ch.alpine.bridge.ref.ann.FieldClips;
import ch.alpine.bridge.ref.ann.FieldInteger;
import ch.alpine.bridge.ref.ann.FieldSlider;
import ch.alpine.bridge.swing.rs.RangeSlider;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.Clip;

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
  private int index;

  /** @param fieldWrap
   * @param fieldClips
   * @param value
   * @param fieldSlider
   * @throws Exception if fieldClips defines an infinite range */
  public RangePanel(FieldWrap fieldWrap, FieldClips fieldClips, Object value, FieldSlider fieldSlider) {
    super(fieldWrap);
    this.fieldClips = fieldClips;
    // determine resolution
    resolution = Objects.nonNull(fieldWrap.getField().getAnnotation(FieldInteger.class))//
        ? fieldClips.getIntegerResolution()
        : RESOLUTION;
    jLabel = new JLabel("", SwingConstants.CENTER);
    if (Objects.nonNull(value)) {
      Clip scalar = (Clip) value;
      // jLabel.setText(Unicode.valueOf(scalar));
      // index = fieldClips.indexOf(scalar, resolution);
    }
    rangeSlider = new RangeSlider(0, resolution, () -> {
      // System.out.println("here");
    });
    rangeSlider.setOpaque(false); // for use in toolbar
    rangeSlider.setPaintTicks(resolution <= TICKS_MAX);
    rangeSlider.setMinorTickSpacing(1);
    rangeSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent changeEvent) {
        int value = rangeSlider.getValue();
        // prevent notifications if slider value hasn't changed
        Scalar scalar = fieldClips.interp(RationalScalar.of(index = value, resolution));
        if (Objects.nonNull(jLabel))
          jLabel.setText(Unicode.valueOf(scalar));
        notifyListeners(scalar.toString());
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

  @Override // from FieldPanel
  public JComponent getJComponent() {
    return jComponent;
  }

  @Override // from FieldPanel
  public void updateJComponent(Object value) {
    index = fieldClips.indexOf((Scalar) value, resolution);
    /* Quote from JSlider:
     * "If the new value is different from the previous value, all change listeners are notified."
     * In case the value is not different from the previous value the function returns immediately
     * and no change listeners are notified. */
    rangeSlider.setValue(index);
  }
}
