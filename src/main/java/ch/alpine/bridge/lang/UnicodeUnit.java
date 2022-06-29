// code by jph
package ch.alpine.bridge.lang;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.ext.Cache;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.sca.Sign;

/** "m*s^-1" -> "m/s"
 * use of unicode characters for degC, Ohm and micro-x
 * use of unicode characters for exponents such as ^-2
 * etc. */
public enum UnicodeUnit {
  ;
  private static final int MAX_SIZE = 512;
  private static final Cache<Unit, String> CACHE = Cache.of(UnicodeUnit::build, MAX_SIZE);

  /** @param unit
   * @return */
  public static String of(Unit unit) {
    return CACHE.apply(unit);
  }

  private static String build(Unit unit) {
    Map<String, Scalar> map = unit.map();
    List<Entry<String, Scalar>> list = map.entrySet().stream() //
        .filter(entry -> Sign.isNegative(entry.getValue())) //
        .collect(Collectors.toList());
    if (list.size() == 1 && 1 < map.size()) {
      Entry<String, Scalar> entry = list.iterator().next();
      Unit den = Unit.of(entry.getKey() + Unit.POWER_DELIMITER + entry.getValue().negate());
      return UnicodeHelper.INSTANCE.toString(unit.add(den).map()) + "/" + UnicodeHelper.INSTANCE.toString(den.map());
    }
    return UnicodeHelper.INSTANCE.toString(map);
  }
}
