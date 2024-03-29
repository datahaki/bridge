// code by jph
package ch.alpine.bridge.usr;

import java.util.HashSet;
import java.util.Set;

public class P74 {
  final Set<Integer> set = new HashSet<>();
  final int limit;

  public P74(int limit) {
    this.limit = limit;
  }

  void rec(int seed) {
    {
      int up = seed + 8;
      if (up <= limit && set.add(up))
        rec(up);
    }
    {
      int dn = seed - 11;
      if (0 <= dn && set.add(dn))
        rec(dn);
    }
  }

  public static void main(String[] args) {
    for (int limit = 10; limit < 20; ++limit) {
      P74 elev = new P74(limit);
      elev.rec(0);
      boolean status = elev.set.size() == limit + 1;
      System.out.println(limit + " " + status);
    }
  }
}
