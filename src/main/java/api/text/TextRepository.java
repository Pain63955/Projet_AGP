package api.text;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class TextRepository {

    private final Path textsDir;

    public TextRepository(String directoryPath) {
        this.textsDir = Paths.get(directoryPath);
    }

    private void ensureDirectoryExists() {
        try {
            Files.createDirectories(textsDir);
        } catch (IOException e) {
            throw new RuntimeException("Cannot create texts directory: " + textsDir, e);
        }
    }

    private Path filePathForKey(String key) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("key is empty");
        }
        // évite des clés qui tentent de sortir du dossier (../)
        if (key.contains("/") || key.contains("\\") || key.contains("..")) {
            throw new IllegalArgumentException("Invalid key: " + key);
        }
        return textsDir.resolve(key + ".txt");
    }

    /** Ajoute/écrase le texte associé à la clé */
    public void putText(String key, String text) {
        Path p = filePathForKey(key);
        try {
            Files.write(p, (text == null ? "" : text).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Cannot write text file: " + p, e);
        }
    }
    
    /** Retourne la liste des clés présentes dans R */
    public List<String> listKeys() {
        List<String> keys = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(textsDir, "*" + ".txt")) {
            for (Path p : stream) {
                String name = p.getFileName().toString();
                if (name.endsWith(".txt")) {
                    name = name.substring(0, name.length() - (".txt").length());
                }
                keys.add(name);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot list texts in: " + textsDir, e);
        }
        return keys;
    }

    public Path getTextsDir() {
        return textsDir;
    }
}
