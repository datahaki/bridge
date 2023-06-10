// code by jph
package ch.alpine.bridge.fig;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.fig.ShowComponent.Option;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.ref.util.ToolbarFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.tmp.ResamplingMethod;
import ch.alpine.tensor.tmp.TimeSeries;

@ReflectionMarker
public class ShowLiveDemo implements Runnable {
  private final Timer timer = new Timer();
  private final JFrame jFrame = new JFrame();
  private final TimeSeries timeSeries = TimeSeries.empty(ResamplingMethod.LINEAR_INTERPOLATION);
  private final Show show = new Show();
  private final ShowComponent showComponent = new ShowComponent();
  // ---
  public Boolean xZoom = true;
  public Boolean xPan = true;
  public Boolean yZoom = true;
  public Boolean yPan = true;

  public ShowLiveDemo() {
    show.add(TsPlot.of(timeSeries)).setLabel("time series");
    showComponent.setShow(show);
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
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setBounds(100, 100, 1000, 900);
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        timeSeries.insert(DateTime.now(), RandomVariate.of(NormalDistribution.standard()));
        show.setCbb(CoordinateBoundingBox.of(timeSeries.domain(), Clips.absolute(3)));
        showComponent.repaint();
        // System.out.println("repaint");
      }
    }, 1000, 100);
  }

  @Override
  public void run() {
    showComponent.setOptionX(Option.PAN, xPan);
    showComponent.setOptionX(Option.ZOOM, xZoom);
    showComponent.setOptionY(Option.PAN, yPan);
    showComponent.setOptionY(Option.ZOOM, yZoom);
    showComponent.repaint();
  }

  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    ShowLiveDemo showComponentDemo = new ShowLiveDemo();
    showComponentDemo.jFrame.setVisible(true);
    WindowClosed.runs(showComponentDemo.jFrame, showComponentDemo.timer::cancel);
  }
}
