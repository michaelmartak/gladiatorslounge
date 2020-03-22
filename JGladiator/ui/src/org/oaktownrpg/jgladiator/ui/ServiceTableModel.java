/**
 * 
 */
package org.oaktownrpg.jgladiator.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.oaktownrpg.jgladiator.framework.GladiatorService;
import org.oaktownrpg.jgladiator.framework.GladiatorServiceProvider;
import org.oaktownrpg.jgladiator.framework.Hub;

/**
 * @author michaelmartak
 *
 */
@SuppressWarnings("serial")
class ServiceTableModel extends AbstractTableModel {

    private static final int COLUMN_COUNT = 4;

    static class ServiceRow {

        private final GladiatorServiceProvider sp;
        private final GladiatorService service;

        ServiceRow(GladiatorServiceProvider sp, GladiatorService service) {
            this.sp = sp;
            this.service = service;
        }

    }

    private final Hub hub;
    private final List<ServiceRow> rows;

    /**
     * 
     */
    ServiceTableModel(Hub hub) {
        this.hub = hub;
        List<ServiceRow> serviceRows = new LinkedList<>();
        hub.services().visitServices((GladiatorServiceProvider sp, GladiatorService service) -> {
            serviceRows.add(new ServiceRow(sp, service));
        });
        rows = new ArrayList<>(serviceRows);
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @Override
    public Object getValueAt(int row, int column) {
        final ServiceRow serviceRow = rows.get(row);
        switch (column) {
        case 0:
            return serviceRow.sp.getLocalizedName();
        case 1:
            return serviceRow.service.getLocalizedName();
        case 2:
            return hub.localization().string("ccg." + serviceRow.service.getCcg());
        case 3:
            return hub.localization().string("jgladiator.service.type." + serviceRow.service.getType().name());
        default:
            return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        String columnKey = "";
        switch (column) {
        case 0:
            columnKey = "jgladiator.service.providerName";
            break;
        case 1:
            columnKey = "jgladiator.service.serviceName";
            break;
        case 2:
            columnKey = "jgladiator.service.ccg";
            break;
        case 3:
            columnKey = "jgladiator.service.type";
            break;
        default:
            return "";
        }

        return hub.localization().string(columnKey);
    }

    public GladiatorService getService(int row) {
        return rows.get(row).service;
    }

}
