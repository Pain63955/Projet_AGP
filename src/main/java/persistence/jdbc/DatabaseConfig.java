package persistence.jdbc;

import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Ipossible de trouver config.properties");
            } else {
                properties.load(input);
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
//test