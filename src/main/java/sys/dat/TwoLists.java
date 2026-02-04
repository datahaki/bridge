// code by jph
package sys.dat;

import java.util.List;

import ch.alpine.tensor.ext.Lists;

public enum TwoLists {
  ;
  /** @param sub sorted(!) list of Comparable
   * @param mySuper sorted(!) list of Comparable
   * @return true, if mySub is subset of mySuper */
  public static <T extends Comparable<T>> boolean areSortedSublists(List<T> sub, List<T> mySuper) {
    int index = 0;
    int count = 0;
    // assertListSorted(mySub);
    // assertListSorted(mySuper);
    while (index < mySuper.size() && count < sub.size()) {
      if (mySuper.get(index).compareTo(sub.get(count)) < 0)
        ++index;
      else //
      if (mySuper.get(index).equals(sub.get(count))) {
        ++index;
        ++count;
      } else
        return false;
    }
    return count == sub.size();
  }

  /** symmetric distance because max function is symmetric
   * 
   * @param sub
   * @param mySuper
   * @return */
  public static int symmetricDistance(List<Integer> sub, List<Integer> mySuper) {
    return Math.max(directionalDistance(sub, mySuper), directionalDistance(mySuper, sub));
  }

  /** symmetric pseudo distance because min function is symmetric
   * 
   * @param sub
   * @param mySuper
   * @return */
  public static int directionalDistanceMin(List<Integer> sub, List<Integer> mySuper) {
    return Math.min(directionalDistance(sub, mySuper), directionalDistance(mySuper, sub));
  }

  public static int directionalDistance(List<Integer> sub, List<Integer> mySuper) {
    int max = 0;
    if (!mySuper.isEmpty())
      for (int myInt : sub) {
        int min = Math.abs(myInt - mySuper.getFirst());
        for (int myCmp : Lists.rest(mySuper))
          min = Math.min(min, Math.abs(myInt - myCmp));
        max = Math.max(min, max);
      }
    return max;
  }
}
