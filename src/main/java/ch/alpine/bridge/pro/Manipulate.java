// code by jph
package ch.alpine.bridge.pro;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Objects;
import java.util.function.Supplier;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Manipulate.html">Manipulate</a> */
public class Manipulate {
  public static JFrame asFrame(Object object, Supplier<Container> function) {
    return new Manipulate(object, function).jFrame;
  }

  // ---
  private final JFrame jFrame = new JFrame();
  private final JPanel jPanel = new JPanel(new BorderLayout());
  private Container container_prev = null;

  private Manipulate(Object object, Supplier<Container> function) {
    JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    jPanel.add(BorderLayout.CENTER, container_prev = Objects.requireNonNull(function.get()));
    jSplitPane.setRightComponent(jPanel);
    {
      JPanel editor = new JPanel(new BorderLayout());
      editor.add(BorderLayout.NORTH, AwtUtil.createToolbar(jSplitPane));
      PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.nested(object);
      editor.add(BorderLayout.CENTER, panelFieldsEditor.createJScrollPane());
      jSplitPane.setLeftComponent(editor);
      panelFieldsEditor.addUniversalListener(() -> receive(function.get()));
    }
    jFrame.setContentPane(jSplitPane);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  }

  private void receive(Container container_next) {
    Objects.requireNonNull(container_next);
    if (container_prev == container_next) {
      container_prev.repaint();
      // SwingUtilities.invokeLater(geometricComponent.jComponent::repaint);
    } else {
      jPanel.removeAll();
      jPanel.add(BorderLayout.CENTER, container_prev = container_next);
      jPanel.validate();
    }
  }
}
