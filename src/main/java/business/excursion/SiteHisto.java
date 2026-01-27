package business.excursion;

import java.util.ArrayList;

public class SiteHisto extends SiteTouristique {
	
	public String guideName;
	ArrayList<String> langues = new ArrayList<>();
	
	public SiteHisto() {
		super();
	}

	

	public String getGuideName() {
		return guideName;
	}



	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}



	public ArrayList<String> getLangues() {
		return langues;
	}



	public void setLangues(ArrayList<String> langues) {
		this.langues = langues;
	}



	@Override
	public double getPrix() {
		return super.getPrix();
	}

}
