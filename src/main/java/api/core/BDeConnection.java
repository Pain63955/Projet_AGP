package api.core;

import java.sql.Connection;

import api.core.BDeConfig;
import api.text.TextRepository;
import api.text.LuceneIndexService;
import persistence.jdbc.JdbcConnection;

public class BDeConnection {

    private BDeConfig config;
    private Connection jdbcConnection;
    private TextRepository textRepo;
    private LuceneIndexService lucene;

    private BDeConnection(BDeConfig config, Connection jdbcConnection, TextRepository textRepo, LuceneIndexService lucene) {
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

        LuceneIndexService lucene = new LuceneIndexService(cfg);
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
        LuceneIndexService lucene =new LuceneIndexService(cfg);
        return new BDeConnection(cfg, jdbc, repo, lucene);
    }

    // --- API publique ---
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

    public LuceneIndexService getLuceneIndexService() {
        return lucene;
    }
}