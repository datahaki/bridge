// code by jph
package demo.tensor;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.win.WindowFunctions;

// 3
/* package */ enum WindowFunctionImage {
  ;
  public static void main(String[] args) throws IOException {
    WindowFunctions[] smoothingKernels = new WindowFunctions[] { //
        WindowFunctions.GAUSSIAN, //
        WindowFunctions.HAMMING, //
        WindowFunctions.BLACKMAN, //
        WindowFunctions.NUTTALL, //
    };
    Show show = new Show();
    show.setPlotLabel("Window Functions");
    for (WindowFunctions windowFunctions : smoothingKernels) {
      Showable showable = show.add(Plot.of(windowFunctions.get(), Clips.absolute(0.5)));
      showable.setLabel(windowFunctions.name());
    }
    show.export(HomeDirectory.Pictures(WindowFunctionImage.class.getSimpleName() + ".png"), new Dimension(400, 300));
  }
}
