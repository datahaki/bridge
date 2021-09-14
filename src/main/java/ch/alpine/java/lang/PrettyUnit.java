// code by jph
package ch.alpine.java.lang;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.sca.Sign;

/** "m*s^-1" -> "m/s" and more */
public enum PrettyUnit {
  ;
  /** @param scalar
   * @return */
  public static String of(Scalar scalar) {
    Unit unit = QuantityUnit.of(scalar);
    return Unit.ONE.equals(unit) //
        ? scalar.toString()
        : Unprotect.withoutUnit(scalar) + " " + of(unit);
  }

  /** @param unit
   * @return */
  public static String of(Unit unit) {
    Map<String, Scalar> map = unit.map();
    long count = map.values().stream().filter(Sign::isNegative).count();
    if (count < 2 && count < map.size()) {
      Optional<Entry<String, Scalar>> optional = map.entrySet().stream() //
          .filter(entry -> Sign.isNegative(entry.getValue())) //
          .findFirst();
      if (optional.isPresent()) {
        Entry<String, Scalar> entry = optional.orElseThrow();
        Unit den = Unit.of(entry.getKey() + Unit.POWER_DELIMITER + entry.getValue().negate());
        return toString(unit.add(den).map()) + "/" + toString(den.map());
      }
    }
    return toString(map);
  }

  /** @param map
   * @return for instance "m*s^-2" */
  private static String toString(Map<String, Scalar> map) {
    return map.entrySet().stream() //
        .map(entry -> atomString(entry.getKey()) + exponentString(entry.getValue().toString())) //
        .collect(Collectors.joining(Unit.JOIN_DELIMITER)); // delimited by '*'
  }

  private static final Set<String> SET_MICRO = Set.of("ug", "um", "us", "uF", "uH", "uS", "uSv", "uW");

  private static String atomString(String atom) {
    if (SET_MICRO.contains(atom))
      return "\u03BC" + atom.substring(1);
    if (atom.equals("degC"))
      return "\u2103"; // unicode oC
    if (atom.equals("EUR"))
      return "\u20ac"; // unicode EUR
    if (atom.equals("K"))
      return "\u212a"; // unicode K
    if (atom.equals("Ohm"))
      return "\u2126";
    if (atom.equals("kOhm"))
      return "k\u2126";
    if (atom.equals("MOhm"))
      return "M\u2126";
    return atom;
  }

  private static String exponentString(String string) {
    if (string.equals("1"))
      return "";
    if (string.equals("-1"))
      return "\u207b\u00b9";
    if (string.equals("2"))
      return "\u00b2";
    if (string.equals("-2"))
      return "\u207b\u00b2";
    if (string.equals("3"))
      return "\u00b3";
    if (string.equals("-3"))
      return "\u207b\u00b3";
    return Unit.POWER_DELIMITER + string; // delimited by '^'
  }
}
