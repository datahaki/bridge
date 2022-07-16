// code by jph
package ch.alpine.bridge.swing;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.io.ResourceData;

/** Careful: the enum entries are used as part of resource locations
 * 
 * {@link PanelFieldsEditor} */
public enum CheckBoxIcon {
  METRO,
  BALLIT,
  BALLOT,
  GENTLEFACE,
  LEDGREEN;

  /** @param n pixel width and height */
  public Icon create(int n, boolean selected) {
    String string = String.format("/ch/alpine/bridge/ref/checkbox/%s/%d.png", name().toLowerCase(), selected ? 1 : 0);
    return new ImageIcon(ImageResize.of(ResourceData.bufferedImage(string), n, n));
  }
}
