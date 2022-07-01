// code by jph
package ch.alpine.bridge.ref.util;

import java.util.Objects;
import java.util.Properties;

import ch.alpine.bridge.ref.FieldWrap;

enum ObjectPropertiesExt {
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