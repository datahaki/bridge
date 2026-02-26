// code by clruch
// adapted by jph
package ch.alpine.bridge.swing.rs;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ch.alpine.bridge.pro.WindowProvider;

class RangeSliderDemo implements WindowProvider {
  private final JPanel jPanel = new JPanel(new BorderLayout());
  private final JLabel rangeSliderValueH1 = new JLabel();
  private final JLabel rangeSliderValueH2 = new JLabel();
  private final RangeSlider rangeSliderH = new RangeSlider(0, 100, () -> {
  });
  private final JLabel rangeSliderValueV1 = new JLabel();
  private final JLabel rangeSliderValueV2 = new JLabel();
  private final RangeSlider rangeSliderV = new RangeSlider(0, 100, () -> {
  });

  public RangeSliderDemo() {
    jPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
    JLabel rangeSliderLabelH1 = new JLabel("Lower value: ");
    JLabel rangeSliderLabelH2 = new JLabel("Upper value: ");
    JLabel rangeSliderLabelV1 = new JLabel("Lower value: ");
    JLabel rangeSliderLabelV2 = new JLabel("Upper value: ");
    rangeSliderH.setMinimum(0);
    rangeSliderH.setMaximum(10);
    // Add listener to update display.
    rangeSliderH.addChangeListener(e -> {
      RangeSlider slider = (RangeSlider) e.getSource();
      rangeSliderValueH1.setText(String.valueOf(slider.getValue()));
      rangeSliderValueH2.setText(String.valueOf(slider.getUpperValue()));
    });
    rangeSliderV.addChangeListener(e -> {
      RangeSlider slider = (RangeSlider) e.getSource();
      rangeSliderValueV1.setText(String.valueOf(slider.getValue()));
      rangeSliderValueV2.setText(String.valueOf(slider.getUpperValue()));
    });
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setFloatable(false);
      jToolBar.add(rangeSliderLabelH1);
      jToolBar.add(rangeSliderValueH1);
      jToolBar.addSeparator();
      jToolBar.add(rangeSliderLabelH2);
      jToolBar.add(rangeSliderValueH2);
      jPanel.add(jToolBar, BorderLayout.NORTH);
    }
    jPanel.add(rangeSliderH, BorderLayout.CENTER);
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setFloatable(false);
      jToolBar.add(rangeSliderLabelV1);
      jToolBar.add(rangeSliderValueV1);
      jToolBar.addSeparator();
      jToolBar.add(rangeSliderLabelV2);
      jToolBar.add(rangeSliderValueV2);
      jPanel.add(jToolBar, BorderLayout.SOUTH);
    }
    rangeSliderV.setValue(0);
    rangeSliderV.setOrientation(SwingConstants.VERTICAL);
    jPanel.add(rangeSliderV, BorderLayout.WEST);
    jPanel.add(new JSlider(SwingConstants.VERTICAL), BorderLayout.EAST);
  }

  @Override
  public JFrame getWindow() {
    // Initialize values.
    rangeSliderH.setValue(3);
    rangeSliderH.setUpperValue(7);
    // Initialize value display.
    rangeSliderValueH1.setText(String.valueOf(rangeSliderH.getValue()));
    rangeSliderValueH2.setText(String.valueOf(rangeSliderH.getUpperValue()));
    // Create window frame.
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setContentPane(jPanel);
    return jFrame;
  }

  static void main() {
    new RangeSliderDemo().runStandalone();
  }
}
