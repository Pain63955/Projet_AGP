package business.excursion;

import java.util.ArrayList;

public class SitesHisto extends SiteTouristique {
	
	public String guideName;
	private ArrayList<String> langues;
	
	
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

	public SitesHisto(String nom, String description, Adresse adresse, BigDecimal prix, int id, String guide, ArrayList<String> langues) {
		super(nom, description, adresse, prix, id);
		this.guideName = guide;
		this.langues= langues;
	}
	
	@Override
	public double getPrix() {
		return super.getPrix();
	}
}
