// code by jph
package ch.alpine.bridge.geo;

import java.awt.image.BufferedImage;

/** @param bufferedImage
 * @param length is file size */
public record TileImage(BufferedImage bufferedImage, long length) {
}
