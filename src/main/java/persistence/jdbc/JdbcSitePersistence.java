package persistence.jdbc;

import business.excursion.Adresse;
import business.excursion.SiteTouristique;
import dao.SitePersistence;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JdbcSitePersistence implements SitePersistence { 
 
	@Override
	public void dataInit() {
		System.err.println("Please don't forget to create tables manually by importing creation.sql in your database !");
		
	}

	public List<SiteTouristique> fetchNear(int adresseID, double km) {
	    // 1) On récupère d'abord le point centre (lat/lng) via une sous-requête "center"
	    // 2) Puis on calcule la distance (Haversine) pour chaque site
	    // 3) On LEFT JOIN les tables filles (activité/historique)
	    // 4) On récupère les langues (pour histo) via GROUP_CONCAT (1 ligne par site)
	    final String sql =
	        "SELECT " +
	        "  st.siteID, st.site_type, st.nom, st.prix, st.adresseID, " +
	        "  sa.duration, sh.guideName, " +
	        "  GROUP_CONCAT(sl.langue ORDER BY sl.langue SEPARATOR ',') AS langues_csv, " +
	        "  (6371 * ACOS( " +
	        "     COS(RADIANS(center.latitude)) * COS(RADIANS(a.latitude)) * " +
	        "     COS(RADIANS(a.longitude) - RADIANS(center.longitude)) + " +
	        "     SIN(RADIANS(center.latitude)) * SIN(RADIANS(a.latitude)) " +
	        "  )) AS distance_km " +
	        "FROM SiteTouristique st " +
	        "JOIN Adresse a ON a.adresseID = st.adresseID " +
	        "JOIN (SELECT latitude, longitude FROM Adresse WHERE adresseID = ?) AS center " +
	        "LEFT JOIN SitesActiv sa ON sa.siteID = st.siteID " +
	        "LEFT JOIN SitesHisto sh ON sh.siteID = st.siteID " +
	        "LEFT JOIN SitesHistoLangues sl ON sl.siteID = st.siteID " +
	        "GROUP BY st.siteID, st.site_type, st.nom, st.prix, st.adresseID, sa.duration, sh.guideName, center.latitude, center.longitude, a.latitude, a.longitude " +
	        "HAVING distance_km <= ? " +
	        "ORDER BY distance_km ASC";

	    List<SiteTouristique> sites = new ArrayList<>();

	    try (Connection conn = JdbcConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, adresseID);
	        ps.setDouble(2, km);

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                // ---- mapping base ----
	                SiteTouristique st = new SiteTouristique();
	                st.setSiteId(rs.getInt("siteID"));
	                st.setNom(rs.getString("nom"));
	                st.setPrix(rs.getBigDecimal("prix"));
	                st.setAdresseId(rs.getInt("adresseID"));
	                st.setSiteType(rs.getString("site_type")); // ou enum Java si tu en as un

	                // ---- mapping tables filles (si ton modèle Java en a besoin) ----
	                // Si tu as de vraies classes filles SitesActiv / SitesHisto :
	                // -> ici, tu peux instancier la bonne classe selon site_type.
	                //
	                // Exemple (à adapter à tes classes réelles) :
	                //
	                // String type = rs.getString("site_type");
	                // if ("SiteActivite".equals(type)) { SitesActiv s = new SitesActiv(); s.setDuration(rs.getFloat("duration")); ... }
	                // else { SitesHisto s = new SitesHisto(); s.setGuideName(rs.getString("guideName")); s.setLangues(...); ... }

	                // ---- langues (CSV -> List) ----
	                String languesCsv = rs.getString("langues_csv");
	                if (languesCsv != null && !languesCsv.isBlank()) {
	                    List<String> langues = Arrays.asList(languesCsv.split(","));
	                    // si ton SiteTouristique n’a pas de langues, ignore.
	                    // si c’est SitesHisto qui a la liste, tu la mettras sur l’objet histo.
	                    // ex: ((SitesHisto) st).setLangues(new ArrayList<>(langues));
	                }

	                // Optionnel : distance
	                // double distanceKm = rs.getDouble("distance_km");

	                sites.add(st);
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println(e.getMessage());
	    }

	    return sites;
	}


	@Override
	public SiteTouristique fetchGamme(String range) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SiteTouristique fetchPrice(double lowPrice, double highPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SiteTouristique fetchNear(Adresse adresse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SiteTouristique fetchKeywords(String keywords) {
		// TODO Auto-generated method stub
		return null;
	}

}
// test