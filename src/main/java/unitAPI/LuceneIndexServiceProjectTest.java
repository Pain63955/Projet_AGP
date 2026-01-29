package unitAPI;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.junit.Test;

import api.core.BDeConfig;
import api.text.LuceneIndexService;
import api.text.TextRepository;

public class LuceneIndexServiceProjectTest {

    @Test
    public void createDirectory_writeFiles_buildIndex_IN_PROJECT() throws Exception {

        // 1️⃣ Chemin R DANS le projet
        Path textsDir = Paths.get("datas/test/texts");

        // nettoyage si déjà présent (important pour relancer le test)
        if (Files.exists(textsDir)) {
            deleteRecursively(textsDir);
        }

        // Vérifie qu'il n'existe pas
        assertFalse(Files.exists(textsDir));

        // 2️⃣ TextRepository doit créer le dossier
        TextRepository repo = new TextRepository(textsDir.toString());
        assertTrue(Files.exists(textsDir));
        assertTrue(Files.isDirectory(textsDir));

        // 3️⃣ Écriture réelle des fichiers
        repo.putText("1", "sculpture renaissance");
        repo.putText("2", "plage sable soleil");
        repo.putText("3", "renaissance art museum");

        assertTrue(Files.exists(textsDir.resolve("1.txt")));
        assertTrue(Files.exists(textsDir.resolve("2.txt")));
        assertTrue(Files.exists(textsDir.resolve("3.txt")));

        // 4️⃣ Config BDe (index = texts/index)
        BDeConfig cfg = new BDeConfig("SiteTouristique", "idSite", textsDir.toString());

        // 5️⃣ Construction index Lucene
        LuceneIndexService lucene = new LuceneIndexService(cfg);
        lucene.buildIndex();

        Path indexDir = textsDir.resolve("index");
        assertTrue(Files.exists(indexDir));
        assertTrue(Files.isDirectory(indexDir));

        // 6️⃣ Recherche Lucene
        HashMap<String, Double> scores = lucene.search("renaissance");

        assertTrue(scores.containsKey("1"));
        assertTrue(scores.containsKey("3"));
        assertFalse(scores.containsKey("2"));
    }

    // --- utilitaire local au test ---
    private static void deleteRecursively(Path path) throws Exception {
        if (!Files.exists(path)) return;

        Files.walk(path)
             .sorted((a, b) -> b.compareTo(a)) // enfants avant parents
             .forEach(p -> {
                 try {
                     Files.delete(p);
                 } catch (Exception e) {
                     throw new RuntimeException(e);
                 }
             });
    }
}

