// code by jph
package ch.alpine.bridge.swing;

import javax.swing.ImageIcon;

import ch.alpine.bridge.ref.FieldsEditorKey;
import ch.alpine.bridge.ref.FieldsEditorManager;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.io.ResourceData;

/** Careful: the enum entries are used as part of resource locations
 * 
 * {@link PanelFieldsEditor} */
public enum CheckBoxIcons {
  /** java */
  DEFAULT, //
  METRO, //
  BALLIT, //
  BALLOT, //
  GENTLEFACE, //
  ;

  /** @param n pixel width and height */
  public void init(int n) {
    if (equals(DEFAULT)) {
      FieldsEditorManager.set(FieldsEditorKey.ICON_CHECKBOX_0, null);
      FieldsEditorManager.set(FieldsEditorKey.ICON_CHECKBOX_1, null);
    } else {
      String folder = "/ch/alpine/bridge/ref/checkbox/" + name().toLowerCase();
      FieldsEditorManager.set(FieldsEditorKey.ICON_CHECKBOX_0, new ImageIcon(ImageResize.of(ResourceData.bufferedImage(folder + "/0.png"), n, n)));
      FieldsEditorManager.set(FieldsEditorKey.ICON_CHECKBOX_1, new ImageIcon(ImageResize.of(ResourceData.bufferedImage(folder + "/1.png"), n, n)));
    }
  }
}
