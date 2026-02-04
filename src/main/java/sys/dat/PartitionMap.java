// code by jph
package sys.dat;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import ch.alpine.tensor.red.Tally;

// TODO MIDI this seems to be something like InverseCDF and BiningMethod!?
public class PartitionMap {
  /** @param collection of integers to partition
   * @param size number of buckets
   * @return */
  public static PartitionMap createFrom(Collection<Integer> collection, int size) {
    NavigableMap<Integer, Long> navigableMap = Tally.sorted(collection.stream());
    // System.out.println(navigableMap);
    // ---
    if (navigableMap.containsKey(0))
      throw new IllegalArgumentException();
    // ---
    int count = 0;
    int thres = 0;
    int bucket = 0;
    PartitionMap partitionMap = new PartitionMap();
    partitionMap.collection_size = collection.size();
    for (Entry<Integer, Long> entry : navigableMap.entrySet()) {
      count += entry.getValue();
      if (thres < count) {
        partitionMap.navigableMap.put(entry.getKey(), ++bucket);
        thres = collection.size() * bucket / size;
        // System.out.println(partitionMap.navigableMap);
      }
    }
    return partitionMap;
  }

  // ---
  public final NavigableMap<Integer, Integer> navigableMap = new TreeMap<>();
  private int collection_size; // just for info

  public PartitionMap() {
    navigableMap.put(0, 0); // entry guarantees getOrdinal(0)==0
  }

  /** @param myInt geq 0
   * @return */
  public int getOrdinal(int myInt) {
    return navigableMap.lowerEntry(myInt + 1).getValue();
  }

  @Override
  public String toString() {
    return "[" + collection_size + "] -> " + navigableMap.keySet();
  }

  public int getCollectionSize() {
    return collection_size;
  }
}
