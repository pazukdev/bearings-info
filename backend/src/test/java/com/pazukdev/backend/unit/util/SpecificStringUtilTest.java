package com.pazukdev.backend.unit.util;

import org.junit.Test;

import static com.pazukdev.backend.util.SpecificStringUtil.*;
import static org.junit.Assert.*;

/**
 * @author Siarhei Sviarkaltsau
 */
public class SpecificStringUtilTest {

    @Test
    public void test() {
        String testString = " 1939 - 1946";

        assertEquals("1939", getStringBeforeDash(testString));
        assertEquals("1946", getStringAfterDash(testString));
        assertTrue(1939 == getIntegerBeforeDash(testString));
        assertTrue(1946 == getIntegerAfterDash(testString));

        testString = " ? - null";

        assertNull(getStringBeforeDash(testString));
        assertNull(getStringAfterDash(testString));
        assertNull(getIntegerBeforeDash(testString));
        assertNull(getIntegerAfterDash(testString));

        testString = " 1939 (1946)";

        assertEquals("1939", getStringBeforeParentheses(testString));
        assertEquals("1946", getStringBetweenParentheses(testString));
        assertTrue(1946 == getIntegerBetweenParentheses(testString));

        testString = "? ( - )";

        assertNull(getStringBeforeParentheses(testString));
        assertNull(getStringBetweenParentheses(testString));
        assertNull(getIntegerBetweenParentheses(testString));
    }

}












