package net.java.dev.moskito.webui.action;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;
import net.anotheria.util.TimeUnit;
import net.java.dev.moskito.webui.bean.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author dsilenko
 */
public class ShowDashboardAction extends BaseMoskitoUIAction{
	private List<DashboardBean> dashboards = new ArrayList<DashboardBean>();
	private String selectedDashboardName = null;
	private final String DASHBOARDS_COOKIE_NAME = "dashboards";

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) {

		if ( !StringUtils.isEmpty(req.getParameter("dashboard")) ){
			selectedDashboardName = req.getParameter("dashboard");
		}
		
		Map<String, GraphDataBean> graphData = new HashMap<String, GraphDataBean>();
		List<ProducerDecoratorBean> decoratedProducers = getDecoratedProducers(req, getAPI().getAllProducers(), graphData);
		refreshWidgetsData(selectedDashboardName, decoratedProducers);


		if (/*widgets.isEmpty() || */req.getParameter("reload") != null){
			try {
				loadDashboardsFromCookie(decoratedProducers, req);
			} catch (JSONException e) {}
		} else {
			List<String> configAttributes = Collections.list(req.getParameterNames());
			List<DashboardWidgetBean.ProducerGroup> widgetConfig = createWidgetConfig(decoratedProducers, configAttributes);
			modifyDashboards(selectedDashboardName, req.getParameter("widgetName"), req.getParameter("widgetType"), widgetConfig, configAttributes);
			try {
				saveWidgetsToCookie(req, res);
			} catch (JSONException e) {}
		}

		List<DashboardWidgetBean> widgetsLeft = new ArrayList<DashboardWidgetBean>();
		List<DashboardWidgetBean> widgetsRight = new ArrayList<DashboardWidgetBean>();

		DashboardBean selectedDasboard = getDashboardByName(selectedDashboardName);
		if (selectedDasboard!=null &&  selectedDasboard.getWidgets()!=null){
			for (DashboardWidgetBean widget : selectedDasboard.getWidgets())
				if (widgetsLeft.size() <= widgetsRight.size())
					widgetsLeft.add(widget);
				else
					widgetsRight.add(widget);
		}

		req.setAttribute("decorators", decoratedProducers);
		req.setAttribute("dashboards", dashboards);
		req.setAttribute("selectedDashboardName", selectedDashboardName);

		//req.setAttribute("widgets", widgets);
		req.setAttribute("widgetsLeft", widgetsLeft);
		req.setAttribute("widgetsRight", widgetsRight);
		//req.setAttribute("graphDatas", graphData.values());
		req.setAttribute("pageTitle", "Dashboard");

		return mapping.findForward( getForward(req) );
	}
	
	/**
	private void storeDashboards(DashboardBean dashboardBean){
		for (DashboardBean dashboard : dashboards){
			if ( dashboard.getName().equals(dashboardBean.getName()) ){
				dashboard = dashboardBean;
				return;
			}
		}

		dashboards.add(dashboardBean);
	}**/

	private void saveWidgetsToCookie(HttpServletRequest req, HttpServletResponse res) throws JSONException {
		int cookieMaxAge = Long.valueOf( TimeUnit.YEAR.getMillis() / 1000).intValue();

		String jsonText = "";
		for (DashboardBean dashboard : dashboards){
			jsonText += dashboard.getName()+":{";
			String widgetJson = "";

			for (DashboardWidgetBean widget : dashboard.getWidgets()){
				String widgetConf = "";
				for(String attribute : widget.getConfigAttributes())
					widgetConf += (widgetConf.length()== 0 ? "" : ",") + attribute;

				widgetJson += widget.getName() + ":{attributes:\"" + widgetConf +"\", type:\"" + widget.getType() + "\"},";
			}

			jsonText += widgetJson + "},";
		}

		Cookie cookie = new Cookie(DASHBOARDS_COOKIE_NAME, "{"+jsonText+"}");
		cookie.setMaxAge(cookieMaxAge);
		cookie.setPath(req.getContextPath());

		res.addCookie(cookie);
	}

	private List<DashboardWidgetBean.ProducerGroup> createWidgetConfig(List<ProducerDecoratorBean> decoratedProducers, List<String> attributes){
		List<DashboardWidgetBean.ProducerGroup> producerGroups = new ArrayList<DashboardWidgetBean.ProducerGroup>();


		for ( ProducerDecoratorBean producerDecoratorBean : decoratedProducers ){
			if (producerDecoratorBean.getVisibility().isHidden())
				continue;

			List<DashboardWidgetBean.Caption> captions = new ArrayList<DashboardWidgetBean.Caption>();
			List<DashboardWidgetBean.Producer> producers = new ArrayList<DashboardWidgetBean.Producer>();

			boolean captionSelected=false;
			boolean producerSelected=false;

			List<Integer> indexes = new ArrayList<Integer>();
			int i = 0;
			for (StatCaptionBean statCaptionBean : producerDecoratorBean.getCaptions()){
				DashboardWidgetBean.Caption caption = new DashboardWidgetBean.Caption(statCaptionBean.getCaption());
				if (attributes.contains(producerDecoratorBean.getName()+"_"+statCaptionBean.getCaption())){
					indexes.add(i);
					caption.setSelectedCaption(true);
					captionSelected = true;
				}
				captions.add(caption);
				i++;
			}

			for (ProducerBean producerBean : producerDecoratorBean.getProducers()){
				DashboardWidgetBean.Producer producer = new DashboardWidgetBean.Producer(producerBean.getId());
				if (attributes.contains(producerBean.getId())){
					producer.setSelectedProducer(true);
					producerSelected = true;
					for (int index : indexes)
						producer.addValue(producerBean.getValues().get(index).getValue());
				}
				producers.add(producer);
			}

			DashboardWidgetBean.ProducerGroup producerGroup = new DashboardWidgetBean.ProducerGroup(producerDecoratorBean.getName());
			producerGroup.setCaptions(captions);
			producerGroup.setProducers(producers);
			if (captionSelected && producerSelected){
				producerGroup.setSelectedGroup(true);
			}

			producerGroups.add(producerGroup);
		}

		return producerGroups;
	}

	private void loadDashboardsFromCookie(List<ProducerDecoratorBean> decoratedProducers, HttpServletRequest req) throws JSONException {
		String jsonText = null;
		Cookie[] cookies = req.getCookies();

		if ( cookies == null )
			return;

		for ( Cookie cookie : cookies )
			if ( cookie.getName().equals(DASHBOARDS_COOKIE_NAME) ){
				jsonText = cookie.getValue();
				break;
			}

		if (jsonText == null)
			return;

		JSONObject dashboardsJSON = new JSONObject(jsonText);
		for (String dashboardName : JSONObject.getNames(dashboardsJSON)){
			JSONObject widgetJSON = dashboardsJSON.getJSONObject(dashboardName);
			for ( String widgetName : JSONObject.getNames(widgetJSON) ){
				JSONObject widgetConf = widgetJSON.getJSONObject(widgetName);

				List<String> configAttributes = Arrays.asList( widgetConf.get("attributes").toString().split(",") );
				modifyDashboards(dashboardName, widgetName, widgetConf.get("type").toString(), createWidgetConfig(decoratedProducers, configAttributes), configAttributes);
			}
		}

	}

	private void refreshWidgetsData(String dashboardName, List<ProducerDecoratorBean> producerDecoratorBeans){
		for (DashboardBean dashboard : dashboards){
			if ( dashboard.getName().equals(dashboardName)){
				for (DashboardWidgetBean widget : dashboard.getWidgets())
					widget.setProducerGroups( createWidgetConfig(producerDecoratorBeans, widget.getConfigAttributes()));
			}
		}
	}

	private void modifyDashboards(String dashboardName, String widgetName, String widgetType, List<DashboardWidgetBean.ProducerGroup> producerGroups, List<String> configAttributes){
		if ( StringUtils.isEmpty(dashboardName))
			return;

		DashboardBean dashboardBean = getDashboardByName(dashboardName);
		if (dashboardBean == null ){
			dashboardBean = new DashboardBean(dashboardName);
			dashboards.add(dashboardBean);
		}

		if (StringUtils.isEmpty(widgetName))
			return;

		List<DashboardWidgetBean> widgetBeans = dashboardBean.getWidgets();

		for (DashboardWidgetBean widget : widgetBeans){
			if (widget.getName().equals(widgetName)){
				widget.setProducerGroups(producerGroups);
				widget.setConfigAttributes(configAttributes);
				return;
			}
		}

		WidgetType wt = WidgetType.TABLE;
		try {
			wt = WidgetType.valueOf(widgetType);
		} catch (Exception s){}

		widgetBeans.add(new DashboardWidgetBean(widgetName, wt, producerGroups, configAttributes));
	}

	private DashboardBean getDashboardByName(String dashboardName){
		for (DashboardBean dashboard : dashboards)
			if ( dashboard.getName().equals(dashboardName) ){
				return dashboard;
			}

		return null;
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskDashBoard?ts=" + System.currentTimeMillis();
	}


	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.DASHBOARD;
	}


}
