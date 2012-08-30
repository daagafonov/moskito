package net.java.dev.moskito.webui.action.dashboards;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;
import net.java.dev.moskito.webui.bean.dashboard.DashboardBean;
import net.java.dev.moskito.webui.bean.dashboard.DashboardWidgetBean;

import org.json.JSONException;

/**
 * Class used for editing existing dashboard - adding/editing/deleting/reordering widgets. 
 * @author dzhmud
 */
public class EditDashboardAction extends BaseDashboardAction {
	
	private static final String ACTION = "action";
	
	private static final String DELETE_WIDGET_PARAMETER_NAME = "deleteWidget";
	
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws JSONException {
		
		String selectedDashboardName = getSelectedDashboardNameFromSession(req);

		if (!StringUtils.isEmpty(req.getParameter(DELETE_WIDGET_PARAMETER_NAME))) {
			deleteWidget(req, req.getParameter(DELETE_WIDGET_PARAMETER_NAME), selectedDashboardName);
			saveDashboardsToCookie(req, res);
		}
		
		return mapping.redirect();
	}

	
	/**
	 * Delete widget with specified name from given dashboard.
	 *
	 * @param widgetName	name of widget that should be deleted.
	 * @param dashboardName name of dashboard from which widget should be deleted
	 */
	private void deleteWidget(HttpServletRequest req, String widgetName, String dashboardName) {
		if (dashboardName == null)
			return;

		List<DashboardBean> dashboards = getDashboardsFromSession(req);
		for (DashboardBean dashboard : dashboards) {
			if (!dashboard.getName().equalsIgnoreCase(dashboardName))
				continue;

			List<DashboardWidgetBean> widgets = dashboard.getWidgets();
			if (widgets == null)
				continue;
			for (int i = 0; i < widgets.size(); i++)
				if (widgets.get(i).getName().equals(widgetName)) {
					widgets.remove(i);
					return;
				}
		}
	}
	
}
