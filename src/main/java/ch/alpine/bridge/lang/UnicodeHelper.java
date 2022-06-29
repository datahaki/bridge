// code by jph
package ch.alpine.bridge.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.io.ResourceData;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.qty.UnitSystem;

/* package */ enum UnicodeHelper {
  INSTANCE;

  private final Map<String, String> terminators = new HashMap<>();
  private final Map<String, String> exponents = new HashMap<>();
  private final Map<String, String> micros = new HashMap<>();

  private UnicodeHelper() {
    terminators.put("degC", "\u2103"); // unicode oC
    terminators.put("K", "\u212a"); // unicode K
    for (String unit : UnitSystem.SI().map().keySet())
      if (unit.endsWith("Ohm")) {
        String prefix = unit.substring(0, unit.length() - 3);
        terminators.put(unit, prefix + '\u2126'); // unicode Omega
      }
    // ---
    terminators.put("EUR", "\u20ac"); // unicode EUR
    terminators.put("GBP", "\u00a3"); // unicode GBP
    terminators.put("USD", "$"); // unicode USD
    terminators.put("JPY", "\u00a5"); // unicode JPY
    // ---
    exponents.put("1", "");
    exponents.put("-1", "\u207b\u00b9");
    exponents.put("2", "\u00b2");
    exponents.put("-2", "\u207b\u00b2");
    exponents.put("3", "\u00b3");
    exponents.put("-3", "\u207b\u00b3");
    // ---
    Properties properties = ResourceData.properties("/ch/alpine/tensor/qty/si.properties");
    Set<String> set = properties.stringPropertyNames();
    for (String key : set)
      if (key.startsWith("_")) { // inflator
        String atom = key.substring(1);
        String uKey = "u" + atom;
        if (!properties.contains(uKey))
          micros.put(uKey, '\u03BC' + terminate(atom));
      }
  }

  /** @param map
   * @return for instance "m*s^-2" */
  public String toString(Map<String, Scalar> map) {
    return map.entrySet().stream() //
        .map(entry -> atomString(entry.getKey()) + exponentString(entry.getValue().toString())) //
        .collect(Collectors.joining(Unit.JOIN_DELIMITER)); // delimited by '*'
  }

  private String atomString(String atom) {
    return micros.getOrDefault(atom, terminate(atom));
  }

  private String terminate(String atom) {
    return terminators.getOrDefault(atom, atom);
  }

  private String exponentString(String string) {
    return exponents.getOrDefault(string, Unit.POWER_DELIMITER + string);
  }
}
