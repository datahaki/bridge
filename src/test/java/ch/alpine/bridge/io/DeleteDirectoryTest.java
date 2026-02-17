// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ext.HomeDirectory;

class DeleteDirectoryTest {
  @Test
  void testLayer0() throws Exception {
    Path folder = HomeDirectory.Downloads.mk_dirs(getClass().getSimpleName() + "0");
    DeleteDirectory deleteDirectory = DeleteDirectory.of(folder, 0, 1, DeleteDirectory.DELETE_FAIL_ABORTS);
    assertEquals(deleteDirectory.fileCount(), 1);
  }

  @Test
  void testLayer1a() throws Exception {
    Path folder = HomeDirectory.Downloads.mk_dirs(getClass().getSimpleName() + "1a");
    Path sample1_txt = folder.resolve("sample1.txt");
    Files.createFile(sample1_txt);
    assertThrows(Exception.class, () -> DeleteDirectory.of(sample1_txt, 2, 10));
    Files.createFile(folder.resolve("sample2.txt"));
    assertThrows(Exception.class, () -> DeleteDirectory.of(folder, 0, 5));
    assertThrows(Exception.class, () -> DeleteDirectory.of(folder, 1, 2));
    DeleteDirectory deleteDirectory = DeleteDirectory.of(folder, 1, 3);
    assertEquals(deleteDirectory.fileCount(), 3);
    int reachedDepth = deleteDirectory.reachedDepth();
    assertEquals(reachedDepth, 1);
  }

  @Test
  void testLayer1b() throws Exception {
    Path folder = HomeDirectory.Downloads.mk_dirs(getClass().getSimpleName() + "1b");
    Files.createFile(folder.resolve("sample1.txt"));
    Files.createFile(folder.resolve("sample2.txt"));
    Path sub = folder.resolve("sub");
    Files.createDirectories(sub);
    DeleteDirectory deleteDirectory = DeleteDirectory.of(folder, 1, 5);
    assertEquals(deleteDirectory.fileCount(), 4);
  }

  @Test
  void testLayer2() throws Exception {
    Path folder = HomeDirectory.Downloads.mk_dirs(getClass().getSimpleName() + "2");
    Files.createFile(folder.resolve("sample1.txt"));
    Files.createFile(folder.resolve("sample2.txt"));
    Path sub = folder.resolve("sub");
    Files.createDirectories(sub);
    Files.createFile(sub.resolve("content1.txt"));
    assertThrows(Exception.class, () -> DeleteDirectory.of(folder, 1, 10));
    DeleteDirectory deleteDirectory = DeleteDirectory.of(folder, 2, 5);
    assertEquals(deleteDirectory.fileCount(), 5);
  }

  @Test
  void testNotFound() {
    Path folder = HomeDirectory.Downloads.resolve(getClass().getSimpleName() + "NotFound");
    assertThrows(Exception.class, () -> DeleteDirectory.of(folder, 1, 10));
  }

  @Test
  void testRenameDirectory() throws IOException {
    Path folder1 = HomeDirectory.Downloads.mk_dirs(getClass().getSimpleName() + "NotFound1234");
    Path folder2 = HomeDirectory.Downloads.resolve(getClass().getSimpleName() + "NotFound1235");
    {
      Path file1 = folder1.resolve("dummy.txt");
      Files.createFile(file1);
    }
    Files.move(folder1, folder2);
    assertTrue(Files.isDirectory(folder2));
    Path file2 = folder2.resolve("dummy.txt");
    assertTrue(Files.isRegularFile(file2));
    Files.delete(file2);
    Files.delete(folder2);
  }
}
