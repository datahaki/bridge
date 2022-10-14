// code by gjoel, jph
package ch.alpine.bridge.fig;

/** Hint:
 * to render list plot in a custom graphics use
 * jFreeChart.draw(graphics2d, rectangle2D)
 * 
 * to export to a graphics file use
 * ChartUtils.saveChartAsPNG(file, jFreeChart, width, height);
 * 
 * to embed figure as separate panel in gui use {@link ChartPanel}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ListPlot.html">ListPlot</a> */
public enum ListPlot {
  ;
  /** Remark:
   * We would like to make joined property of VisualRow, but JFreeChart does not support
   * this granularity.
   * 
   * @param visualSet
   * @param joined for lines between coordinates, otherwise scattered points
   * @return */
  public static JFreeChart of(VisualSet visualSet, boolean joined) {
    // int limit = xySeriesCollection.getSeriesCount();
    // for (int index = 0; index < limit; ++index) {
    // VisualRow visualRow = visualSet.getVisualRow(index);
    // xyItemRenderer.setSeriesPaint(index, visualRow.getColor());
    // xyItemRenderer.setSeriesStroke(index, visualRow.getStroke());
    // }
    // https://github.com/jfree/jfreechart/issues/301
    // StaticHelper.setRange(visualSet.getAxisX(), xyPlot.getDomainAxis());
    // StaticHelper.setRange(visualSet.getAxisY(), xyPlot.getRangeAxis());
    return null;
  }

  /** Mathematica's default is to draw data points as separate dots,
   * i.e. "Joined->False".
   * 
   * Tested with up to 10 million points - a little slow but possible.
   * 
   * @param visualSet
   * @return */
  public static JFreeChart of(VisualSet visualSet) {
    return of(visualSet, false);
  }
}
