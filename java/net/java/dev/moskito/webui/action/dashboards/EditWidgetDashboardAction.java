package net.java.dev.moskito.webui.action.dashboards;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.action.CommandRedirect;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;
import net.java.dev.moskito.webui.bean.WidgetType;
import net.java.dev.moskito.webui.bean.dashboard.DashboardBean;
import net.java.dev.moskito.webui.bean.dashboard.DashboardWidgetBean;

import org.json.JSONException;

public class EditWidgetDashboardAction extends BaseDashboardAction {
	
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws JSONException {
		String dashboardId = req.getParameter(DASHBOARD_PARAMETER_NAME);
		String widgetId = req.getParameter(WIDGET_ID_PARAMETER_NAME);
		String widgetName = req.getParameter(WIDGET_NAME_PARAMETER_NAME);
		if (!StringUtils.isEmpty(dashboardId)) {
			
			
			List<String> configAttributes = Collections.list(req.getParameterNames());
			System.out.println("1.Create widget parameter names : " + configAttributes);
			configAttributes.remove(WIDGET_NAME_PARAMETER_NAME);
			configAttributes.remove(WIDGET_TYPE_PARAMETER_NAME);
			configAttributes.remove(DASHBOARD_PARAMETER_NAME);
			System.out.println("2. Filtered create widget parameter names : " + configAttributes);

//			List<ProducerGroup> widgetContent = getWidgetContent(producerDecoratorBeans, configAttributes);
			DashboardBean dash = getDashboardById(req, dashboardId);
			if (dash != null) {
				DashboardWidgetBean widget = dash.getWidget(Integer.parseInt(widgetId));
//			widget.setId(getDashboards(req).getNewWidgetId());
//			widget.setType(WidgetType.getTypeByName(req.getParameter(WIDGET_TYPE_PARAMETER_NAME), WidgetType.TABLE));
				widget.setConfigAttributes(configAttributes);
				if (!widget.getName().equals(widgetName)) {
					widget.setName(widgetName);
				}
				CookiePersistence.saveDashboardsToCookie(req, res);
			
			} else {
				//TODO log it
			}

//			putWidgetToDashboard(req, dashboardName, widget, widgetContent);
//			if (bean.getWidgetsLeft().size() > bean.getWidgetsRight().size()) {
//				bean.addWidgetRight(widget);
//			} else {
//				bean.addWidgetLeft(widget);
//			}
			
		}
		
		return mapping.redirect().addParameter(DASHBOARD_PARAMETER_NAME, dashboardId);
	}
	
	/**
	 * Puts widget into specified dashboard. If specified dashboard is absent it wil be created automatically.
	 *
	 * @param req
	 * @param dashboardName	name of dashboard into which widget will be puted
	 * @param widgetName	   widget name
	 * @param widgetTypeName   widget type name
	 * @param producerGroups   content of widget
	 * @param configAttributes config that helps for content for widget
	 */
//	protected void putWidgetToDashboard(HttpServletRequest req, String dashboardName, DashboardWidgetBean widgetBean, List<ProducerGroup> producerGroups) {
//		if (StringUtils.isEmpty(dashboardName))
//			return;
//
//		DashboardBean dashboardBean = getDashboardByName(req, dashboardName);
//		if (dashboardBean == null) {
////			dashboardBean = new DashboardBean(dashboardName);
////			putDashboardToSession(req, dashboardBean);
//			throw new IllegalArgumentException("Wrong dashboard name passed! Can't add widget to dashboard '"+dashboardName+"'!");
//		}
//
//		for (List<DashboardWidgetBean> widgetBeans : new List[]{dashboardBean.getWidgetsLeft(), dashboardBean.getWidgetsRight()}) {
//
//			for (DashboardWidgetBean widget : widgetBeans)
//				if (widget.getName().equals(widgetBean.getName())) {
//					widget.setProducerGroups(producerGroups);
//					return;
//				}
//			widgetBean.setProducerGroups(producerGroups);
//			widgetBeans.add(widgetBean);
//		}
//	}
	
}
