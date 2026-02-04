// code by jph
package sys.dat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** iterator over list of items (by reference, i.e. items are not cloned) */
public class OuterProduct<T> implements Iterator<List<T>>, Iterable<List<T>> {
  private final List<List<T>> list;
  private final OuterProductInteger outerProductInteger;

  public OuterProduct(List<List<T>> list) {
    this.list = list;
    int[] myInt = new int[list.size()];
    int count = 0;
    for (List<T> bucket : list) {
      myInt[count] = bucket.size();
      ++count;
    }
    outerProductInteger = new OuterProductInteger(myInt, true);
  }

  @Override
  public boolean hasNext() {
    return outerProductInteger.hasNext();
  }

  @Override
  public List<T> next() {
    outerProductInteger.next(); // first evil
    List<T> myList = new ArrayList<>();
    int count = 0;
    for (List<T> myBucket : this.list) {
      myList.add(myBucket.get(outerProductInteger.myInteger[count])); // second evil
      ++count;
    }
    return myList;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Iterator<List<T>> iterator() {
    return this;
  }
}
