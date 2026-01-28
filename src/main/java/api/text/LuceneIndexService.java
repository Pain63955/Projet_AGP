package api.text;

import java.io.File;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneIndexService {
	
	public static class LuceneHit {
	    private final int key;
	    private final double score;

	    public LuceneHit(int key, double score) {
	        this.key = key;
	        this.score = score;
	    }

	    public int getKey(){ 
	    	return key; 
	    }
	    public double getScore(){ 
	    	return score; 
	    }
	}

	
	private static void repertoryIndex(File repertory, IndexWriter w) throws Exception {
	    File[] fichiers = repertory.listFiles((dir, name) -> name.endsWith(".txt"));

	    if (fichiers == null) return;

	    for (File f : fichiers) { 
	        System.out.println("Indexation : " + f.getPath());

	        String id = f.getName().replaceFirst("\\.txt$", "");
	        Document doc = new Document();
	        doc.add(new StringField("id", id, Field.Store.YES));

	        try (FileReader reader = new FileReader(f)) {
	            doc.add(new TextField("content", reader));
	            w.addDocument(doc);
	        }
	    }
	}

	// lit tous les fichiers de R, indexe (id=nom du fichier, content=texte)
	public void buildIndex() throws Exception {
	    Analyzer analyzer = new StandardAnalyzer();

	    Path indexPath = FileSystems.getDefault().getPath("data/index");
	    Directory index = FSDirectory.open(indexPath);

	    IndexWriterConfig config = new IndexWriterConfig(analyzer);
	    config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

	    try (IndexWriter w = new IndexWriter(index, config)) {
	        File repertory = new File("data/descriptions");
	        repertoryIndex(repertory, w);
	        w.close();
	    }
	}
	
	// retourne des couples (key, score) triés score desc (TopDocs)
	public List<LuceneHit> search(String textQuery) throws Exception {
		int MAX_RESULTS = 100;
		// 4. Interroger l'index
		Analyzer analyzer = new StandardAnalyzer();
		Path indexPath = FileSystems.getDefault().getPath("data/index");
	    Directory index = FSDirectory.open(indexPath);
	    
	    DirectoryReader ireader = DirectoryReader.open(index);
	    IndexSearcher searcher = new IndexSearcher(ireader); //l'objet qui fait la recherche dans l'index
	    
	    String reqstr = "Forest"; // UTILISER TEXTQUERY String reqstr = textQuery; qui est en parametre
	    	
	    //Parsing de la requete en un objet Query
	    //  "contenu" est le champ interrogé par defaut si aucun champ n'est precisé
	    QueryParser qp = new QueryParser("content", analyzer); 
	    Query req = qp.parse(reqstr);

	    TopDocs results_top = searcher.search(req, MAX_RESULTS); //recherche
	    
	    List<LuceneHit> results = new ArrayList<>(results_top.scoreDocs.length);
        for (ScoreDoc sd : results_top.scoreDocs) {
            Document doc = searcher.doc(sd.doc);

            int key = Integer.parseInt(doc.get("id"));
            results.add(new LuceneHit(key, sd.score));
        }
	    return results;
	}
	
	public HashMap<Integer, Double> sortScores(List<LuceneHit> results) throws Exception {
		 HashMap<Integer, Double> hashResults = new HashMap<Integer, Double>();
		 for (LuceneHit res : results) {	           
	            hashResults.put(res.getKey(), res.getScore());
	        }
		return hashResults;
	}

} 
