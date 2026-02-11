// code by jph
package ch.alpine.bridge.fig;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.ScreenRectangles;
import ch.alpine.bridge.io.ImageClipboard;
import ch.alpine.bridge.lang.FriendlyFormat;
import ch.alpine.tensor.ext.HomeDirectory;

public enum ShowWindow {
  ;
  private static final int SIZE = 800;

  /** non-blocking
   * 
   * @param shows
   * @return */
  public static JDialog asDialog(Show... shows) {
    return asDialog(List.of(shows));
  }

  /** non-blocking
   * 
   * @param list
   * @return */
  public static JDialog asDialog(List<Show> list) {
    Component parentComponent = null;
    /* false -> non-modal == non-blocking */
    JDialog jDialog = new JDialog(JOptionPane.getFrameForComponent(parentComponent), false);
    jDialog.setContentPane(createContainer(list));
    jDialog.setSize(SIZE, SIZE);
    jDialog.setLocationRelativeTo(parentComponent);
    jDialog.setTitle(StaticHelper.defaultTitle());
    jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    ScreenRectangles.create().placement(jDialog);
    jDialog.setVisible(true);
    return jDialog;
  }

  public static JFrame asFrame(Show... shows) {
    return asFrame(List.of(shows));
  }

  public static JFrame asFrame(List<Show> list) {
    Component parentComponent = null;
    JFrame jFrame = new JFrame();
    jFrame.setContentPane(createContainer(list));
    jFrame.setSize(SIZE, SIZE);
    jFrame.setLocationRelativeTo(parentComponent);
    jFrame.setTitle(StaticHelper.defaultTitle());
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    ScreenRectangles.create().placement(jFrame);
    jFrame.setVisible(true);
    return jFrame;
  }

  private static BufferedImage fromComponent(JComponent center) {
    Dimension dimension = center.getSize();
    BufferedImage bufferedImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    center.printAll(graphics);
    graphics.dispose();
    return bufferedImage;
  }

  private static Container createContainer(List<Show> list) {
    JPanel contentPane = new JPanel(new BorderLayout());
    JPanel center = ShowGridComponent.of(list);
    contentPane.add(BorderLayout.CENTER, center);
    JToolBar jToolBar = new JToolBar();
    jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
    jToolBar.setFloatable(false);
    {
      JButton jButton = new JButton("copy");
      jButton.addActionListener(_ -> ImageClipboard.copy(fromComponent(center)));
      jToolBar.add(jButton);
    }
    {
      JButton jButton = new JButton("export");
      jButton.addActionListener(_ -> {
        try {
          String string = "fig_" + System.nanoTime() + ".png";
          Path path = HomeDirectory.Pictures.resolve(FriendlyFormat.sanitize(string));
          ImageIO.write(fromComponent(center), "png", Files.newOutputStream(path));
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      });
      jToolBar.add(jButton);
    }
    contentPane.add(BorderLayout.NORTH, jToolBar);
    return contentPane;
  }
}
