package api;

import java.io.*;
import java.nio.file.*;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;

public class LuceneQuery {
	private static void repertoryIndex(File repertory,IndexWriter w) throws Exception {
		//File repertory = new File("data/descriptions/");
		File[] fichiers = repertory.listFiles((dir, name) -> name.endsWith(".txt"));

		if (fichiers != null) {
		    for (File f : fichiers) {
		        System.out.println("Indexation : " + f.getPath());

		        Document doc = new Document();
		        doc.add(new TextField("nom", f.getName(), Field.Store.YES));
		        doc.add(new TextField("contenu", new FileReader(f)));

		        w.addDocument(doc);
		    }
		}
	}

	public static void main(String[] args) throws Exception{
		int MAX_RESULTS = 100; //nombre max de réponses retournées
		
	    // 1. Specifier l'analyseur pour le texte.
	    //    Le même analyseur est utilisé pour l'indexation et la recherche
	    Analyzer analyseur = new StandardAnalyzer();

	    // 2. Creation de l'index
//	    Directory index = new RAMDirectory();  //création index en mémoire
	    Path indexpath = FileSystems.getDefault().getPath("data/index"); //localisation index
	    Directory index = FSDirectory.open(indexpath);  //création index sur disque
	    
	    IndexWriterConfig config = new IndexWriterConfig(analyseur);
	    IndexWriter w = new IndexWriter(index, config);

	 // 3. Indexation automatique de tous les fichiers du dossier data/
	    File repertory = new File("data/descriptions");
	    repertoryIndex(repertory, w);

	    //File f = new File("data/fichier.txt");
	    /*Document doc = new Document();
   		doc.add(new Field("nom", f.getName(), TextField.TYPE_STORED));
   		doc.add(new Field("contenu", new FileReader(f), TextField.TYPE_NOT_STORED));
   		w.addDocument(doc);*/
   		//indexer les autres documents de la même façon
   		
   		w.close(); //on ferme le index writer après l'indexation de tous les documents

    	// 4. Interroger l'index
	    DirectoryReader ireader = DirectoryReader.open(index);
	    IndexSearcher searcher = new IndexSearcher(ireader); //l'objet qui fait la recherche dans l'index
	    String reqstr = "Forest";
	    	
	    //Parsing de la requete en un objet Query
	    //  "contenu" est le champ interrogé par defaut si aucun champ n'est precisé
	    QueryParser qp = new QueryParser("contenu", analyseur); 
	    Query req = qp.parse(reqstr);

	    TopDocs resultats = searcher.search(req, MAX_RESULTS); //recherche
	    
	    // 6. Affichage resultats
	    System.out.println(resultats.totalHits + " documents correspondent");
	    for(int i=0; i<resultats.scoreDocs.length; i++) {
	    	int docId = resultats.scoreDocs[i].doc;
	    	Document d = searcher.doc(docId);
	    	System.out.println(d.get("nom") + ": score " + resultats.scoreDocs[i].score);
	    }
	    
	    // fermeture seulement quand il n'y a plus besoin d'acceder aux resultats
	    ireader.close();
	    
	  }
	  
}

