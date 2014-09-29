package sample.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Fail fast methods to help verify method arguments.
 * <p/>
 * These usually take the object to be verified along with a String with the name of the variable so that
 * they can throw an easier to debug exception. If the object is in an incorrect state, the appropriate
 * exception is thrown (i.e. IllegalArgumentException or NullPointerException)
 * <p/>
 * They don't take a message as a string in order to avoid excess string concatenations.
 *
 * @author konstantine.kougios
 */
public final class FailFast {

    private FailFast() {
    }

    /**
     * fails if s is blank.
     *
     * @param s        the value to be tested
     * @param variable the variable name
     */
    public static void blank(String s, String variable) {
        if (StringUtils.isBlank(s)) throw new IllegalArgumentException(variable + " can't be blank");
    }

    /**
     * fails if o is null
     *
     * @param o        the object to be tested
     * @param variable the variable name
     */
    public static void notNull(Object o, String variable) {
        if (o == null) throw new NullPointerException(variable + " can't be null");
    }

    public static void between(int value, int low, int high) {
        if (value < low || value > high)
            throw new IllegalArgumentException("integer value " + value + " out of bounds (" + low + "," + high + ")");
    }
}
