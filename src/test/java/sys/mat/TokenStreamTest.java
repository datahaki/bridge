package sys.mat;

import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class TokenStreamTest {
  @Test
  void test() {
    StringTokenizer stringTokenizer = new StringTokenizer("asd weiu 123123");
    Stream<String> stream = Stream.iterate(null, _ -> stringTokenizer.hasMoreTokens(), _ -> stringTokenizer.nextToken());
    System.out.println(stream.toList());
    List<String> list = TokenStream.of("asd weiu 123123").toList();
    System.out.println(list);
    StringTokenizer stringTokenizer2 = new StringTokenizer("asd weiu 123123");
    List<String> list2 = Stream.generate(stringTokenizer2::nextToken).limit(stringTokenizer2.countTokens()).toList();
    System.out.println(list2);
  }
}
