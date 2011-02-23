package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.List;

public class UseCaseElementNodeBean {
	/**
	 * Subelements.
	 */
	private List<UseCaseElementNodeBean> children;
	
	/**
	 * Short call description.
	 */
	private String callShort;
	/**
	 * Long call description.
	 */
	private String callLong;
	/**
	 * Duration of the call (brutto).
	 */
	private long duration;
	/**
	 * Netto time spent in the call.
	 */
	private long timespent;
	/**
	 * If true the call was aborted (ended with exception).
	 */
	private boolean aborted;
	

	
	public UseCaseElementNodeBean(String call, long aDuration, boolean anAborted){
		children = new ArrayList<UseCaseElementNodeBean>();
		
		callLong = call;
		
		duration = aDuration;
		aborted = anAborted;
		
		if (call==null)
			call = "";
		if (call.length()>100)
			call = call.substring(0,97)+"...";
		callShort = call;

		
	}
	
	public void addChild(UseCaseElementNodeBean child){
		children.add(child);
	}

	public List<UseCaseElementNodeBean> getChildren() {
		return children;
	}

	public void setChildren(List<UseCaseElementNodeBean> children) {
		this.children = children;
	}

	public String getCallShort() {
		return callShort;
	}

	public void setCallShort(String callShort) {
		this.callShort = callShort;
	}

	public String getCallLong() {
		return callLong;
	}

	public void setCallLong(String callLong) {
		this.callLong = callLong;
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

	public boolean isAborted() {
		return aborted;
	}

	public void setAborted(boolean aborted) {
		this.aborted = aborted;
	}
	
	
}
