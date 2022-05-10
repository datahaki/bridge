// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class InvalidFieldCollectionTest {
  @Test
  public void testFieldClip() {
    FieldClipInvalidExample fieldClipInvalidExample = new FieldClipInvalidExample();
    assertFalse(InvalidFieldCollection.isEmpty(fieldClipInvalidExample));
  }

  @Test
  public void testFieldInteger() {
    FieldIntegerInvalidExample fieldIntegerInvalidExample = new FieldIntegerInvalidExample();
    assertFalse(InvalidFieldCollection.isEmpty(fieldIntegerInvalidExample));
  }
}