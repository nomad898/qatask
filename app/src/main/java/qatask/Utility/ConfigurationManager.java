package qatask.Utility;

import java.io.*;
import java.util.Properties;

public class ConfigurationManager {
    private Properties properties = new Properties(); 

    public ConfigurationManager(String propertyFile) 
        throws IOException, FileNotFoundException {
        try (FileReader reader = new FileReader(propertyFile)) {
            this.properties.load(reader);
        }       
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
