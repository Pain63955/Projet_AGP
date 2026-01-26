package business.excursion;

import java.math.BigDecimal;

public class SitesActiv extends SiteTouristique {
	
	private float duration;
	
	@Override
	public BigDecimal getPrix() {
		return super.getPrix();
	}

	public void setDurationRatio(float durationRatio) {
		this.duration = durationRatio;
	}

	public float getDurationRatio() {
		return duration;
	}

	public SitesActiv(String nom, String description, Adresse adresse, BigDecimal prix, int id, float duration) {
		super(nom, description, adresse, prix, id);
		this.duration = duration;
	}
	
	
}
