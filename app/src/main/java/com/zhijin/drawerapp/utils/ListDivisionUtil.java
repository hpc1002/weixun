package com.zhijin.drawerapp.utils;

import com.zhijin.drawerapp.bean.Lives;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hpc on 2017/3/16.
 */

public class ListDivisionUtil {
    /**
     * 按指定大小，分割集合，将集合按规定个数分为n个部分
     * @param list
     * @param len
     * @return
     */
    public static List<List<Lives>> splitList(List<Lives> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }

        List<List<Lives>> result = new ArrayList<List<Lives>>();


        int size = list.size();
        int count = (size + len - 1) / len;


        for (int i = 0; i < count; i++) {
            List<Lives> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }
}
