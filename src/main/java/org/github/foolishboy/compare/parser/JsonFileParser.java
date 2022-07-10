package org.github.foolishboy.compare.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * properties parser
 *
 * @author wang
 * @date 2022-07-09
 */
public class JsonFileParser implements Parser {

    private static final String JSON_FILE = ".json";

    @Override
    public Map<String, Object> parse(File file) {
        JSONObject jsonObject;
        try {
            jsonObject = JSON.parseObject(new FileInputStream(file), new TypeReference<JSONObject>() {
            }.getType());
        } catch (Exception e) {
            throw new RuntimeException("Parse json failed!", e);
        }
        Map<String, Object> result = new HashMap<>();
        dfs(jsonObject, result, "", "");
        return result;
    }

    public void dfs(Object original, Map<String, Object> result, String keyPrefix, String key) {
        if (original instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) original;
            for (String k : jsonObject.keySet()) {
                String currentKey = keyPrefix + k;
                Object value = jsonObject.get(k);
                dfs(value, result, currentKey + ".", currentKey);
            }
        } else if (original instanceof Collection) {
            // process list
            int i = 0;
            Collection<Object> list = (Collection<Object>) original;
            boolean primitive = false;
            for (Object element : list) {
                if (element instanceof JSONObject || element instanceof Collection) {
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

    @Override
    public boolean supports(File file) {
        if (file == null) {
            return false;
        }

        String fileName = file.getName();
        if (fileName.endsWith(JSON_FILE)) {
            return true;
        }
        return false;
    }

}
