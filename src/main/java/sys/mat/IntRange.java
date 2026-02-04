// code by jph
package sys.mat;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.stream.IntStream;

import ch.alpine.tensor.ext.Integers;

// TODO MIDI class make final
// TODO MIDI rename to IntClip ?
public class IntRange implements Comparable<IntRange>, Iterable<Integer>, Serializable {
  public static IntRange positive(int max) {
    return new IntRange(0, max);
  }

  public static IntRange closed(int min, int max) {
    return new IntRange(min, Math.addExact(max, 1));
  }

  /** inclusive */
  private final int min;
  /** exclusive */
  private final int max;

  public IntRange(int min, int max) {
    Integers.requireLessEquals(min, max);
    this.min = min;
    this.max = max;
  }

  public int min() {
    return min;
  }

  public int max() {
    return max;
  }

  /** any subclass has to override create(...)
   * 
   * @param min
   * @param max
   * @return */
  @SuppressWarnings("unchecked")
  protected <T extends IntRange> T create(int min, int max) {
    return (T) new IntRange(min, max);
  }

  public final <T extends IntRange> T withMin(int min) {
    return create(min, max);
  }

  public final <T extends IntRange> T withMax(int max) {
    return create(min, max);
  }

  /** @param value
   * @return min <= value && value < max */
  public final boolean isInside(int value) {
    return min <= value //
        && value < max;
  }

  public int requireInside(int value) {
    if (isInside(value))
      return value;
    throw new IllegalArgumentException(Integer.toString(value));
  }

  public final int getWidth() {
    return max - min;
  }

  public final boolean isEmpty() {
    return getWidth() == 0;
  }

  public final boolean nonEmpty() {
    return !isEmpty();
  }

  public final int distanceFrom(int value) {
    if (max - 1 < value)
      return value - max + 1;
    if (value < min)
      return min - value;
    return 0;
  }

  /** @param value
   * @return Math.min(Math.max(min, value), max) */
  public final int clip(int value) {
    if (value < min)
      return min;
    return Math.min(max, value);
  }

  public final int getMinMax2(int value) {
    if (isEmpty())
      throw new RuntimeException("empty");
    if (value < min)
      return min;
    int last = max - 1;
    return Math.min(last, value);
  }

  public final <Type extends IntRange> Type translate(int delta) {
    return create(min + delta, max + delta);
  }

  public final <Type extends IntRange> Type shear(int delta_min, int delta_max) {
    return create(min + delta_min, max + delta_max);
  }

  public final <Type extends IntRange> Type intersect(IntRange intRange) {
    return intersect(intRange.min, intRange.max);
  }

  public final <Type extends IntRange> Type intersect(int _min, int _max) {
    _min = Math.max(_min, min);
    _max = Math.min(_max, max);
    return _min <= _max ? create(_min, _max) : create(min, min);
  }

  /** @param intRange
   * @return smallest interval covering this and intRange */
  public final <Type extends IntRange> Type cover(IntRange intRange) {
    return create(Math.min(min, intRange.min), Math.max(max, intRange.max));
  }

  /** only unions of intervals that overlap constitute a new interval
   * 
   * @param intRange
   * @return */
  public final boolean isUnionable(IntRange intRange) {
    return !intersect(intRange).isEmpty() || min == intRange.max || max == intRange.min;
  }

  public final <Type extends IntRange> Type union(IntRange intRange) {
    if (!isUnionable(intRange))
      throw new RuntimeException(this + " union " + intRange);
    return cover(intRange);
  }

  public SortedSet<Integer> capSet(SortedSet<Integer> sortedSet) {
    return sortedSet.subSet(min, max);
  }

  public <Type> SortedMap<Integer, Type> capMap(SortedMap<Integer, Type> sortedMap) {
    return sortedMap.subMap(min, max);
  }

  public IntStream stream() {
    return IntStream.range(min, max);
  }

  /** @return {@link RandomAccess} */
  public List<Integer> toList() {
    return stream().boxed().toList();
  }

  @Override
  public final int compareTo(IntRange intRange) {
    int cmp = Integer.compare(min, intRange.min);
    return cmp != 0 ? cmp : Integer.compare(max, intRange.max);
  }

  @Override
  public final Iterator<Integer> iterator() {
    return new Iterator<>() {
      int count = min;

      @Override
      public boolean hasNext() {
        return count < max;
      }

      @Override
      public Integer next() {
        return count++;
      }
    };
  }

  public final Iterable<Integer> reversed() {
    return () -> new Iterator<>() {
      int count = max;

      @Override
      public boolean hasNext() {
        return min < count;
      }

      @Override
      public Integer next() {
        return --count;
      }
    };
  }

  @Override
  public final String toString() {
    return String.format("[%d,%d)", min, max);
  }

  @Override
  public final boolean equals(Object object) {
    return object instanceof IntRange intRange //
        && min == intRange.min //
        && max == intRange.max;
  }

  @Override
  public final int hashCode() {
    return Objects.hash(min, max);
  }
}
