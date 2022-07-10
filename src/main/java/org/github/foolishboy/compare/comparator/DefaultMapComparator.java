package org.github.foolishboy.compare.comparator;

import java.util.*;

/**
 * map比较器
 *
 * @author wang
 * @date 2022-07-09
 */
public class DefaultMapComparator implements MapComparator {

    @Override
    public CompareResult diffKey(Map<String, Object> oldMap, Map<String, Object> newMap) {
        List<String> addedKeys = new ArrayList<>();
        List<String> deletedKeys = new ArrayList<>();

        Set<String> oldKeys = oldMap != null ? oldMap.keySet() : Collections.emptySet();
        Set<String> newKeys = newMap != null ? newMap.keySet() : Collections.emptySet();
        for (String oldKey : oldKeys) {
            if (!newKeys.contains(oldKey)) {
                deletedKeys.add(oldKey);
            }
        }

        for (String newKey : newKeys) {
            if (!oldKeys.contains(newKey)) {
                addedKeys.add(newKey);
            }
        }

        CompareResult compareResult = new CompareResult();
        compareResult.setAddedKeys(addedKeys);
        compareResult.setDeletedKeys(deletedKeys);
        return compareResult;
    }

    @Override
    public CompareResult diffValue(Map<String, Object> oldMap, Map<String, Object> newMap) {
        // 先看key的变化
        CompareResult compareResult = diffKey(oldMap, newMap);

        if (oldMap == null || newMap == null) {
            return compareResult;
        }


        // 相同的key比较value
        List<CompareResult.ChangedDetail> changedDetails = new ArrayList<>();
        for (String key : oldMap.keySet()) {
            Object valueBefore = oldMap.get(key);
            Object valueAfter = newMap.get(key);

            if (Objects.equals(valueBefore, valueAfter)
                    || (valueBefore != null && valueAfter != null && valueBefore.toString().equals(valueAfter.toString()))) {
                continue;
            }

            CompareResult.ChangedDetail changedDetail = new CompareResult.ChangedDetail();
            changedDetail.setKey(key);
            changedDetail.setValueBefore(valueBefore);
            changedDetail.setValueAfter(valueAfter);
            changedDetails.add(changedDetail);
        }
        compareResult.setChangedDetails(changedDetails);

        return compareResult;
    }

}
