// code by jph
package ch.alpine.bridge.cal;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Throw;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.qty.UnitSystem;

public enum DateTimeInterval {
  _001_MS(LocalTimes._001_MS) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusNanos(1000_000);
    }
  },
  _010_MS(LocalTimes._010_MS) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusNanos(10_000_000);
    }
  },
  _100_MS(LocalTimes._100_MS) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusNanos(100_000_000);
    }
  },
  _01_S(LocalTimes._01_S) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusSeconds(1);
    }
  },
  _02_S(LocalTimes._02_S) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusSeconds(2);
    }
  },
  _05_S(LocalTimes._05_S) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusSeconds(5);
    }
  },
  _10_S(LocalTimes._10_S) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusSeconds(10);
    }
  },
  _15_S(LocalTimes._15_S) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusSeconds(15);
    }
  },
  _20_S(LocalTimes._20_S) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusSeconds(20);
    }
  },
  _30_S(LocalTimes._30_S) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusSeconds(30);
    }
  },
  _01_MIN(LocalTimes._01_MIN) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusMinutes(1);
    }
  },
  _02_MIN(LocalTimes._02_MIN) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusMinutes(2);
    }
  },
  _05_MIN(LocalTimes._05_MIN) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusMinutes(5);
    }
  },
  _10_MIN(LocalTimes._10_MIN) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusMinutes(10);
    }
  },
  _15_MIN(LocalTimes._15_MIN) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusMinutes(15);
    }
  },
  _20_MIN(LocalTimes._20_MIN) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusMinutes(20);
    }
  },
  _30_MIN(LocalTimes._30_MIN) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusMinutes(30);
    }
  },
  _01_H(LocalTimes._1_H) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusHours(1);
    }
  },
  _02_H(LocalTimes._2_H) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusHours(2);
    }
  },
  _03_H(LocalTimes._3_H) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusHours(3);
    }
  },
  _04_H(LocalTimes._4_H) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusHours(4);
    }
  },
  _06_H(LocalTimes._6_H) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusHours(6);
    }
  },
  _12_H(LocalTimes._12_H) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusHours(12);
    }
  },
  _1_DAY(LocalDates.DAY) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusDays(1);
    }
  },
  _1_WK(LocalDates._1_WK) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusDays(7);
    }
  },
  HALF_MONTH(LocalDates.HALF_MONTH) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      LocalDate localDate = localDateTime.toLocalDate();
      LocalTime localTime = localDateTime.toLocalTime();
      return localDate.getDayOfMonth() < 16 //
          ? LocalDateTime.of(localDate.withDayOfMonth(16), localTime)
          : LocalDateTime.of(localDate.withDayOfMonth(1).plusMonths(1), localTime);
    }
  },
  _1_MO(LocalDates._1_MO) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusMonths(1);
    }
  },
  _3_MO(LocalDates._3_MO) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusMonths(3);
    }
  },
  _6_MO(LocalDates._6_MO) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusMonths(6);
    }
  },
  _1_YR(LocalDates._1_YR) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusYears(1);
    }
  },
  _10_YR(LocalDates._10_YR) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusYears(10);
    }
  },
  _100_YR(LocalDates._100_YR) {
    @Override
    public LocalDateTime plus(LocalDateTime localDateTime) {
      return localDateTime.plusYears(100);
    }
  },;

  private final LocalDates localDates;
  private final LocalTimes localTimes;
  private final ChronoUnit chronoUnit;

  DateTimeInterval(LocalTimes localTimes) {
    this.localDates = LocalDates.DAY;
    this.localTimes = localTimes;
    this.chronoUnit = localTimes.chronoUnit;
  }

  DateTimeInterval(LocalDates localDates) {
    this.localDates = localDates;
    this.localTimes = LocalTimes.MIDNIGHT;
    this.chronoUnit = localDates.chronoUnit;
  }

  /** Example:
   * _1_DAY.floor(2022, 12, 13, 14, 15, 16) == 2022, 12, 13, 0, 0
   *
   * @param localDateTime
   * @return given localDateTime floored to the nearest date with
   * resolution specified by this DateTime interval */
  public final LocalDateTime floor(LocalDateTime localDateTime) {
    return LocalDateTime.of( //
        localDates.floor(localDateTime.toLocalDate()), //
        localTimes.floor(localDateTime.toLocalTime()));
  }

  public final DateTime floor(Scalar dateObject) {
    return DateTime.of(floor(((DateTime) dateObject).localDateTime()));
  }

  /** @param localDateTime
   * @return floor unless that is equals to given localDateTime, otherwise
   * floor(candidate.minusNanos(1)) */
  public final LocalDateTime floorOrMinus(LocalDateTime localDateTime) {
    LocalDateTime candidate = floor(localDateTime);
    return localDateTime.equals(candidate) //
        ? floor(candidate.minusNanos(1))
        : candidate;
  }

  /** Example:
   * MONTH.plus(2022, 2, 1, 0, 0) == 2022, 3, 1, 0, 0
   * i.e. accounts for the fact that certain months have less than 30 days.
   *
   * @param localDateTime
   * @return given localDateTime plus interval */
  public abstract LocalDateTime plus(LocalDateTime localDateTime);

  public final DateTime plus(DateTime startAttempt) {
    return DateTime.of(plus(startAttempt.localDateTime()));
  }

  /** Example:
   * MONTH.stepHint == 31 * DAY.stepHint() == 2678400[s]
   *
   * @return approximate difference between plus(floor(x))-floor(x) as
   * Quantity with unit "s" */
  public final Scalar durationHint() {
    LocalDateTime min = LocalDateTime.of(1980, 1, 1, 0, 0);
    LocalDateTime max = plus(min);
    return DateTime.seconds(Duration.between(min, max));
  }

  public final ChronoUnit getSmallestDefined() {
    return chronoUnit;
  }

  /** @param duration with unit compatible to "s"
   * @return */
  public static DateTimeInterval findAboveEquals(Scalar duration) {
    duration = UnitSystem.SI().apply(duration);
    for (DateTimeInterval dateTimeIntervals : DateTimeInterval.values())
      if (Scalars.lessEquals(duration, dateTimeIntervals.durationHint()))
        return dateTimeIntervals;
    throw new Throw(duration);
  }
}
