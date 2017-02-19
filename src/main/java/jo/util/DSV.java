/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jo.util;

import java.util.List;

/**
 *
 * @author joost.meulenkamp
 */
public class DSV {

    public static int getColumnIndex(List<List<String>> lists, String name) {
        int index = -1;
        List<String> header = lists.get(0);
        int size = header.size();
        for (int i = 0; i < size; i++) {
            String key = header.get(i);
            if (key.equals(name)) {
                index = i;
            }
        }
        return index;
    }

    public static void removeColumn(List<List<String>> lists, String name) {
        int index = getColumnIndex(lists, name);
        if (index == -1) {
            return;
        }
        removeColumn(lists, (int) index);
    }

    public static void removeColumn(List<List<String>> lists, int index) {
        for (List<String> list : lists) {
            list.remove(index);
        }
    }

    public static void addColumn(List<List<String>> lists, String name) {
        List<String> header = lists.get(0);
        header.add(name);
        int size = lists.size();
        for (int i = 1; i < size; i++) {
            List<String> row = lists.get(i);
            row.add("");
        }
    }

    public static void renameColumn(List<List<String>> lists, String name, String rename) {
        int index = getColumnIndex(lists, name);
        if (index == -1) {
            return;
        }
        renameColumn(lists, index, rename);
    }

    public static void renameColumn(List<List<String>> lists, int index, String rename) {
        List<String> header = lists.get(0);
        header.set(index, rename);
    }

    public static void copyColumn(List<List<String>> lists, String name, String rename) {
        int index = getColumnIndex(lists, name);
        if (index == -1) {
            return;
        }
        copyColumn(lists, (int) index, rename);
    }

    public static void copyColumn(List<List<String>> lists, int index, String rename) {
        List<String> header = lists.get(0);
        header.add(rename);
        int size = lists.size();
        for (int i = 1; i < size; i++) {
            List<String> row = lists.get(i);
            String value = row.get(index);
            row.add(value);
        }
    }
}
