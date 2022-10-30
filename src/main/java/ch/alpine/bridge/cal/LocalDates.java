// code by jph
package ch.alpine.bridge.cal;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/* package */ enum LocalDates {
  DAY(ChronoUnit.DAYS) {
    @Override
    public LocalDate floor(LocalDate localDate) {
      return localDate;
    }
  },
  _1_WK(ChronoUnit.DAYS) {
    @Override
    public LocalDate floor(LocalDate localDate) {
      DayOfWeek dayOfWeek = localDate.getDayOfWeek();
      return localDate.minusDays(dayOfWeek.ordinal());
    }
  },
  HALF_MONTH(ChronoUnit.DAYS) {
    @Override
    public LocalDate floor(LocalDate localDate) {
      return localDate.getDayOfMonth() < 16 //
          ? localDate.withDayOfMonth(1)
          : localDate.withDayOfMonth(16);
    }
  },
  _1_MO(ChronoUnit.MONTHS) {
    @Override
    public LocalDate floor(LocalDate localDate) {
      return localDate.withDayOfMonth(1);
    }
  },
  _3_MO(ChronoUnit.MONTHS) {
    @Override
    public LocalDate floor(LocalDate localDate) {
      return localDate.minusMonths(localDate.getMonth().ordinal() % 3).withDayOfMonth(1);
    }
  },
  _6_MO(ChronoUnit.MONTHS) {
    @Override
    public LocalDate floor(LocalDate localDate) {
      return localDate.minusMonths(localDate.getMonth().ordinal() % 6).withDayOfMonth(1);
    }
  },
  _1_YR(ChronoUnit.YEARS) {
    @Override
    public LocalDate floor(LocalDate localDate) {
      return LocalDate.of(localDate.getYear(), 1, 1);
    }
  },
  _2_YR(ChronoUnit.YEARS) {
    @Override
    public LocalDate floor(LocalDate localDate) {
      int year = localDate.getYear();
      return LocalDate.of(year - Math.floorMod(year, 2), 1, 1);
    }
  },
  _5_YR(ChronoUnit.YEARS) {
    @Override
    public LocalDate floor(LocalDate localDate) {
      int year = localDate.getYear();
      return LocalDate.of(year - Math.floorMod(year, 5), 1, 1);
    }
  },
  _10_YR(ChronoUnit.YEARS) {
    @Override
    public LocalDate floor(LocalDate localDate) {
      int year = localDate.getYear();
      return LocalDate.of(year - Math.floorMod(year, 10), 1, 1);
    }
  },
  _20_YR(ChronoUnit.YEARS) {
    @Override
    public LocalDate floor(LocalDate localDate) {
      int year = localDate.getYear();
      return LocalDate.of(year - Math.floorMod(year, 20), 1, 1);
    }
  },
  _50_YR(ChronoUnit.YEARS) {
    @Override
    public LocalDate floor(LocalDate localDate) {
      int year = localDate.getYear();
      return LocalDate.of(year - Math.floorMod(year, 50), 1, 1);
    }
  },
  _100_YR(ChronoUnit.YEARS) {
    @Override
    public LocalDate floor(LocalDate localDate) {
      int year = localDate.getYear();
      return LocalDate.of(year - Math.floorMod(year, 100), 1, 1);
    }
  },;

  final ChronoUnit chronoUnit;

  private LocalDates(ChronoUnit chronoUnit) {
    this.chronoUnit = chronoUnit;
  }

  public abstract LocalDate floor(LocalDate localDate);
}
