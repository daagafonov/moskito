package net.java.dev.moskito.webui.action.dashboards;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.action.CommandRedirect;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.webui.bean.dashboard.DashboardsConfig;

import org.json.JSONException;

public class DeleteDashboardAction extends BaseDashboardAction {
	
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws JSONException {
		
		int dashboardId = Integer.valueOf(req.getParameter(DASHBOARD_PARAMETER_NAME));
		DashboardsConfig dashboards = getDashboards(req);
		if (dashboards.removeById(dashboardId)) {
			CookiePersistence.saveDashboardsToCookie(req, res);
		} else {
			//TODO log it
		}

		CommandRedirect redirect = mapping.redirect();
		if (dashboards.size() > 0) {
			redirect = redirect.addParameter(DASHBOARD_PARAMETER_NAME, ""+dashboards.get(0).getId());
		}
		return redirect;
	}

}
