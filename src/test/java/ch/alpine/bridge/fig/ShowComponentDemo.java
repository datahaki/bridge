// code by jph
package ch.alpine.bridge.fig;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.ref.util.ToolbarFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;

@ReflectionMarker
public class ShowComponentDemo implements Runnable {
  private final JFrame jFrame = new JFrame();
  private final ShowComponent showComponent = new ShowComponent();
  public ShowDemos showDemos = ShowDemos.ARRAY_PLOT0;

  public ShowComponentDemo() {
    JPanel jPanel = new JPanel(new BorderLayout());
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
      FieldsEditor fieldsEditor = ToolbarFieldsEditor.add(this, jToolBar);
      fieldsEditor.addUniversalListener(this);
      jPanel.add(BorderLayout.NORTH, jToolBar);
    }
    jPanel.add(BorderLayout.CENTER, showComponent);
    jFrame.setContentPane(jPanel);
    // jFrame.setContentPane();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setBounds(100, 100, 1000, 900);
  }

  @Override
  public void run() {
    showComponent.setShow(showDemos.create());
    showComponent.repaint();
  }

  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    ShowComponentDemo showComponentDemo = new ShowComponentDemo();
    showComponentDemo.jFrame.setVisible(true);
  }
}
