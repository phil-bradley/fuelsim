/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 *
 * @author PBradley
 */
public class StringUtils {

    public static char[] alphaNumsArray = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static char[] alphaArray = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static char[] digitsArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static char[] hexDigitsArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static char[] base36DigitsArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static String alphanums = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String reducedAlphanums = "ABCDEFGHJKMNPQRSTUVWXYZ23456789";
    public static String nums = "0123456789";

    public static String blanks(int length) {

        if (length < 0) {
            throw new IllegalArgumentException("Cannot create string of length " + length);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(" ");
        }

        return sb.toString();
    }

    public static String chars(char c, int length) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(c);
        }

        return sb.toString();
    }

    public static boolean hasValue(String s) {
        if (s == null) {
            return false;
        }

        s = s.trim();
        return (s.length() > 0);
    }

    public static String stripSpaces(String s) {
        if (s == null) {
            return "";
        }

        return s.replaceAll("\\s", "");
    }

    public static String consolidateSpaces(String s) {
        if (s == null) {
            return "";
        }

        return s.trim().replaceAll("( )+", " ");
    }

    public static boolean isBlank(String s) {
        return (!hasValue(s));
    }

    public static boolean isUuid(String s) {
        try {
            UUID uuid = UUID.fromString(s);
            return true;
        } catch (IllegalArgumentException iax) {
            return false;
        }
    }

    public static boolean isNumber(String s) {

        if (null == s) {
            return false;
        }

        try {
            new BigDecimal(s.trim());
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    public static boolean isZero(String s) {

        if (!isNumber(s)) {
            return false;
        }

        try {
            BigDecimal n = new BigDecimal(s.trim());
            return (n.compareTo(BigDecimal.ZERO) == 0);
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static String stripNonNumeric(String data) {

        if (data == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder("");
        char[] dataAsArray = data.toCharArray();

        for (int i = 0; i < dataAsArray.length; i++) {

            char c = dataAsArray[i];

            if (isDigit(c)) {
                sb.append(c);
            }

            if (c == '-') {
                sb.append(c);
            }

            if (c == '.') {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static boolean isDigit(char ch) {
        char[] numsArray = nums.toCharArray();

        for (int i = 0; i < numsArray.length; i++) {
            char d = numsArray[i];

            if (ch == d) {
                return true;
            }
        }

        return false;
    }

    public static boolean isInteger(String s) {

        if (null == s) {
            return false;
        }

        try {
            new BigInteger(s.trim());
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    public static boolean isLong(String s) {

        if (s == null) {
            return false;
        }

        try {
            Long.parseLong(s.trim());
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isNumericChar(char c) {
        String s = "0123456789";
        int idx = s.indexOf(c);

        return (idx != -1);
    }

    public static String fitToLength(String leftMessage, String rightMessage, int length) {

        if (length == 0) {
            return "";
        }

        String s = fitToLength(leftMessage, length);
        StringBuilder sb = new StringBuilder(s);

        if (rightMessage.length() > length - 1) {
            rightMessage = rightMessage.substring(0, length - 1);
        }

        int idx = (length - rightMessage.length()) - 1;
        sb.setCharAt(idx, ' ');

        for (int i = 0; i < rightMessage.length(); i++) {
            idx = (length - rightMessage.length()) + i;
            sb.setCharAt(idx, rightMessage.charAt(i));
        }

        return sb.toString();
    }

    public static String fitToLength(int i, int length) {
        return fitToLength(Integer.toString(i), length, ' ');
    }

    public static String fitToLength(long l, int length) {
        return fitToLength(Long.toString(l), length, ' ');
    }

    public static String fitToLength(String s, int length) {
        return fitToLength(s, length, ' ');
    }

    public static String fitToLength(String s, int length, char padChar) {

        if (s == null) {
            s = "";
        }

        if (s.length() > length) {
            return s.substring(0, length);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(s);

        while (sb.length() < length) {
            sb.append(padChar);
        }

        return sb.toString();
    }

    public static String safeTrim(String s) {
        if (s == null) {
            return "";
        }

        return s.trim();
    }

    public static String ifBlank(String s, String defaultValue) {

        if (isBlank(s)) {
            return defaultValue;
        }

        return s;
    }

    public static int stringToInt(String s, int defaultValue) {

        if (isBlank(s)) {
            return defaultValue;
        }

        return Integer.parseInt(safeTrim(s));
    }

    public static long stringToLong(String s, long defaultValue) {

        if (isBlank(s)) {
            return defaultValue;
        }

        return Long.parseLong(safeTrim(s));
    }

    public static List<String> split(String s, String splitter) {

        List<String> ret = new ArrayList<>();

        String[] tokens = s.split(Pattern.quote(splitter));
        ret.addAll(Arrays.asList(tokens));

        return ret;
    }

    public static String trimToLength(String s, int maxLength, String elipsis) {

        if (s.length() <= maxLength) {
            return s;
        }

        if (maxLength <= 0) {
            return "";
        }

        int elipsisLen = elipsis.length();

        if (elipsisLen >= maxLength) {
            return elipsis;
        }

        String trimmed = trimToLength(s, maxLength - elipsisLen).trim();
        return trimmed + elipsis;
    }

    public static String trimToLength(String s, int maxLength) {

        if (s == null) {
            return "";
        }

        if (maxLength <= 0) {
            return "";
        }

        if (s.trim().length() <= maxLength) {
            return s.trim();
        }

        String trimmed = s.substring(0, maxLength).trim();
        return trimmed;
    }

    public static boolean isLocale(String id) {
        String[] tokens = id.split("_");

        if (tokens.length != 2) {
            return false;
        }

        String lang = tokens[0];
        String region = tokens[1];

        Locale locale = new Locale(lang, region);
        return (locale != null);
    }

    public static Locale stringToLocale(String id) {

        if (!isLocale(id)) {
            return null;
        }

        String[] tokens = id.split("_");

        String lang = tokens[0];
        String region = tokens[1];

        Locale locale = new Locale(lang, region);
        return locale;
    }

    public static String join(String[] items, String joinStr) {
        return join(items, joinStr, "");
    }

    public static String join(String[] items, String joinStr, String quoteChar) {

        StringBuilder sb = new StringBuilder("");

        for (String item : items) {

            if (item == null) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(joinStr);
            }
            sb.append(quoteChar).append(item).append(quoteChar);
        }

        return sb.toString();
    }

    public static String join(List<String> items, String joinStr, String quoteChar) {
        String[] arr = new String[items.size()];
        arr = items.toArray(arr);
        return join(arr, joinStr, quoteChar);
    }

    public static String join(List<String> items, String joinStr) {
        return join(items, joinStr, "");
    }

    public static String join(List<String> items) {
        return join(items, ",");
    }

    public static String joinObjects(List<? extends Object> objects) {
        List<String> strings = new ArrayList<>();

        for (Object o : objects) {

            if (o == null) {
                continue;
            }

            strings.add(o.toString());
        }

        return join(strings);
    }

    public static String joinInts(List<Integer> items, String joinStr) {
        List<String> l = new ArrayList<>();

        for (Integer i : items) {
            if (i == null) {
                continue;
            }
            l.add(i.toString());
        }

        return join(l, joinStr);
    }

    public static String joinInts(List<Integer> items) {
        return joinInts(items, ",");
    }

    public static String joinInts(int[] items) {
        return joinInts(items, ",");
    }

    public static String joinInts(int[] items, String joinStr) {
        List<String> l = new ArrayList<>();

        for (Integer i : items) {
            l.add(i.toString());
        }

        return join(l, joinStr);
    }

    public static String joinLongs(List<Long> items, String joinStr) {
        return joinLongs(items, joinStr, "");
    }

    public static String joinLongs(List<Long> items, String joinStr, String quoteChar) {
        List<String> l = new ArrayList<>();

        for (Long i : items) {
            if (i == null) {
                continue;
            }

            l.add(i.toString());
        }

        return join(l, joinStr, quoteChar);
    }

    public static String joinLongs(List<Long> items) {
        return joinLongs(items, ",");
    }

    public static String joinLongs(long[] items) {
        return joinLongs(items, ",");
    }

    public static String joinLongs(long[] items, String joinStr) {
        return joinLongs(items, joinStr, "");
    }

    public static String joinLongs(long[] items, String joinStr, String quoteChar) {
        List<String> l = new ArrayList<>();

        for (Long i : items) {
            l.add(quoteChar + i.toString() + quoteChar);
        }

        return join(l, joinStr);
    }

    public static String trimLeadingZeros(String s) {
        return safeTrim(s).replaceFirst("^0+(?!$)", "");
    }

    /**
     *
     * @param s
     * @return A String where any trailing spaces are removed until there is
     * only 1 left
     */
    public static String compressTrailingSpaces(String s) {

        if (isBlank(s)) {
            return s;
        }

        if (s.endsWith(" ")) {
            return safeTrim(s) + " ";
        }

        return safeTrim(s);
    }

    public static boolean isIpAddress(String s) {

        if (s.startsWith(".") || s.endsWith(".")) {
            return false;
        }

        String[] tokens = s.split(Pattern.quote("."));

        if (tokens.length != 4) {
            return false;
        }

        for (String token : tokens) {
            if (!isInteger(token)) {
                return false;
            }

            int quad = Integer.parseInt(token);

            if (quad < 0 || quad > 255) {
                return false;
            }
        }

        return true;
    }

    public static boolean isMulticastAddress(String s) {

        if (!isIpAddress(s)) {
            return false;
        }

        String[] tokens = s.split(Pattern.quote("."));

        int quad1 = Integer.parseInt(tokens[0]);
        return (quad1 >= 224);
    }

    public static String sha1(String input) throws NoSuchAlgorithmException {

        if (null == input) {
            return null;
        }

        MessageDigest digest = MessageDigest.getInstance("SHA1");
        digest.update(input.getBytes(), 0, input.length());
        return new BigInteger(1, digest.digest()).toString(16);
    }

    public static String sha256(String input) throws NoSuchAlgorithmException {

        if (null == input) {
            return null;
        }

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(input.getBytes());
        return new BigInteger(1, digest.digest()).toString(16);
    }

    public static String ifNull(Object s, String defaultValue) {

        if (s == null) {
            return defaultValue;
        }

        return s.toString();
    }

    public static String stripNonAlphaNums(String s) {

        if (s == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (isAlphaNum(c) || c == ' ') {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static boolean isAlphaNum(char ch) {
        for (char an : alphaNumsArray) {
            if (ch == an) {
                return true;
            }
        }

        return false;
    }

    public static String removeBreaks(String s) {
        if (s == null) {
            return "";
        }

        if (s.isEmpty()) {
            return "";
        }

        s = s.replaceAll("\n", "");
        s = s.replaceAll("\b", "");
        return s;
    }

    public static String head(String s, int length) {

        if (length < 0) {
            throw new IllegalArgumentException("Cannot create string of length " + length);
        }

        if (s == null) {
            return "";
        }

        if (length == 0) {
            return "";
        }

        if (s.length() <= length) {
            return s;
        }

        return s.substring(0, length);
    }

    public static String toHex(long v) {
        return toBase(v, hexDigitsArray);
    }

    public static String toBase36(long v) {
        return toBase(v, base36DigitsArray);
    }

    private static String toBase(long v, char[] digits) {

        long value = Math.abs(v);
        int base = digits.length;

        List<Integer> remainders = new ArrayList<>();

        while (true) {
            long remainder = value % base;
            long divided = value / base;

            remainders.add((int) remainder);

            if (divided == 0) {
                break;
            }

            value = divided;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = remainders.size() - 1; i >= 0; i--) {
            int remainder = remainders.get(i);
            sb.append(digits[remainder]);
        }

        if (v < 0) {
            return "-" + sb.toString();
        }

        return sb.toString();
    }

    public static String chomp(String s) {

        if (s == null) {
            return null;
        }

        int idx = s.length();

        for (int i = s.length() - 1; i >= 0; i--) {

            char c = s.charAt(i);

            if (c == '\n' || c == '\r') {
                idx--;
            } else {
                break;
            }
        }

        return s.substring(0, idx);
    }

}
