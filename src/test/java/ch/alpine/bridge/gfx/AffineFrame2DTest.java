package ch.alpine.bridge.gfx;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.mat.IdentityMatrix;

class AffineFrame2DTest {
  @Test
  void test() {
    AffineFrame2D affineFrame2D = new AffineFrame2D(IdentityMatrix.of(3));
    assertTrue(affineFrame2D.isAxisAligned());
  }
}
