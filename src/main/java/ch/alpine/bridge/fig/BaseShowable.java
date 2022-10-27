// code by jph
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.io.Serializable;
import java.util.Objects;

public abstract class BaseShowable implements Showable, Serializable {
  private String string = "";
  private Color color = Color.BLACK;
  private transient Stroke stroke = new BasicStroke(1.5f);

  @Override
  public void tender(ShowableConfig showableConfig, Graphics graphics) {
    // ---
  }

  @Override
  public final void setLabel(String string) {
    this.string = string;
  }

  @Override
  public final String getLabel() {
    return string;
  }

  @Override
  public final void setColor(Color color) {
    this.color = color;
  }

  @Override
  public final Color getColor() {
    return color;
  }

  @Override
  public final void setStroke(Stroke stroke) {
    this.stroke = stroke;
  }

  @Override
  public final Stroke getStroke() {
    return Objects.isNull(stroke) //
        ? new BasicStroke(1.5f)
        : stroke;
  }
}
