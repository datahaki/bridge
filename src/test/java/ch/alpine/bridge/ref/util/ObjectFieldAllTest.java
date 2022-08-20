// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldFuse;
import ch.alpine.bridge.ref.ann.FieldPreferredWidth;
import ch.alpine.bridge.ref.ann.FieldSlider;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;

class ObjectFieldAllTest {
  @ReflectionMarker
  public static class Param {
    @FieldSlider
    @FieldClip(min = "0", max = "2")
    @FieldPreferredWidth(250)
    public Scalar alpha = RealScalar.of(2);
    @FieldPreferredWidth(150)
    public Integer length = 300;
    @FieldFuse("generate")
    public transient Boolean generate = true;
  }

  private final Param param = new Param();
  int countGui = 0;
  int countIo = 0;

  @Test
  void test() {
    ObjectFields.of(param, new ObjectFieldAll() {
      @Override
      public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
        ++countGui;
      }
    });
    ObjectFields.of(param, new ObjectFieldIo() {
      @Override
      public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
        ++countIo;
      }
    });
    assertEquals(countIo + 1, countGui); // one transient is not part of IO
  }
}
