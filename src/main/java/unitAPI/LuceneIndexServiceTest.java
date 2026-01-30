package unitAPI;

import static org.junit.Assert.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import org.junit.Test;

import api.engine.BDeConfig;
import api.text.LuceneService;
import api.text.TextRepository;
import org.junit.Test;

public class LuceneIndexServiceTest {

	@Test
    public void textRepository_createsDirectory_and_luceneIndexesFiles() throws Exception {

        // 1️⃣ On choisit un dossier qui N'EXISTE PAS encore
        Path baseTmp = Files.createTempDirectory("bde_test_");
        Path textsDir = baseTmp.resolve("texts"); // <-- n'existe pas
        assertFalse(Files.exists(textsDir));

        // 2️⃣ Création TextRepository → doit créer le dossier
        TextRepository repo = new TextRepository(textsDir.toString());
        assertTrue("Text directory must be created by TextRepository",
                   Files.exists(textsDir));
        assertTrue(Files.isDirectory(textsDir));

        // 3️⃣ Écriture réelle de fichiers texte
        repo.putText("1", "sculpture renaissance");
        repo.putText("2", "plage sable soleil");
        repo.putText("3", "renaissance art museum");

        assertTrue(Files.exists(textsDir.resolve("1.txt")));
        assertTrue(Files.exists(textsDir.resolve("2.txt")));
        assertTrue(Files.exists(textsDir.resolve("3.txt")));

        // 4️⃣ Config BDe (index = texts/index)
        BDeConfig cfg = new BDeConfig("SiteTouristique", "idSite", textsDir.toString());

        // 5️⃣ Construction index Lucene (réel sur disque)
        LuceneService lucene = new LuceneService(cfg);
        lucene.buildIndex();

        Path indexDir = textsDir.resolve("index");
        assertTrue("Index directory must exist", Files.exists(indexDir));
        assertTrue(Files.isDirectory(indexDir));

        // 6️⃣ Recherche Lucene
        HashMap<String, Double> scores = lucene.search("renaissance");

        // 7️⃣ Vérifications sémantiques
        assertTrue(scores.containsKey("1"));
        assertTrue(scores.containsKey("3"));
        assertFalse(scores.containsKey("2")); // pas de renaissance

        assertEquals(2, scores.size());
    }

}
