package net.java.dev.moskito.webui.bean;

import net.java.dev.moskito.webui.action.dashboards.BaseDashboardAction;

/**
 * Enum for widget types.
 *
 * @author dsilenko
 */
public enum WidgetType {

	TABLE("Table"),
	THRESHOLD("Threshold"),
	CHART("Chart");

	private String type;

	private WidgetType(String aType) {
		type = aType;
	}

	public String getType() {
		return type;
	}
	
	public static WidgetType getTypeByName(String name, WidgetType def) {
		if (BaseDashboardAction.DEBUG) 
			System.out.println("WidgetType.getTypeByName() got '"+name+"' as param.");
		for (WidgetType type : values()) {
			if (type.getType().equals(name))
				return type;
		}
		return def;
	}

}
