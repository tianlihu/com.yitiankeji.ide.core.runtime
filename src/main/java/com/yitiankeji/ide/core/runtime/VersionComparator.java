package com.yitiankeji.ide.core.runtime;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 版本对比器，按版本倒序 **/
public class VersionComparator {

    /** 版本号匹配用到的正则表达式 **/
    private static final String VERSION_PATTERN = "(\\d+|[a-zA-Z]+)(\\.(\\d+|[a-zA-Z]+))*";
    /** 版本号匹配用到的正则表达式编译对象 **/
    private static final Pattern PATTERN = Pattern.compile(VERSION_PATTERN);

    /**
     * 版本比较
     *
     * <pre>
     * 返回值：
     * 1. 小于0：version1比version2大，即version1比较新
     * 2. 等于0：version1和version2版本相同
     * 3. 大于0：version1比version2小，即version2比较新
     * </pre>
     **/
    public static int compare(String version1, String version2) {
        if (StringUtils.equals(version1, version2)) {
            return 0;
        }
        String[] parts1 = version1.split("\\.");
        String[] parts2 = version2.split("\\.");

        int length = Math.max(parts1.length, parts2.length);
        for (int i = 0; i < length; i++) {
            String part1 = i < parts1.length ? parts1[i] : "";
            String part2 = i < parts2.length ? parts2[i] : "";

            Matcher m1 = PATTERN.matcher(part1);
            Matcher m2 = PATTERN.matcher(part2);
            if (m1.matches() && m2.matches()) {
                boolean isNumber1 = isNumber(part1);
                boolean isNumber2 = isNumber(part2);

                if (isNumber1 && isNumber2) {
                    int value1 = Integer.parseInt(part1);
                    int value2 = Integer.parseInt(part2);
                    if (value1 != value2) {
                        return value2 - value1;
                    }
                } else if (!isNumber1 && !isNumber2) {
                    int result = part2.compareToIgnoreCase(part1);
                    if (result != 0) {
                        return result;
                    }
                } else {
                    return isNumber1 ? 1 : -1;
                }
            } else {
                int result = part2.compareToIgnoreCase(part1);
                if (result != 0) {
                    return result;
                }
            }
        }

        return 0;
    }

    /** 判断字符串是否是数字 **/
    private static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
