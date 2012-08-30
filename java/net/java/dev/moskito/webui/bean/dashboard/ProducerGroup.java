package net.java.dev.moskito.webui.bean.dashboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a group of producers.
 */
public class ProducerGroup {
	private String groupName;
	private List<Producer> producers = new ArrayList<Producer>();
	private List<Caption> captions = new ArrayList<Caption>();
	private boolean selectedGroup;

	public ProducerGroup(String aGroupName) {
		groupName = aGroupName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String aGroupName) {
		groupName = aGroupName;
	}

	public List<Producer> getProducers() {
		return producers;
	}

	public void setProducers(List<Producer> aProducers) {
		producers = aProducers;
	}

	public List<Caption> getCaptions() {
		return captions;
	}

	public void setCaptions(List<Caption> aCaptions) {
		captions = aCaptions;
	}

	public boolean isSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(boolean selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	@Override
	public String toString() {
		return "ProducerGroup [groupName=" + groupName + ", producers="
				+ producers + ", captions=" + captions + ", selectedGroup="
				+ selectedGroup + "]";
	}

}
