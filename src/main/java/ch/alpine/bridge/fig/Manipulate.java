// code by jph
package ch.alpine.bridge.fig;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.function.Supplier;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.ScreenRectangles;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public enum Manipulate {
  ;
  private static final int WIDTH = 1200;
  private static final int SIZE = 1000;

  public static JFrame asFrame(Object object, Supplier<JComponent> function) {
    Component parentComponent = null;
    JFrame jFrame = new JFrame();
    jFrame.setTitle(StaticHelper.defaultTitle());
    JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    final JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(BorderLayout.CENTER, function.get());
    jSplitPane.setRightComponent(jPanel);
    {
      JPanel side = new JPanel(new BorderLayout());
      side.add(BorderLayout.NORTH, StaticHelper.createToolbar(jPanel));
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
    jFrame.setSize(WIDTH, SIZE);
    jFrame.setLocationRelativeTo(parentComponent);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    ScreenRectangles.create().placement(jFrame);
    jFrame.setVisible(true);
    return jFrame;
  }
}
