// code by jph
package ch.alpine.bridge;

import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import ch.alpine.bridge.pro.RunProvider;
import ch.alpine.bridge.pro.SanityCheckRunProvider;
import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.tensor.ext.ref.InstanceDiscovery;

/** TODO inspect if proper window providers
 * 
 * class ch.alpine.bridge.demo.fig.ShowDemo
 * class ch.alpine.bridge.demo.fig.ShowWindowDemo
 * class ch.alpine.bridge.demo.ref.StoredExtensionDemo
 * class ch.alpine.bridge.demo.ref.TemplateEnumDemo
 * class ch.alpine.bridge.demo.ref.FieldsEditorDemo
 * class ch.alpine.bridge.demo.ref.OtherPackageParamDemo
 * class ch.alpine.bridge.demo.ref.AnotherPanelFieldsEditorDemo
 * class ch.alpine.bridge.demo.ref.GuiExtensionSynced
 * class ch.alpine.bridge.demo.ref.ToolbarFieldsEditorDemo
 * class ch.alpine.bridge.demo.ref.GuiExtensionDemo
 * class ch.alpine.bridge.demo.ref.PanelFieldsEditorDemo
 * class ch.alpine.bridge.demo.ref.DialogFieldsEditorDemo
 * class ch.alpine.bridge.demo.ref.PartialDemo */
class RunProviderTest {
  @TestFactory
  Stream<DynamicTest> dynamicTests() {
    return InstanceDiscovery.of(getClass().getPackageName(), RunProvider.class).stream() //
        .map(Supplier::get) //
        .map(instance -> DynamicTest.dynamicTest(instance.toString(), //
            () -> {
              SanityCheckRunProvider.INSTANCE.accept(instance);
              if (instance instanceof WindowProvider) {
                IO.println(instance.getClass());
                Thread.sleep(2000);
              }
            }));
  }
}
