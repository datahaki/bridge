// code from stackoverflow
// adapted by jph
package ch.alpine.bridge.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.alpine.tensor.ext.PackageTestAccess;

/** "In computer science, a disjoint-set data structure, also called a union-find
 * data structure or merge-find set, is a data structure that stores a collection
 * of disjoint (non-overlapping) sets."
 * Reference: https://en.wikipedia.org/wiki/Disjoint-set_data_structure */
public class DisjointSets {
  /** @param initialSize
   * @return */
  public static DisjointSets allocate(int initialSize) {
    DisjointSets disjointSets = new DisjointSets();
    IntStream.range(0, initialSize).forEach(i -> disjointSets.add());
    return disjointSets;
  }

  // ---
  private final List<Node> list = new ArrayList<>();

  /** @return index of added set */
  public int add() {
    int index = list.size();
    list.add(new Node(index));
    return index;
  }

  /** @param index1
   * @param index2 */
  public void union(int index1, int index2) {
    int key_x = key(index1);
    int key_y = key(index2);
    if (key_x != key_y) {
      Node node_x = list.get(key_x);
      Node node_y = list.get(key_y);
      if (node_x.rank < node_y.rank)
        node_x.setParent(key_y);
      else if (node_x.rank > node_y.rank)
        node_y.setParent(key_x);
      else {
        node_y.setParent(key_x);
        ++node_x.rank;
      }
    }
  }

  /** @param index
   * @return representative of index */
  public int key(int index) {
    Node node = list.get(index);
    if (node.parent != index)
      node.setParent(key(node.parent)); // path collapse
    return node.parent;
  }

  /** Example:
   * Map<Integer, Integer> map = disjointSets.createMap(new AtomicInteger()::getAndIncrement);
   * 
   * @param supplier
   * @return */
  public <T> Map<Integer, T> createMap(Supplier<T> supplier) {
    // list.stream().map(n -> n.parent).map(this::key).distinct();
    return IntStream.range(0, list.size()) //
        .map(this::key) //
        .boxed() //
        .distinct() //
        .collect(Collectors.toMap(Function.identity(), key -> supplier.get()));
  }

  // ---
  private static class Node {
    private int parent;
    private Integer rank = 0;

    public Node(int index) {
      parent = index;
    }

    public void setParent(int index) {
      parent = index;
      rank = null;
    }
  }

  @PackageTestAccess
  int depth(int index) {
    int depth = 0;
    while (list.get(index).parent != index) {
      index = list.get(index).parent;
      ++depth;
    }
    return depth;
  }

  @PackageTestAccess
  Collection<Integer> parents() {
    return list.stream().map(n -> n.parent).collect(Collectors.toSet());
  }

  @PackageTestAccess
  Collection<Integer> representatives() {
    return IntStream.range(0, list.size()) //
        .map(this::key) //
        .boxed() //
        .collect(Collectors.toSet());
  }
}
