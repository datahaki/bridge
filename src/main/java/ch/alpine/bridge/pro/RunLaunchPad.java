// code by jph
package ch.alpine.bridge.pro;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.ColumnPanel;
import ch.alpine.bridge.cgr.InstanceDiscovery;
import ch.alpine.bridge.cgr.InstanceRecord;
import ch.alpine.tensor.img.ColorDataLists;

// TODO BRIDGE introduce search field on top to filter demos
public class RunLaunchPad {
  public static WindowProvider create(String packageName) throws Exception {
    List<InstanceRecord<RunProvider>> list = //
        InstanceDiscovery.of(packageName, RunProvider.class);
    return new WindowProvider() {
      @Override
      public Window getWindow() {
        return new RunLaunchPad(packageName, list.stream().sorted(COMPARATOR).toList()).jFrame;
      }
    };
  }

  private static final Comparator<InstanceRecord<?>> COMPARATOR = //
      new Comparator<InstanceRecord<?>>() {
        @Override
        public int compare(InstanceRecord<?> ir1, InstanceRecord<?> ir2) {
          return ir1.toString().compareTo(ir2.toString());
        }
      };

  // ---
  record Entry(String string, JComponent jComponent) {
  }

  private final JFrame jFrame = new JFrame();
  private final List<Entry> listing = new LinkedList<>();

  private RunLaunchPad(String rootName, List<InstanceRecord<RunProvider>> list) {
    final int length = rootName.length();
    String prev = rootName;
    ColumnPanel columnPanel = new ColumnPanel();
    Font font = new JLabel().getFont().deriveFont(Font.BOLD);
    for (InstanceRecord<RunProvider> instanceRecord : list) {
      String packageName = instanceRecord.packageName();
      if (!prev.equals(packageName)) {
        JLabel jLabel = new JLabel(packageName.substring(length).replace(".", " "));
        jLabel.setFont(font);
        columnPanel.add(jLabel);
        prev = packageName;
      }
      RunProviderType rpt = RunProviderType.getType(instanceRecord.subcls());
      Icon icon = SolidIcon.create(ColorDataLists._110.strict().getColor(rpt.ordinal()), 18);
      String string = instanceRecord.friendly();
      JButton jButton = new JButton(string, icon);
      listing.add(new Entry(string.toUpperCase(), jButton));
      jButton.setHorizontalAlignment(SwingConstants.LEFT);
      jButton.addActionListener(_ -> instanceRecord.supplier().get().runStandalone());
      columnPanel.add(jButton);
    }
    JPanel contentPane = new JPanel(new BorderLayout());
    JTextField jTextField = new JTextField();
    contentPane.add(jTextField, BorderLayout.NORTH);
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(columnPanel, BorderLayout.NORTH);
    JScrollPane jScrollPane = new JScrollPane(jPanel);
    jScrollPane.getVerticalScrollBar().setUnitIncrement(25);
    contentPane.add(jScrollPane, BorderLayout.CENTER);
    jTextField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent keyEvent) {
        String search = jTextField.getText().toUpperCase();
        boolean isEmpty = search.isEmpty();
        for (Entry entry : listing)
          entry.jComponent.setVisible(isEmpty || entry.string().contains(search));
        JViewport jViewport = jScrollPane.getViewport();
        jViewport.setViewPosition(new Point(0, 0));
        // chatgpt:
        // "Whenever the contents of a JScrollPane change size
        // then call revalidate() on the viewport view."
        jViewport.revalidate();
      }
    });
    jFrame.setContentPane(contentPane);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setTitle(rootName + " [" + list.size() + "]");
  }
}
