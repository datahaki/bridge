// code by clruch
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/* package */ class RangeSliderDemo extends JPanel {
  private final JLabel rangeSliderValueH1 = new JLabel();
  private final JLabel rangeSliderValueH2 = new JLabel();
  private final RangeSlider rangeSliderH = new RangeSlider(0, 100, () -> {
    // ---
  });
  private final JLabel rangeSliderValueV1 = new JLabel();
  private final JLabel rangeSliderValueV2 = new JLabel();
  private final RangeSlider rangeSliderV = new RangeSlider(0, 100, () -> {
    // ---
  });

  public RangeSliderDemo() {
    setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
    setLayout(new BorderLayout());
    JLabel rangeSliderLabelH1 = new JLabel("Lower value: ");
    JLabel rangeSliderLabelH2 = new JLabel("Upper value: ");
    JLabel rangeSliderLabelV1 = new JLabel("Lower value: ");
    JLabel rangeSliderLabelV2 = new JLabel("Upper value: ");
    rangeSliderH.setMinimum(0);
    rangeSliderH.setMaximum(10);
    // Add listener to update display.
    rangeSliderH.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        RangeSlider slider = (RangeSlider) e.getSource();
        rangeSliderValueH1.setText(String.valueOf(slider.getValue()));
        rangeSliderValueH2.setText(String.valueOf(slider.getUpperValue()));
      }
    });
    rangeSliderV.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        RangeSlider slider = (RangeSlider) e.getSource();
        rangeSliderValueV1.setText(String.valueOf(slider.getValue()));
        rangeSliderValueV2.setText(String.valueOf(slider.getUpperValue()));
      }
    });
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setFloatable(false);
      jToolBar.add(rangeSliderLabelH1);
      jToolBar.add(rangeSliderValueH1);
      jToolBar.addSeparator();
      jToolBar.add(rangeSliderLabelH2);
      jToolBar.add(rangeSliderValueH2);
      add(jToolBar, BorderLayout.NORTH);
    }
    add(rangeSliderH, BorderLayout.CENTER);
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setFloatable(false);
      jToolBar.add(rangeSliderLabelV1);
      jToolBar.add(rangeSliderValueV1);
      jToolBar.addSeparator();
      jToolBar.add(rangeSliderLabelV2);
      jToolBar.add(rangeSliderValueV2);
      add(jToolBar, BorderLayout.SOUTH);
    }
    rangeSliderV.setValue(0);
    rangeSliderV.setOrientation(SwingConstants.VERTICAL);
    add(rangeSliderV, BorderLayout.WEST);
    add(new JSlider(SwingConstants.VERTICAL), BorderLayout.EAST);
  }

  public JFrame display() {
    // Initialize values.
    rangeSliderH.setValue(3);
    rangeSliderH.setUpperValue(7);
    // Initialize value display.
    rangeSliderValueH1.setText(String.valueOf(rangeSliderH.getValue()));
    rangeSliderValueH2.setText(String.valueOf(rangeSliderH.getUpperValue()));
    // Create window frame.
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setTitle("Range Slider Demo");
    // Set window content and validate.
    jFrame.getContentPane().setLayout(new BorderLayout());
    jFrame.getContentPane().add(this, BorderLayout.CENTER);
    // jFrame.pack();
    // Set window location and display.
    jFrame.setBounds(100, 100, 500, 300);
    jFrame.setLocationRelativeTo(null);
    jFrame.setVisible(true);
    return jFrame;
  }

  public static void main(String[] args) {
    RangeSliderDemo rangeSliderDemo = new RangeSliderDemo();
    rangeSliderDemo.display();
  }
}
