// code by clruch
package ch.alpine.bridge.swing.rs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JSlider;

import ch.alpine.tensor.ext.Integers;

public class RangeSlider extends JSlider {
  private boolean isPressed = false;
  public RangeSliderUI rangeSliderUI;

  /** Constructs a RangeSlider with the specified default minimum and maximum
   * values. */
  public RangeSlider(int min, int max, Runnable mouseReleaseUpdate) {
    super(min, max);
    initSlider(mouseReleaseUpdate);
  }

  /** Initializes the slider by setting default properties. */
  private void initSlider(Runnable mouseReleaseUpdate) {
    setOrientation(HORIZONTAL);
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        try {
          mouseReleaseUpdate.run();
          isPressed = false;
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {
        isPressed = true;
      }
    });
  }

  /** Overrides the superclass method to install the UI delegate to draw two
   * thumbs. */
  @Override
  public void updateUI() {
    // don-t initialize this in the constructor, otherwise no range slider
    // will show up...!
    rangeSliderUI = new RangeSliderUI(this);
    setUI(rangeSliderUI);
    // Update UI for slider labels. This must be called after updating the
    // UI of the slider. Refer to JSlider.updateUI().
    updateLabelUIs();
  }

  /** Returns the upper value in the range. */
  public int getUpperValue() {
    return getValue() + getExtent();
  }

  /** Sets the lower value in the range. */
  @Override
  public void setValue(int value) {
    int oldValue = getValue();
    if (oldValue == value) {
      return;
    }
    // Compute new value and extent to maintain upper value.
    int oldExtent = getExtent();
    int newValue = Integers.clip(getMinimum(), oldValue + oldExtent).applyAsInt(value);
    int newExtent = oldExtent + oldValue - newValue;
    // Set new value and extent, and fire a single change event.
    getModel().setRangeProperties(newValue, newExtent, getMinimum(), getMaximum(), getValueIsAdjusting());
  }

  /** Sets the upper value in the range. */
  public void setUpperValue(int value) {
    // Compute new extent.
    int lowerValue = getValue();
    int newExtent = Integers.clip(0, getMaximum() - lowerValue).applyAsInt(value - lowerValue);
    // Set extent to set upper value.
    setExtent(newExtent);
  }

  public void printValues() {
    System.out.println(getValue() + " - " + getUpperValue());
  }

  public boolean mousePressed() {
    return isPressed;
  }

  public Boolean lastDragging() {
    return rangeSliderUI.lastDragging();
  }
}
