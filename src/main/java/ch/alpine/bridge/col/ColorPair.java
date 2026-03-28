// code by jph
package ch.alpine.bridge.col;

import java.awt.Color;
import java.io.Serializable;

public record ColorPair(Color fill, Color draw) implements Serializable {
  public ColorPair solid() {
    return new ColorPair( //
        Colors.withAlpha(fill, 255), //
        Colors.withAlpha(draw, 255));
  }
}
