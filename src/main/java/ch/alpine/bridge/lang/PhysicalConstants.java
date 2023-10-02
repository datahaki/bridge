// code by jph
package ch.alpine.bridge.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.ext.ResourceData;
import ch.alpine.tensor.qty.QuantityMagnitude;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.qty.UnitConvert;

/** EXPERIMENTAL
 * 
 * inspired by
 * https://reference.wolfram.com/language/Compatibility/tutorial/PhysicalConstants.html */
public enum PhysicalConstants {
  ;
  private static final Map<String, Scalar> MAP = new HashMap<>();
  static {
    Properties properties = ResourceData.properties("/ch/alpine/bridge/lang/physical_constants.properties");
    for (String key : properties.stringPropertyNames())
      MAP.put(key, Scalars.fromString(properties.getProperty(key)));
  }

  /** @param key for instance "BohrRadius"
   * @return */
  public static Scalar of(String key) {
    return MAP.get(key);
  }

  public static Scalar convert(String key, Unit unit) {
    return UnitConvert.SI().to(unit).apply(of(key));
  }

  public static Scalar magnitude(String key, Unit unit) {
    return QuantityMagnitude.SI().in(unit).apply(of(key));
  }
}
