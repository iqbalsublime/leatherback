package com.rc.core.test.util;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;

public class AssertUtil {
    public static void AssertEqualDateTime(Date expected, Date actual) {
        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.setTime(expected);

        Calendar actualCalendar = Calendar.getInstance();
        actualCalendar.setTime(actual);

        Assert.assertEquals(expectedCalendar.get(Calendar.YEAR), actualCalendar.get(Calendar.YEAR));
        Assert.assertEquals(expectedCalendar.get(Calendar.MONTH), actualCalendar.get(Calendar.MONTH));
        Assert.assertEquals(expectedCalendar.get(Calendar.DATE), actualCalendar.get(Calendar.DATE));
        Assert.assertEquals(expectedCalendar.get(Calendar.HOUR), actualCalendar.get(Calendar.HOUR));
        Assert.assertEquals(expectedCalendar.get(Calendar.MINUTE), actualCalendar.get(Calendar.MINUTE));
        Assert.assertEquals(expectedCalendar.get(Calendar.SECOND), actualCalendar.get(Calendar.SECOND));
    }

    public static void AssertNotEqualDateTime(Date expected, Date actual) {
        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.setTime(expected);

        Calendar actualCalendar = Calendar.getInstance();
        actualCalendar.setTime(actual);

        Assert.assertNotEquals(expectedCalendar.get(Calendar.YEAR), actualCalendar.get(Calendar.YEAR));
        Assert.assertNotEquals(expectedCalendar.get(Calendar.MONTH), actualCalendar.get(Calendar.MONTH));
        Assert.assertNotEquals(expectedCalendar.get(Calendar.DATE), actualCalendar.get(Calendar.DATE));
        Assert.assertNotEquals(expectedCalendar.get(Calendar.HOUR), actualCalendar.get(Calendar.HOUR));
        Assert.assertNotEquals(expectedCalendar.get(Calendar.MINUTE), actualCalendar.get(Calendar.MINUTE));
        Assert.assertNotEquals(expectedCalendar.get(Calendar.SECOND), actualCalendar.get(Calendar.SECOND));
    }

    public static void AssertEqualDate(Date expected, Date actual) {
        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.setTime(expected);

        Calendar actualCalendar = Calendar.getInstance();
        actualCalendar.setTime(actual);

        Assert.assertEquals(expectedCalendar.get(Calendar.YEAR), actualCalendar.get(Calendar.YEAR));
        Assert.assertEquals(expectedCalendar.get(Calendar.MONTH), actualCalendar.get(Calendar.MONTH));
        Assert.assertEquals(expectedCalendar.get(Calendar.DATE), actualCalendar.get(Calendar.DATE));
    }

    public static void AssertNotEqualDate(Date expected, Date actual) {
        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.setTime(expected);

        Calendar actualCalendar = Calendar.getInstance();
        actualCalendar.setTime(actual);

        Assert.assertNotEquals(expectedCalendar.get(Calendar.YEAR), actualCalendar.get(Calendar.YEAR));
        Assert.assertNotEquals(expectedCalendar.get(Calendar.MONTH), actualCalendar.get(Calendar.MONTH));
        Assert.assertNotEquals(expectedCalendar.get(Calendar.DATE), actualCalendar.get(Calendar.DATE));
    }

    public static void AssertEqualTime(Date expected, Date actual) {
        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.setTime(expected);

        Calendar actualCalendar = Calendar.getInstance();
        actualCalendar.setTime(actual);

        Assert.assertEquals(expectedCalendar.get(Calendar.HOUR), actualCalendar.get(Calendar.HOUR));
        Assert.assertEquals(expectedCalendar.get(Calendar.MINUTE), actualCalendar.get(Calendar.MINUTE));
        Assert.assertEquals(expectedCalendar.get(Calendar.SECOND), actualCalendar.get(Calendar.SECOND));
    }

    public static void AssertNotEqualTime(Date expected, Date actual) {
        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.setTime(expected);

        Calendar actualCalendar = Calendar.getInstance();
        actualCalendar.setTime(actual);

        Assert.assertNotEquals(expectedCalendar.get(Calendar.HOUR), actualCalendar.get(Calendar.HOUR));
        Assert.assertNotEquals(expectedCalendar.get(Calendar.MINUTE), actualCalendar.get(Calendar.MINUTE));
        Assert.assertNotEquals(expectedCalendar.get(Calendar.SECOND), actualCalendar.get(Calendar.SECOND));
    }
}
