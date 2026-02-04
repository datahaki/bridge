// code by jph
package sys.mat;

import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;

/** abstract so that individual names for coordinates can be given */
public abstract class OrderedTuple implements Comparable<OrderedTuple> {
  protected final int x;
  protected final int y;

  public OrderedTuple(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // not used yet
  protected abstract <Type extends OrderedTuple> Type create(int x, int y);

  /** final, because not possible to override by another {@link Comparable} interface */
  @Override
  public final int compareTo(OrderedTuple myOrderedTuple) {
    int cmp = Integer.compare(x, myOrderedTuple.x);
    return cmp != 0 ? cmp : Integer.compare(y, myOrderedTuple.y);
  }

  /** used in NativeFormat */
  @Override
  public String toString() { //
    return String.format("(%d,%d)", x, y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public boolean equals(Object object) {
    return object instanceof OrderedTuple orderedTuple //
        && x == orderedTuple.x //
        && y == orderedTuple.y;
  }

  /** @param myMap use subMap outside to make more efficient
   * @param y
   * @return */
  public static <Type> NavigableMap<Integer, Type> cutMap(Map<? extends OrderedTuple, Type> myMap, int y) {
    NavigableMap<Integer, Type> myNavigableMap = new TreeMap<>();
    for (Entry<? extends OrderedTuple, Type> myEntry : myMap.entrySet())
      if (myEntry.getKey().y == y)
        myNavigableMap.put(myEntry.getKey().x, myEntry.getValue());
    return myNavigableMap;
  }
}
