package net.java.dev.moskito.webui.action.dashboards;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;
import net.java.dev.moskito.webui.bean.dashboard.DashboardsConfig;

import org.json.JSONException;

public class DeleteDashboardAction extends BaseDashboardAction {
	
	private static final String DASHBOARD_NAME = "name";

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws JSONException {
		
		String dashboardName = req.getParameter(DASHBOARD_NAME);
		if (!StringUtils.isEmpty(dashboardName)) {
			DashboardsConfig dashboards = getDashboardsFromSession(req);
			if (dashboards.removeByName(dashboardName)) {
				saveDashboardsToCookie(req, res);
			} else {
				//TODO log it
			}
		}
		return mapping.redirect();
	}

}
