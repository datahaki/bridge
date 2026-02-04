// code by jph
package sys.col;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.alpine.bridge.swing.LookAndFeels;
import ch.alpine.bridge.swing.SpinnerLabel;
import ch.alpine.tensor.img.ColorDataLists;

class PalettePreview implements ChangeListener {
  private static final int LENGTH = 16;
  // ---
  private final JFrame jFrame = new JFrame();
  private final SpinnerLabel<ColorDataLists> sl = SpinnerLabel.of(ColorDataLists.values());
  private final List<JLabel> jLabels = new ArrayList<>();
  private final JSlider jSliderS = new JSlider(0, 255, 255); // saturation
  private final JSlider jSliderV = new JSlider(0, 255, 255); // brightness

  public PalettePreview() {
    jFrame.setResizable(false);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setTitle(getClass().getSimpleName());
    // ---
    Container container = jFrame.getContentPane();
    final int rows = LENGTH + 3;
    container.setLayout(new GridLayout(rows, 1));
    {
      sl.setValue(ColorDataLists._097);
      sl.addSpinnerListener(_ -> stateChanged(null));
      container.add(sl);
    }
    {
      jSliderS.addChangeListener(this);
      jSliderS.setToolTipText("saturation");
      container.add(jSliderS);
    }
    {
      jSliderV.addChangeListener(this);
      jSliderV.setToolTipText("value");
      container.add(jSliderV);
    }
    for (int count = 0; count < LENGTH; ++count) {
      JLabel jLabel = new JLabel();
      jLabel.setOpaque(true);
      container.add(jLabel);
      jLabels.add(jLabel);
    }
    jFrame.setBounds( //
        200, 100, //
        280, rows * 28 + 40);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent changeEvent) {
    HuePalette huePalette = HuePalette.of(sl.getValue().cyclic());
    for (int count = 0; count < LENGTH; ++count) {
      Color color = huePalette.getColor(count, jSliderS.getValue() / 255., jSliderV.getValue() / 255., 1);
      HueFromColor hue = HueFromColor.of(color);
      jLabels.get(count).setText(hue.toFriendlyString() + " " + Math.round(hue.hue() * 360));
      jLabels.get(count).setBackground(color);
    }
  }

  static void main() {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    PalettePreview palettePreview = new PalettePreview();
    palettePreview.jFrame.setVisible(true);
  }
}
