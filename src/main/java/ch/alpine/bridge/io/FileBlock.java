// code by jph adapted from Jean-Francois Briere
// http://www.velocityreviews.com/forums/t137115-preventing-multiple-instance-standalone-desktop-gui-applications.html
package ch.alpine.bridge.io;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import ch.alpine.tensor.ext.PackageTestAccess;

/** also works if file already exists before any launch */
public class FileBlock {
  private static final Pattern PATTERN = Pattern.compile("^[\\w-.]{1,255}$");

  @PackageTestAccess
  static boolean validFilename(String string) {
    return PATTERN.matcher(string).matches();
  }

  /** @param folder to generated `.lock` file in
   * @param uid unique identifier that is may be used as part of filename
   * @param showMessage whether to pop-up error dialog
   * @return
   * @throws Exception if given uid cannot be used as part of filename */
  public static boolean of(File folder, String uid, boolean showMessage) {
    FileBlock fileBlock = new FileBlock(folder, uid);
    boolean isActive = fileBlock.isActive();
    if (isActive && showMessage)
      fileBlock.showMessage();
    return isActive;
  }

  // ---
  private final File folder;
  private final String uid;
  private RandomAccessFile randomAccessFile;
  private FileChannel fileChannel;
  private FileLock fileLock;

  private FileBlock(File folder, String identifier) {
    this.folder = folder;
    this.uid = identifier;
  }

  /* package */ boolean isActive() {
    try {
      File file = new File(folder, '.' + uid + ".lock");
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
    JOptionPane.showMessageDialog( //
        null, //
        uid + " is already running.", //
        "Execution blocked", //
        JOptionPane.ERROR_MESSAGE);
  }
}
