package ch.alpine.java.ref.obj;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ch.alpine.java.ref.gui.ToolbarsComponent;

public class FieldEditorDemo {
  public static void main(String[] args) {
    SimpleParam simpleParam = new SimpleParam();
    ToolbarsComponent toolbarsComponent = new FieldEditor(simpleParam).toolbarsComponent();
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setContentPane(toolbarsComponent.getJPanel());
    jFrame.setBounds(100, 100, 500, 500);
    jFrame.setVisible(true);
  }
}
