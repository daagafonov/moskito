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
	
	private DashboardBean selectedDashboard;
	
	public DashboardBean getDashboard(String name) {
		if (!StringUtils.isEmpty(name))
			for (DashboardBean bean: this)
				if (bean.getName().equals(name))
					return bean;
		return null;
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
		
//		for (int i = 0; i < size(); i++) {
//			if (get(i).getName().equals(bean.getName())){
//				bean.setName(bean.getName()+"*");
//				add(bean);
//			}
//		}
		while(getDashboard(bean.getName()) != null){
			bean.setName(bean.getName()+"*");
		}
		super.add(bean);
		if (selectedDashboard == null) {
			selectedDashboard = bean;
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
			if (get(i).getName().equals(bean.getName()) && i != pos){
				bean.setName(bean.getName()+"*");
				add(pos, bean);
			}
		}
		if (selectedDashboard == null) {
			selectedDashboard = bean;
		}
		super.add(pos, bean);
	}

	public DashboardBean getSelectedDashboard() {
		DashboardBean result = selectedDashboard;
		if (result == null  && !isEmpty()) {
			result = get(0);
			selectedDashboard = result;
		}
		return result;
	}
	
	public void resetSelectedDashboard() {
		if (!isEmpty())
			selectedDashboard = get(0);
	}
	
	public boolean removeByName(String dashboardName) {
		if (StringUtils.isEmpty(dashboardName))
			throw new IllegalArgumentException("DashboardsConfig can't work with dashboard without name!");
		for (int i = 0; i < size(); i++) {
			if (get(i).getName().equals(dashboardName)){
				remove(i);
				if (selectedDashboard!= null && selectedDashboard.getName().equals(dashboardName)){
					resetSelectedDashboard();
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
			return removeByName(bean.getName());
		}
		return false;
	}

	public void setSelectedDashboard(DashboardBean selectedDashboard) {
		this.selectedDashboard = selectedDashboard;
	}
	
	public void setSelectedDashboard(String selectedDashboard) {
		DashboardBean bean = getDashboard(selectedDashboard);
		if (bean != null)
			this.selectedDashboard = bean;
		else
			throw new IllegalArgumentException("Can't set '"+selectedDashboard+"' dashboard as selected! We don't have dashboard with such name!");
	}

	@Override
	public String toString() {
		return "DashboardsConfig [selectedDashboard=" + selectedDashboard
				+ ", toString()=" + super.toString() + "]";
	}
	
}
