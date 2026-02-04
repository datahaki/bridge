package ch.alpine.bridge.fig;

import java.awt.Component;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.ScreenRectangles;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

public class Manipulate extends JDialog {
  private static final int WIDTH = 1200;
  private static final int SIZE = 1000;

  public static JDialog of(Object object, Supplier<List<Show>> function) {
    Manipulate showDialog = new Manipulate(null, object, function);
    ScreenRectangles.create().placement(showDialog);
    showDialog.setVisible(true);
    return showDialog;
  }

  public Manipulate(Component parentComponent, Object object, Supplier<List<Show>> function) {
    super(JOptionPane.getFrameForComponent(parentComponent), false); // non-blocking
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    jSplitPane.setRightComponent(GridComponent.asd(function.get()));
    {
      PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.nested(object);
      panelFieldsEditor.addUniversalListener(() -> {
        jSplitPane.setRightComponent(GridComponent.asd(function.get()));
        // showComponent.setShow(function.get());
        // showComponent.repaint();
      });
      jSplitPane.setLeftComponent(panelFieldsEditor.createJScrollPane());
    }
    setContentPane(jSplitPane);
    setSize(WIDTH, SIZE);
    jSplitPane.setDividerLocation(200);
    setLocationRelativeTo(parentComponent);
    ScreenRectangles.create().placement(this);
  }
}
