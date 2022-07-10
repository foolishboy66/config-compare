package org.github.foolishboy.compare.parser;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * yaml/yml parser
 *
 * @author wang
 * @date 2022-07-09
 */
public class YamlFileParser implements Parser {

    private static final Yaml PARSER = new Yaml();

    private static final List<String> YAML = Arrays.asList(".yaml", ".yml");

    @Override
    public Map<String, Object> parse(File file) {
        FileReader reader;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("file not exist", e);
        }
        LinkedHashMap<String, Object> linkedHashMap = PARSER.loadAs(reader, LinkedHashMap.class);
        Map<String, Object> result = new HashMap<>();
        dfs(linkedHashMap, result, "", "");
        return result;
    }

    @Override
    public boolean supports(File file) {
        if (file == null) {
            return false;
        }

        String fileName = file.getName();
        for (String yml : YAML) {
            if (fileName.endsWith(yml)) {
                return true;
            }
        }
        return false;
    }

    private void dfs(Object original, Map<String, Object> result, String keyPrefix, String key) {
        if (original instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) original;
            for (String k : linkedHashMap.keySet()) {
                String currentKey = keyPrefix + k;
                Object value = linkedHashMap.get(k);
                dfs(value, result, currentKey + ".", currentKey);
            }
        } else if (original instanceof Collection) {
            // process list
            int i = 0;
            Collection<Object> list = (Collection<Object>) original;
            boolean primitive = false;
            for (Object element : list) {
                if (element instanceof LinkedHashMap || element instanceof Collection) {
                    dfs(element, result, key + "[" + i + "]" + ".", key);
                    i++;
                } else {
                    primitive = true;
                }
            }
            if (primitive) {
                result.put(key, list);
            }
        } else {
            result.put(key, original);
        }
    }

}
