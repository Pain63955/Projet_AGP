package business.excursion;

public class SitesActiv extends SiteTouristique {
	
	private float durationRatio;
	
	@Override
	public double getPrix() {
		return super.getPrix();
	}

	public void setDurationRatio(float durationRatio) {
		this.durationRatio = durationRatio;
	}

	public float getDurationRatio() {
		return durationRatio;
	}
	
	
}
