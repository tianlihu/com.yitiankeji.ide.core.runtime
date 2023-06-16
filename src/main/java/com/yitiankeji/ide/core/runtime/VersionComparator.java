package com.yitiankeji.ide.core.runtime;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionComparator {

    private static final String VERSION_PATTERN = "(\\d+|[a-zA-Z]+)(\\.(\\d+|[a-zA-Z]+))*";
    private static final Pattern pattern = Pattern.compile(VERSION_PATTERN);

    public static int compare(String version1, String version2) {
        String[] parts1 = version1.split("\\.");
        String[] parts2 = version2.split("\\.");

        int length = Math.max(parts1.length, parts2.length);
        for (int i = 0; i < length; i++) {
            String part1 = i < parts1.length ? parts1[i] : "";
            String part2 = i < parts2.length ? parts2[i] : "";

            Matcher m1 = pattern.matcher(part1);
            Matcher m2 = pattern.matcher(part2);
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

    private static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
