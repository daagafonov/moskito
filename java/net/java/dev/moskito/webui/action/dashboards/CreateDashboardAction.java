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
 * Action for creating new empty dashboard with provided name.
 * Dashboard is added to the end of the dashboards list.
 * If dashboard with provided name already exists, name will be appended with '*' till it becomes unique.
 * @author dzhmud
 */
public class CreateDashboardAction extends BaseDashboardAction {
	
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws JSONException {
		String dashboardName = req.getParameter(DASHBOARD_NAME);
		if (!StringUtils.isEmpty(dashboardName)) {
			DashboardBean bean = new DashboardBean(dashboardName);
			DashboardsConfig dashboards = getDashboardsFromSession(req);
			dashboards.add(bean);
			dashboards.setSelectedDashboard(bean);//go to just created empty dashboard.
			
			saveDashboardsToCookie(req, res);
		}
		return mapping.redirect();
	}

}
