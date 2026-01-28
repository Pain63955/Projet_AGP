package unitAPI;

import static org.junit.Assert.*;
import org.junit.Test;

import api.core.BDeConfig;

public class BDeConfigTest {

    @Test(expected = IllegalArgumentException.class)
    public void constructor_nullTable_throws() {
        new BDeConfig(null, "idSite", "data/texts");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_nullKey_throws() {
        new BDeConfig("SiteTouristique", null, "data/texts");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_nullDir_throws() {
        new BDeConfig("SiteTouristique", "idSite", null);
    }

    @Test
    public void getIndexPath_noTrailingSlash_ok() {
        BDeConfig cfg = new BDeConfig("SiteTouristique", "idSite", "data/texts");
        assertEquals("data/texts/index", cfg.getIndexPath());
    }

    @Test
    public void getIndexPath_trailingSlash_ok() {
        BDeConfig cfg = new BDeConfig("SiteTouristique", "idSite", "data/texts/");
        assertEquals("data/texts/index", cfg.getIndexPath());
    }

    @Test(expected = IllegalStateException.class)
    public void validate_missingTable_throws() {
        BDeConfig cfg = new BDeConfig();
        cfg.setKeyColumn("idSite");
        cfg.setDirectoryPath("data/texts");
        cfg.validate();
    }

    @Test(expected = IllegalStateException.class)
    public void validate_missingKey_throws() {
        BDeConfig cfg = new BDeConfig();
        cfg.setTableName("SiteTouristique");
        cfg.setDirectoryPath("data/texts");
        cfg.validate();
    }

    @Test(expected = IllegalStateException.class)
    public void validate_missingDir_throws() {
        BDeConfig cfg = new BDeConfig();
        cfg.setTableName("SiteTouristique");
        cfg.setKeyColumn("idSite");
        cfg.validate();
    }

    @Test
    public void validate_ok() {
        BDeConfig cfg = new BDeConfig("SiteTouristique", "idSite", "data/texts");
        cfg.validate(); // ne doit pas throw
    }
}
