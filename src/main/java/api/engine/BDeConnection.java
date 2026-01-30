package api.engine;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import api.text.TextRepository;
import api.engine.BDeConfig;
import api.text.LuceneService;
import persistence.jdbc.JdbcConnection;

public class BDeConnection {

    private BDeConfig config;
    private Connection jdbcConnection;
    private TextRepository textRepo;
    private LuceneService lucene;

    private BDeConnection(BDeConfig config, Connection jdbcConnection, TextRepository textRepo, LuceneService lucene) {
        this.config = config;
        this.jdbcConnection = jdbcConnection;
        this.textRepo = textRepo;
        this.lucene = lucene;
    }

    /** Point d’entrée principal côté utilisateur */
    public static BDeConnection open(BDeConfig cfg) {
        if (cfg == null) {
            throw new IllegalArgumentException("BDeConfig is null");
        }
        Connection jdbc = JdbcConnection.getConnection();
        if (jdbc == null) {
            throw new IllegalStateException("Cannot obtain JDBC connection");
        }

        TextRepository repo = new TextRepository(cfg.getDirectoryPath());

        LuceneService lucene = new LuceneService(cfg);
        return new BDeConnection(cfg, jdbc, repo, lucene);
    }
    
    public static BDeConnection open(BDeConfig cfg, Connection jdbc) {
    	if (cfg == null) {
            throw new IllegalArgumentException("BDeConfig is null");
        }
    	if (jdbc == null) {
            throw new IllegalStateException("Cannot obtain JDBC connection");
        }
        TextRepository repo = new TextRepository(cfg.getDirectoryPath());
        LuceneService lucene =new LuceneService(cfg);
        return new BDeConnection(cfg, jdbc, repo, lucene);
    }

    
    public BDeStatement prepareStatement(String query) {
        return new BDeStatement(this, query);
    }

    public void putText(String key, String text) {
        textRepo.putText(key, text);
    }

    public void buildIndex() throws Exception {
        lucene.buildIndex();
    }

    public void close() throws Exception {
        jdbcConnection.close();
    }

    // pour faciliter les intéractions entre classes avec lucene
    public HashMap<String, Double> search(String textQuery) throws Exception {
    	return lucene.search(textQuery);
    }
    
    public ArrayList<String> sortScores(HashMap<String, Double> scoreByKey) throws Exception {
    	return lucene.sortScores(scoreByKey);
    }
    
 // --- getters internes (utiles pour BDeStatement / opérateurs) ---
    public BDeConfig getConfig() {
        return config;
    }

    public Connection getJdbcConnection() {
        return jdbcConnection;
    }

    public TextRepository getTextRepository() {
        return textRepo;
    }

    public LuceneService getLuceneIndexService() {
        return lucene;
    }
}