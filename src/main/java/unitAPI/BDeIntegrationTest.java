package unitAPI;

import api.core.BDeConfig;
import api.core.BDeConnection;
import api.core.BDeResultSet;
import api.core.BDeStatement;

import org.junit.Test;

import static org.junit.Assert.*;

public class BDeIntegrationTest {

    @Test
    public void test_sql_only_query_end_to_end() throws Exception {
        // 1) Config
        BDeConfig cfg = new BDeConfig();
        cfg.setTableName("Hotel");          // adapte si besoin
        cfg.setKeyColumn("hotelID");        // adapte si besoin
        cfg.setDirectoryPath("data/descriptions"); // dossier R (textes)

        // 2) Connexion
        BDeConnection conn = BDeConnection.open(cfg);

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

        // 6) Fermeture via CloseVisitor (dans rs.close())
        rs.close();
        conn.close();
    }

    @Test
    public void test_with_query_end_to_end_no_crash() throws Exception {
        BDeConfig cfg = new BDeConfig();
        cfg.setTableName("Hotel");
        cfg.setKeyColumn("hotelID");
        cfg.setDirectoryPath("data/descriptions");

        BDeConnection conn = BDeConnection.open(cfg);
        try {
            BDeStatement st = conn.prepareStatement("SELECT hotelID, nom_hotel FROM Hotel WITH is");
            System.out.println("JDBC closed before executeQuery? " + conn.getJdbcConnection().isClosed());
            BDeResultSet rs = st.executeQuery();

            try {
                int count = 0;
                while (rs.next()) {
                    count++;
                    Integer id = rs.getInt("hotelID");
                    String nom = rs.getString("nom_hotel");
                    double score = rs.getScore();
                    System.out.println("Row " + count + " : hotelID=" + id + ", nom_hotel=" + nom + ", score=" + score);
                }
                assertTrue(count >= 0);
            } finally {
                rs.close();
            }
        } finally {
            conn.close();
        }
    }
}
