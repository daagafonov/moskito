package net.java.dev.moskito.core.producers;

import java.util.Map;

public class DefaultStatsSnapshot implements IStatsSnapshot {
	private Map<String, Number> properties;

	public Map<String, Number> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Number> properties) {
		this.properties = properties;
	}
}
