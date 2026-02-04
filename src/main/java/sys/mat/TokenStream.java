// code by jph
package sys.mat;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public enum TokenStream {
  ;
  public static Stream<String> of(StringTokenizer stringTokenizer) {
    List<String> list = new LinkedList<>();
    while (stringTokenizer.hasMoreTokens())
      list.add(stringTokenizer.nextToken());
    return list.stream();
  }

  public static Stream<String> of(String string) {
    return of(new StringTokenizer(string));
  }
}
