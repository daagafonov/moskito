package net.java.dev.moskito.webui.bean.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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

	public List<DashboardWidgetBean> getWidgetsLeft() {
		return widgetsLeft;
	}
	
	public List<DashboardWidgetBean> getWidgetsRight() {
		return widgetsRight;
	}

	public void setWidgetsLeft(List<DashboardWidgetBean> aWidgets) {
		widgetsLeft = aWidgets;
	}
	
	public void setWidgetsRight(List<DashboardWidgetBean> aWidgets) {
		widgetsRight = aWidgets;
	}

	@Override
	public String toString() {
		return "DashboardBean [name=" + name + ", widgetsLeft=" + widgetsLeft
				+ ", widgetsRight=" + widgetsRight + "]";
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("left", getWidgetsJson(widgetsLeft));
		json.put("right", getWidgetsJson(widgetsRight));
		return json;
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

	public List<DashboardWidgetBean> getWidgets() {
		// TODO SHOULD BE DELETED
		return new ArrayList<DashboardWidgetBean>(){{ 
			addAll(widgetsLeft);
			addAll(widgetsRight);
		}};
	}

}
