// code by jph
package ch.alpine.java.ref;

import ch.alpine.java.util.AssertFail;
import junit.framework.TestCase;

public class ObjectFieldsTest extends TestCase {
  public void testSimple() {
    AssertFail.of(() -> ObjectFields.of(null, null));
  }
}
