// code by jph
package ch.alpine.bridge.pro;

import java.awt.Container;
import java.awt.Window;
import java.util.function.Consumer;

import javax.swing.JComponent;

import ch.alpine.bridge.awt.OffscreenRender;
import ch.alpine.bridge.cgr.InstanceRecord;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowGridComponent;
import ch.alpine.bridge.io.AnsiStyle;
import ch.alpine.bridge.io.GitHubCI;
import ch.alpine.tensor.qty.Timing;
import ch.alpine.tensor.sca.Round;

/** DO NOT USE IN THE APPLICATION LAYER */
public class SanityCheckRunProvider implements Consumer<InstanceRecord<RunProvider>> {
  protected static final int SIZE = 800;

  @Override
  public final void accept(InstanceRecord<RunProvider> instanceRecord) {
    GitHubCI.INFO.println("Running " + AnsiStyle.BOLD.wrap(instanceRecord));
    Timing timing = Timing.started();
    RunProvider runProvider = instanceRecord.supplier().get();
    switch (runProvider) {
    case WindowProvider windowProvider -> check(windowProvider);
    case ManipulateProvider manipulateProvider -> check(manipulateProvider);
    case ShowProvider showProvider -> check(showProvider);
    case VoidProvider voidProvider -> check(voidProvider);
    }
    GitHubCI.INFO.println("Time elapsed: " + timing.seconds().maps(Round._3));
  }

  /** function renders content of window offscreen
   * 
   * @param windowProvider */
  protected void check(WindowProvider windowProvider) {
    Window window = windowProvider.getWindow();
    window.setSize(SIZE, SIZE);
    OffscreenRender.of(window);
  }

  /** @param manipulateProvider */
  protected void check(ManipulateProvider manipulateProvider) {
    Container container = manipulateProvider.getContainer();
    container.setSize(SIZE, SIZE);
    OffscreenRender.of(container);
  }

  /** @param showProvider */
  protected void check(ShowProvider showProvider) {
    Show show = showProvider.getShow();
    JComponent jComponent = ShowGridComponent.of(show);
    jComponent.setSize(SIZE, SIZE);
    OffscreenRender.of(jComponent);
  }

  /** @param voidProvider */
  protected void check(VoidProvider voidProvider) {
    voidProvider.runStandalone();
  }
}
