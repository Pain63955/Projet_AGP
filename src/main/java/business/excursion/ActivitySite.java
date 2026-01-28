package business.excursion;

public class ActivitySite extends TouristSite {
	
	private float durationRatio;
	
	public ActivitySite() {
		super();
	}

	@Override
	public double getPrice() {
		return super.getPrice();
	}

	public void setDurationRatio(float durationRatio) {
		this.durationRatio = durationRatio;
	}

	public float getDurationRatio() {
		return durationRatio;
	}
	
	
}
