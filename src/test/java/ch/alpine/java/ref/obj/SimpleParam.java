// code by jph
package ch.alpine.java.ref.obj;

import java.awt.Color;
import java.util.Properties;

import ch.alpine.java.ref.FieldSelection;
import ch.alpine.tensor.mat.re.Pivots;

public class SimpleParam extends BaseSimple {
  private final int ignore_int = 2;
  private final Integer ignore_Integer = 2;
  private final String ignore_final_String = "asd";
  @FieldSelection(list = "a|b|c")
  public String string = "abc";
  public Boolean flag = false;
  public Pivots pivot = Pivots.ARGMAX_ABS;
  // public Scalar[] scalars = { Pi.VALUE, RealScalar.ZERO, ComplexScalar.I };
  public final AnotherParam anotherParam = new AnotherParam();
  public final EmptyParam emptyParam = new EmptyParam();
  public final NestedParam[] nestedParams = { new NestedParam(), new NestedParam() };

  public static void main(String[] args) {
    SimpleParam simpleParam = new SimpleParam();
    simpleParam.nestedParams[1].some = false;
    simpleParam.nestedParams[1].text = "here!";
    simpleParam.nestedParams[1].anotherParam.color = Color.BLUE;
    {
      ObjectFieldString objectFieldPrint = new ObjectFieldString();
      ObjectFieldVisitor.of(objectFieldPrint, simpleParam);
      System.out.println(objectFieldPrint);
    }
    ObjectFieldExport objectFieldExport = new ObjectFieldExport();
    ObjectFieldVisitor.of(objectFieldExport, simpleParam);
    Properties properties = objectFieldExport.getProperties();
    {
      ObjectFieldImport objectFieldImport = new ObjectFieldImport(properties);
      SimpleParam simpleCopy = new SimpleParam();
      ObjectFieldVisitor.of(objectFieldImport, simpleCopy);
      {
        ObjectFieldString objectFieldPrint = new ObjectFieldString();
        ObjectFieldVisitor.of(objectFieldPrint, simpleCopy);
        System.out.println(objectFieldPrint);
      }
    }
  }
}
