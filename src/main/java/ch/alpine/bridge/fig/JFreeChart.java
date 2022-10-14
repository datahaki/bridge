package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public interface JFreeChart {
  void draw(Graphics2D graphics, Rectangle rectangle);

  void draw(Graphics2D graphics, Rectangle rectangle, CoordinateBoundingBox cbb);
}
