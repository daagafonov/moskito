package net.java.dev.moskito.webui.bean;

import java.util.List;

/**
 * This bean represents a duplicate step during a journey call. 
 * @author lrosenberg
 *
 */
public class JourneyCallDuplicateStepBean {
	/**
	 * Call description aka method name and parameters.
	 */
	private String call;
	/**
	 * Positions in journey call it occures at.
	 */
	private List<String> positions;
	
	/**
	 * Total duration of the calls in this duplicate.
	 */
	private long duration;
	
	/**
	 * Total time spent int the calls in this duplicate.
	 */
	private long timespent;

	public String getCall() {
		return call;
	}
	public void setCall(String call) {
		this.call = call;
	}
	public List<String> getPositions() {
		return positions;
	}
	public void setPositions(List<String> positions) {
		this.positions = positions;
	}
	
	public int getNumberOfCalls(){
		return positions.size();
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public long getTimespent() {
		return timespent;
	}
	public void setTimespent(long timespent) {
		this.timespent = timespent;
	}
}
