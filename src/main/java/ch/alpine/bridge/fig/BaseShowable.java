// code by jph
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

import ch.alpine.bridge.awt.AwtUtil;

public abstract class BaseShowable implements Showable, Serializable {
  private String string = "";
  private Color color = Color.BLACK;
  private transient Stroke stroke = new BasicStroke(1.5f);
  protected final Set<PlotOption> set = EnumSet.noneOf(PlotOption.class);

  @Override
  public void tender(ShowableConfig showableConfig, Graphics2D graphics) {
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
  public void setAlpha(int alpha) {
    setColor(AwtUtil.withAlpha(color, alpha));
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

  @Override
  public final void set(PlotOption plotOption, boolean status) {
    if (status)
      set.add(plotOption);
    else
      set.remove(plotOption);
  }
}
