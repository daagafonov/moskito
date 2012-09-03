package net.java.dev.moskito.webui.action.dashboards;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;
import net.anotheria.util.TimeUnit;
import net.java.dev.moskito.webui.action.BaseMoskitoUIAction;
import net.java.dev.moskito.webui.bean.GraphDataBean;
import net.java.dev.moskito.webui.bean.NaviItem;
import net.java.dev.moskito.webui.bean.ProducerBean;
import net.java.dev.moskito.webui.bean.ProducerDecoratorBean;
import net.java.dev.moskito.webui.bean.StatCaptionBean;
import net.java.dev.moskito.webui.bean.WidgetType;
import net.java.dev.moskito.webui.bean.dashboard.Caption;
import net.java.dev.moskito.webui.bean.dashboard.DashboardBean;
import net.java.dev.moskito.webui.bean.dashboard.DashboardWidgetBean;
import net.java.dev.moskito.webui.bean.dashboard.DashboardsConfig;
import net.java.dev.moskito.webui.bean.dashboard.Producer;
import net.java.dev.moskito.webui.bean.dashboard.ProducerGroup;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Dashboard action. This code is yet experimental and already unsupported.
 *
 * @author dsilenko
 */
public class BaseDashboardAction extends BaseMoskitoUIAction {

	protected static final String DASHBOARD_PARAMETER_NAME = "dashboard";
	
	protected static final String WIDGET_NAME_PARAMETER_NAME = "widgetName";
	protected static final String WIDGET_TYPE_PARAMETER_NAME = "widgetType";
	protected static final String WIDGET_ID_PARAMETER_NAME = "widgetId";
	protected static final String RELOAD_PARAMETER_NAME = "reload";
	
	private static final String DASHBOARDS_COOKIE_NAME = "dashboards";
	
	private static final String DASHBOARDS_SESSION_ATTR = "dashboards";
	
	protected static final String DASHBOARD_NAME = "dashboardName";
	
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws JSONException {
		printReq(req);
		String selectedDashboardId = req.getParameter(DASHBOARD_PARAMETER_NAME);
		DashboardBean dash = null;
		DashboardsConfig dashes = getDashboards(req);

		if (StringUtils.isEmpty(selectedDashboardId)) {
			dash = dashes.getDefaultDashboard();
//			if (dash != null)
//				selectedDashboard = dash.getName();
		} else {
			try {
//				dashes.setSelectedDashboard(selectedDashboard);
				dash = dashes.getDashboard(Integer.parseInt(selectedDashboardId));
			} catch (NumberFormatException e) {
				//TODO log wrong dashboard ID
			} catch (IllegalArgumentException e) {
				//TODO log wrong dashboard ID
			}
		}

		if (dash != null) {
			
			List<ProducerDecoratorBean> producerDecoratorBeans = getDecoratedProducers(req, getAPI().getAllProducers(), new HashMap<String, GraphDataBean>());
			refreshWidgetsData(req, Integer.parseInt(selectedDashboardId), producerDecoratorBeans);


//			if (/*widgets.isEmpty() || */req.getParameter(RELOAD_PARAMETER_NAME) != null) {
//				try {
//					CookiePersistence.loadDashboardsFromCookie(/*producerDecoratorBeans, */req);
//				} catch (JSONException e) {
//					System.out.println(e);
//					//todo handle this case and log
//				}
//			} else {
//				@SuppressWarnings("unchecked")
//				List<String> configAttributes = Collections.list(req.getParameterNames());
//				configAttributes.remove(WIDGET_NAME_PARAMETER_NAME);
//				configAttributes.remove(WIDGET_TYPE_PARAMETER_NAME);
//
//				List<ProducerGroup> widgetContent = getWidgetContent(producerDecoratorBeans, configAttributes);
//				DashboardWidgetBean widget = new DashboardWidgetBean(req.getParameter(WIDGET_NAME_PARAMETER_NAME));
//				widget.setType(WidgetType.getTypeByName(req.getParameter(WIDGET_TYPE_PARAMETER_NAME), WidgetType.TABLE));
//				widget.setConfigAttributes(configAttributes);
//
//				putWidgetToDashboard(req, selectedDashboardName, widget, widgetContent);
//
//				CookiePersistence.saveDashboardsToCookie(req, res);//TODO is this needed?
//			}


			//		List<DashboardWidgetBean> widgetsLeft = new ArrayList<DashboardWidgetBean>();
			//		List<DashboardWidgetBean> widgetsRight = new ArrayList<DashboardWidgetBean>();
			//		DashboardBean selectedDashboard = getDashboardByName(req, selectedDashboardName);
			//		if (selectedDashboard != null && selectedDashboard.getWidgets() != null) {
			////			for (DashboardWidgetBean widget : selectedDashboard.getWidgets())
			////				if (widgetsLeft.size() <= widgetsRight.size())
			////					widgetsLeft.add(widget);
			////				else
			////					widgetsRight.add(widget);
			//		}


			req.setAttribute("decorators", producerDecoratorBeans);
			req.setAttribute("dashboards", getDashboards(req));
			req.setAttribute("selectedDashboardId", selectedDashboardId);
			//req.setAttribute("widgets", widgets);
//			if (selectedDashboard != null) {
				req.setAttribute("widgetsLeft", dash.getWidgetsLeft());
				req.setAttribute("widgetsRight", dash.getWidgetsRight());
//			}
		}
		//req.setAttribute("graphDatas", graphData.values());
		req.setAttribute("isCanAddWidget", dash != null);
		req.setAttribute("pageTitle", "Dashboard");

		return mapping.findCommand(getForward(req));
	}



	/**
	 * Returns selected dashboard name from session.
	 *
	 * @param req
	 * @return dashboards
	 */
//	protected String getSelectedDashboardNameFromSession(HttpServletRequest req) {

//		String dashboard = req.getParameter(DASHBOARD_PARAMETER_NAME);
//		if (!StringUtils.isEmpty(dashboard)) {
//			req.getSession().setAttribute("selectedDashboardName", dashboard);
//			return dashboard;
//		}
//
//		String selectedDashBoardName = (String) req.getSession().getAttribute("selectedDashboardName");
//		if (StringUtils.isEmpty(selectedDashBoardName)) {
//			List<DashboardBean> dashboardBeans = getDashboardsFromSession(req);
//			if (!dashboardBeans.isEmpty())
//				req.getSession().setAttribute("selectedDashboardName", dashboardBeans.get(0));
//		}
//
//		return selectedDashBoardName;
//		DashboardBean dash = getDashboards(req).getSelectedDashboard();
//		return dash == null ? null : dash.getName(); 
//	}
	

	/**
	 * Returns dashboards from session.
	 *
	 * @param req
	 * @return dashboards
	 */
	protected static DashboardsConfig getDashboards(HttpServletRequest req) {
		Object dashboards = req.getSession().getAttribute(DASHBOARDS_SESSION_ATTR);
		if (dashboards == null) {
			try {
				CookiePersistence.loadDashboardsFromCookie(req);
				dashboards = req.getSession().getAttribute(DASHBOARDS_SESSION_ATTR);
			} catch (JSONException e) {
				e.printStackTrace();// TODO Auto-generated catch block
			}
			if (dashboards == null) {
				DashboardsConfig dashboardsNew = new DashboardsConfig();
				req.getSession().setAttribute(DASHBOARDS_SESSION_ATTR, dashboardsNew);
				return dashboardsNew;
			}
		}

		if (dashboards instanceof DashboardsConfig)
			return DashboardsConfig.class.cast(dashboards);

		throw new IllegalArgumentException("Wrong attribute in session");
	}


	/**
	 * Refreshes data for all widgets in specified dashboard.
	 *
	 * @param req
	 * @param dashboardId ID of dashboard that should be refreshed
	 * @param producerDecoratorBeans data for widgets
	 */
	@SuppressWarnings("unchecked")
	private void refreshWidgetsData(HttpServletRequest req, int dashboardId, List<ProducerDecoratorBean> producerDecoratorBeans) {
		DashboardBean dashboard = getDashboards(req).getDashboard(dashboardId);
		if (dashboard != null)
			for (List<DashboardWidgetBean> widgets : new List[]{dashboard.getWidgetsLeft(), dashboard.getWidgetsRight()})
				for (DashboardWidgetBean widget : widgets)
					widget.setProducerGroups(getWidgetContent(producerDecoratorBeans, widget.getConfigAttributes()));
	}
	
	/**
	 * Creates content for widget. Content - is a representation of html form with selected properties and loaded values.
	 *
	 * @param producerDecoratorBeans data for widgets
	 * @param attributes			 config for widgets
	 * @return content for widget
	 */
	private List<ProducerGroup> getWidgetContent(List<ProducerDecoratorBean> producerDecoratorBeans, List<String> attributes) {
		List<ProducerGroup> producerGroups = new ArrayList<ProducerGroup>();


		for (ProducerDecoratorBean producerDecoratorBean : producerDecoratorBeans) {
			if (producerDecoratorBean.getVisibility().isHidden())
				continue;

			List<Caption> captions = new ArrayList<Caption>();
			List<Producer> producers = new ArrayList<Producer>();

			boolean captionSelected = false;
			boolean producerSelected = false;

			List<Integer> indexes = new ArrayList<Integer>();
			int i = 0;
			for (StatCaptionBean statCaptionBean : producerDecoratorBean.getCaptions()) {
				Caption caption = new Caption(statCaptionBean.getCaption());
				if (attributes.contains(producerDecoratorBean.getName() + "_" + statCaptionBean.getCaption())) {
					indexes.add(i);
					caption.setSelectedCaption(true);
					captionSelected = true;
				}
				captions.add(caption);
				i++;
			}

			for (ProducerBean producerBean : producerDecoratorBean.getProducers()) {
				Producer producer = new Producer(producerBean.getId());
				if (attributes.contains(producerBean.getId())) {
					producer.setSelectedProducer(true);
					producerSelected = true;
					for (int index : indexes)
						producer.addValue(producerBean.getValues().get(index).getValue());
				}
				producers.add(producer);
			}

			ProducerGroup producerGroup = new ProducerGroup(producerDecoratorBean.getName());
			producerGroup.setCaptions(captions);
			producerGroup.setProducers(producers);

			if (captionSelected && producerSelected)
				producerGroup.setSelectedGroup(true);


			producerGroups.add(producerGroup);
		}

		return producerGroups;
	}

	

	/**
	 * If there is no dashboard with given name than null will be returned.
	 *
	 * @param req
	 * @param dashboardId dashboard id
	 * @return dash board with specified name
	 */
	protected DashboardBean getDashboardById(HttpServletRequest req, String dashboardId) {
		if (!StringUtils.isEmpty(dashboardId)) {
			try {
				int id = Integer.parseInt(dashboardId);
				for (DashboardBean dashboard : getDashboards(req))
					if (dashboard.getId() == id)
						return dashboard;
			} catch (NumberFormatException e) {
				//TODO log this
			}
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
	
	private static final String NEXT_DASHBOARD_ID_ATTR = "nextDashboardId";
	protected int getNextDashBoardId(HttpServletRequest req) {
		Integer nextId = Integer.class.cast(req.getSession().getAttribute(NEXT_DASHBOARD_ID_ATTR));
		if (nextId == null){
			nextId = Integer.valueOf(1);
		}
		saveNextDashBoardId(req, nextId);
		return nextId;
	}
	protected void saveNextDashBoardId(HttpServletRequest req, long id) {
		req.getSession().setAttribute(NEXT_DASHBOARD_ID_ATTR, id);
	}
	private static final String NEXT_WIDGET_ID_ATTR = "nextWidgetId";
	protected int getNextWidgetId(HttpServletRequest req) {
		Integer nextId = Integer.class.cast(req.getSession().getAttribute(NEXT_WIDGET_ID_ATTR));
		if (nextId == null){
			nextId = Integer.valueOf(1);
		}
		saveNextWidgetId(req, nextId);
		return nextId;
	}
	protected void saveNextWidgetId(HttpServletRequest req, long id) {
		req.getSession().setAttribute(NEXT_WIDGET_ID_ATTR, id);
	}
	
	@Deprecated //TODO delete this method
	private static void printReq(HttpServletRequest req) {
		System.out.println("REQ in ");
		String debug = "";
		try {
			@SuppressWarnings("unchecked") Enumeration<String> en = req.getParameterNames();
			while (en.hasMoreElements()) {
				String p = "" + en.nextElement();
				if (debug.length() > 0)
					debug += ", ";
				debug += p + "= " + req.getParameter(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("--> " + debug);
	}
	
	private static final Pattern FINE_PARAMETER_VALUE = Pattern.compile("\\w+");
	
	protected static String encodeParam(String parameterValue) {
		if (!FINE_PARAMETER_VALUE.matcher(parameterValue).matches()) {
			try {
				parameterValue = URLEncoder.encode(parameterValue, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return parameterValue;
	}
	
	
	protected static class CookiePersistence {
		/**
		 * Perform saving all dashboards into cookie.
		 *
		 * @param req
		 * @param res
		 */
		protected static void saveDashboardsToCookie(HttpServletRequest req, HttpServletResponse res) throws JSONException {

			JSONObject jsonDashboards = new JSONObject();
			DashboardsConfig config = getDashboards(req);

			if (config.getDefaultDashboard() != null) {
				jsonDashboards.put("defaultDash", config.getDefaultDashboard().getId());
			}
			for (DashboardBean dashboard : config) {
				jsonDashboards.put(dashboard.getName(), dashboard.toJSON());
			}


			Cookie cookie = null;
			try {
				cookie = new Cookie(DASHBOARDS_COOKIE_NAME, URLEncoder.encode( compressString(jsonDashboards.toString()), "UTF-8"));
			} catch (IOException e) {
				System.out.println(e);
			}

			int cookieMaxAge = Long.valueOf(TimeUnit.YEAR.getMillis() / 1000).intValue();
			cookie.setMaxAge(cookieMaxAge);
			cookie.setPath(req.getContextPath());

			res.addCookie(cookie);
		}

		/**
		 * Loads dashboards with widgets from cookies.
		 *
		 * @param producerDecoratorBeans data for widgets
		 * @param req					HttpServletRequest
		 * @throws JSONException
		 */
		protected static void loadDashboardsFromCookie(/*List<ProducerDecoratorBean> producerDecoratorBeans,*/ HttpServletRequest req) throws JSONException {

			DashboardsConfig dashes = new DashboardsConfig();
			req.getSession().setAttribute(DASHBOARDS_SESSION_ATTR, dashes);

			Cookie[] cookies = req.getCookies();
			if (cookies == null)
				return;
			String cookieValue = null;

			for (Cookie cookie : cookies)
				if (cookie.getName().equals(DASHBOARDS_COOKIE_NAME)) {
					cookieValue = cookie.getValue();
					break;
				}

			if (cookieValue == null)
				return;

			JSONObject jsonDashboards = null;
			try {
				jsonDashboards = new JSONObject(decompressString(URLDecoder.decode(cookieValue, "UTF-8")));
			} catch (IOException e) {
				System.out.println(e);			//TODO log here
			} catch (DataFormatException e) {
				System.out.println(e);			//TODO log here
			}


			if (jsonDashboards != null) {
				String[] dashboardNames = JSONObject.getNames(jsonDashboards);
				if (dashboardNames == null || dashboardNames.length == 0)
					return;
				
				int selectedDash = jsonDashboards.getInt("defaultDash");

				for (String dashboardName : dashboardNames) {
					if (dashboardName.equals("defaultDash")) {
						continue;
					}
					JSONObject jsonDash = jsonDashboards.getJSONObject(dashboardName);
					DashboardBean dash = DashboardBean.fromJSON(/*dashboardName, */jsonDash);
					dashes.add(dash);
					if (selectedDash == dash.getId()) {
						dashes.setDefaultDashboard(dash);
					}
				}
			}
		}

		/**
		 * Compresses data using zip deflater.
		 *
		 * @param data data to compress
		 * @return string representation of compressed data, encoded with BASE64Encoder
		 * @throws IOException
		 */
		private static String compressString(String data) throws IOException {
			byte[] input = data.getBytes("UTF-8");
			Deflater df = new Deflater();
			df.setLevel(Deflater.BEST_COMPRESSION);
			df.setInput(input);

			ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
			df.finish();
			byte[] buff = new byte[1024];
			while (!df.finished()) {
				int count = df.deflate(buff);
				baos.write(buff, 0, count);
			}
			baos.close();
			byte[] output = baos.toByteArray();

			return Base64.encodeBase64String(output);
		}

		/**
		 * Decompresses data.
		 *
		 * @param data data to decompress
		 * @return decompressed string
		 * @throws IOException
		 * @throws DataFormatException
		 */
		private static String decompressString(String data) throws IOException, DataFormatException {
			byte[] input = Base64.decodeBase64(data);

			Inflater ifl = new Inflater();
			ifl.setInput(input);

			ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
			byte[] buff = new byte[1024];
			while (!ifl.finished()) {
				int count = ifl.inflate(buff);
				baos.write(buff, 0, count);
			}
			baos.close();
			byte[] output = baos.toByteArray();

			return new String(output, "UTF-8");
		}

	}


}
