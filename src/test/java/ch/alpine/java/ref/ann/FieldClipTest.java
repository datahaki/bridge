// code by jph
package ch.alpine.java.ref.ann;

import java.lang.reflect.Field;

import ch.alpine.java.ref.gui.PanelFieldsEditor;
import ch.alpine.java.util.AssertFail;
import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Clip;
import junit.framework.TestCase;

public class FieldClipTest extends TestCase {
  @ReflectionMarker
  public static class FailEx {
    @FieldClip(min = "0[A]", max = "3[W]")
    public Scalar current = Quantity.of(4, "A");
  }

  public void testFailEx() {
    FailEx fieldClipCorrupt = new FailEx();
    AssertFail.of(() -> new PanelFieldsEditor(fieldClipCorrupt));
  }

  @FieldClip(min = "0[super]", max = "Infinity[super]")
  public Scalar awesome = Quantity.of(4, "super");

  public void testSimple() throws NoSuchFieldException, SecurityException {
    FieldClipTest fieldClipCorrupt = new FieldClipTest();
    Field field = fieldClipCorrupt.getClass().getField("awesome");
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    Clip clip = FieldClips.of(fieldClip);
    assertEquals(clip.max(), Quantity.of(DoubleScalar.POSITIVE_INFINITY, "super"));
  }
}
