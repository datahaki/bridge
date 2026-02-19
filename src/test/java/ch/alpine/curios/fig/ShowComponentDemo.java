// code by jph
package ch.alpine.curios.fig;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import ch.alpine.bridge.fig.ShowComponent;
import ch.alpine.bridge.fig.ShowComponent.Option;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.ref.util.ToolbarFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;

@ReflectionMarker
public class ShowComponentDemo implements Runnable {
  private final JFrame jFrame = new JFrame();
  private final ShowComponent showComponent = new ShowComponent();
  // ---
  public Showcases showDemos = Showcases.ListLinePlot0;
  public Font font = new JPanel().getFont();
  public Boolean xZoom = true;
  public Boolean xPan = true;
  public Boolean yZoom = true;
  public Boolean yPan = true;

  public ShowComponentDemo() {
    JPanel jPanel = new JPanel(new BorderLayout());
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
      FieldsEditor fieldsEditor = ToolbarFieldsEditor.addToComponent(this, jToolBar);
      fieldsEditor.addUniversalListener(this);
      jPanel.add(BorderLayout.NORTH, jToolBar);
    }
    jPanel.add(BorderLayout.CENTER, showComponent);
    jFrame.setContentPane(jPanel);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setBounds(100, 100, 1200, 900);
    run();
  }

  @Override
  public void run() {
    showComponent.setFont(font);
    showComponent.setShow(showDemos.getShow());
    showComponent.setOptionX(Option.PAN, xPan);
    showComponent.setOptionX(Option.ZOOM, xZoom);
    showComponent.setOptionY(Option.PAN, yPan);
    showComponent.setOptionY(Option.ZOOM, yZoom);
    showComponent.repaint();
  }

  static void main() {
    LookAndFeels.autoDetect();
    ShowComponentDemo showComponentDemo = new ShowComponentDemo();
    showComponentDemo.jFrame.setVisible(true);
  }
}
