// code by jph
package ch.alpine.java.ref.obj;

import java.util.Properties;

import ch.alpine.java.ref.FieldWrap;

public class ObjectFieldExport implements ObjectFieldCallback {
  private final Properties properties = new Properties();

  @Override
  public void elemental(String prefix, FieldWrap fieldWrap, Object object, Object value) {
    properties.setProperty(prefix, value.toString());
  }

  public Properties properties() {
    return properties;
  }
}
