// code by jph, gjoel
package ch.alpine.bridge.ref.util;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.JComponent;

import ch.alpine.bridge.ref.FieldPanel;
import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.FieldsEditorParam;
import ch.alpine.bridge.ref.ann.FieldPreferredWidth;
import ch.alpine.bridge.util.CopyOnWriteLinkedSet;

/** Order of listener notification:
 * 1) the value is updated in the object's field
 * 2) the universal listeners are notified
 * 3) the specific {@link FieldPanel} listeners are notified */
public abstract class FieldsEditor {
  /** the association of FieldPanel and Object is required.
   * In a field editor instance, the object as value in the map is not necessary unique. */
  private record FieldPanelObject(FieldPanel fieldPanel, Object object) {
    public void updateJComponent() {
      Object value = StaticHelper.get(fieldPanel.fieldWrap().getField(), object);
      if (Objects.nonNull(value))
        // any exception caused by implementation based on presented value is deliberately not caught
        fieldPanel.updateJComponent(value);
    }
  }

  private final List<FieldPanelObject> list = new LinkedList<>();
  private final Set<Runnable> set = new CopyOnWriteLinkedSet<>();

  /** register listener that converts a GUI update to value and assigns field
   * register listener to notify universal listeners
   * 
   * function applies annotations specific to field that concern layout
   * of corresponding {@link JComponent}.
   * 
   * The list of annotations regarded currently consists of:
   * {@link FieldPreferredWidth}
   * 
   * @param fieldPanel
   * @param object */
  protected final void register(FieldPanel fieldPanel, Object object) {
    list.add(new FieldPanelObject(fieldPanel, object));
    FieldWrap fieldWrap = fieldPanel.fieldWrap();
    fieldPanel.addListener(string -> fieldWrap.setIfValid(object, string));
    fieldPanel.addListener(string -> notifyUniversalListeners());
    // ---
    JComponent jComponent = fieldPanel.getJComponent();
    // ---
    FieldsEditorParam.GLOBAL.minExtension(jComponent);
    // ---
    FieldPreferredWidth fieldPreferredWidth = fieldWrap.getField().getAnnotation(FieldPreferredWidth.class);
    if (Objects.nonNull(fieldPreferredWidth)) {
      Dimension dimension = jComponent.getPreferredSize();
      // for instance, a JSlider has a default preferred width of 200
      dimension.width = fieldPreferredWidth.value();
      jComponent.setPreferredSize(dimension);
    }
  }

  /** the function exposes the FieldPanel instances used for the fields in the editor.
   * By appending a consumer via {@link FieldPanel#addListener(Consumer)} to a single
   * instance, granular behavior can be achieved.
   * 
   * In contrast {@link #addUniversalListener(Runnable)} adds a given consumer to every
   * FieldPanel instance, because this is the more needed option in applications.
   * 
   * @return field panel for each field in the object that appears in the editor */
  public final List<FieldPanel> list() {
    return list.stream().map(FieldPanelObject::fieldPanel).collect(Collectors.toList());
  }

  /** in case the object field values have been modified outside the gui, invoking
   * {@link #updateJComponents()} causes the gui components to update their
   * appearance based on the new value.
   * 
   * Naturally, this action should not be triggered while the user is actively
   * modifying the fields, but by a rare, sporadic external trigger. */
  public final void updateJComponents() {
    list.forEach(FieldPanelObject::updateJComponent);
  }

  /** @param runnable that will be run if any value in editor was subject to change */
  public final void addUniversalListener(Runnable runnable) {
    set.add(runnable);
  }

  /** @param runnable to remove from set of universal listeners */
  public final void removeUniversalListener(Runnable runnable) {
    set.remove(runnable);
  }

  /** Hint:
   * typically this function is not called by the application layer */
  public final void notifyUniversalListeners() {
    set.forEach(Runnable::run);
  }
}
