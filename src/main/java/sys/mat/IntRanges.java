// code by jph
package sys.mat;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.Stream;

// TODO MIDI use clip
public class IntRanges<T extends IntRange> implements Iterable<T> {
  private final NavigableSet<T> navigableSet = new TreeSet<>();

  private boolean attemptUnion(T myType, T myIntRange) {
    if (myType != null && myType.isUnionable(myIntRange)) {
      navigableSet.remove(myType);
      union(myType.union(myIntRange));
      return true;
    }
    return false;
  }

  public void union(T myIntRange) {
    if (attemptUnion(navigableSet.lower(myIntRange), myIntRange))
      return;
    if (attemptUnion(navigableSet.higher(myIntRange), myIntRange))
      return;
    if (!myIntRange.isEmpty())
      navigableSet.add(myIntRange);
  }

  public boolean isEmpty() {
    return navigableSet.isEmpty();
  }

  public T getIntervalWith(int value) {
    for (T myType : this)
      if (myType.isInside(value))
        return myType;
    return null;
  }

  // public boolean isMember(int value) {
  // myNavigableSet.lower(value);
  // }
  //
  // public void removeInt(int value) {
  // IntRanges<IntRange> myIntRanges = new IntRanges<>();
  // myIntRanges.getCover();
  // }
  public void intersect(IntRange myIntRange) {
    intersect(myIntRange.min(), myIntRange.max());
  }

  public void intersect(int min, int max) {
    Collection<T> list = new LinkedList<>(navigableSet);
    navigableSet.clear();
    for (T myType : list)
      union(myType.intersect(min, max));
  }

  public <More extends IntRange> void intersect(IntRanges<More> myIntRanges) {
    Collection<T> list = new LinkedList<>(navigableSet);
    navigableSet.clear();
    for (T myType : list)
      for (More myPart : myIntRanges)
        union(myType.intersect(myPart)); // inefficient algorithm
  }

  public int min() {
    return navigableSet.first().min();
  }

  public int max() {
    return navigableSet.last().max();
  }

  public IntRange getCover() {
    return new IntRange(min(), max());
  }

  public Stream<Integer> toIntStream() {
    return navigableSet.stream() //
        .flatMap(myIntRange -> myIntRange.stream().boxed()); //
  }

  public List<Integer> toIntList() {
    return toIntStream().toList();
  }

  @Override
  public Iterator<T> iterator() {
    return navigableSet.iterator();
  }

  public void print() {
    for (T type : navigableSet)
      System.out.print(type);
    System.out.println();
  }

  public static void demo1() {
    IntRanges<IntRange> myIntRanges = new IntRanges<>();
    System.out.println(myIntRanges.isEmpty());
    myIntRanges.union(new IntRange(-2, 10));
    System.out.println(myIntRanges.isEmpty());
    myIntRanges.intersect(15, 21);
    System.out.println(myIntRanges.isEmpty());
    myIntRanges.union(new IntRange(-2, 10));
    myIntRanges.union(new IntRange(20, 25));
    myIntRanges.union(new IntRange(11, 20));
    myIntRanges.union(new IntRange(0, 0));
    myIntRanges.intersect(4, 16);
    myIntRanges.print();
    for (int myInt : myIntRanges.toIntList())
      System.out.println(myInt);
  }
}
