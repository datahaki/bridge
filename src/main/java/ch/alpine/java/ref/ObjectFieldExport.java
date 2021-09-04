// code by jph
package ch.alpine.java.ref;

import java.util.Objects;
import java.util.Properties;

public class ObjectFieldExport implements ObjectFieldVisitor {
  private final Properties properties = new Properties();

  @Override // from ObjectFieldVisitor
  public void accept(String prefix, FieldWrap fieldWrap, Object object, Object value) {
    if (Objects.nonNull(value))
      properties.setProperty(prefix, fieldWrap.toString(value));
  }

  public Properties getProperties() {
    return properties;
  }
}
