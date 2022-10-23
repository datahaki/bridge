// code by gjoel, jph
package ch.alpine.bridge.fig;

@Deprecated
public class VisualBase {
  private final Axis axisX = new Axis();
  private final Axis axisY = new Axis();
  private String plotLabel = "";

  /** @param string to appear above plot */
  public final void setPlotLabel(String string) {
    plotLabel = string;
  }

  /** @return */
  public final String getPlotLabel() {
    return plotLabel;
  }

  public final Axis getAxisX() {
    return axisX;
  }

  public final Axis getAxisY() {
    return axisY;
  }
}
