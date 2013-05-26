package cland.google.gdm;

public class Step
{
	 public String distanceText;
	 public Integer distanceValue;
	 public String durationText;
	 public Integer durationValue;
	 public Double startLocationLat;
	 public Double startLocationLng;
	 public Double endLocationLat;
	 public Double endLocationLng;
	 public String htmlInstructions;
	 
	 public Step(String distText,Integer distValue, String durationText, Integer durationValue, double startLocationLat, double startLocationLng,double endLocationLat, double endLocationLng,String instructions)
	 {
		 	this.distanceText = distText;
		 	this.distanceValue = distValue;
		 	this.durationText = durationText;
		 	this.durationValue = durationValue;
			this.startLocationLat = startLocationLat;
			this.startLocationLng = startLocationLng;
			this.endLocationLat = endLocationLat;
			this.endLocationLng = endLocationLng;
			this.htmlInstructions = instructions;				
	 }//end constructor



	public String getDistanceText() {
		return distanceText;
	}

	public void setDistanceText(String distanceText) {
		this.distanceText = distanceText;
	}

	public Integer getDistanceValue() {
		return distanceValue;
	}

	public void setDistanceValue(Integer distanceValue) {
		this.distanceValue = distanceValue;
	}

	public String getDurationText() {
		return durationText;
	}

	public void setDurationText(String durationText) {
		this.durationText = durationText;
	}

	public Integer getDurationValue() {
		return durationValue;
	}

	public void setDurationValue(Integer durationValue) {
		this.durationValue = durationValue;
	}

	public Double getStartLocationLat() {
		return startLocationLat;
	}

	public void setStartLocationLat(Double startLocationLat) {
		this.startLocationLat = startLocationLat;
	}

	public Double getStartLocationLng() {
		return startLocationLng;
	}

	public void setStartLocationLng(Double startLocationLng) {
		this.startLocationLng = startLocationLng;
	}

	public Double getEndLocationLat() {
		return endLocationLat;
	}

	public void setEndLocationLat(Double endLocationLat) {
		this.endLocationLat = endLocationLat;
	}

	public Double getEndLocationLng() {
		return endLocationLng;
	}

	public void setEndLocationLng(Double endLocationLng) {
		this.endLocationLng = endLocationLng;
	}

	public String getHtmlInstructions() {
		return htmlInstructions;
	}

	public void setHtmlInstructions(String instructions) {
		this.htmlInstructions = instructions;
	}

	@Override
	public String toString() {
		return "Step [distanceText=" + distanceText + ", distanceValue="
				+ distanceValue + ", durationText=" + durationText
				+ ", durationValue=" + durationValue + ", startLocationLat="
				+ startLocationLat + ", startLocationLng=" + startLocationLng
				+ ", endLocationLat=" + endLocationLat + ", endLocationLng="
				+ endLocationLng + ", instructions=" + htmlInstructions + "]";
	}
	 
	 
} //end inner class Step
