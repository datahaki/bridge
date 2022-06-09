// code by jph
package ch.alpine.bridge.ref;

public enum UnicodeDemo {
  ;
  public static void main(String[] args) {
    for (int i = 0x10000; i < 0x110000; ++i) {
      String string = new String(new int[] { i }, 0, 1);
      System.out.println(string);
    }
  }
}
