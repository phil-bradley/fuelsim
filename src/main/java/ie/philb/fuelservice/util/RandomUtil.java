/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author PBradley
 */
public class RandomUtil {

    private static Random random = new SecureRandom();

    // Return a random integer between min and max inclusive
    public static int randomInt(int min, int max) {
        int range = (max - min) + 1;
        int offset = (int) (Math.floor((double) range * random.nextDouble()));
        return min + offset;
    }

    public static long randomLong(long min, long max) {
        long range = (max - min) + 1L;
        int offset = (int) (Math.floor((double) range * random.nextDouble()));
        return min + offset;
    }

    public static BigDecimal randomBigDecimal(int min, int max, int scale) {
        double range = (double) (max - min) + 1;
        double offset = ((double) range * random.nextDouble());
        double value = min + offset;
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_EVEN);
    }

    public static BigInteger randomBigInt(int min, int max) {
        long range = (max - min) + 1L;
        long offset = (long) (Math.floor((double) range * random.nextDouble()));
        return BigInteger.valueOf(min + offset);
    }

    public static boolean randomBoolean() {
        return random.nextBoolean();
    }

    public static String randomString(int length) {

        StringBuilder sb = new StringBuilder();
        int idx = 0;

        for (int i = 0; i < length; i++) {
            idx = randomInt(0, StringUtils.alphanums.length() - 1);
            sb.append(StringUtils.alphanums.charAt(idx));
        }

        return sb.toString();
    }

    public static String randomReducedString(int length) {

        StringBuilder sb = new StringBuilder();
        int idx = 0;

        for (int i = 0; i < length; i++) {
            idx = randomInt(0, StringUtils.reducedAlphanums.length() - 1);
            sb.append(StringUtils.reducedAlphanums.charAt(idx));
        }

        return sb.toString();
    }

    public static String randomNumericString(int length) {

        StringBuilder sb = new StringBuilder();
        int idx = 0;

        // Initial character should not be zero
        idx = randomInt(1, StringUtils.nums.length() - 1);
        sb.append(StringUtils.nums.charAt(idx));

        for (int i = 1; i < length; i++) {
            idx = randomInt(0, StringUtils.nums.length() - 1);
            sb.append(StringUtils.nums.charAt(idx));
        }

        return sb.toString();
    }

    /**
     * @param probability
     * @return true with a probability <i>p</i>. P should range between 0 and 1.
     */
    public static boolean succeedWithProbability(double p) {
        return (new SecureRandom().nextDouble() < p);
    }

}
