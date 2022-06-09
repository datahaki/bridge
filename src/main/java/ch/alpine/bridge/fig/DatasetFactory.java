// code by gjoel, jph
package ch.alpine.bridge.fig;

import java.util.function.Function;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.xy.CategoryTableXYDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.ScalarUnaryOperator;

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
    ScalarUnaryOperator toRealsX = visualSet.getAxisX().toReals();
    ScalarUnaryOperator toRealsY = visualSet.getAxisY().toReals();
    for (VisualRow visualRow : visualSet.visualRows()) {
      String labelString = visualRow.getLabelString();
      XYSeries xySeries = new XYSeries(labelString.isEmpty() //
          ? xySeriesCollection.getSeriesCount()
          : labelString, //
          visualRow.getAutoSort());
      for (Tensor point : visualRow.points()) {
        Number numberX = toRealsX.apply(point.Get(0)).number();
        Number numberY = toRealsY.apply(point.Get(1)).number();
        xySeries.add( //
            numberX, //
            // infinity throws an IllegalArgumentException
            Double.isFinite(numberY.doubleValue()) //
                ? numberY
                : Double.NaN);
      }
      xySeriesCollection.addSeries(xySeries);
    }
    return xySeriesCollection;
  }

  /** @param visualSet
   * @param naming for instance Scalar::toString
   * @return */
  public static CategoryDataset defaultCategoryDataset(VisualSet visualSet, Function<Scalar, String> naming) {
    DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
    ScalarUnaryOperator toRealsY = visualSet.getAxisY().toReals();
    for (VisualRow visualRow : visualSet.visualRows())
      for (Tensor point : visualRow.points()) {
        defaultCategoryDataset.addValue( //
            toRealsY.apply(point.Get(1)).number(), //
            visualRow.getLabel(), //
            naming.apply(point.Get(0)));
      }
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
    ScalarUnaryOperator toRealsY = visualSet.getAxisY().toReals();
    for (VisualRow visualRow : visualSet.visualRows())
      for (Tensor point : visualRow.points())
        timeTableXYDataset.add( //
            StaticHelper.timePeriod(point.Get(0)), //
            toRealsY.apply(point.Get(1)).number(), //
            visualRow.getLabel(), //
            true);
    return timeTableXYDataset;
  }

  public static TableXYDataset categoryTableXYDataset(VisualSet visualSet) {
    CategoryTableXYDataset categoryTableXYDataset = new CategoryTableXYDataset();
    ScalarUnaryOperator toRealsX = visualSet.getAxisX().toReals();
    ScalarUnaryOperator toRealsY = visualSet.getAxisY().toReals();
    for (VisualRow visualRow : visualSet.visualRows()) {
      String label = visualRow.getLabelString().isEmpty() //
          ? String.valueOf(categoryTableXYDataset.getSeriesCount())
          : visualRow.getLabelString();
      for (Tensor point : visualRow.points())
        categoryTableXYDataset.add( //
            toRealsX.apply(point.Get(0)).number(), //
            toRealsY.apply(point.Get(1)).number(), //
            label, //
            true); // requires string, might lead to overwriting
    }
    return categoryTableXYDataset;
  }
}
