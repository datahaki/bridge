// code by jph
package ch.alpine.bridge.ref;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.border.Border;

@Deprecated
/* package */ class PreferredWidth {
  private final JComponent jComponent;
  private final int offset;
  private final FontMetrics fontMetrics;
  private int max_stringWidth;

  public PreferredWidth(JComponent jComponent, int offset) {
    this.jComponent = jComponent;
    this.offset = offset;
    fontMetrics = jComponent.getFontMetrics(jComponent.getFont());
  }

  public void hostString(String string) {
    max_stringWidth = Math.max(max_stringWidth, fontMetrics.stringWidth(string));
  }

  public void installPreferredWidth() {
    Dimension dimension = jComponent.getPreferredSize();
    Border border = jComponent.getBorder();
    Insets insets = Objects.nonNull(border) //
        ? border.getBorderInsets(jComponent)
        : new Insets(0, 0, 0, 0);
    dimension.width = max_stringWidth + insets.left + insets.right + offset;
    jComponent.setPreferredSize(dimension);
  }
}
