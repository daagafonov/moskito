package net.java.dev.moskito.webui.bean.dashboard;

public class Caption {
	private String captionName;
	private String description;
	private boolean selectedCaption;

	public Caption(String aCaptionName) {
		captionName = aCaptionName;
	}

	public Caption(String aCaptionName, String aDescription) {
		this(aCaptionName);
		description = aDescription;
	}

	public String getCaptionName() {
		return captionName;
	}

	public void setCaptionName(String aCaptionName) {
		captionName = aCaptionName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String aDescription) {
		description = aDescription;
	}

	public boolean isSelectedCaption() {
		return selectedCaption;
	}

	public void setSelectedCaption(boolean selectedCaption) {
		this.selectedCaption = selectedCaption;
	}

	@Override
	public String toString() {
		return "Caption [captionName=" + captionName + ", description="
				+ description + ", selectedCaption=" + selectedCaption
				+ "]";
	}

}
