package net.java.dev.moskito.webui.action.dashboards;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.action.CommandRedirect;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;
import net.java.dev.moskito.webui.bean.dashboard.DashboardBean;
import net.java.dev.moskito.webui.bean.dashboard.DashboardsConfig;

import org.json.JSONException;

/**
 * Action for creating new empty dashboard with provided name.
 * Dashboard is added to the end of the dashboards list.
 * If dashboard with provided name already exists, name will be appended with '*' till it becomes unique.
 * @author dzhmud
 */
public class CreateDashboardAction extends BaseDashboardAction {
	
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws JSONException {
		String dashboardName = req.getParameter(DASHBOARD_NAME);
		Integer dashboardId = null;
		if (!StringUtils.isEmpty(dashboardName)) {
			DashboardsConfig dashboards = getDashboards(req);
			DashboardBean bean = new DashboardBean(dashboardName);
			bean.setId(dashboards.getMaxDashId() + 1);
			dashboards.add(bean);
			dashboardId = bean.getId();
			
			CookiePersistence.saveDashboardsToCookie(req, res);
		}
		
		CommandRedirect redirect = mapping.redirect();
		if (dashboardId != null) {
			redirect = redirect.addParameter(DASHBOARD_PARAMETER_NAME, dashboardId.toString());
		}
		return redirect;
	}
	
}
