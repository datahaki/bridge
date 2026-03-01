// code by jph
package ch.alpine.bridge.pro;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

import ch.alpine.bridge.cgr.InstanceRecord;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.ext.UserName;
import ch.alpine.tensor.qty.Timing;
import ch.alpine.tensor.sca.Round;

/** DO NOT USE IN THE APPLICATION LAYER */
public class SanityCheckRunProvider implements Consumer<InstanceRecord<RunProvider>> {
  private static final int WIDTH = 800;
  private static final int HEIGHT = 800;

  @Override
  public final void accept(InstanceRecord<RunProvider> instanceRecord) {
    Timing timing = Timing.started();
    println(instanceRecord);
    RunProvider runProvider = instanceRecord.supplier().get();
    switch (runProvider) {
    case WindowProvider windowProvider -> check(windowProvider);
    case ManipulateProvider manipulateProvider -> check(manipulateProvider);
    case ShowProvider showProvider -> check(showProvider);
    case VoidProvider voidProvider -> check(voidProvider);
    }
    println("Time elapsed: " + timing.seconds().maps(Round._3));
  }

  private static void println(Object object) {
    if (UserName.whoami().startsWith("runner"))
      IO.println(" [INFO] " + object);
  }

  /** function renders content of window offscreen
   * 
   * @param windowProvider */
  protected void check(WindowProvider windowProvider) {
    Window window = windowProvider.getWindow();
    window.setSize(WIDTH, HEIGHT);
    BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    window.printAll(graphics);
    graphics.dispose();
  }

  protected void check(ManipulateProvider manipulateProvider) {
    Container jComponent = manipulateProvider.getContainer();
    jComponent.setSize(WIDTH, HEIGHT);
    jComponent.doLayout(); // mandatory
    int width = jComponent.getWidth();
    Integers.requireEquals(width, WIDTH);
    int height = jComponent.getHeight();
    Integers.requireEquals(height, HEIGHT);
    if (width == 0 || height == 0) {
      throw new IllegalStateException("Component must have a size");
    }
    BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    jComponent.printAll(graphics);
    graphics.dispose();
  }

  protected void check(ShowProvider showProvider) {
    Show show = showProvider.getShow();
    Dimension dimension = new Dimension(800, 800);
    BufferedImage bufferedImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    show.render_autoIndent(graphics, new Rectangle(dimension));
    graphics.dispose();
  }

  protected void check(VoidProvider voidProvider) {
    voidProvider.runStandalone();
  }
}
