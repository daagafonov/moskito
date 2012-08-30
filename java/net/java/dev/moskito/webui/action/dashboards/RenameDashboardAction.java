package net.java.dev.moskito.webui.action.dashboards;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
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
	
	private static final String DASHBOARD_OLD_NAME = "dashboardOldName";
	
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws JSONException {
		String dashboardName = req.getParameter(DASHBOARD_OLD_NAME);
		String newName = req.getParameter(DASHBOARD_NAME);
		if (!StringUtils.isEmpty(dashboardName)&& !StringUtils.isEmpty(newName)) {
			DashboardsConfig dashboards = getDashboards(req);
			DashboardBean bean = dashboards.getDashboard(dashboardName);
			if (bean != null) {
				bean.setName(newName);
				CookiePersistence.saveDashboardsToCookie(req, res);
			} else {
				//TODO log this!
			}
		}
		return mapping.redirect();
	}

}

