// code by jph
package ch.alpine.bridge.ref;

import java.awt.Color;

import ch.alpine.bridge.ref.ann.FieldLabel;
import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
public class EmptyParam {
  @FieldLabel("select button background")
  public final Color selectColor = new Color(128, 255, 0);
}
