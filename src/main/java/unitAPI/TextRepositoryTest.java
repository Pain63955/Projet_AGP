package unitAPI;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.Test;

import api.text.TextRepository;

public class TextRepositoryTest {

    @Test
    public void putText_createsFile_and_listKeys_containsKey() throws Exception {
        Path tmpDir = Files.createTempDirectory("bde_text_repo_");
        TextRepository repo = new TextRepository(tmpDir.toString());

        repo.putText("12", "sculpture renaissance");

        // Vérifie que le fichier existe vraiment (car pas de readText dans ta classe)
        Path expectedFile = tmpDir.resolve("12.txt");
        assertTrue(Files.exists(expectedFile));

        // Vérifie le contenu sur disque
        String content = new String(Files.readAllBytes(expectedFile), StandardCharsets.UTF_8);
        assertEquals("sculpture renaissance", content);

        // Vérifie listKeys()
        List<String> keys = repo.listKeys();
        assertTrue(keys.contains("12"));
    }

    @Test
    public void putText_nullText_writesEmptyFile() throws Exception {
        Path tmpDir = Files.createTempDirectory("bde_text_repo_");
        TextRepository repo = new TextRepository(tmpDir.toString());

        repo.putText("1", null);

        Path expectedFile = tmpDir.resolve("1.txt");
        assertTrue(Files.exists(expectedFile));

        String content = new String(Files.readAllBytes(expectedFile), StandardCharsets.UTF_8);
        assertEquals("", content);
    }


    @Test(expected = IllegalArgumentException.class)
    public void putText_invalidKey_withSlash_throws() throws Exception {
        Path tmpDir = Files.createTempDirectory("bde_text_repo_");
        TextRepository repo = new TextRepository(tmpDir.toString());

        repo.putText("bad/key", "x");
    }

    @Test(expected = IllegalArgumentException.class)
    public void putText_invalidKey_withBackslash_throws() throws Exception {
        Path tmpDir = Files.createTempDirectory("bde_text_repo_");
        TextRepository repo = new TextRepository(tmpDir.toString());

        repo.putText("bad\\key", "x");
    }

    @Test(expected = IllegalArgumentException.class)
    public void putText_invalidKey_withDotDot_throws() throws Exception {
        Path tmpDir = Files.createTempDirectory("bde_text_repo_");
        TextRepository repo = new TextRepository(tmpDir.toString());

        repo.putText("../escape", "x");
    }
}
