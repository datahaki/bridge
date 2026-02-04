// code by jph
package sys.io;

import java.io.File;
import java.io.IOException;

import ch.alpine.bridge.io.DeleteDirectory;

public enum DeleteDirectoryWrap {
  ;
  public static void of(File directory, int max_depth, long max_count) throws IOException {
    DeleteDirectory deleteDirectory = DeleteDirectory.of(directory, max_depth, max_count);
    long deletedCount = deleteDirectory.fileCount();
    if (0 < deletedCount)
      System.out.println("deleted: " + deletedCount);
  }
}
