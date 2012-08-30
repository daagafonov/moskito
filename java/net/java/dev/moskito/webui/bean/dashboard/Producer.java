package net.java.dev.moskito.webui.bean.dashboard;

import java.util.ArrayList;
import java.util.List;

public class Producer {
	private String id;
	private List<String> values = new ArrayList<String>();
	private boolean selectedProducer;

	public Producer(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String aId) {
		id = aId;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> aValues) {
		values = aValues;
	}

	public void addValue(String aValue){
		values.add(aValue);
	}

	public boolean isSelectedProducer() {
		return selectedProducer;
	}

	public void setSelectedProducer(boolean aSelectedProducer) {
		selectedProducer = aSelectedProducer;
	}

	@Override
	public String toString() {
		return "Producer [id=" + id + ", values=" + values
				+ ", selectedProducer=" + selectedProducer + "]";
	}

}
