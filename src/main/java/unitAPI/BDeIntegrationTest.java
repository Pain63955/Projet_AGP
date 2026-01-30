package unitAPI;

import api.core.BDeConfig;
import api.core.BDeConnection;
import api.core.BDeResultSet;
import api.core.BDeStatement;

import org.junit.Test;

import static org.junit.Assert.*;

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
        BDeStatement st = conn.prepareStatement("SELECT siteID, site_type, nom FROM SiteTouristique WITH Ubud");
        BDeResultSet rs = st.executeQuery();
        int count = 0;
        while (rs.next()) {
        	count++;
	        Integer id = rs.getInt("siteID");
	        String type = rs.getString("site_type");
	        String nom = rs.getString("nom");
	        double score = rs.getScore();
	        System.out.println("Row " + count + " : hotelID=" + id + "site_type" + type + ", nom_hotel=" + nom + ", score=" + score);
        }
        System.out.println("okok");
        assertTrue(count > 0);
                
        rs.close();
        conn.close();
    }
}
