// code by jph
package ch.alpine.bridge.awt;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;

import ch.alpine.bridge.io.ImageClipboard;
import ch.alpine.bridge.lang.FriendlyFormat;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.qty.DateTime;

public enum AwtUtil {
  ;
  public static Point center(Dimension dimension) {
    return new Point(dimension.width / 2, dimension.height / 2);
  }

  public static JToolBar createToolbar(JComponent jComponent) {
    JToolBar jToolBar = new JToolBar();
    {
      jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
      jToolBar.setFloatable(false);
      {
        JButton jButton = new JButton("copy");
        jButton.addActionListener(_ -> ImageClipboard.copy(OffscreenRender.of(jComponent, BufferedImage.TYPE_INT_ARGB)));
        jToolBar.add(jButton);
      }
      {
        JButton jButton = new JButton("export");
        jButton.addActionListener(_ -> {
          String title = "fig_" + DateTime.of(LocalDate.now(), LocalTime.now().withNano(0));
          {
            Path path = HomeDirectory.Pictures.resolve(FriendlyFormat.sanitize(title + ".png"));
            try (OutputStream outputStream = Files.newOutputStream(path)) {
              ImageIO.write(OffscreenRender.of(jComponent, BufferedImage.TYPE_INT_ARGB), "png", outputStream);
            } catch (Exception exception) {
              exception.printStackTrace();
            }
          }
          {
            Path path = HomeDirectory.Pictures.resolve(FriendlyFormat.sanitize(title + ".jpg"));
            IO.println(path);
            try (OutputStream outputStream = Files.newOutputStream(path)) {
              ImageIO.write(OffscreenRender.of(jComponent, BufferedImage.TYPE_3BYTE_BGR), "jpg", outputStream);
            } catch (Exception exception) {
              exception.printStackTrace();
            }
          }
        });
        jToolBar.add(jButton);
      }
    }
    return jToolBar;
  }
}
