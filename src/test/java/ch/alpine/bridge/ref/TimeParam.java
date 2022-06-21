// code by jph
package ch.alpine.bridge.ref;

import java.time.LocalDateTime;

import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
public class TimeParam {
  public LocalDateTime dateTime = LocalDateTime.now();
}
