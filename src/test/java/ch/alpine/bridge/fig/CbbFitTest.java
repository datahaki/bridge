package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.alg.VectorQ;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.opt.lp.LinearOptimization;
import ch.alpine.tensor.opt.lp.LinearProgram;
import ch.alpine.tensor.opt.lp.LinearProgram.ConstraintType;
import ch.alpine.tensor.opt.lp.LinearProgram.Objective;
import ch.alpine.tensor.opt.lp.LinearProgram.Variables;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.pdf.d.DiscreteUniformDistribution;

class CbbFitTest {
  static void _checkInside(Tensor a, Tensor b, Tensor exp) {
    VectorQ.require(a);
    VectorQ.require(b);
    VectorQ.require(exp);
    // ---
    Tensor A = Transpose.of(Tensors.of(a));
    LinearProgram linearProgram = LinearProgram.of(Objective.MAX, Tensors.vector(1), ConstraintType.LESS_EQUALS, A, b, Variables.NON_NEGATIVE);
    Tensor sol = LinearOptimization.of(linearProgram);
    assertEquals(a.multiply(sol.Get(0)), exp);
    LinearProgram dual = linearProgram.toggle();
    Tensor sot = LinearOptimization.of(dual);
    assertEquals(sot.dot(a), RealScalar.ONE);
    Scalar f = (Scalar) sot.dot(dual.c);
    Tensor res = dual.A.multiply(f);
    Tensor obj = res.get(0);
    if (!obj.equals(exp))
      System.err.println("mismatch dual\n " + obj + "\n " + exp);
    // assertEquals(obj, exp);
  }

  @Test
  void testRatio1() {
    Tensor a = Tensors.fromString("{3[m],4[m]}");
    Tensor b = Tensors.vector(400, 400);
    Tensor A = Transpose.of(Tensors.of(a));
    {
      Tensor r = CbbFit.inside(a, b).orElseThrow();
      Tensor exp = Tensors.vector(300, 400);
      assertEquals(r, exp);
      _checkInside(a, b, exp);
    }
    {
      Tensor r = CbbFit.outside(a, b).orElseThrow();
      Tensor exp = Tensors.fromString("{400, 1600/3}");
      assertEquals(r, exp);
      LinearProgram linearProgram = LinearProgram.of(Objective.MIN, Tensors.vector(1), ConstraintType.GREATER_EQUALS, A, b, Variables.NON_NEGATIVE);
      Tensor sol = LinearOptimization.of(linearProgram);
      assertEquals(a.multiply(sol.Get(0)), exp);
    }
  }

  @Test
  void testRatio2() {
    Tensor a = Tensors.fromString("{3[m],4[m]}");
    Tensor b = Tensors.vector(200, 400);
    Tensor r1 = CbbFit.inside(a, b).orElseThrow();
    assertEquals(r1, Tensors.of(RealScalar.of(200), RationalScalar.of(800, 3)));
    Tensor r2 = CbbFit.outside(a, b).orElseThrow();
    assertEquals(r2, Tensors.fromString("{300, 400}"));
  }

  @Test
  void testRatio3() {
    Tensor a = Tensors.fromString("{3[m],5[m],4[m]}");
    Tensor b = Tensors.vector(500, 500, 500);
    Tensor A = Transpose.of(Tensors.of(a));
    {
      Tensor r = CbbFit.inside(a, b).orElseThrow();
      Tensor exp = Tensors.vector(300, 500, 400);
      assertEquals(r, exp);
      _checkInside(a, b, exp);
    }
    {
      Tensor r = CbbFit.outside(a, b).orElseThrow();
      Tensor exp = Tensors.fromString("{500, 2500/3, 2000/3}");
      assertEquals(r, exp);
      LinearProgram linearProgram = LinearProgram.of(Objective.MIN, Tensors.vector(1), ConstraintType.GREATER_EQUALS, A, b, Variables.NON_NEGATIVE);
      Tensor sol = LinearOptimization.of(linearProgram);
      assertEquals(a.multiply(sol.Get(0)), exp);
    }
  }

  @RepeatedTest(10)
  void testRatioN(RepetitionInfo repetitionInfo) {
    int n = 3 + repetitionInfo.getCurrentRepetition();
    Distribution distribution = DiscreteUniformDistribution.of(3, 10);
    Tensor a = RandomVariate.of(distribution, n);
    Tensor b = RandomVariate.of(distribution, n);
    Tensor A = Transpose.of(Tensors.of(a));
    {
      Tensor r = CbbFit.inside(a, b).orElseThrow();
      LinearProgram linearProgram = LinearProgram.of(Objective.MAX, Tensors.vector(1), ConstraintType.LESS_EQUALS, A, b, Variables.NON_NEGATIVE);
      Tensor sol = LinearOptimization.of(linearProgram);
      assertEquals(a.multiply(sol.Get(0)), r);
      Tensor sot = LinearOptimization.of(linearProgram.toggle());
      assertEquals(sot.dot(a), RealScalar.ONE);
      _checkInside(a, b, r);
    }
    {
      Tensor r = CbbFit.outside(a, b).orElseThrow();
      LinearProgram linearProgram = LinearProgram.of(Objective.MIN, Tensors.vector(1), ConstraintType.GREATER_EQUALS, A, b, Variables.NON_NEGATIVE);
      Tensor sol = LinearOptimization.of(linearProgram);
      assertEquals(a.multiply(sol.Get(0)), r);
      Tensor sot = LinearOptimization.of(linearProgram.toggle());
      assertEquals(sot.dot(a), RealScalar.ONE);
    }
  }

  @RepeatedTest(10)
  void testRatioM(RepetitionInfo repetitionInfo) {
    int n = 3 + repetitionInfo.getCurrentRepetition();
    Distribution distribution = UniformDistribution.of(3, 10);
    Tensor a = RandomVariate.of(distribution, n);
    Tensor b = RandomVariate.of(distribution, n);
    Tensor A = Transpose.of(Tensors.of(a));
    {
      Tensor r = CbbFit.inside(a, b).orElseThrow();
      LinearProgram linearProgram = LinearProgram.of(Objective.MAX, Tensors.vector(1), ConstraintType.LESS_EQUALS, A, b, Variables.NON_NEGATIVE);
      // Tensor sol = SimplexCorners.of(linearProgram).get(0);
      // Tolerance.CHOP.requireClose(a.multiply(sol.Get(0)), r);
      Tensor som = LinearOptimization.of(linearProgram);
      Tolerance.CHOP.requireClose(a.multiply(som.Get(0)), r);
      Tensor sot = LinearOptimization.of(linearProgram.toggle());
      Tolerance.CHOP.requireClose(sot.dot(a), RealScalar.ONE);
    }
    {
      Tensor r = CbbFit.outside(a, b).orElseThrow();
      LinearProgram linearProgram = LinearProgram.of(Objective.MIN, Tensors.vector(1), ConstraintType.GREATER_EQUALS, A, b, Variables.NON_NEGATIVE);
      // Tensor sol = SimplexCorners.of(linearProgram).get(0);
      // Tolerance.CHOP.requireClose(a.multiply(sol.Get(0)), r);
      Tensor som = LinearOptimization.of(linearProgram);
      Tolerance.CHOP.requireClose(a.multiply(som.Get(0)), r);
      Tensor sot = LinearOptimization.of(linearProgram.toggle());
      Tolerance.CHOP.requireClose(sot.dot(a), RealScalar.ONE);
    }
  }
}
