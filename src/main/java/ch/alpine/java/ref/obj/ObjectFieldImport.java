// code by jph
package ch.alpine.java.ref.obj;

import java.util.Objects;
import java.util.Properties;

import ch.alpine.java.ref.FieldWrap;
import ch.alpine.java.ref.ObjectProperties;

public class ObjectFieldImport implements ObjectFieldCallback {
  private final Properties properties;

  public ObjectFieldImport(Properties properties) {
    this.properties = properties;
  }

  @Override
  public void elemental(String prefix, FieldWrap fieldWrap, Object object, Object value) {
    String string = properties.getProperty(prefix);
    if (Objects.nonNull(string))
      ObjectProperties.setIfValid(fieldWrap, object, string);
  }
}
