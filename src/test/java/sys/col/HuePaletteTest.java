// code by jph
package sys.col;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import ch.alpine.tensor.img.ColorDataLists;

class HuePaletteTest {
  @ParameterizedTest
  @EnumSource
  void test(ColorDataLists cdl) {
    HuePalette.of(cdl.cyclic());
  }
}
