// code by jph
package ch.alpine.bridge.usr;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TargetSumTest {
  @Test
  void testSimple() {
    int nums1[] = { 2, 5, 10, 4 }; // true
    assertTrue(TargetSum.check(nums1, 0));
    assertTrue(TargetSum.check(nums1, 11));
    assertTrue(TargetSum.check(nums1, 12));
    assertTrue(TargetSum.check(nums1, 19));
    assertFalse(TargetSum.check(nums1, 1));
    assertFalse(TargetSum.check(nums1, 3));
    assertFalse(TargetSum.check(nums1, 8));
  }

  @Test
  void testDuplicates() {
    int nums1[] = { 2, 5, 10, 2 }; // true
    assertTrue(TargetSum.check(nums1, 0));
    assertTrue(TargetSum.check(nums1, 9));
    assertTrue(TargetSum.check(nums1, 12));
    assertTrue(TargetSum.check(nums1, 19));
    assertFalse(TargetSum.check(nums1, 1));
    assertFalse(TargetSum.check(nums1, 3));
    assertFalse(TargetSum.check(nums1, 8));
  }
}
