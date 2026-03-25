// code by jph
package ch.alpine.bridge.gfx;

import java.awt.Color;
import java.io.Serializable;

import ch.alpine.bridge.awt.AwtUtil;

public record ColorPair(Color fill, Color draw) implements Serializable {
  public ColorPair solid() {
    return new ColorPair( //
        AwtUtil.withAlpha(fill, 255), //
        AwtUtil.withAlpha(draw, 255));
  }
}
