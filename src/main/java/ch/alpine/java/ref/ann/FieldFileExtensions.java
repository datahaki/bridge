// code by gjoel
package ch.alpine.java.ref.ann;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/** interprets specification in {@link FieldFileExtension} to {@link FileFilter} */
public enum FieldFileExtensions {
  ;

  /** @param fileExtension
   * @return {@link FileNameExtensionFilter} with description {@link FieldFileExtension#description()}
   * and extensions {@link FieldFileExtension#extensions()} */
  public static FileFilter of(FieldFileExtension fileExtension) {
    return new FileNameExtensionFilter(fileExtension.description(), fileExtension.extensions());
  }
}
