package com.ef;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputUtils {

    public static boolean containsKey(HashMap<String, String> arguments, String key) {
        return InputUtils.isValid(key) && arguments.get(key) != null;
    }

    public static String getValue(HashMap<String, String> arguments, String key) throws Exception {
        if (InputUtils.isValid(key)) {
            return arguments.get(key);
        } else {
            throw new Exception("Value does not exists");
        }
    }

    public static boolean isValid(String value) {
        if (value == null) {
            return false;
        } else {
            value = value.trim();
            if (value.equals("")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidInt(String value) {
        if (!isValid(value)) {
            return false;
        }

        try {
            Integer.parseInt(value);
        } catch (java.lang.NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static boolean isValidInt(int value, int minimum) {
        return value < minimum;
    }

    public static boolean isValidInt(int value, int minimum, int maximum) {
        return value >= minimum && value <= maximum;
    }

    public static boolean isValidDate(String date) {
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}\\.\\d{2}:\\d{2}:\\d{2}$");
        Matcher matcher = pattern.matcher(date);

        return matcher.find();
    }
}
