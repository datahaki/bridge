// adapted from chatgpt
package ch.alpine.bridge.lang;

import java.text.BreakIterator;
import java.util.Locale;

/**
 * 
 */
public enum GraphemeCount {
  ;
  /** @param string
   * @return */
  public static int of(String string) {
    BreakIterator breakIterator = BreakIterator.getCharacterInstance(Locale.ROOT);
    breakIterator.setText(string);
    breakIterator.first();
    int count = 0;
    while (breakIterator.next() != BreakIterator.DONE)
      ++count;
    return count;
  }
}
