// code by jph
package ch.alpine.bridge.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.util.List;
import java.util.Optional;

import javax.swing.JComponent;
import javax.swing.JToolBar;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.lang.EnumValue;
import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldSelectionArray;
import ch.alpine.bridge.ref.ann.FieldSelectionCallback;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public abstract class FontDialog extends DialogBase<Font> {
  private static final String DEMO = "Abc123!";

  @ReflectionMarker
  public static class FontParam {
    @FieldSelectionCallback("names")
    public String name; // "Dialog"
    public FontStyle style; // PLAIN
    @FieldClip(min = "0", max = "Infinity")
    @FieldSelectionArray({ "10", "11", "12", "13", "14", "15", "16", "17", "18", "20", "22", "25" })
    public Integer size; // 12

    /** @param font */
    protected FontParam(Font font) {
      name = font.getName();
      style = EnumValue.fromOrdinal(FontStyle.class, font.getStyle());
      size = font.getSize();
    }

    @ReflectionMarker
    public static List<String> names() {
      GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
      return List.of(graphicsEnvironment.getAvailableFontFamilyNames());
    }

    /** Experimentation has shown that a font name unknown to the graphics environment
     * will cause a fallback to a known font
     * 
     * @return font as specified by this instance */
    public Font toFont() {
      return new Font(name, style.ordinal(), size);
    }
  }

  // ---
  private final JComponent jComponent = new JComponent() {
    @Override
    protected void paintComponent(Graphics _g) {
      Dimension dimension = getSize();
      Point point = new Point(dimension.width / 2, dimension.height / 2);
      Graphics2D graphics = (Graphics2D) _g.create();
      graphics.setColor(Color.WHITE);
      graphics.fillRect(0, 0, dimension.width, dimension.height);
      RenderQuality.setQuality(graphics);
      graphics.setFont(fontParam.toFont());
      FontMetrics fontMetrics = graphics.getFontMetrics();
      int ascent = fontMetrics.getAscent();
      int stringWidth = fontMetrics.stringWidth(DEMO);
      graphics.setColor(Color.DARK_GRAY);
      graphics.drawString(DEMO, point.x - stringWidth / 2, point.y + ascent / 2);
      graphics.dispose();
    }
  };
  private final FontParam fontParam;
  private final PanelFieldsEditor panelFieldsEditor;

  public FontDialog(Font font) {
    super(font);
    fontParam = new FontParam(font);
    // ---
    jComponent.setPreferredSize(new Dimension(200, 60));
    // ---
    panelFieldsEditor = PanelFieldsEditor.splits(fontParam);
    panelFieldsEditor.addUniversalListener(() -> {
      jComponent.repaint();
      selection(current());
    });
  }

  @Override
  public String getTitle() {
    return "Font selection";
  }

  @Override
  public Optional<JComponent> getComponentWest() {
    return Optional.empty();
  }

  @Override
  public Optional<JComponent> getComponentNorth() {
    return Optional.of(jComponent);
  }

  @Override
  public PanelFieldsEditor panelFieldsEditor() {
    return panelFieldsEditor;
  }

  @Override
  public void decorate(JToolBar jToolBar) {
    // ---
  }

  @Override
  public Font current() {
    return fontParam.toFont();
  }
}
