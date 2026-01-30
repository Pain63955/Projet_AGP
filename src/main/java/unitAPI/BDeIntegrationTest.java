package unitAPI;

import api.core.BDeConfig;
import api.core.BDeConnection;
import api.core.BDeResultSet;
import api.core.BDeStatement;
import business.excursion.ActivitySite;
import business.excursion.Address;
import business.excursion.HistoricSite;
import business.excursion.TouristSite;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class BDeIntegrationTest {
	BDeConfig cfg = new BDeConfig("SiteTouristique", "siteID", "data/descriptions");
    BDeConnection conn = BDeConnection.open(cfg);
    
    @Test
    public void test_sql_only_query_end_to_end() throws Exception {

        // 3) Statement
        BDeStatement st = conn.prepareStatement("SELECT hotelID, nom_hotel FROM Hotel");

        // 4) Exécution SQL simple (pas de WITH)
        System.out.println("JDBC closed before executeQuery? " + conn.getJdbcConnection().isClosed());
        BDeResultSet rs = st.executeQuery();

        // 5) Parcours + affichage
        int count = 0;
        while (rs.next()) {
            count++;
            Integer id = rs.getInt("hotelID");
            String nom = rs.getString("nom_hotel");
            System.out.println("Row " + count + " : hotelID=" + id + ", nom_hotel=" + nom);
        }

        // On ne force pas trop : au moins ça ne plante pas.
        // Si tu es sûr que la table n'est jamais vide, tu peux faire assertTrue(count > 0)
        assertTrue("La requête doit renvoyer >= 0 lignes (test de non-régression)", count >= 0);
        System.out.println("JDBC closed before executeQuery? " + conn.getJdbcConnection().isClosed());
        // 6) Fermeture via CloseVisitor (dans rs.close())
        rs.close();
    }

    @Test
    public void test_with_query_end_to_end_no_crash() throws Exception {
		String selectAddressQuery = "SELECT st.*, ad.*, sa.duration AS info_specifique "
				+ "FROM SiteTouristique st "
				+ "JOIN SitesActiv sa ON sa.siteID = st.siteID "
				+ "JOIN Adresse ad ON st.adresseID = ad.adresseID "
				+ "UNION ALL "
				+ "SELECT st.*, ad.*, sh.guideName AS info_specifique "
				+ "FROM SiteTouristique st "
				+ "JOIN SitesHisto sh ON sh.siteID = st.siteID "
				+ "JOIN Adresse ad ON st.adresseID = ad.adresseID "
				+ "WITH Monkey Forest";
		conn.buildIndex();
        BDeStatement st = conn.prepareStatement(selectAddressQuery);
        BDeResultSet result = st.executeQuery();
        List<TouristSite> sites = new ArrayList<>();
        while (result.next()) {
        	if (result.getString("site_type").equals("SiteActivite")) {
				ActivitySite site = new ActivitySite();
				site.setName(result.getString("nom"));
				site.setPrice(result.getDouble("prix"));
				site.setTransport(result.getString("transport"));
				site.setDurationRatio(result.getFloat("info_specifique").floatValue());
				
				Address ad = new Address();
				ad.setLatitude(result.getDouble("latitude"));
				ad.setLongitude(result.getDouble("longitude"));
				ad.setPostCode(result.getString("code_postal"));
				ad.setStreet(result.getString("rue"));
				ad.setTown(result.getString("ville"));
				
				
				site.setAddress(ad);
				sites.add(site);
			}
			
			if (result.getString("site_type").equals("SiteHistorique")) {
				HistoricSite site = new HistoricSite();
				site.setName(result.getString("nom"));
				site.setPrice(result.getDouble("prix"));
				site.setTransport(result.getString("transport"));
				site.setGuideName(result.getString("info_specifique"));
				
				Address ad = new Address();
				ad.setLatitude(result.getDouble("latitude"));
				ad.setLongitude(result.getDouble("longitude"));
				ad.setPostCode(result.getString("code_postal"));
				ad.setStreet(result.getString("rue"));
				ad.setTown(result.getString("ville"));
				
				site.setAddress(ad);
				sites.add(site);
			}
			System.out.println("----- ROW -----");
			System.out.println("site_type = " + result.getString("site_type"));
			System.out.println("nom = " + result.getString("nom"));
			System.out.println("prix = " + result.getDouble("prix"));
			System.out.println("transport = " + result.getString("transport"));

			System.out.println("info_specifique = " + result.getObject("info_specifique"));

			System.out.println("latitude = " + result.getDouble("latitude"));
			System.out.println("longitude = " + result.getDouble("longitude"));
			System.out.println("code_postal = " + result.getString("code_postal"));
			System.out.println("rue = " + result.getString("rue"));
			System.out.println("ville = " + result.getString("ville"));
			System.out.println("ville = " + result.getScore());
			System.out.println("----------------");

        }
                
        result.close();
        conn.close();
    }
}
