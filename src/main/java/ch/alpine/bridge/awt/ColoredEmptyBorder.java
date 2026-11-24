// code by gemini
package ch.alpine.bridge.awt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Objects;

import javax.swing.border.AbstractBorder;

public class ColoredEmptyBorder extends AbstractBorder {
  private final int top;
  private final int left;
  private final int bottom;
  private final int right;
  private final Color fillColor;

  public ColoredEmptyBorder(int top, int left, int bottom, int right, Color fillColor) {
    if (top < 0 || left < 0 || bottom < 0 || right < 0) {
      throw new IllegalArgumentException("Border insets must be non-negative");
    }
    this.top = top;
    this.left = left;
    this.bottom = bottom;
    this.right = right;
    this.fillColor = Objects.requireNonNull(fillColor, "fillColor must not be null");
  }

  public ColoredEmptyBorder(Insets insets, Color fillColor) {
    this(Objects.requireNonNull(insets, "insets must not be null").top, insets.left, insets.bottom, insets.right, fillColor);
  }

  @Override
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    if (width <= 0 || height <= 0) {
      return;
    }
    Color previousColor = g.getColor();
    g.setColor(fillColor);
    int effectiveTop = Math.min(top, height);
    int effectiveBottom = Math.min(bottom, Math.max(0, height - effectiveTop));
    int effectiveLeft = Math.min(left, width);
    int effectiveRight = Math.min(right, Math.max(0, width - effectiveLeft));
    // Top strip
    if (effectiveTop > 0) {
      g.fillRect(x, y, width, effectiveTop);
    }
    // Bottom strip
    if (effectiveBottom > 0) {
      g.fillRect(x, y + height - effectiveBottom, width, effectiveBottom);
    }
    // Left and right strips between top and bottom
    int middleY = y + effectiveTop;
    int middleHeight = Math.max(0, height - effectiveTop - effectiveBottom);
    if (effectiveLeft > 0 && middleHeight > 0) {
      g.fillRect(x, middleY, effectiveLeft, middleHeight);
    }
    if (effectiveRight > 0 && middleHeight > 0) {
      g.fillRect(x + width - effectiveRight, middleY, effectiveRight, middleHeight);
    }
    g.setColor(previousColor);
  }

  @Override
  public Insets getBorderInsets(Component c) {
    return new Insets(top, left, bottom, right);
  }

  @Override
  public Insets getBorderInsets(Component c, Insets insets) {
    insets.set(top, left, bottom, right);
    return insets;
  }

  @Override
  public boolean isBorderOpaque() {
    return fillColor.getAlpha() == 255;
  }

  public Color getFillColor() {
    return fillColor;
  }
}
