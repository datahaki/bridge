// code by jph
package sys.dat;

public enum HtmlConversion {
  ;
  // TODO TPF external library!
  public static String removeHtml(String string) {
    string = string.replace("&Auml;", "Ae");
    string = string.replace("&Ouml;", "Oe");
    string = string.replace("&Uuml;", "Ue");
    string = string.replace("&auml;", "ae");
    string = string.replace("&ouml;", "oe");
    string = string.replace("&uuml;", "ue");
    string = string.replace("&szlig;", "ss");
    string = string.replace("&aacute;", "a");
    string = string.replace("&eacute;", "e");
    string = string.replace("&agrave;", "a");
    string = string.replace("&egrave;", "e");
    string = string.replace("&Aring;", "A");
    string = string.replace("&aring;", "a");
    string = string.replaceAll("<.*?>", "");
    return string;
  }
}
