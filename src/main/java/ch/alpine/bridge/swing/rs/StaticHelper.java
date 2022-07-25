package ch.alpine.bridge.swing.rs;

import java.util.function.IntUnaryOperator;

import ch.alpine.tensor.ext.Integers;

enum StaticHelper {
  ;
  public static IntUnaryOperator clip(int a, int b) {
    Integers.requireLessEquals(a, b);
    return operand -> Math.min(Math.max(a, operand), b);
  }
}
