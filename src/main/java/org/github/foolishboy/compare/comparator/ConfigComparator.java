package org.github.foolishboy.compare.comparator;

import org.github.foolishboy.compare.parser.Parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 配置文件比较器
 *
 * @author wang
 * @date 2022-07-10
 */
public class ConfigComparator {

    /**
     * 老文件
     */
    private final File oldFile;

    /**
     * 新文件
     */
    private final File newFile;

    /**
     * 比较方式：key-diff/value-diff
     */
    private final String action;

    /**
     * map比较器
     */
    private final MapComparator mapComparator;

    /**
     * 可用的解析器
     */
    private List<Parser> availableParsers;

    public ConfigComparator(File oldFile, File newFile, String action, MapComparator mapComparator) {
        this.oldFile = oldFile;
        this.newFile = newFile;
        this.action = action;
        this.mapComparator = mapComparator;
        availableParsers = new ArrayList<>();
    }

    /**
     * 启动比较逻辑
     */
    public void startUp() {
        Map<String, Object> oldMap = tryToParseFile(oldFile);
        Map<String, Object> newMap = tryToParseFile(newFile);
        CompareResult compareResult = executeDiff(oldMap, newMap, action);
        printResult(compareResult);
    }

    /**
     * 尝试按照文件类型解析
     *
     * @param file 文件
     * @return k->v
     */
    private Map<String, Object> tryToParseFile(File file) {
        if (availableParsers == null || availableParsers.isEmpty()) {
            throw new RuntimeException("No parser found!");
        }

        for (Parser parser : availableParsers) {
            if (parser.supports(file)) {
                return parser.parse(file);
            }
        }
        throw new RuntimeException("Unsupported file type, you must specific file with [yml,yaml,json,properties]...");
    }

    private CompareResult executeDiff(Map<String, Object> oldMap, Map<String, Object> newMap, String action) {
        Action actionType = Action.parseFrom(action);
        if (Objects.requireNonNull(actionType) == Action.DIFF_VALUE) {
            return mapComparator.diffValue(oldMap, newMap);
        }
        return mapComparator.diffKey(oldMap, newMap);
    }

    public static void printResult(CompareResult compareResult) {
        List<String> addedKeys = compareResult.getAddedKeys();
        if (addedKeys != null && !addedKeys.isEmpty()) {
            System.out.println("******************added items:******************");
            System.out.println();
            for (String item : addedKeys) {
                System.out.println(item);
            }
        }

        System.out.println();
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println();
        System.out.println();

        List<String> deletedKeys = compareResult.getDeletedKeys();
        if (deletedKeys != null && !deletedKeys.isEmpty()) {
            System.out.println("******************deleted items:******************");
            System.out.println();
            for (String item : deletedKeys) {
                System.out.println(item);
            }
        }

        System.out.println();
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println();
        System.out.println();

        List<CompareResult.ChangedDetail> changedDetails = compareResult.getChangedDetails();
        if (changedDetails != null && !changedDetails.isEmpty()) {
            System.out.println("******************changed items:******************");
            System.out.println();
            for (CompareResult.ChangedDetail changedDetail : changedDetails) {
                System.out.println("key:" + changedDetail.getKey() + " changed from " + changedDetail.getValueBefore() + " to " + changedDetail.getValueAfter());
            }
        }

    }

    public boolean registerParser(Parser parser) {
        if (availableParsers == null) {
            availableParsers = new ArrayList<>();
        }
        availableParsers.add(parser);
        return true;
    }

}
