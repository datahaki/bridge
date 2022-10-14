// code by jph
package ch.alpine.bridge.cal;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/* package */ enum LocalTimes {
  NANOS(ChronoUnit.NANOS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return localTime;
    }
  },
  _001_MS(ChronoUnit.MILLIS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return localTime.minusNanos(localTime.getNano() % 1_000_000);
    }
  },
  _010_MS(ChronoUnit.MILLIS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return localTime.minusNanos(localTime.getNano() % 10_000_000);
    }
  },
  _100_MS(ChronoUnit.MILLIS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return localTime.minusNanos(localTime.getNano() % 100_000_000);
    }
  },
  _01_S(ChronoUnit.SECONDS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return localTime.withNano(0);
    }
  },
  _02_S(ChronoUnit.SECONDS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return localTime.minusSeconds(localTime.getSecond() % 2).withNano(0);
    }
  },
  _05_S(ChronoUnit.SECONDS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return localTime.minusSeconds(localTime.getSecond() % 5).withNano(0);
    }
  },
  _10_S(ChronoUnit.SECONDS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return localTime.minusSeconds(localTime.getSecond() % 10).withNano(0);
    }
  },
  _15_S(ChronoUnit.SECONDS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return localTime.minusSeconds(localTime.getSecond() % 15).withNano(0);
    }
  },
  _20_S(ChronoUnit.SECONDS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return localTime.minusSeconds(localTime.getSecond() % 20).withNano(0);
    }
  },
  _30_S(ChronoUnit.SECONDS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return localTime.minusSeconds(localTime.getSecond() % 30).withNano(0);
    }
  },
  _01_MIN(ChronoUnit.MINUTES) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return LocalTime.of(localTime.getHour(), localTime.getMinute());
    }
  },
  _02_MIN(ChronoUnit.MINUTES) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return LocalTime.of(localTime.getHour(), floorToMultiple(localTime.getMinute(), 2));
    }
  },
  _05_MIN(ChronoUnit.MINUTES) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return LocalTime.of(localTime.getHour(), floorToMultiple(localTime.getMinute(), 5));
    }
  },
  _10_MIN(ChronoUnit.MINUTES) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return LocalTime.of(localTime.getHour(), floorToMultiple(localTime.getMinute(), 10));
    }
  },
  _15_MIN(ChronoUnit.MINUTES) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return LocalTime.of(localTime.getHour(), floorToMultiple(localTime.getMinute(), 15));
    }
  },
  _20_MIN(ChronoUnit.MINUTES) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return LocalTime.of(localTime.getHour(), floorToMultiple(localTime.getMinute(), 20));
    }
  },
  _30_MIN(ChronoUnit.MINUTES) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return LocalTime.of(localTime.getHour(), floorToMultiple(localTime.getMinute(), 30));
    }
  },
  _1_H(ChronoUnit.HOURS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return LocalTime.of(localTime.getHour(), 0);
    }
  },
  _2_H(ChronoUnit.HOURS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return LocalTime.of(floorToMultiple(localTime.getHour(), 2), 0);
    }
  },
  _3_H(ChronoUnit.HOURS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return LocalTime.of(floorToMultiple(localTime.getHour(), 3), 0);
    }
  },
  _4_H(ChronoUnit.HOURS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return LocalTime.of(floorToMultiple(localTime.getHour(), 4), 0);
    }
  },
  _6_H(ChronoUnit.HOURS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return LocalTime.of(floorToMultiple(localTime.getHour(), 6), 0);
    }
  },
  _12_H(ChronoUnit.HOURS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return LocalTime.of(floorToMultiple(localTime.getHour(), 12), 0);
    }
  },
  MIDNIGHT(ChronoUnit.HOURS) {
    @Override
    public LocalTime floor(LocalTime localTime) {
      return LocalTime.MIDNIGHT;
    }
  };

  final ChronoUnit chronoUnit;

  private LocalTimes(ChronoUnit chronoUnit) {
    this.chronoUnit = chronoUnit;
  }

  public abstract LocalTime floor(LocalTime localTime);

  private static int floorToMultiple(int x, int mod) {
    return x - (x % mod);
  }
}
