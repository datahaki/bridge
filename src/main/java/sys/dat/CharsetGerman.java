// code by jph
package sys.dat;

public enum CharsetGerman {
  ;
  public static String autoReplaceGerman(String string) {
    string = string.replace("Ae", "\u00C4"); // Ae
    string = string.replace("ae", "\u00E4"); // ae
    // -
    string = string.replace("Oe", "\u00D6"); // Oe
    string = string.replace("oe", "\u00F6"); // oe
    // -
    string = string.replace("Ue", "\u00DC"); // Ue bwv0045_03
    string = string.replace("ue", "\u00FC"); // ue cantata001_03
    // -
    string = string.replace("ss", "\u00DF"); // sz cantata135_04 (abundant)
    return string;
  }

  public static String to7bit(String string) {
    string = string.replace("\u00C4", "Ae"); // Ae
    string = string.replace("\u00E4", "ae"); // ae
    // - ,
    string = string.replace("\u00D6", "Oe"); // Oe
    string = string.replace("\u00F6", "oe"); // oe
    // - ,
    string = string.replace("\u00DC", "Ue"); // Ue bwv0045_03
    string = string.replace("\u00FC", "ue"); // ue cantata001_03
    // - ,
    string = string.replace("\u00DF", "ss"); // sz cantata135_04 (abundant)
    return string;
  }

  public static String toHtml(String string) {
    string = string.replace("\u00C4", "&Auml;"); // Ae
    string = string.replace("\u00E4", "&auml;"); // ae
    // - ,
    string = string.replace("\u00D6", "&Ouml;"); // Oe
    string = string.replace("\u00F6", "&ouml;"); // oe
    // - ,
    string = string.replace("\u00DC", "&Uuml;"); // Ue bwv0045_03
    string = string.replace("\u00FC", "&uuml;"); // ue cantata001_03
    // - ,
    string = string.replace("\u00DF", "&szlig;"); // sz cantata135_04 (abundant)
    return string;
  }
}
