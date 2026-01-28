package business.excursion;

import java.util.ArrayList;

public class HistoricSite extends TouristSite {
	
	public String guideName;
	ArrayList<String> languages = new ArrayList<>();
	
	public HistoricSite() {
		super();
	}

	public String getGuideName() {
		return guideName;
	}

	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}

	public ArrayList<String> getLanguages() {
		return languages;
	}

	public void setLanguages(ArrayList<String> langues) {
		this.languages = langues;
	}

	@Override
	public double getPrice() {
		return super.getPrice();
	}
}
