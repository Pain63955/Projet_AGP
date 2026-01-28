package unitAPI;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.Test;

import api.text.TextRepository;

public class TextRepositoryTest {

	 @Test
	    public void putReadDelete_listKeys_ok() throws Exception {
	        Path tmpDir = Files.createTempDirectory("bde_text_repo_");
	        TextRepository repo = new TextRepository(tmpDir.toString());

	        // put + read
	        repo.putText("12", "sculpture renaissance");
	        assertEquals("sculpture renaissance", repo.readText("12"));

	        // listKeys
	        List<String> keys = repo.listKeys();
	        assertTrue(keys.contains("12"));

	        // delete
	        assertTrue(repo.deleteText("12"));
	        assertNull(repo.readText("12"));
	    }
	 
	 @Test
	    public void read_missing_returnsNull() throws Exception {
	        Path tmpDir = Files.createTempDirectory("bde_text_repo_");
	        TextRepository repo = new TextRepository(tmpDir.toString());

	        assertNull(repo.readText("999"));
	    }

	    @Test(expected = IllegalArgumentException.class)
	    public void put_invalidKey_withSlash_throws() throws Exception {
	        Path tmpDir = Files.createTempDirectory("bde_text_repo_");
	        TextRepository repo = new TextRepository(tmpDir.toString());

	        repo.putText("bad/key", "x");
	    }

	    @Test(expected = IllegalArgumentException.class)
	    public void put_invalidKey_withBackslash_throws() throws Exception {
	        Path tmpDir = Files.createTempDirectory("bde_text_repo_");
	        TextRepository repo = new TextRepository(tmpDir.toString());

	        repo.putText("bad\\key", "x");
	    }

	    @Test(expected = IllegalArgumentException.class)
	    public void put_invalidKey_withDotDot_throws() throws Exception {
	        Path tmpDir = Files.createTempDirectory("bde_text_repo_");
	        TextRepository repo = new TextRepository(tmpDir.toString());

	        repo.putText("../escape", "x");
	    }

	    @Test
	    public void put_nullText_writesEmptyString() throws Exception {
	        Path tmpDir = Files.createTempDirectory("bde_text_repo_");
	        TextRepository repo = new TextRepository(tmpDir.toString());

	        repo.putText("1", null);
	        assertEquals("", repo.readText("1"));
	    }

}
