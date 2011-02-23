package net.java.dev.moskito.webui.bean;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;
import net.java.dev.moskito.core.treshold.ThresholdStatus;

public class ThresholdBean implements IComparable{
	private String name;
	private String status;
	private String colorCode;
	private String timestamp;
	private String description;
	private String value;
	private String change;
	private ThresholdStatus statusForSorting;
	private long timestampForSorting;
	private int id;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override public String toString(){
		return getName()+" "+getStatus()+" "+getTimestamp()+" "+getDescription()+" "+getValue();
	}
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}
	
	@Override
	public int compareTo(IComparable anotherObject, int method) {
		ThresholdBean anotherBean = (ThresholdBean)anotherObject;
		switch(method){
		case ThresholdBeanSortType.BY_CHANGE:
			return BasicComparable.compareLong(timestampForSorting, anotherBean.timestampForSorting);
		case ThresholdBeanSortType.BY_NAME:
			return BasicComparable.compareString(name, anotherBean.name);
		case ThresholdBeanSortType.BY_STATUS:
			return statusForSorting.compareTo(anotherBean.statusForSorting);
		}
		throw new IllegalArgumentException("Unknow sort method: "+method);
	}
	public ThresholdStatus getStatusForSorting() {
		return statusForSorting;
	}
	public void setStatusForSorting(ThresholdStatus statusForSorting) {
		this.statusForSorting = statusForSorting;
	}
	public long getTimestampForSorting() {
		return timestampForSorting;
	}
	public void setTimestampForSorting(long timestampForSorting) {
		this.timestampForSorting = timestampForSorting;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
