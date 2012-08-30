package net.java.dev.moskito.webui.action.dashboards;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;
import net.java.dev.moskito.webui.bean.dashboard.DashboardBean;
import net.java.dev.moskito.webui.bean.dashboard.DashboardsConfig;

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
			if (deleteWidget(req, req.getParameter(DELETE_WIDGET_PARAMETER_NAME), selectedDashboardName)) {
				CookiePersistence.saveDashboardsToCookie(req, res);
			}
		}
		
		return mapping.redirect();
	}

	
	/**
	 * Delete widget with specified ID from given dashboard.
	 *
	 * @param widgetId	ID of widget that should be deleted.
	 * @param dashboardName name of dashboard from which widget should be deleted
	 */
	private boolean deleteWidget(HttpServletRequest req, String widgetId, String dashboardName) {
		if (StringUtils.isEmpty(dashboardName) || StringUtils.isEmpty(widgetId))
			return false;

		DashboardsConfig dashboards = getDashboards(req);
		DashboardBean dashboard = dashboards.getDashboard(dashboardName);
		return dashboard.removeWidget(Long.valueOf(widgetId));
	}
	
}
