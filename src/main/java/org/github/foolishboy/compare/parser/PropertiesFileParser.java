package org.github.foolishboy.compare.parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * properties parser
 *
 * @author wang
 * @date 2022-07-09
 */
public class PropertiesFileParser implements Parser {

    private static final String PROPERTIES = ".properties";

    @Override
    public Map<String, Object> parse(File file) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
        } catch (IOException e) {
            throw new RuntimeException("Load properties failed!", e);
        }
        return new HashMap<String, Object>((Map) properties);
    }

    @Override
    public boolean supports(File file) {
        if (file == null) {
            return false;
        }

        String fileName = file.getName();
        return fileName.endsWith(PROPERTIES);
    }

}
