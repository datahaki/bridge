// code by gjoel
package ch.alpine.bridge.ref.ann;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/** interprets specification in {@link FieldFileExtension} to {@link FileFilter} */
public enum FieldFileExtensions {
  ;
  /** @param fieldFileExtension
   * @return {@link FileNameExtensionFilter} with description {@link FieldFileExtension#description()}
   * and extensions {@link FieldFileExtension#extensions()} */
  public static FileFilter of(FieldFileExtension fieldFileExtension) {
    return new FileNameExtensionFilter(fieldFileExtension.description(), fieldFileExtension.extensions());
  }
}
