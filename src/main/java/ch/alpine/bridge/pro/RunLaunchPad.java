// code by jph
package ch.alpine.bridge.pro;

import java.awt.Font;
import java.awt.Window;
import java.util.Comparator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.ColumnPanel;
import ch.alpine.bridge.cgr.InstanceDiscovery;
import ch.alpine.bridge.cgr.InstanceRecord;
import ch.alpine.tensor.img.ColorDataLists;

// TODO all windows should have titles (noise demo simplex)
// TODO redundant to Col1 ...
public class RunLaunchPad {
  public static WindowProvider create(String packageName) {
    List<InstanceRecord<RunProvider>> list = //
        InstanceDiscovery.of(packageName, RunProvider.class);
    return new WindowProvider() {
      @Override
      public Window getWindow() {
        return new RunLaunchPad(list).jFrame;
      }
    };
  }

  private static final Comparator<InstanceRecord<?>> COMPARATOR = //
      new Comparator<InstanceRecord<?>>() {
        @Override
        public int compare(InstanceRecord<?> o1, InstanceRecord<?> o2) {
          return o1.toString().compareTo(o2.toString());
        }
      };
  private final JFrame jFrame = new JFrame();
  private final ColumnPanel columnPanel = new ColumnPanel();

  private RunLaunchPad(List<InstanceRecord<RunProvider>> list) {
    List<InstanceRecord<RunProvider>> sorted = list.stream().sorted(COMPARATOR).toList();
    // ---
    String prev = "";
    for (InstanceRecord<RunProvider> instanceRecord : sorted) {
      String packageName = instanceRecord.packageName();
      if (!prev.equals(packageName)) {
        JLabel jLabel = new JLabel(packageName);
        Font font = jLabel.getFont().deriveFont(Font.BOLD);
        jLabel.setFont(font);
        columnPanel.append(jLabel);
        prev = packageName;
      }
      RunProviderType rpt = RunProviderType.getType(instanceRecord.subcls());
      Icon icon = SolidIcon.create(ColorDataLists._110.strict().getColor(rpt.ordinal()), 18);
      JButton jButton = new JButton(instanceRecord.friendly(), icon);
      jButton.setHorizontalAlignment(SwingConstants.LEFT);
      jButton.addActionListener(_ -> instanceRecord.supplier().get().runStandalone());
      columnPanel.append(jButton);
    }
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JScrollPane jScrollPane = new JScrollPane(columnPanel);
    jScrollPane.getVerticalScrollBar().setUnitIncrement(25);
    jFrame.setContentPane(jScrollPane);
  }
}
