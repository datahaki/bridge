package sys.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.tensor.Unprotect;

class FileUtilsTest {
  @Disabled
  @Test
  void testSome(@TempDir File folder) throws Exception {
    File src = Unprotect.file("/mid/bwv1086.mid");
    File dst = new File(folder, "target.mid");
    assertFalse(dst.exists());
    Files.copy(src.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
    assertTrue(dst.exists());
  }
}
