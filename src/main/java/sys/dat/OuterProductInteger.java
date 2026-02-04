// code by jph
package sys.dat;

import java.util.Iterator;
import java.util.List;

import sys.mat.IntRange;

/** serves as the algorithm for OuterProduct */
class OuterProductInteger implements Iterator<List<Integer>>, Iterable<List<Integer>> {
  final Integer[] myInteger;
  final int[] value;
  final int[] direction;
  int count = 0;
  final int total;

  public OuterProductInteger(int[] value, boolean forward) {
    int total = 1;
    myInteger = new Integer[value.length];
    for (int c0 = 0; c0 < value.length; ++c0) {
      myInteger[c0] = 0;
      total *= value[c0];
    }
    this.value = value;
    this.total = total;
    direction = new int[value.length];
    for (int c0 : IntRange.positive(value.length))
      direction[c0] = forward ? value.length - c0 - 1 : c0;
  }

  // @Deprecated // because rather use explicit
  public OuterProductInteger(int[] myInt) {
    this(myInt, false);
  }

  @Override
  public boolean hasNext() {
    return count < total;
  }

  @Override
  public List<Integer> next() {
    if (0 < count)
      // for (int c0 = myInt.length - 1; 0 <= c0; --c0) {
      for (int c0 : direction) {
        ++myInteger[c0];
        myInteger[c0] %= value[c0];
        if (myInteger[c0] != 0)
          break;
      }
    ++count;
    return List.of(myInteger);
  }

  @Override
  public Iterator<List<Integer>> iterator() {
    return this;
  }
}
