// code by jph
package ch.alpine.bridge.util;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/** Set with the following characteristics:
 * 
 * 1) a null value is not permitted
 * 
 * 2) iterating of the collection gives the elements in the same order
 * as they were added, therefore "Linked" in the class name
 * 
 * 3) invoking {@link #add(Object)} with an object that is already
 * in the collection throws an exception
 * 
 * 4) invoking {@link #remove(Object)} with an object that is not present
 * in the collection throws an exception
 * 
 * 5) collection offers the same guarantees as {@link CopyOnWriteArrayList}
 * when used by multiple threads.
 * 
 * @implSpec
 * The implementation of the collection is backed by {@link CopyOnWriteArrayList} */
public class CopyOnWriteLinkedSet<E> extends AbstractSet<E> {
  private final CopyOnWriteArrayList<E> list = new CopyOnWriteArrayList<>();

  @Override
  public boolean add(E value) {
    Objects.requireNonNull(value);
    if (list.addIfAbsent(value))
      return true;
    throw new IllegalArgumentException("already in set: " + value);
  }

  @Override
  public boolean remove(Object value) {
    Objects.requireNonNull(value);
    if (list.remove(value))
      return true;
    throw new IllegalArgumentException("not in set: " + value);
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public void clear() {
    list.clear();
  }

  @Override
  public boolean contains(Object object) {
    return list.contains(object);
  }

  @Override
  public Iterator<E> iterator() {
    return list.iterator(); // remove() is not supported
  }

  @Override
  public Object[] toArray() {
    return list.toArray();
  }

  @Override
  public <T> T[] toArray(T[] array) {
    return list.toArray(array);
  }

  @Override
  public boolean containsAll(Collection<?> collection) {
    return list.containsAll(collection);
  }

  @Override
  public boolean addAll(Collection<? extends E> collection) {
    collection.forEach(this::add);
    return true;
  }

  @Override
  public boolean retainAll(Collection<?> collection) {
    throw new UnsupportedOperationException();
  }
}
