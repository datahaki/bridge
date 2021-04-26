// code by gjoel, jph
package ch.ethz.idsc.tensor.fig;

import java.util.Objects;
import java.util.function.Function;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.xy.CategoryTableXYDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.api.ScalarUnaryOperator;
import ch.ethz.idsc.tensor.qty.QuantityMagnitude;
import ch.ethz.idsc.tensor.qty.QuantityUnit;
import ch.ethz.idsc.tensor.qty.Unit;

/** functionality to convert {@link VisualSet} to a dataset
 * for the instantiation of a JFreeChart object */
/* package */ enum DatasetFactory {
  ;
  /** Quote from the JFreeChart javadoc: "[XYSeries] represents a sequence
   * of zero or more data items in the form (x, y)."
   * 
   * @param visualSet
   * @return
   * @see ListPlot */
  public static XYSeriesCollection xySeriesCollection(VisualSet visualSet) {
    XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
    ScalarUnaryOperator suoX = null;
    ScalarUnaryOperator suoY = null;
    for (VisualRow visualRow : visualSet.visualRows()) {
      String labelString = visualRow.getLabelString();
      XYSeries xySeries = new XYSeries(labelString.isEmpty() //
          ? xySeriesCollection.getSeriesCount()
          : labelString, //
          visualRow.getAutoSort());
      Tensor points = visualRow.points();
      if (Objects.isNull(suoX) && !Tensors.isEmpty(points)) {
        {
          Unit unit = QuantityUnit.of(points.Get(0, 0));
          visualSet.setUnitX(unit);
          suoX = QuantityMagnitude.SI().in(unit);
        }
        {
          Unit unit = QuantityUnit.of(points.Get(0, 1));
          visualSet.setUnitY(unit);
          suoY = QuantityMagnitude.SI().in(unit);
        }
      }
      for (Tensor point : points) {
        Number numberX = suoX.apply(point.Get(0)).number();
        Scalar valueY = suoY.apply(point.Get(1));
        Number numberY = NumberQ.of(valueY) ? valueY.number() : Double.NaN;
        xySeries.add(numberX, numberY);
      }
      xySeriesCollection.addSeries(xySeries);
    }
    return xySeriesCollection;
  }

  public static CategoryDataset defaultCategoryDataset(VisualSet visualSet) {
    return defaultCategoryDataset(visualSet, Scalar::toString);
  }

  public static CategoryDataset defaultCategoryDataset(VisualSet visualSet, Function<Scalar, String> naming) {
    DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
    for (VisualRow visualRow : visualSet.visualRows())
      for (Tensor point : visualRow.points())
        defaultCategoryDataset.addValue( //
            point.Get(1).number().doubleValue(), //
            visualRow.getLabel(), //
            naming.apply(point.Get(0)));
    return defaultCategoryDataset;
  }

  /** Quote from the JFreeChart javadoc: "[...] The {@link TableXYDataset}
   * interface requires all series to share the same set of x-values. When
   * adding a new item <code>(x, y)</code> to one series, all other series
   * automatically get a new item <code>(x, null)</code> unless a non-null
   * item has already been specified."
   * 
   * @param visualSet
   * @return */
  public static TableXYDataset timeTableXYDataset(VisualSet visualSet) {
    TimeTableXYDataset timeTableXYDataset = new TimeTableXYDataset();
    for (VisualRow visualRow : visualSet.visualRows())
      for (Tensor point : visualRow.points())
        timeTableXYDataset.add( //
            StaticHelper.timePeriod(point.Get(0)), //
            point.Get(1).number().doubleValue(), //
            visualRow.getLabel());
    return timeTableXYDataset;
  }

  public static TableXYDataset categoryTableXYDataset(VisualSet visualSet) {
    CategoryTableXYDataset categoryTableXYDataset = new CategoryTableXYDataset();
    for (VisualRow visualRow : visualSet.visualRows()) {
      String label = visualRow.getLabelString().isEmpty() //
          ? String.valueOf(categoryTableXYDataset.getSeriesCount())
          : visualRow.getLabelString();
      for (Tensor point : visualRow.points())
        categoryTableXYDataset.add( //
            point.Get(0).number().doubleValue(), //
            point.Get(1).number().doubleValue(), //
            label); // requires string, might lead to overwriting
    }
    return categoryTableXYDataset;
  }
}
