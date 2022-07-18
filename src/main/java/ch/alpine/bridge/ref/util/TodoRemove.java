package ch.alpine.bridge.ref.util;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.alpine.tensor.ext.Integers;

/* package */ enum TodoRemove {
  ;
  /** traverses the indices of a hypothetical array of given dimensions
   * 
   * Example:
   * Array.stream(2, 1, 3) gives the following lists of integers
   * [0, 0, 0]
   * [0, 0, 1]
   * [0, 0, 2]
   * [1, 0, 0]
   * [1, 0, 1]
   * [1, 0, 2]
   * 
   * @param dimensions with non-negative entries
   * @return stream of unmodifiable lists of integers
   * @throws Exception if any dimension is negative */
  @SafeVarargs
  public static Stream<List<Integer>> stream(int... dimensions) {
    return stream(Integers.asList(dimensions));
  }

  /** @param dimensions with non-negative entries
   * @return stream of unmodifiable lists of integers
   * @throws Exception if any dimension is negative */
  public static Stream<List<Integer>> stream(List<Integer> dimensions) {
    dimensions.forEach(Integers::requirePositiveOrZero);
    return recur(Stream.of(new int[dimensions.size()]), 0, dimensions) //
        .map(int[]::clone) //
        .map(Integers::asList);
  }

  private static Stream<int[]> recur(Stream<int[]> stream, int level, List<Integer> dimensions) {
    return level == dimensions.size() //
        ? stream
        : recur(stream.flatMap(array -> IntStream.range(0, dimensions.get(level)).mapToObj(i -> {
          array[level] = i;
          return array;
        })), level + 1, dimensions);
  }
}
