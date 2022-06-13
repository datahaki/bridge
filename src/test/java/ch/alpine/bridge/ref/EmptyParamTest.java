// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

class EmptyParamTest {
  @Test
  void testGui() {
    EmptyParam emptyParam = new EmptyParam();
    ObjectFieldVisitor objectFieldVisitor = new ObjectFieldAll() {
      @Override
      public void push(String key, Field field, Integer index) {
        fail();
      }

      @Override
      public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
        fail();
      }

      @Override
      public void pop() {
        fail();
      }
    };
    ObjectFields.of(emptyParam, objectFieldVisitor);
  }

  @Test
  void testIo() {
    EmptyParam emptyParam = new EmptyParam();
    ObjectFieldVisitor objectFieldVisitor = new ObjectFieldIo() {
      @Override
      public void push(String key, Field field, Integer index) {
        fail();
      }

      @Override
      public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
        fail();
      }

      @Override
      public void pop() {
        fail();
      }
    };
    ObjectFields.of(emptyParam, objectFieldVisitor);
  }
}
