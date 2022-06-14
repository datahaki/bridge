// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.qty.Quantity;

class ParamContainerTest {
  @Test
  void testSimple() {
    ParamContainer paramContainer = ParamContainer.INSTANCE;
    assertInstanceOf(Quantity.class, paramContainer.maxTor);
    assertEquals(paramContainer.shape.length(), 4);
  }
  // public void testEnumPropagate() {
  // Properties properties;
  // {
  // ParamContainerEnum paramContainerEnum = new ParamContainerEnum();
  // paramContainerEnum.pivots = Pivots.FIRST_NON_ZERO;
  // paramContainerEnum.nameString = NameString.SECOND;
  // ObjectProperties tensorProperties = ObjectProperties.wrap(paramContainerEnum);
  // properties = tensorProperties.createProperties();
  // }
  // {
  // ParamContainerEnum paramContainerEnum = new ParamContainerEnum();
  // ObjectProperties tensorProperties = ObjectProperties.wrap(paramContainerEnum);
  // tensorProperties.set(properties);
  // assertEquals(paramContainerEnum.pivots, Pivots.FIRST_NON_ZERO);
  // assertEquals(paramContainerEnum.nameString, NameString.SECOND);
  // }
  // }
  //
  // public void testEnumNull() {
  // Properties properties;
  // {
  // ParamContainerEnum paramContainerEnum = new ParamContainerEnum();
  // ObjectProperties tensorProperties = ObjectProperties.wrap(paramContainerEnum);
  // properties = tensorProperties.createProperties();
  // }
  // properties.setProperty("nameString", NameString.THIRD.name());
  // {
  // ParamContainerEnum paramContainerEnum = new ParamContainerEnum();
  // ObjectProperties tensorProperties = ObjectProperties.wrap(paramContainerEnum);
  // tensorProperties.set(properties);
  // assertNull(paramContainerEnum.pivots);
  // assertEquals(paramContainerEnum.nameString, NameString.THIRD);
  // }
  // }
}
