// code by jph
package ch.alpine.bridge.fig;

import java.awt.Component;
import java.util.function.Supplier;

import javax.swing.JComponent;
import javax.swing.JFrame;
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
    JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    jSplitPane.setRightComponent(function.get());
    {
      PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.nested(object);
      jSplitPane.setLeftComponent(panelFieldsEditor.createJScrollPane());
      panelFieldsEditor.addUniversalListener(() -> jSplitPane.setRightComponent(function.get()));
    }
    jFrame.setContentPane(jSplitPane);
    jFrame.setSize(WIDTH, SIZE);
    jFrame.setLocationRelativeTo(parentComponent);
    // jFrame.setTitle(defaultTitle());
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    ScreenRectangles.create().placement(jFrame);
    jFrame.setVisible(true);
    return jFrame;
  }
}
