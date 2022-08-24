package org.chenxilin.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 随机工具
 *
 * @author chenxilin
 */
public class RandomUtil {
    public static Object getRandom(List<Object> list) {
        int n = new Random().nextInt(list.size());
        return list.get(n);
    }

    public static void main(String[] args) {
        List<String> x = new ArrayList<>();
        x.add("1");
        x.add("2");
        x.add("3");
        x.add("4");

        int n = 10;
        for (int i = 0; i < n; i++) {
            System.out.println(getRandom(Collections.singletonList(x)));
        }

    }

}
