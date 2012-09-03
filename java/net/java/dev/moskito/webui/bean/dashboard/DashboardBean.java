package net.java.dev.moskito.webui.bean.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Dashboard object.
 *
 * @author dsilenko
 */
public class DashboardBean implements Serializable {

	private static final long serialVersionUID = -3814471914706674484L;
	private String name;
	private int id;
	private List<DashboardWidgetBean> widgetsLeft = new ArrayList<DashboardWidgetBean>();
	private List<DashboardWidgetBean> widgetsRight = new ArrayList<DashboardWidgetBean>();

	public DashboardBean(String aName) {
		name = aName;
	}

//	public DashboardBean(String aName, List<DashboardWidgetBean> aWidgets) {
//		name = aName;
//		widgets = aWidgets;
//	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		name = aName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<DashboardWidgetBean> getWidgetsLeft() {
		return widgetsLeft;
	}
	
	public List<DashboardWidgetBean> getWidgetsRight() {
		return widgetsRight;
	}

	public void setWidgetsLeft(List<DashboardWidgetBean> aWidgets) {
		if (aWidgets != null)
			widgetsLeft = aWidgets;
	}
	
	public void setWidgetsRight(List<DashboardWidgetBean> aWidgets) {
		if (aWidgets != null)
			widgetsRight = aWidgets;
	}
	
	public void addWidgetLeft(DashboardWidgetBean widget) {
		widgetsLeft.add(widget);
	}
	
	public void addWidgetRight(DashboardWidgetBean widget) {
		widgetsRight.add(widget);
	}

	@SuppressWarnings("unchecked")
	public boolean removeWidget(int widgetId) {
		for (List<DashboardWidgetBean> widgets : new List[]{widgetsLeft, widgetsRight}) {
			for (Iterator<DashboardWidgetBean> it = widgets.iterator(); it.hasNext(); ) {
				DashboardWidgetBean widget = it.next();
				if (widget.getId() == widgetId) {
					it.remove();
					return true;
				}
			}
		}
		return false;
	} 
	
	@SuppressWarnings("unchecked")
	public DashboardWidgetBean getWidget(int widgetId) {
		for (List<DashboardWidgetBean> widgets : new List[]{widgetsLeft, widgetsRight}) {
			for (Iterator<DashboardWidgetBean> it = widgets.iterator(); it.hasNext(); ) {
				DashboardWidgetBean widget = it.next();
				if (widget.getId() == widgetId) {
					return widget;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "DashboardBean [name=" + name + ", widgetsLeft=" + widgetsLeft
				+ ", widgetsRight=" + widgetsRight + "]";
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("name", name);
		json.put("id", id);
		json.put("left", getWidgetsJson(widgetsLeft));
		json.put("right", getWidgetsJson(widgetsRight));
		return json;
	}
	
	public static DashboardBean fromJSON(JSONObject json) throws JSONException {
		
		DashboardBean dash = new DashboardBean(json.getString("name"));
		dash.setId(json.getInt("id"));
		JSONArray widgetsLeft = json.getJSONArray("left");
		JSONArray widgetsRight = json.getJSONArray("right");
		
		if (widgetsLeft.length() > 0 ) {
			for (int i = 0; i < widgetsLeft.length(); i++) {
				JSONObject jsonWidget = widgetsLeft.getJSONObject(i);
				DashboardWidgetBean widget = DashboardWidgetBean.fromJson(jsonWidget);
				dash.addWidgetLeft(widget);
			}
		}
		if (widgetsRight.length() > 0 ) {
			for (int i = 0; i < widgetsRight.length(); i++) {
				JSONObject jsonWidget = widgetsRight.getJSONObject(i);
				DashboardWidgetBean widget = DashboardWidgetBean.fromJson(jsonWidget);
				dash.addWidgetRight(widget);
			}
		}
		
		return dash;
	}
	
	/**
	 * Make JSON representation of list with widgets.
	 *
	 * @param widgets list of dashboard widgets
	 * @return json representation
	 * @throws JSONException
	 */
	private static JSONArray getWidgetsJson(List<DashboardWidgetBean> beans) throws JSONException {
		JSONArray jsonWidgets = new JSONArray();
		if (beans != null && !beans.isEmpty()) {
			for (DashboardWidgetBean widget : beans)
				jsonWidgets.put(widget.toJSON());
		}
		return jsonWidgets;
	}

	@Deprecated 		// TODO SHOULD BE DELETED
	public List<DashboardWidgetBean> getWidgets() {
		return new ArrayList<DashboardWidgetBean>(){{ 
			addAll(widgetsLeft);
			addAll(widgetsRight);
		}};
	}

}
