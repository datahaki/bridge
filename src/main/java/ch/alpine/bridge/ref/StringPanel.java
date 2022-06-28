// code by jph
package ch.alpine.bridge.ref;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.UndoManager;

import ch.alpine.bridge.swing.UIManagerColor;

/* package */ abstract class StringPanel extends FieldPanel {
  private static final Color COLOR_FAIL_BGND = new Color(255, 192, 192);
  private static final Color COLOR_FAIL_TEXT = new Color(51, 51, 51);
  // private static final int CARET_WIDTH = 2;
  private static final int MASK = InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK;
  private static final int UNDO = InputEvent.CTRL_DOWN_MASK;
  private static final int REDO = MASK;
  // ---
  /* protected */ final JTextField jTextField;
  private String fallbackValue = null;

  public StringPanel(FieldWrap fieldWrap, Object value) {
    super(fieldWrap);
    jTextField = Objects.isNull(value) //
        ? new JTextField()
        : new JTextField(fallbackValue = fieldWrap.toString(value));
    UndoManager undoManager = new UndoManager();
    jTextField.getDocument().addUndoableEditListener(undoManager);
    jTextField.setFont(FieldsEditorManager.getFont(FieldsEditorKey.FONT_TEXTFIELD));
    {
      FieldsEditorManager.establish(FieldsEditorKey.INT_STRING_PANEL_HEIGHT, jTextField);
    }
    jTextField.addActionListener(l -> nofifyIfValid(jTextField.getText()));
    jTextField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
        case KeyEvent.VK_ESCAPE: {
          if (Objects.nonNull(fallbackValue))
            jTextField.setText(fallbackValue);
          break;
        }
        case KeyEvent.VK_Z: {
          int modifiers = keyEvent.getModifiersEx();
          if ((modifiers & MASK) == UNDO && undoManager.canUndo())
            try {
              undoManager.undo();
            } catch (Exception exception) {
              exception.printStackTrace();
            }
          if ((modifiers & MASK) == REDO && undoManager.canRedo())
            try {
              undoManager.redo();
            } catch (Exception exception) {
              exception.printStackTrace();
            }
          break;
        }
        default:
          break;
        }
      }

      @Override
      public void keyReleased(KeyEvent keyEvent) {
        indicateGui();
      }
    });
    jTextField.addFocusListener(new FocusListener() {
      private String _value;

      @Override
      public void focusGained(FocusEvent focusEvent) {
        _value = jTextField.getText();
      }

      @Override
      public void focusLost(FocusEvent focusEvent) {
        String string = jTextField.getText();
        if (!string.equals(_value))
          nofifyIfValid(_value = string);
      }
    });
    jTextField.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void removeUpdate(DocumentEvent documentEvent) {
        // ---
      }

      @Override
      public void insertUpdate(DocumentEvent documentEvent) {
        // when text is pasted. On linux, this may be via center mouse button
        indicateGui();
      }

      @Override
      public void changedUpdate(DocumentEvent documentEvent) {
        // ---
      }
    });
    indicateGui();
    addListener(string -> fallbackValue = string);
  }

  protected final JTextField getJTextField() {
    return jTextField;
  }

  protected final void indicateGui() {
    if (jTextField.isEditable())
      if (isValid(jTextField.getText())) {
        jTextField.setForeground(UIManagerColor.TextField_foreground.get());
        jTextField.setBackground(UIManagerColor.TextField_background.get());
      } else {
        jTextField.setForeground(COLOR_FAIL_TEXT);
        jTextField.setBackground(COLOR_FAIL_BGND);
      }
  }

  // ---
  // general function
  protected final void nofifyIfValid(String string) {
    if (isValid(string))
      notifyListeners(string);
  }

  // general function
  private boolean isValid(String string) {
    Object object = fieldWrap().toValue(string);
    return Objects.nonNull(object) //
        && fieldWrap().isValidValue(object);
  }

  @Override // from FieldPanel
  public void updateJComponent(Object value) {
    String string = fieldWrap().toString(value);
    jTextField.setText(string);
    indicateGui();
  }
}
