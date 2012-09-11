package net.java.dev.moskito.webui.action.dashboards;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.action.CommandRedirect;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.webui.bean.dashboard.DashboardBean;
import net.java.dev.moskito.webui.bean.dashboard.DashboardsConfig;

import org.json.JSONException;

public class DeleteWidgetAction extends BaseDashboardAction {
	
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws JSONException {
		String dashboard = req.getParameter(DASHBOARD_PARAMETER_NAME);
		int dashboardId = Integer.valueOf(dashboard);
		int widgetId =Integer.valueOf(req.getParameter(WIDGET_ID_PARAMETER_NAME));
		//TODO check params!
				
		DashboardsConfig dashboards = getDashboards(req);
		DashboardBean dash = dashboards.getDashboard(dashboardId);
		if (dash.removeWidget(widgetId)) {
			CookiePersistence.saveDashboardsToCookie(req, res);
		} else {
			//TODO log it
		}

		CommandRedirect redirect = mapping.redirect();
		redirect = redirect.addParameter(DASHBOARD_PARAMETER_NAME, dashboard);
		return redirect;
	}

}
