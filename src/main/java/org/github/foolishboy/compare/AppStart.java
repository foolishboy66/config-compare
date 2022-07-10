package org.github.foolishboy.compare;

import org.github.foolishboy.compare.comparator.Action;
import org.github.foolishboy.compare.comparator.ConfigComparator;
import org.github.foolishboy.compare.comparator.DefaultMapComparator;
import org.github.foolishboy.compare.parser.JsonFileParser;
import org.github.foolishboy.compare.parser.PropertiesFileParser;
import org.github.foolishboy.compare.parser.YamlFileParser;

import java.io.File;
import java.net.URL;

/**
 * start app
 */
public class AppStart {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("[Usage]To use config-compare you should boot app like this 'java -jar config-compare.jar <oldFile> <newFile> [<action>]' .\n" +
                    "The args you should pass follow the rule:\n" +
                    "1. path to old file\n" +
                    "2. path to new file\n" +
                    "3. optional: action, eg 'diff-key' to compare key or 'diff-value' to compare value (default: diff-key)");
            System.exit(1);
        }
        File oldFile = tryToGetFile(args[0]);
        File newFile = tryToGetFile(args[1]);

        String action = Action.DIFF_KEY.getValue();
        if (args.length == 3) {
            action = args[2];
            if (!Action.supports(action)) {
                System.out.println("Incorrect action, please specific 'diff-key' to compare key or 'diff-value' to compare value (default: key)");
                System.exit(1);
            }
        }
        if (!oldFile.exists()) {
            System.out.println("Incorrect path to old file!");
            System.exit(1);
        }
        if (!newFile.exists()) {
            System.out.println("Incorrect path to old file!");
            System.exit(1);
        }

        // 初始化比较器
        ConfigComparator configComparator = new ConfigComparator(oldFile, newFile, action, new DefaultMapComparator());

        // 注册解析器
        configComparator.registerParser(new YamlFileParser());
        configComparator.registerParser(new PropertiesFileParser());
        configComparator.registerParser(new JsonFileParser());

        // 启动比较器
        configComparator.startUp();

        System.exit(0);
    }

    private static File tryToGetFile(String filePath) {
        // 先尝试从classpath加载
        try {
            URL resource = AppStart.class.getClassLoader().getResource(filePath);
            if (resource != null) {
                filePath = resource.getPath();
            }
        } catch (Exception e) {
            // ignore
        }
        return new File(filePath);
    }

}
