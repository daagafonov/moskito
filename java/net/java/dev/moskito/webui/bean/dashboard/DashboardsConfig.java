package net.java.dev.moskito.webui.bean.dashboard;

import java.util.ArrayList;
import java.util.Collection;

import net.anotheria.util.StringUtils;

/**
 * Wrapper class for list of DashboardBeans. Created to put here some often operations.
 * @author dzhmud
 */
public class DashboardsConfig extends ArrayList<DashboardBean> {
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 6625586000446992098L;
	
	private DashboardBean defaultDashboard;
	
	public DashboardBean getDashboard(int id) {
		for (DashboardBean bean: this)
			if (bean.getId() == id)
				return bean;
		return null;
	}
	
	public DashboardBean getDefaultDashboard() {
		if (defaultDashboard == null && size() > 0) {
			setDefaultDashboard(get(0));
		}
		return defaultDashboard;
	}
	
	public void setDefaultDashboard(DashboardBean dash) {
		defaultDashboard = dash;
	}
	
	/**
	 * Adds new dashboard. 
	 * If dashboard with such name already exists, appends '*' symbol to dashboard name till we get unique name.
	 * 
	 * @see Collection#add(Object)
	 */
	@Override
	public boolean add(DashboardBean bean) {
		checkBean(bean);
		
		for (int i = 0; i < size(); i++) {
			if (get(i).getName().equals(bean.getName())){
				bean.setName(bean.getName()+"*");
				add(bean);
			}
		}
//		while(getDashboard(bean.getName()) != null){
//			bean.setName(bean.getName()+"*");
//		}
		super.add(bean);
		if (defaultDashboard == null) {
			defaultDashboard = bean;
		}
		return true;
	}

	/**
	 * Method for checking if we can process DashboardBean.
	 * Checks if bean is NULL and if it has name.
	 * Thrown exceptions implemented as described in {@link Collection#add(Object)}
	 * @param bean
	 */
	private static final void checkBean(DashboardBean bean) {
		if (bean == null)
			throw new NullPointerException("DashboardsConfig can't work with NULL dashboard bean!");
		if (StringUtils.isEmpty(bean.getName()))
			throw new IllegalArgumentException("DashboardsConfig can't work with dashboard without name!");
	}
	
	/**
	 * Adds new dashboard to the specified position. 
	 * If dashboard with such name already exists and it's not in the desired position, appends '*' symbol to dashboard name till we get unique name.
	 * 
	 * @see Collection#add(Object)
	 */
	@Override
	public void add(int pos, DashboardBean bean) {
		checkBean(bean);
		for (int i = 0; i < size(); i++) {
			if (get(i).getName().equals(bean.getName())){
				bean.setName(bean.getName()+"*");
				add(pos, bean);
			}
		}
		if (defaultDashboard == null) {
			defaultDashboard = bean;
		}
		super.add(pos, bean);
	}

//	public DashboardBean getSelectedDashboard() {
//		DashboardBean result = defaultDashboard;
//		if (result == null  && !isEmpty()) {
//			result = get(0);
//			defaultDashboard = result;
//		}
//		return result;
//	}
//	
//	public void resetSelectedDashboard() {
//		if (!isEmpty())
//			defaultDashboard = get(0);
//	}
	
	public boolean removeById(int dashboardId) {
		for (int i = 0; i < size(); i++) {
			if (get(i).getId() == dashboardId){
				remove(i);
				if (defaultDashboard != null && defaultDashboard.getId() == dashboardId && size() > 0){
					defaultDashboard = get(0);
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean remove(Object dashboard) {//TODO check if this is needed
		if (dashboard instanceof DashboardBean) {
			DashboardBean bean = DashboardBean.class.cast(dashboard);
			checkBean(bean);
			return removeById(bean.getId());
		}
		return false;
	}

//	public void setSelectedDashboard(DashboardBean selectedDashboard) {
//		this.defaultDashboard = selectedDashboard;
//	}
//	
//	public void setSelectedDashboard(String selectedDashboard) {
//		DashboardBean bean = getDashboard(selectedDashboard);
//		if (bean != null)
//			this.defaultDashboard = bean;
//		else
//			throw new IllegalArgumentException("Can't set '"+selectedDashboard+"' dashboard as selected! We don't have dashboard with such name!");
//	}
	
	public int getMaxWidgetId() {
		int maxId = 0;
		for (DashboardBean dash : this) {
			for (DashboardWidgetBean widget : dash.getWidgetsLeft()) {
				maxId = Math.max(maxId, widget.getId());
			}
			for (DashboardWidgetBean widget : dash.getWidgetsRight()) {
				maxId = Math.max(maxId, widget.getId());
			}
		}
		return maxId;
	}
	
	public int getNewWidgetId() {
		return getMaxWidgetId() + 1;
	}
	
	public int getMaxDashId() {
		int maxId = 0;
		for (DashboardBean dash : this) {
			maxId = Math.max(maxId, dash.getId());
		}
		return maxId;
	}

	@Override
	public String toString() {
		return "DashboardsConfig [defaultDashboard=" + defaultDashboard
				+ ", toString()=" + super.toString() + "]";
	}
	
}
