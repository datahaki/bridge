// code by jph adapted from Jean-Francois Briere
// http://www.velocityreviews.com/forums/t137115-preventing-multiple-instance-standalone-desktop-gui-applications.html
package ch.alpine.java.io;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Objects;

import javax.swing.JOptionPane;

/** also works if file already exists before any launch */
public class FileBlock {
  public static boolean of(File folder, Class<?> cls, boolean showMessage) {
    FileBlock fileBlock = new FileBlock(folder, cls);
    boolean isActive = fileBlock.isActive();
    if (isActive && showMessage)
      fileBlock.showMessage();
    return isActive;
  }

  // ---
  private final File folder;
  private final Class<?> cls;
  private RandomAccessFile randomAccessFile;
  private FileChannel fileChannel;
  private FileLock fileLock;

  private FileBlock(File folder, Class<?> cls) {
    this.folder = folder;
    this.cls = cls;
  }

  /* package */ boolean isActive() {
    try {
      File file = new File(folder, '.' + cls.getCanonicalName() + ".lock");
      randomAccessFile = new RandomAccessFile(file, "rw");
      fileChannel = randomAccessFile.getChannel();
      fileLock = fileChannel.tryLock(); // documentation not clear on "return vs. exception"
      if (Objects.isNull(fileLock)) { // standard behavior if file exists
        release();
        return true;
      }
      // File::deleteOnExit is not used to ensure release() is called before deleting file
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        release(); // remove all locks
        file.delete(); // finally delete file
      }));
      return false;
    } catch (Exception exception) {
      System.err.println(getClass().getSimpleName() + ": " + exception.getMessage());
    }
    release();
    return true;
  }

  private void release() {
    try {
      if (Objects.nonNull(fileLock))
        fileLock.release();
    } catch (Exception exception) {
      System.out.println("FileLock (ignore!): " + exception);
    }
    try {
      fileChannel.close();
    } catch (Exception exception) {
      System.out.println("FileChannel (ignore!): " + exception);
    }
    try {
      randomAccessFile.close();
    } catch (Exception exception) {
      System.out.println("RandomAccessFile (ignore!): " + exception);
    }
  }

  private void showMessage() {
    JOptionPane.showMessageDialog(null, cls.getSimpleName() + " is already running.", "Execution blocked", JOptionPane.ERROR_MESSAGE);
  }
}