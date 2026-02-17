// code by jph
package ch.alpine.bridge.pro;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.function.Supplier;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public enum Manipulate {
  ;
  public static JFrame asFrame(Object object, Supplier<Container> function) {
    JFrame jFrame = new JFrame();
    JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    final JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(BorderLayout.CENTER, function.get());
    jSplitPane.setRightComponent(jPanel);
    {
      JPanel side = new JPanel(new BorderLayout());
      side.add(BorderLayout.NORTH, AwtUtil.createToolbar(jPanel));
      PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.nested(object);
      side.add(BorderLayout.CENTER, panelFieldsEditor.createJScrollPane());
      jSplitPane.setLeftComponent(side);
      panelFieldsEditor.addUniversalListener(() -> {
        jPanel.removeAll();
        jPanel.add(BorderLayout.CENTER, function.get());
        jPanel.validate();
      });
    }
    jFrame.setContentPane(jSplitPane);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    return jFrame;
  }
}
