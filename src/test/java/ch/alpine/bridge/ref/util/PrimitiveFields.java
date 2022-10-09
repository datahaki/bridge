// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;

public class PrimitiveFields {
  public String string;
  public Integer asd;
  public int int_value;
  public byte byte_value;
  public int[] int_array;

  public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
    PrimitiveFields primitiveFields = new PrimitiveFields();
    System.out.println(primitiveFields.int_value);
    int count = 0;
    for (Field field : PrimitiveFields.class.getDeclaredFields()) {
      Class<?> type = field.getType();
      System.out.println("---");
      System.out.println("[" + type.getCanonicalName() + "]"); // "int"
      System.out.println(type.isPrimitive());
      System.out.println(type.isArray());
      System.out.println(type.getComponentType());
      if (count == 2)
        field.set(primitiveFields, 2);
      if (count == 3)
        field.set(primitiveFields, (byte) 3);
      if (count == 4)
        field.set(primitiveFields, new int[] { 2, 3, 4 });
      ++count;
    }
    System.out.println(primitiveFields.int_value);
    System.out.println(primitiveFields.byte_value);
  }
}
