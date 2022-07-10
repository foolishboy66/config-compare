package org.github.foolishboy.compare.comparator;

import java.util.Map;

/**
 * 比较器接口
 *
 * @author wang
 * @date 2022-07-09
 */
public interface MapComparator {

    /**
     * 比较key
     *
     * @param oldMap 老map
     * @param newMap 新map
     * @return 比较结果
     */
    CompareResult diffKey(Map<String, Object> oldMap, Map<String, Object> newMap);

    /**
     * 比较value
     *
     * @param oldMap 老map
     * @param newMap 新map
     * @return 比较结果
     */
    CompareResult diffValue(Map<String, Object> oldMap, Map<String, Object> newMap);

}
