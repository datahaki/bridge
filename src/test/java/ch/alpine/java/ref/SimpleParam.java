// code by jph
package ch.alpine.java.ref;

import java.awt.Color;
import java.io.File;
import java.util.Properties;

import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.mat.re.Pivots;

public class SimpleParam extends BaseParam {
  private final int ignore_int = 2;
  private final Integer ignore_Integer = 2;
  private final String ignore_final_String = "asd";
  @FieldSelection(list = "a|b|c")
  public String string = "abc";
  public Boolean flag = false;
  public Pivots pivot = Pivots.ARGMAX_ABS;
  // public Scalar[] scalars = { Pi.VALUE, RealScalar.ZERO, ComplexScalar.I };
  public final AnotherParam anotherParam = new AnotherParam();
  public final NestedParam[] nestedParams = { new NestedParam(), new NestedParam() };

  public static class AnotherParam {
    public File file = HomeDirectory.file();
    public Color color = Color.RED;
  }

  public static class NestedParam extends BaseParam {
    public Boolean some = true;
    public String text = "grolley";
    public final AnotherParam anotherParam = new AnotherParam();
  }

  public static void main(String[] args) {
    SimpleParam simpleParam = new SimpleParam();
    simpleParam.nestedParams[1].some = false;
    simpleParam.nestedParams[1].text = "here!";
    simpleParam.nestedParams[1].anotherParam.color = Color.BLUE;
    {
      System.out.println(ObjectFieldString.of(simpleParam));
    }
    ObjectFieldExport objectFieldExport = new ObjectFieldExport();
    ObjectFields.of(simpleParam, objectFieldExport);
    Properties properties = objectFieldExport.getProperties();
    {
      ObjectFieldImport objectFieldImport = new ObjectFieldImport(properties);
      SimpleParam simpleCopy = new SimpleParam();
      ObjectFields.of(simpleCopy, objectFieldImport);
      {
        System.out.println(ObjectFieldString.of(simpleCopy));
      }
    }
  }
}
