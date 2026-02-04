// code by jph
package sys.dat;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

// TODO MIDI class design is immature
public class BivariateMap<P1, P2, V> implements Serializable {
  record BiEntry<P1, P2, V>(P1 p1, P2 p2, V value) implements Serializable {
  }

  private final Map<P1, Map<P2, V>> map = new HashMap<>();
  private final V unput;

  /** @param unput response for empty get requests */
  public BivariateMap(V unput) {
    this.unput = unput;
  }

  public void put(P1 p1) {
    if (!map.containsKey(p1))
      map.put(p1, new HashMap<>());
  }

  public void put(P1 p1, P2 p2, V value) {
    put(p1);
    map.get(p1).put(p2, value);
  }

  public boolean containsKey(P1 p1, P2 p2) {
    return map.containsKey(p1) //
        && map.get(p1).containsKey(p2);
  }

  public V get(P1 p1, P2 p2) {
    if (!map.containsKey(p1))
      return unput;
    Map<P2, V> row = map.get(p1);
    return row.getOrDefault(p2, unput);
  }

  public Collection<P1> keySet() {
    return map.keySet();
  }

  /** @param p1
   * @return unmodifiable map */
  public Map<P2, V> getRowMap(P1 p1) {
    return map.containsKey(p1) ? Collections.unmodifiableMap(map.get(p1)) : new HashMap<>();
  }

  /** @param p1
   * @param collection
   * @return unmodifiable map */
  public Map<P2, V> getRowMap(P1 p1, Collection<P2> collection) {
    Map<P2, V> myReturn = new HashMap<>();
    for (P2 p2 : collection)
      myReturn.put(p2, get(p1, p2));
    return Collections.unmodifiableMap(myReturn);
  }

  public Map<P1, V> getColumnMap(P2 myP2) {
    Map<P1, V> myReturn = new HashMap<>();
    for (Entry<P1, Map<P2, V>> myEntry : map.entrySet())
      if (myEntry.getValue().containsKey(myP2))
        myReturn.put(myEntry.getKey(), myEntry.getValue().get(myP2));
    return myReturn;
  }

  public Map<P1, V> getColumnMap(P2 myP2, Collection<P1> myCollection) {
    Map<P1, V> myReturn = new HashMap<>();
    for (P1 myP1 : myCollection)
      myReturn.put(myP1, get(myP1, myP2));
    return myReturn;
  }

  public void clear() {
    map.clear();
  }

  public Set<Entry<P1, Map<P2, V>>> entrySet() {
    return map.entrySet();
  }

  public BivariateMap<P2, P1, V> transpose() {
    BivariateMap<P2, P1, V> bivariateMap = new BivariateMap<>(unput);
    for (Entry<P1, Map<P2, V>> myEntry : map.entrySet())
      for (Entry<P2, V> myEntrySub : myEntry.getValue().entrySet())
        bivariateMap.put(myEntrySub.getKey(), myEntry.getKey(), myEntrySub.getValue());
    return bivariateMap;
  }

  public void printout() {
    for (P1 myP1 : map.keySet()) {
      System.out.println(myP1 + " =>");
      for (Entry<P2, V> entry : getRowMap(myP1).entrySet())
        System.out.println(" " + entry.getKey() + " -> " + entry.getValue());
    }
  }

  @SuppressWarnings("unchecked")
  public void incr(P1 myP1, P2 myP2) {
    V value = get(myP1, myP2);
    if (value instanceof Integer number && Objects.nonNull(unput)) {
      put(myP1, myP2, (V) (Integer) (number + 1));
    }
  }
}
