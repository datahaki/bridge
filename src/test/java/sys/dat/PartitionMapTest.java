// code by jph
package sys.dat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class PartitionMapTest {
  @Test
  @Disabled
  void testSimple() {
    List<Integer> list = new ArrayList<>();
    RandomGenerator randomGenerator = ThreadLocalRandom.current();
    int upper = randomGenerator.nextInt(10);
    for (int c1 = 0; c1 < upper; ++c1) {
      int num = randomGenerator.nextInt(1000);
      list.add(1 + num);
    }
    PartitionMap myPartitionMap = PartitionMap.createFrom(list, 3);
    // System.out.println(myPartitionMap);
    // System.out.println(myPartitionMap.getOrdinal(400));
  }

  @Test
  void testWalkthrough() {
    PartitionMap partitionMap = PartitionMap.createFrom(Arrays.asList(2, 3, 3, 4, 10, 10, 10, 3, 2, 2, 4, 4, 4, 4, 4, 2, 2, 2, 5, 4, 10, 10, 10, 10, 10), 2);
  }
}
