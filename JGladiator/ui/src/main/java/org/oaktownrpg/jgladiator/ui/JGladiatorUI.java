/**
 * 
 */
package org.oaktownrpg.jgladiator.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.batik.util.gui.resource.JToolbarButton;
import org.oaktownrpg.jgladiator.framework.GladiatorService;
import org.oaktownrpg.jgladiator.framework.LookupService;
import org.oaktownrpg.jgladiator.framework.ServiceFailure;
import org.oaktownrpg.jgladiator.framework.ServiceFailure.Severity;
import org.oaktownrpg.jgladiator.framework.ServiceType;
import org.oaktownrpg.jgladiator.framework.ServiceTypeEnum;
import org.oaktownrpg.jgladiator.framework.UIService;
import org.oaktownrpg.jgladiator.framework.ccg.GatherScope;
import org.oaktownrpg.jgladiator.framework.helper.AbstractService;

/**
 * UI for JGladiator. Initial version written using Java2D / Swing.
 * 
 * @author michaelmartak
 *
 */
public class JGladiatorUI extends AbstractService<JGladiatorUIServiceProvider>
        implements GladiatorService, UIService, Runnable {

    private JFrame frame;
    private JTable serviceTable;
    private Action importAction;
    private ServiceTableModel serviceTableModel;
    private GladiatorService selectedService;
    private LookupService lookupService;

    public JGladiatorUI(JGladiatorUIServiceProvider provider) {
        super(provider, "ui");
    }

    /**
     * Starts the JGladiator UI
     * 
     * @throws InterruptedException
     * @throws InvocationTargetException
     */
    @Override
    public void start(Consumer<ServiceFailure> onFailure, Runnable onReady) {
        try {
            EventQueue.invokeAndWait(this);
            onReady.run();
        } catch (InvocationTargetException | InterruptedException e) {
            onFailure.accept(new ServiceFailure(Severity.FATAL, localize("ui.failure"), e));
        }
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
        frame.setTitle(localize("jgladiator.frameTitle"));
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
        tabs.addTab(localize(title), component);
    }

    private Container servicesTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JToolBar toolbar = new JToolBar();
        JToolbarButton importButton = new JToolbarButton();
        importAction = importAction();
        importButton.setAction(importAction);
        toolbar.add(importButton);
        panel.add(toolbar, BorderLayout.NORTH);
        serviceTableModel = new ServiceTableModel(hub());
        serviceTable = new JTable(serviceTableModel);
        panel.add(new JScrollPane(serviceTable), BorderLayout.CENTER);
        serviceTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        serviceTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                serviceTableSelectionChanged();
            }
        });
        return panel;
    }

    void serviceTableSelectionChanged() {
        importAction.setEnabled(selectedServiceSupportsGather());
    }

    private boolean selectedServiceSupportsGather() {
        int row = serviceTable.getSelectedRow();
        if (row < 0) {
            return false;
        }
        selectedService = serviceTableModel.getService(row);
        ServiceTypeEnum type = selectedService.getType();
        if (type != ServiceTypeEnum.LOOKUP) {
            return false;
        }
        lookupService = selectedService.asType(LookupService.class);
        return lookupService.canGather();
    }

    private Action importAction() {
        RunnableAction action = new RunnableAction();
        action.putValue(Action.NAME, localize("ui.action.import"));
        action.setEnabled(false);
        action.setRunnable(this::gather);
        return action;
    }

    void gather() {
        if (lookupService == null || !lookupService.canGather()) {
            return;
        }
        final LookupService ls = lookupService;
        hub().executors().execute(
                () -> ls.gather(hub().cardLookup(), GatherScope.CARD_SETS, "*", (failure) -> handleFailure(failure)));
    }

    void handleFailure(ServiceFailure failure) {
        Logger.getLogger(getClass().getName()).severe(failure.getLocalizedMessage());
    }

    private Container builderTab() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(1024, 768));
        // TODO
        return panel;
    }

    @Override
    public ServiceTypeEnum getType() {
        return ServiceTypeEnum.UI;
    }

    @Override
    public <T extends ServiceType> T asType(Class<T> serviceType) {
        return serviceType.cast(this);
    }

}
