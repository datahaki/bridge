// code by jph
package ch.alpine.bridge.ref.ann;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ex.AnnotatedContainer;
import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.qty.UnitSystem;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

class FieldClipsTest {
  @Test
  void testSimple() throws Exception {
    Field field = AnnotatedContainer.class.getField("clipped");
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    Clip clip = FieldClips.wrap(fieldClip).clip();
    assertEquals(clip.min(), RealScalar.of(2));
    assertEquals(clip.max(), RealScalar.of(6));
  }

  @Test
  void testQuantity() throws Exception {
    Field field = AnnotatedContainer.class.getField("quantityClipped");
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    Clip clip = FieldClips.wrap(fieldClip).clip();
    assertEquals(clip.min(), Quantity.of(2, "kW"));
    assertEquals(clip.max(), Quantity.of(6, "kW"));
  }

  /** Example: it was discovered that due to floating point imprecision
   * 20[L*min^-1] < 20.0[L*min^-1] when values are converted to SI unit "m^3*s^-1"
   * although equality is expected. As a remedy therefore the smaller/larger of
   * the exact and numeric value of min/max is taken. */
  @Test
  void testIssue1() {
    Clip clip = Clips.interval(Quantity.of(0, "L*min^-1"), Quantity.of(20, "L*min^-1"));
    ScalarUnaryOperator suo1 = UnitSystem.SI();
    ScalarUnaryOperator suo2 = UnitConvert.SI().to(QuantityUnit.of(clip));
    ScalarUnaryOperator suo = suo1.andThen(suo2);
    assertTrue(clip.isInside(suo.apply(Quantity.of(20, "L*min^-1"))));
    Scalar max = suo.apply(Quantity.of(20.0, "L*min^-1"));
    max.zero();
  }

  @Test
  void testIssue2() {
    Clip clip = Clips.interval(Quantity.of(0, "L*min^-1"), Quantity.of(20.0, "L*min^-1"));
    ScalarUnaryOperator suo1 = UnitSystem.SI();
    ScalarUnaryOperator suo2 = UnitConvert.SI().to(QuantityUnit.of(clip));
    ScalarUnaryOperator suo = suo1.andThen(suo2);
    assertTrue(clip.isInside(suo.apply(Quantity.of(20, "L*min^-1"))));
    Scalar max = suo.apply(Quantity.of(20.0, "L*min^-1"));
    max.zero();
    // assertTrue(clip.isInside(suo.apply(Quantity.of(20.0, "L*min^-1"))));
  }

  @FieldClip(min = "0[super]", max = "Infinity[super]")
  public Scalar awesome = Quantity.of(4, "super");

  @Test
  void testSimple2() throws NoSuchFieldException, SecurityException {
    FieldClipsTest fieldClipsTest = new FieldClipsTest();
    Field field = fieldClipsTest.getClass().getField("awesome");
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    Clip clip = FieldClips.wrap(fieldClip).clip();
    assertEquals(clip.max(), Quantity.of(DoubleScalar.POSITIVE_INFINITY, "super"));
  }

  @Test
  void testWithUnit() {
    FieldClip fieldClip = new FieldClip() {
      @Override
      public Class<? extends Annotation> annotationType() {
        return FieldClip.class;
      }

      @Override
      public String min() {
        return "123[m]";
      }

      @Override
      public String max() {
        return "123[km]";
      }

      @Override
      public boolean useMinUnit() {
        return true;
      }

      @Override
      public boolean integer() {
        return false;
      }
    };
    FieldClips fieldClips = FieldClips.wrap(fieldClip);
    Clip clip = fieldClips.clip();
    clip.requireInside(Quantity.of(124, "m"));
  }

  @Test
  void testWithUnitMax() {
    FieldClip fieldClip = new FieldClip() {
      @Override
      public Class<? extends Annotation> annotationType() {
        return FieldClip.class;
      }

      @Override
      public String min() {
        return "123[m]";
      }

      @Override
      public String max() {
        return "123[km]";
      }

      @Override
      public boolean useMinUnit() {
        return false;
      }

      @Override
      public boolean integer() {
        return false;
      }
    };
    FieldClips fieldClips = FieldClips.wrap(fieldClip);
    Clip clip = fieldClips.clip();
    clip.requireInside(Quantity.of(122, "km"));
  }

  @Test
  void test() {
    FieldClip fieldClip = new FieldClip() {
      @Override
      public Class<? extends Annotation> annotationType() {
        return FieldClip.class;
      }

      @Override
      public String min() {
        return "123";
      }

      @Override
      public String max() {
        return "1230";
      }

      @Override
      public boolean useMinUnit() {
        return true;
      }

      @Override
      public boolean integer() {
        return false;
      }
    };
    FieldClips fieldClips = FieldClips.wrap(fieldClip);
    assertFalse(fieldClips.test(DoubleScalar.INDETERMINATE));
    assertFalse(fieldClips.test(DoubleScalar.POSITIVE_INFINITY));
    assertFalse(fieldClips.test(DoubleScalar.NEGATIVE_INFINITY));
    assertEquals(fieldClips.interp(RationalScalar.HALF).toString(), "1353/2");
  }

  @Test
  void testInfty() {
    FieldClip fieldClip = new FieldClip() {
      @Override
      public Class<? extends Annotation> annotationType() {
        return FieldClip.class;
      }

      @Override
      public String min() {
        return "0";
      }

      @Override
      public String max() {
        return "Infinity";
      }

      @Override
      public boolean useMinUnit() {
        return true;
      }

      @Override
      public boolean integer() {
        return false;
      }
    };
    FieldClips fieldClips = FieldClips.wrap(fieldClip);
    assertFalse(fieldClips.test(DoubleScalar.INDETERMINATE));
    assertTrue(fieldClips.test(DoubleScalar.POSITIVE_INFINITY));
    assertFalse(fieldClips.test(DoubleScalar.NEGATIVE_INFINITY));
  }
}
