// code by jph
package ch.alpine.java.awt;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface BufferedImageSupplier {
  /** @return bufferedImage */
  BufferedImage bufferedImage();
}
