package ch.alpine.java.ref.gui;

import ch.alpine.java.ref.ObjectProperties;
import junit.framework.TestCase;

public class StoredExtensionTest extends TestCase {
  public void testSimple() {
    ObjectProperties.wrap(new StoredExtension());
  }
}
