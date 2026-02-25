// code by jph
package ch.alpine.bridge.demo.fig;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowComponent;
import ch.alpine.bridge.fig.ShowComponent.Option;
import ch.alpine.bridge.fig.TsPlot;
import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.ref.util.ToolbarFieldsEditor;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.tmp.ResamplingMethod;
import ch.alpine.tensor.tmp.TimeSeries;

@ReflectionMarker
class ShowLiveDemo implements WindowProvider {
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
      FieldsEditor fieldsEditor = ToolbarFieldsEditor.addToComponent(this, jToolBar);
      fieldsEditor.addUniversalListener(this::update);
      jPanel.add(BorderLayout.NORTH, jToolBar);
    }
    jPanel.add(BorderLayout.CENTER, showComponent);
    jFrame.addWindowListener(new WindowAdapter() {
      Timer timer;

      @Override
      public void windowOpened(WindowEvent e) {
        timer = new Timer();
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
      public void windowClosed(WindowEvent e) {
        timer.cancel();
      }
    });
    jFrame.setContentPane(jPanel);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setBounds(100, 100, 1000, 900);
  }

  private void update() {
    showComponent.setOptionX(Option.PAN, xPan);
    showComponent.setOptionX(Option.ZOOM, xZoom);
    showComponent.setOptionY(Option.PAN, yPan);
    showComponent.setOptionY(Option.ZOOM, yZoom);
    showComponent.repaint();
  }

  @Override
  public Window getWindow() {
    return jFrame;
  }

  static void main() {
    new ShowLiveDemo().runStandalone();
  }
}
