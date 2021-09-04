// code by jph
package ch.alpine.java.ref.obj;

import java.util.Objects;
import java.util.Properties;

import ch.alpine.java.ref.FieldWrap;

public class ObjectFieldExport implements ObjectFieldCallback {
  private final Properties properties = new Properties();

  @Override
  public void elemental(String prefix, FieldWrap fieldWrap, Object object, Object value) {
    if (Objects.nonNull(value))
      properties.setProperty(prefix, fieldWrap.toString(value));
  }

  public Properties getProperties() {
    return properties;
  }
}
