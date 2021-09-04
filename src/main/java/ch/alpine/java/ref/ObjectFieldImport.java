// code by jph
package ch.alpine.java.ref;

import java.util.Objects;
import java.util.Properties;

public class ObjectFieldImport implements ObjectFieldVisitor {
  private final Properties properties;

  public ObjectFieldImport(Properties properties) {
    this.properties = properties;
  }

  @Override
  public void accept(String prefix, FieldWrap fieldWrap, Object object, Object value) {
    String string = properties.getProperty(prefix);
    if (Objects.nonNull(string))
      ObjectProperties.setIfValid(fieldWrap, object, string);
  }
}
