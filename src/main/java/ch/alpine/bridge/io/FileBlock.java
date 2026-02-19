// code by jph adapted from Jean-Francois Briere
// http://www.velocityreviews.com/forums/t137115-preventing-multiple-instance-standalone-desktop-gui-applications.html
package ch.alpine.bridge.io;

import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UncheckedIOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import javax.swing.JOptionPane;

/** also works if file already exists before any launch */
public class FileBlock {
  /** @param folder to generated `.lock` file in
   * @param uid unique identifier that is may be used as part of filename
   * @param showMessage whether to pop-up error dialog
   * @return whether uid was reserved by a previous instance
   * @throws Exception if given uid cannot be used as part of filename */
  public static boolean of(Path folder, boolean showMessage) {
    FileBlock fileBlock = new FileBlock(folder);
    boolean isActive = fileBlock.isActive();
    if (isActive && showMessage)
      fileBlock.showMessage();
    return isActive;
  }

  public static boolean of(Path folder) {
    return of(folder, !GraphicsEnvironment.isHeadless());
  }

  // ---
  private final Path folder;
  private RandomAccessFile randomAccessFile;
  private FileChannel fileChannel;
  private FileLock fileLock;

  private FileBlock(Path folder) {
    this.folder = folder;
  }

  /* package */ boolean isActive() {
    try {
      Path path = folder.resolve(".FileBlock.lock");
      randomAccessFile = new RandomAccessFile(path.toFile(), "rw");
      fileChannel = randomAccessFile.getChannel();
      fileLock = fileChannel.tryLock(); // documentation not clear on "return vs. exception"
      if (Objects.isNull(fileLock)) { // standard behavior if file exists
        release();
        return true;
      }
      // File::deleteOnExit is not used to ensure release() is called before deleting file
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        release(); // remove all locks
        try {
          Files.delete(path); // finally delete file
        } catch (IOException ioException) {
          throw new UncheckedIOException(ioException);
        }
      }));
      return false;
    } catch (Exception exception) {
      // ---
    }
    release();
    return true;
  }

  private void release() {
    try {
      if (Objects.nonNull(fileLock))
        fileLock.release();
    } catch (Exception exception) {
      IO.println("FileLock (ignore!): " + exception);
    }
    try {
      fileChannel.close();
    } catch (Exception exception) {
      IO.println("FileChannel (ignore!): " + exception);
    }
    try {
      randomAccessFile.close();
    } catch (Exception exception) {
      IO.println("RandomAccessFile (ignore!): " + exception);
    }
  }

  private void showMessage() {
    JOptionPane.showMessageDialog( //
        null, //
        folder + "\nis already running.", //
        "Execution blocked", //
        JOptionPane.ERROR_MESSAGE);
  }
}
