package net.java.dev.moskito.webui.action.dashboards;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.action.CommandRedirect;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;
import net.java.dev.moskito.webui.action.dashboards.BaseDashboardAction.CookiePersistence;
import net.java.dev.moskito.webui.bean.dashboard.DashboardBean;
import net.java.dev.moskito.webui.bean.dashboard.DashboardsConfig;

import org.json.JSONException;

/**
 * Action for renaming dashboard with provided name.
 * @author dzhmud
 */
public class RenameDashboardAction extends BaseDashboardAction {
	
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws JSONException {
		int dashboardId = Integer.parseInt(req.getParameter(DASHBOARD_PARAMETER_NAME));
		String newName = req.getParameter(DASHBOARD_NAME);
		if (!StringUtils.isEmpty(newName)) {
			DashboardsConfig dashboards = getDashboards(req);
			DashboardBean bean = dashboards.getDashboard(dashboardId);
			if (bean != null) {
				bean.setName(newName);
				CookiePersistence.saveDashboardsToCookie(req, res);
			} else {
				//TODO log this!
			}
		}
		return mapping.redirect().addParameter(DASHBOARD_PARAMETER_NAME, ""+dashboardId);
	}

}

