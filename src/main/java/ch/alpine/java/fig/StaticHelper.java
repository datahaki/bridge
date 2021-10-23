// code by gjoel
package ch.alpine.java.fig;

import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.jfree.data.time.Second;
import org.jfree.data.time.TimePeriod;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Last;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.opt.nd.Box;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.qty.UnitConvert;

/* package */ enum StaticHelper {
  ;
  // TODO 20210831 the implementation can be improved by implementing TimePeriod
  private static final Calendar CALENDAR = Calendar.getInstance();

  /** @param time
   * @return */
  public static TimePeriod timePeriod(Scalar time) {
    long timeL = time.number().longValue();
    int hours = Math.toIntExact(TimeUnit.SECONDS.toHours(timeL));
    int minutes = Math.toIntExact(TimeUnit.SECONDS.toMinutes(timeL) - 60 * hours);
    int seconds = Math.toIntExact(TimeUnit.SECONDS.toSeconds(timeL) - minutes * 60 - hours * 3600);
    int day = 1;
    int month = CALENDAR.get(Calendar.MONTH) + 1; // Month are 0 based, thus it is necessary to add 1
    int year = CALENDAR.get(Calendar.YEAR);
    return new Second(seconds, minutes, hours, day, month, year); // month and year can not be zero
  }

  /* package */ static VisualArray create(BufferedImage bufferedImage, VisualSet visualSet, Tensor domain, Scalar yhi) {
    Unit unitX = visualSet.getAxisX().getUnit();
    Unit unitY = visualSet.getAxisY().getUnit();
    ScalarUnaryOperator suoX = UnitConvert.SI().to(unitX);
    ScalarUnaryOperator suoY = UnitConvert.SI().to(unitY);
    Box box = Box.of( //
        Tensors.of(suoX.apply(domain.Get(0)), suoY.apply(yhi.zero())), //
        Tensors.of(suoX.apply(Last.of(domain)), suoY.apply(yhi)));
    VisualArray visualArray = new VisualArray(box, bufferedImage);
    visualArray.getAxisX().setLabel(visualSet.getAxisX().getLabel());
    visualArray.getAxisY().setLabel(visualSet.getAxisY().getLabel());
    return visualArray;
  }
}
