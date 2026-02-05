// code by jph
package showcase.data;

import java.awt.Color;
import java.time.LocalTime;

import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
public class ColorParam {
  public Color color = Color.RED;
  public LocalTime localTime = LocalTime.NOON;
}
