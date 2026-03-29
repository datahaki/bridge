// code by jph
package ch.alpine.bridge.gfx;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

import ch.alpine.bridge.col.ColorPair;

public record TextContour(Graphics2D graphics, FontRenderContext frc, Font font) {
  private static final ColorPair TEXT = new ColorPair(Color.BLACK, new Color(255, 255, 255, 192));
  /** BasicStroke.JOIN_BEVEL or BasicStroke.JOIN_ROUND */
  private static final Stroke STROKE = new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);

  /** @param graphics
   * @return */
  public static TextContour of(Graphics2D graphics) {
    return new TextContour(graphics, graphics.getFontRenderContext(), graphics.getFont());
  }

  public void draw(ColorPair colorPair, String string, float x, float y) {
    GlyphVector glyphVector = font.createGlyphVector(frc, string);
    Shape shape = glyphVector.getOutline(x, y);
    graphics.setStroke(STROKE); // thickness of outline
    // Shape outlineShape = STROKE.createStrokedShape(shape);
    // {
    // Area fillArea = new Area(shape);
    // Area outlineArea = new Area(outlineShape);
    // outlineArea.subtract(fillArea);
    // graphics.setColor(colorPair.draw());
    // graphics.fill(outlineArea);
    // graphics.setColor(colorPair.fill());
    // graphics.fill(fillArea);
    // }
    // Draw fill first
    // Then draw outline as a filled shape
    // graphics.setColor(colorPair.draw());
    // graphics.fill(outlineShape);
    // graphics.setColor(colorPair.fill());
    // graphics.fill(shape);
    graphics.setColor(colorPair.draw());
    graphics.draw(shape);
    graphics.setStroke(new BasicStroke());
    graphics.setColor(colorPair.fill());
    graphics.fill(shape);
  }

  public void draw(ColorPair colorPair, String string, int x, int y) {
    draw(colorPair, string, (float) x, (float) y);
  }

  public void draw(String string, float x, float y) {
    draw(TEXT, string, x, y);
  }
}
