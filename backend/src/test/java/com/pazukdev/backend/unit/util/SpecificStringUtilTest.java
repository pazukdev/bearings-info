package com.pazukdev.backend.unit.util;

import com.pazukdev.backend.constant.bearing.BearingType;
import org.junit.Test;

import java.util.Arrays;

import static com.pazukdev.backend.util.SpecificStringUtil.*;
import static org.junit.Assert.*;

/**
 * @author Siarhei Sviarkaltsau
 */
public class SpecificStringUtilTest {

    @Test
    public void test() {
        String testString = "";

        assertNull(getString(testString));
        assertNull(getInteger(testString));

        testString = "-";

        assertNull(getString(testString));
        assertNull(getInteger(testString));

        testString = "null";

        assertNull(getString(testString));
        assertNull(getInteger(testString));

        testString = "?";

        assertNull(getString(testString));
        assertNull(getInteger(testString));


        testString = " 1939 - 1946";

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

        testString = " ? ( - )";

        assertNull(getStringBeforeParentheses(testString));
        assertNull(getStringBetweenParentheses(testString));
        assertNull(getIntegerBetweenParentheses(testString));

        testString = "207; 205; 204";
        assertEquals("[207, 205, 204]", getList(testString).toString());
    }

    @Test
    public void enumTest() {
        assertEquals("Cylindrical roller", enumToCapitalizedLowerCaseString(BearingType.CYLINDRICAL_ROLLER));
        assertTrue(BearingType.CYLINDRICAL_ROLLER == stringToEnum(BearingType.class, "Cylindrical roller"));

        String testString = "[DEEPGROOVE, CYLINDRICAL_ROLLER, TAPERED_ROLLER]";
        assertEquals(testString, Arrays.asList(getNames(BearingType.class)).toString());

        testString = "[Deepgroove, Cylindrical roller, Tapered roller]";
        assertEquals(testString, enumClassToCapitalizedStrings(BearingType.class).toString());
    }

}












