// code by jph
package ch.alpine.bridge.ref.util;

import ch.alpine.bridge.ref.ann.FieldExistingDirectory;
import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
public class ExampleBadDirectory {
  @FieldExistingDirectory
  public String text = "abc";
}
