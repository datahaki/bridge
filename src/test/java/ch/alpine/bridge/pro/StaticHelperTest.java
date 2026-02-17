// code by jph
package ch.alpine.bridge.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ext.HomeDirectory;

class StaticHelperTest {
  @Test
  void test() {
    String string = getClass().getName();
    String[] splits = string.split("\\.");
    assertEquals(splits.length, 5);
    List<String> list = Arrays.asList(splits);
    assertEquals(list.size(), 5);
    Path path0 = HomeDirectory._local_share.resolve();
    Path path1 = HomeDirectory._local_share.resolve(splits);
    assertEquals(path0.getNameCount() + splits.length, path1.getNameCount());
  }
}
