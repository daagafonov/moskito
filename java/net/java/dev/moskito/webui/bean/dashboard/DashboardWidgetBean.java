package net.java.dev.moskito.webui.bean.dashboard;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.java.dev.moskito.webui.bean.ProducerDecoratorBean;
import net.java.dev.moskito.webui.bean.WidgetType;

/**
 * Class for storing widget config.
 *
 * @author dsilenko
 */
public class DashboardWidgetBean implements Serializable {

	/**
	 *	Serial version UID. 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private WidgetType type;
	private List<ProducerDecoratorBean> producerDecoratorBeans;
	private List<String> configAttributes;
	private List<ProducerGroup> producerGroups;

	public DashboardWidgetBean() {
	}

	public DashboardWidgetBean(String aName) {
		name = aName;
	}

	public DashboardWidgetBean(String aName, WidgetType aType, List<ProducerGroup> aProducerGroups) {
		name = aName;
		type = aType;
		producerGroups = aProducerGroups;
	}

	public DashboardWidgetBean(String aName, WidgetType aType, List<ProducerGroup> aProducerGroups, List<String> aConfigAttributes) {
		this(aName, aType, aProducerGroups);
		configAttributes = aConfigAttributes;
	}

	public void setProducerGroups(List<ProducerGroup> producerGroups) {
		this.producerGroups = producerGroups;
	}

	public List<ProducerGroup> getProducerGroups() {
		return producerGroups;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WidgetType getType() {
		return type;
	}

	public void setType(WidgetType widgetType) {
		type = widgetType;
	}

	public List<ProducerDecoratorBean> getProducerDecoratorBeans() {
		return producerDecoratorBeans;
	}

	public void setProducerDecoratorBeans(List<ProducerDecoratorBean> aProducerDecoratorBeans) {
		producerDecoratorBeans = aProducerDecoratorBeans;
	}

	public List<String> getConfigAttributes() {
		return configAttributes;
	}

	public void setConfigAttributes(List<String> aConfigAttributes) {
		configAttributes = aConfigAttributes;
	}
	
	@Override
	public String toString() {
		return "DashboardWidgetBean [id=" + id + ", name=" + name + ", type="
				+ type + ", producerDecoratorBeans=" + producerDecoratorBeans
				+ ", configAttributes=" + configAttributes
				+ ", producerGroups=" + producerGroups + "]";
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject jsonWidget = new JSONObject();
		jsonWidget.put("id", getId());
		jsonWidget.put("name", getName());
		jsonWidget.put("type", getType());
		jsonWidget.put("attributes", getConfigAttributes());
		return jsonWidget;
	}
	
	public static DashboardWidgetBean fromJson(JSONObject jsonWidget) throws JSONException {
		DashboardWidgetBean widget = null;
		if (jsonWidget != null) {
			widget = new DashboardWidgetBean();
			widget.setId(jsonWidget.getInt("id"));
			widget.setName(jsonWidget.getString("name"));
			widget.setType(WidgetType.getTypeByName(jsonWidget.getString("type"), WidgetType.TABLE));
			
			List<String> attributes = new ArrayList<String>();
			JSONArray jsonAttributes = jsonWidget.getJSONArray("attributes");
			for (int i = 0; i < jsonAttributes.length(); i++)
				attributes.add(jsonAttributes.getString(i));
		}
		return widget;
	}

}
