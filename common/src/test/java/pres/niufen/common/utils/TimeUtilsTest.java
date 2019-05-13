package pres.niufen.common.utils;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class TimeUtilsTest {

    @org.junit.Test
    public void timeToInt() {
        System.out.println(TimeUtils.timeToInt("13:00"));
        System.out.println(TimeUtils.timeToInt("15:00"));

        System.out.println(TimeUtils.intToTime(720));
        System.out.println(TimeUtils.intToTime(1439));
    }

    @org.junit.Test
    public void intToTime() {
    }
}