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
import org.oaktownrpg.jgladiator.framework.ServiceVisitor;

/**
 * @author michaelmartak
 *
 */
@SuppressWarnings("serial")
class ServicesTableModel extends AbstractTableModel {

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
	ServicesTableModel(Hub hub) {
		this.hub = hub;
		List<ServiceRow> serviceRows = new LinkedList<>();
		ServiceVisitor.visit(hub.getServiceProviders(), (GladiatorServiceProvider sp, GladiatorService service) -> {
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
		return 3;
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			return rows.get(row).sp.getLocalizedName();
		case 1:
			return rows.get(row).service.getLocalizedName();
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
			columnKey = "jgladiator.service.status";
			break;
		default:
			return "";
		}

		return hub.localization().string(columnKey);
	}

}
