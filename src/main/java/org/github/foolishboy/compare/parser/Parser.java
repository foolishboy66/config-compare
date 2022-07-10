package org.github.foolishboy.compare.parser;

import java.io.File;
import java.util.Map;

/**
 * parser接口
 *
 * @author wang
 * @date 2022-07-09
 */
public interface Parser {

    /**
     * 解析输入文件为键值对
     *
     * @param file 输入文件
     * @return k -> v
     */
    Map<String, Object> parse(File file);

    /**
     * 是否支持
     *
     * @param file 文件
     * @return true/false
     */
    boolean supports(File file);

}
