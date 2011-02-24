package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Items in the navigation.
 *
 * @author lrosenberg.
 */
public enum NaviItem {
	/**
	 * Dashboard menu item.
	 */
	DASHBOARD("Dashboard"),
	/**
	 * Producers menu item.
	 */
	PRODUCERS("Producers"),
	/**
	 * Use-cases navi items.
	 */
	USECASES("Use Cases"),
	/**
	 * Monitoring sessions navi item.
	 */
	SESSIONS("Monitoring Sessions"),
	/**
	 * Charts navi item.
	 */
	CHARTS("Charts"),
	/**
	 * Constant for actions without own navigation item.
	 */
	NONE("None"),
	THRESHOLDS("Thresholds");
	
	
	/**
	 * Current menu.
	 */
	private static ArrayList<NaviItem> menu;
	static{
		menu = new ArrayList<NaviItem>();
		menu.add(DASHBOARD);
		menu.add(PRODUCERS);
		menu.add(THRESHOLDS);
		menu.add(SESSIONS);
		menu.add(CHARTS);
		//menu.add(USECASES);
	}
	/**
	 * Caption of the navi item.
	 */
	private String caption;
	
	private NaviItem(String aCaption){
		caption = aCaption;
	}
	
	public String getId(){
		return toString().toLowerCase();
	}
	
	public String getCaption(){
		return caption;
	}
	
	public static final List<NaviItem> getMenu(){
		return menu;
	}
}
