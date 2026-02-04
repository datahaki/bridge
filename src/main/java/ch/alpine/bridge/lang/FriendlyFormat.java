// code by jph
package ch.alpine.bridge.lang;

import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.qty.CompatibleUnitQ;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.sca.Abs;
import ch.alpine.tensor.sca.Floor;
import ch.alpine.tensor.sca.Round;
import ch.alpine.tensor.sca.exp.Log10;

/* contains redundancies with MetricPrefix */
public enum FriendlyFormat {
  ;
  private static final Map<Integer, String> MAP = new HashMap<>();
  static {
    MAP.put(15, "P");
    MAP.put(12, "T");
    MAP.put(9, "G");
    MAP.put(6, "M");
    MAP.put(3, "k");
    MAP.put(0, "");
    MAP.put(-3, "m");
    MAP.put(-6, "u");
    MAP.put(-9, "n");
    MAP.put(-12, "p");
    MAP.put(-15, "f");
  }
  private static final Predicate<String> PREDICATE = //
      Pattern.compile("[%A-Z_a-z]+").asMatchPredicate();

  /** @param scalar
   * @param unit atomic
   * @return */
  public static Scalar of(Scalar scalar, String unit) {
    if (unit.isEmpty() || //
        PREDICATE.test(unit)) {
      Scalar abs = Abs.FUNCTION.apply(scalar);
      Scalar log = Log10.FUNCTION.apply(abs);
      Scalar floor = Floor.toMultipleOf(RealScalar.of(3)).apply(log);
      int exp = Scalars.intValueExact(floor);
      Scalar fallback = Quantity.of(scalar, unit);
      if (MAP.containsKey(exp)) {
        Unit target = Unit.of(MAP.get(exp) + unit);
        if (CompatibleUnitQ.SI().with(target).test(fallback))
          return Round._1.apply(UnitConvert.SI().to(target).apply(fallback));
      }
      return fallback;
    }
    throw new IllegalArgumentException(unit);
  }

  public static Scalar of(Number number, String unit) {
    return of(RealScalar.of(number), unit);
  }

  /** @return -18+4/7 for display, not parsing */
  public static String toHighSchoolString(Scalar scalar) {
    Scalar floor = Floor.FUNCTION.apply(scalar);
    if (Scalars.isZero(floor))
      return scalar.toString();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(floor);
    Scalar reman = scalar.subtract(floor);
    if (Scalars.nonZero(reman))
      stringBuilder.append("+" + reman);
    return stringBuilder.toString();
  }

  // ---
  private static final HexFormat HEX_FORMAT = HexFormat.ofDelimiter(" ");

  /** @param data
   * @return for instance "[a0 12 cd 3f ff 00]" */
  public static String of(byte[] data) {
    return '[' + HEX_FORMAT.formatHex(data) + ']';
  }

  // ---
  private static final Pattern PATTERN = Pattern.compile("\\W+");

  /** removes blank-space, and non-letter characters
   * Careful: also '.' is removed
   * 
   * Reference:
   * https://stackoverflow.com/questions/1184176/how-can-i-safely-encode-a-string-in-java-to-use-as-a-filename
   * 
   * @param string
   * @return letter, digits, and '_' in myString */
  public static String safeFileTitle(String string) {
    return PATTERN.matcher(string).replaceAll("");
  }

  // ---
  private static final String[][] PAIRS = { //
      { "<", "&lt;" }, //
      { ">", "&gt;" }, //
      { "'", "&apos;" } };

  public static String convertChars(String string) {
    for (String[] pair : PAIRS)
      string = string.replaceAll(pair[0], pair[1]);
    return string;
  }

  public static String convertAmps(String string) {
    // for (String[] pair : PAIRS)
    // if (string.contains(pair[0]))
    // throw new IllegalArgumentException(string);
    // ---
    for (String[] pair : PAIRS)
      string = string.replaceAll(pair[1], pair[0]);
    return string;
  }

  // ---
  public static String toCamelCase(String string) {
    return Stream.of(string.split("_")) //
        .map(FriendlyFormat::headRest) //
        .collect(Collectors.joining());
  }

  private static String headRest(String string) {
    char first = Character.toUpperCase(string.charAt(0));
    return first + string.substring(1).toLowerCase();
  }
}
