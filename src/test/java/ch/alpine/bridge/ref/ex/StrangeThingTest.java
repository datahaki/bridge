// code by jph
package ch.alpine.bridge.ref.ex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.util.ObjectFieldAll;
import ch.alpine.bridge.ref.util.ObjectFieldVisitor;
import ch.alpine.bridge.ref.util.ObjectFields;

class StrangeThingTest {
  @Test
  void test() {
    StrangeThing strangeThing = new StrangeThing();
    ObjectFieldVisitor objectFieldAll = new ObjectFieldAll() {
      @Override
      public void push(String key, Field field, Integer index) {
        fail();
      }

      @Override
      public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
        assertEquals(key, "string");
      }
    };
    ObjectFields.of(strangeThing, objectFieldAll);
  }
}
