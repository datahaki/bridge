// code by jph
package sys.gui;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Shape2DCollection implements Iterable<Shape2DObject> {
  private final Collection<Shape2DObject> collection = new LinkedList<>();

  private List<Shape2DObject> contains(Point2D point2D) {
    return collection.stream() //
        .filter(shapeObject -> shapeObject.shape().contains(point2D)) //
        .toList();
  }

  public Optional<Shape2DObject> findFirst(Point2D point2D) {
    return contains(point2D).stream() //
        .findFirst();
  }

  public void add(Shape2DObject shapeObject) {
    collection.add(shapeObject);
  }

  public void clear() {
    collection.clear();
  }

  @Override
  public Iterator<Shape2DObject> iterator() {
    return collection.iterator();
  }
}
