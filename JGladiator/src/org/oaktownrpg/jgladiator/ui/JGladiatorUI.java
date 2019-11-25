/**
 * 
 */
package org.oaktownrpg.jgladiator.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.oaktownrpg.jgladiator.framework.Hub;

/**
 * UI for JGladiator. Initial version written using Java2D / Swing.
 * 
 * @author michaelmartak
 *
 */
public class JGladiatorUI implements Runnable {

    private final Hub hub;
    private JFrame frame;

    public JGladiatorUI(Hub hub) {
        this.hub = hub;
    }

    /**
     * Starts the JGladiator UI
     * 
     * @throws InterruptedException
     * @throws InvocationTargetException
     */
    public void start() throws InvocationTargetException, InterruptedException {
        EventQueue.invokeAndWait(this);
    }

    @Override
    public void run() {
        // Set the look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            Logger.getLogger(getClass().getName()).warning(e.getMessage());
        }
        // Main window settings
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(hub.localization().string("jgladiator.frameTitle"));
        // Main content pane
        JTabbedPane tabs = new JTabbedPane();
        addTab(tabs, "jgladiator.tab.services", servicesTab());
        addTab(tabs, "jgladiator.tab.builder", builderTab());
        frame.setContentPane(tabs);
        // Show the window
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addTab(JTabbedPane tabs, String title, Container component) {
        tabs.addTab(hub.localization().string(title), component);
    }

    private Container servicesTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable(new ServicesTableModel(hub));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private Container builderTab() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(1024, 768));
        // TODO
        return panel;
    }

}
