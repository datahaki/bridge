// code by jph
package sys.dat;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;

public enum MapOperations {
  ;
  public static <T> void translate(Map<Integer, T> src, int offset, Map<Integer, T> dst) {
    for (Entry<Integer, T> myEntry : src.entrySet())
      dst.put(myEntry.getKey() + offset, myEntry.getValue());
  }

  public static <T> void translate(Map<Integer, T> map, int offset) {
    Map<Integer, T> buffer = new HashMap<>(map);
    map.clear();
    translate(buffer, offset, map);
  }

  public static <T> void scaleMap(NavigableMap<Integer, T> navigableMap, int factor) {
    Map<Integer, T> map = new HashMap<>();
    for (Entry<Integer, T> entry : navigableMap.entrySet())
      map.put(factor * entry.getKey(), entry.getValue());
    navigableMap.clear();
    navigableMap.putAll(map);
  }

  public static <T> void scaleMapSafe(NavigableMap<Integer, T> navigableMap, int div) {
    Map<Integer, T> map = new HashMap<>();
    for (Entry<Integer, T> entry : navigableMap.entrySet())
      map.put(Math.floorDiv(entry.getKey(), div), entry.getValue());
    navigableMap.clear();
    navigableMap.putAll(map);
  }
}
