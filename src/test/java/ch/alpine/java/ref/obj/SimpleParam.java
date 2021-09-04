// code by jph
package ch.alpine.java.ref.obj;

import ch.alpine.tensor.ComplexScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.mat.re.Pivots;
import ch.alpine.tensor.num.Pi;

public class SimpleParam {
  public String string = "abc";
  public Boolean flag = false;
  public Pivots pivot = Pivots.ARGMAX_ABS;
  public Scalar[] scalars = { Pi.VALUE, RealScalar.ZERO, ComplexScalar.I };
  public AnotherParam anotherParam = new AnotherParam();
  public EmptyParam emptyParam = new EmptyParam();
  public NestedParam[] nestedParams = { new NestedParam(), new NestedParam() };

  public static void main(String[] args) {
    SimpleParam simpleParam = new SimpleParam();
    simpleParam.nestedParams[1].some = false;
    simpleParam.nestedParams[1].text = "here!";
    simpleParam.nestedParams[1].tensors[1] = ComplexScalar.of(1, 2);
    ObjectFieldVisitor.of(ObjectFieldPrint.INSTANCE, simpleParam);
  }
}
