package net.java.dev.moskito.core.producers;

import java.util.Date;
import java.util.Map;

public class DefaultStatsSnapshot implements IStatsSnapshot {
	
	private String name;
	private Date dateCreated;
	private Map<String, Number> properties;
	
	@Override
	public Map<String, Number> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Number> properties) {
		this.properties = properties;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
