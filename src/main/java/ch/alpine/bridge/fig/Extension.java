// code by jph
package ch.alpine.bridge.fig;

/** file extensions used by the tensor library
 * 
 * @see ImportHelper
 * @see ExportHelper */
/* package */ enum Extension {
  // TODO redundant to TensorExtension...
  /** uncompressed loss-less image format, no alpha channel */
  BMP,
  GIF,
  /** compressed, lossy image format */
  JPEG,
  JPG,
  /** compressed image format with alpha channel */
  PNG,
  /** Tag Image File Format */
  TIF,
  TIFF;

  /** @param string
   * @return
   * @throws IllegalArgumentException if given string does not match
   * any known file types */
  public static Extension of(String string) {
    return valueOf(string.toUpperCase());
  }
}
