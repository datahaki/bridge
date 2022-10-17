// code by legion
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Graphics;

public interface Showable {
  void render(ShowableConfig showableConfig, Graphics _g);

  void setLabel(String string);

  void setColor(Color color);
}
