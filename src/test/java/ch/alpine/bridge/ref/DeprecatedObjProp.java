// code by jph
package ch.alpine.bridge.ref;

import java.util.Objects;
import java.util.Properties;

enum DeprecatedObjProp {
  ;
  /** @param object
   * @return new instance of {@link Properties} */
  public static Properties properties(Object object) {
    Properties properties = new Properties();
    ObjectFieldVisitor objectFieldVisitor = new ObjectFieldIo() {
      @Override // from ObjectFieldVisitor
      public void accept(String prefix, FieldWrap fieldWrap, Object object, Object value) {
        if (Objects.nonNull(value))
          properties.setProperty(prefix, fieldWrap.toString(value));
      }
    };
    ObjectFields.of(object, objectFieldVisitor);
    return properties;
  }
}
