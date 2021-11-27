// code by gjoel, jph
package ch.alpine.java.fig;

import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimePeriod;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Last;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.qty.QuantityMagnitude;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

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

  /** @param bufferedImage
   * @param visualSet
   * @param domain
   * @param yhi with unit of domain negated
   * @return */
  public static VisualImage create(BufferedImage bufferedImage, VisualSet visualSet, Tensor domain, Scalar yhi) {
    Unit unitX = visualSet.getAxisX().getUnit();
    ScalarUnaryOperator suoX = UnitConvert.SI().to(unitX);
    Clip clipX = Clips.interval(suoX.apply(domain.Get(0)), suoX.apply(Last.of(domain)));
    // ---
    Unit unitY = visualSet.getAxisY().getUnit();
    ScalarUnaryOperator suoY = UnitConvert.SI().to(unitY);
    Clip clipY = Clips.interval(suoY.apply(yhi.zero()), suoY.apply(yhi));
    // ---
    VisualImage visualImage = new VisualImage(bufferedImage, clipX, clipY);
    visualImage.getAxisX().setLabel(visualSet.getAxisX().getLabel());
    visualImage.getAxisY().setLabel(visualSet.getAxisY().getLabel());
    return visualImage;
  }

  public static void setRange(Axis axis, ValueAxis valueAxis) {
    if (valueAxis instanceof NumberAxis) {
      // Mathematica does not include zero in the y-axes by default
      // whereas jfreechart does so.
      // the code below emulates the behavior of Mathematica
      NumberAxis numberAxis = (NumberAxis) valueAxis;
      numberAxis.setAutoRangeIncludesZero(false);
    }
    Optional<Clip> optional = axis.getOptionalClip();
    if (optional.isPresent()) {
      Clip clip = optional.orElseThrow();
      ScalarUnaryOperator suo = QuantityMagnitude.SI().in(axis.getUnit());
      valueAxis.setRange( //
          suo.apply(clip.min()).number().doubleValue(), //
          suo.apply(clip.max()).number().doubleValue());
    }
  }
}
