// code by jph
package ch.alpine.java.ref.util;

import junit.framework.TestCase;

public class InvalidFieldCollectionTest extends TestCase {
  public void testFieldClip() {
    FieldClipInvalidExample fieldClipInvalidExample = new FieldClipInvalidExample();
    assertFalse(InvalidFieldCollection.isEmpty(fieldClipInvalidExample));
  }

  public void testFieldInteger() {
    FieldIntegerInvalidExample fieldIntegerInvalidExample = new FieldIntegerInvalidExample();
    assertFalse(InvalidFieldCollection.isEmpty(fieldIntegerInvalidExample));
  }
}
