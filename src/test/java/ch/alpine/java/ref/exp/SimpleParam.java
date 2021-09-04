// code by jph
package ch.alpine.java.ref.exp;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.num.Pi;

public class SimpleParam {
  public String string;
  public Scalar[] scalars = { Pi.VALUE, RealScalar.ZERO };
  public List<Tensor> tensors;
  public NestedParam nestedParam = new NestedParam();

  public static void main(String[] args) {
    Class<?> cls = SimpleParam.class;
    SimpleParam simpleParam = new SimpleParam();
    for (Field field : cls.getDeclaredFields()) {
      System.out.println("---");
      System.out.println(field);
      Class<?> type = field.getType();
      System.out.println(type);
      System.out.println(field.getGenericType());
      System.out.println("isArray=" + type.isArray());
      Class<?> cls2 = Scalar[].class;
      System.out.println(type.equals(cls2));
      System.out.println(type.getComponentType());
      if (type.isArray()) {
        try {
          Object object = field.get(simpleParam);
          Object[] s = (Object[]) object;
          System.out.println("length=" + s.length);
          s[0] = RealScalar.of(-2);
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    System.out.println(Arrays.asList(simpleParam.scalars));
  }
}
