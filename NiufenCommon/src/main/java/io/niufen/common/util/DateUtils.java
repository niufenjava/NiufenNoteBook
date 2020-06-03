package io.niufen.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * ClassName:DateUtils <br/>
 * Function: 日期公共处理类. <br/>
 * Date: 2018年10月26日 上午10:48:02 <br/>
 *
 * @author 戴
 */
public class DateUtils {


    /**
     * Date对象转换为{@link Instant}对象
     *
     * @param temporalAccessor Date对象
     * @return {@link Instant}对象
     * @since 5.0.2
     */
    public static Instant toInstant(TemporalAccessor temporalAccessor) {
        if (null == temporalAccessor) {
            return null;
        }

        Instant result;
        if (temporalAccessor instanceof Instant) {
            result = (Instant) temporalAccessor;
        } else if (temporalAccessor instanceof LocalDateTime) {
            result = ((LocalDateTime) temporalAccessor).atZone(ZoneId.systemDefault()).toInstant();
        } else if (temporalAccessor instanceof ZonedDateTime) {
            result = ((ZonedDateTime) temporalAccessor).toInstant();
        } else if (temporalAccessor instanceof OffsetDateTime) {
            result = ((OffsetDateTime) temporalAccessor).toInstant();
        } else if (temporalAccessor instanceof LocalDate) {
            result = ((LocalDate) temporalAccessor).atStartOfDay(ZoneId.systemDefault()).toInstant();
        } else if (temporalAccessor instanceof LocalTime) {
            // 指定本地时间转换 为Instant，取当天日期
            result = ((LocalTime) temporalAccessor).atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant();
        } else if (temporalAccessor instanceof OffsetTime) {
            // 指定本地时间转换 为Instant，取当天日期
            result = ((OffsetTime) temporalAccessor).atDate(LocalDate.now()).toInstant();
        } else {
            result = Instant.from(temporalAccessor);
        }

        return result;
    }

    /**
     * @return 格式化字符串："yyyy-MM-dd"
     */
    public static final String PATTERN_A = "yyyy-MM-dd";
    /**
     * 格式化字符串："yyyyMMdd"
     */
    public static final String PATTERN_B = "yyyyMMdd";
    /**
     * 格式化字符串："yyyy-MM-dd HH-mm-ss"
     */
    public static final String PATTERN_C = "yyyy-MM-dd HH-mm-ss";
    /**
     * 格式化字符串："yyyy-MM-dd HH:mm:ss"
     */
    public static final String PATTERN_D = "yyyy-MM-dd HH:mm:ss";
    /**
     * 格式化字符串："yyyy-MM-dd HH:mm"
     */
    public static final String PATTERN_E = "yyyy-MM-dd HH:mm";
    /**
     * 格式化字符串："yyyyMMddHHmmss"
     */
    public static final String PATTERN_F = "yyyyMMddHHmmss";
    /**
     * 格式化字符串："yyyy年MM月dd日"
     */
    public static final String PATTERN_G = "yyyy年MM月dd日";
    /**
     * 格式化字符串："yyyy-MM"
     */
    public static final String PATTERN_H = "yyyy-MM";
    /**
     * 格式化字符串："yyyyMM"
     */
    public static final String PATTERN_I = "yyyyMM";
    /**
     * 格式化字符串："yyyyMM"
     */
    public static final String PATTERN_CRON = "ss mm HH dd MM ? yyyy";
    /**
     * 毫秒数：一天
     */
    public static final Long ONE_DAYM_MSEC = 24 * 60 * 60 * 1000L;
    /**
     * 毫秒数：半天
     */
    public static final Long HALF_DAYM_MSEC = 1000 * 3600 * 12L;

    /**
     * 将日期格式化为指定的格式. <br/>
     *
     * @param date    日期
     * @param pattern 格式
     * @return
     * @author 戴
     */
    public static String formateDate(Date date, String pattern) {
        return dateToString(date, pattern);
    }


    /**
     * 格式化日期为yyyy-MM-dd
     *
     * @param date 日期
     * @return
     */
    public static String formateDateYMD(Date date) {
        return dateToString(date, PATTERN_A);
    }


    /**
     * 格式化日期为yyyy-MM
     *
     * @param date 日期
     * @return
     */
    public static String formateYearAndMonth(Date date) {
        return dateToString(date, PATTERN_H);
    }


    /**
     * 取当天日期. <br/>
     *
     * @return Date
     */
    public static Date getDate() {
        return Calendar.getInstance().getTime();
    }


    /**
     * 取当天日期. <br/>
     *
     * @return Date
     */
    public static Date curTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = df.format(new Date());
        return stringToDate(format, "yyyy-MM-dd HH:mm:ss");
    }


    /**
     * 获取指定年月日的日期. <br/>
     * 格式为yyyy-MM-dd HH-mm-ss 00-00-00 <br/>
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return Date
     */
    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0);
        return cal.getTime();
    }


    /**
     * 获取指定 年,月,日,小时,分,秒的时间. <br/>
     *
     * @param year   年
     * @param month  月
     * @param date   日期
     * @param hour   小时
     * @param mintue 分钟
     * @param second 秒
     * @return Date
     * @author 戴
     */
    public static Date getDate(int year, int month, int date, int hour, int mintue, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, mintue);
        cal.set(Calendar.SECOND, second);
        return cal.getTime();
    }


    /**
     * 获取指定日期前或者指定日期后n天的日期. <br/>
     * <p>
     * n为负 -,则取指定日期n天前的日期.<br/>
     * n为正 +,则取指定日期n天后的日期.<br/>
     * </p>
     *
     * @param date 日期
     * @param days 天
     * @return Date
     * @author 戴
     */
    public static Date getSomeDaysBeforeAfter(Date date, int days) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(5, days);
        gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
        return gc.getTime();
    }


    /**
     * 获取指定日期的 年份. <br/>
     *
     * @param date 日期
     * @return
     * @author 戴
     */
    public static int getDateYear(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }


    /**
     * 取指定日期月份
     *
     * @param date 指定日期<br>
     * @return <br>
     * @author: 戴 <br>
     */
    public static int getDateMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }


    /**
     * 取指定日期日份
     *
     * @param date 指定日期<br>
     * @return <br>
     * @author: 戴 <br>
     */
    public static int getDateDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DATE);
    }


    /**
     * 取指定日期小时
     *
     * @param date 取指定日期
     * @return
     */
    public static int getDateHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }


    /**
     * 取指定日期分钟
     *
     * @param date 取指定日期
     * @return
     */
    public static int getDateMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }


    /**
     * 取指定日期的第二天的开始时间,小时,分,秒为00:00:00
     *
     * @param date 取指定日期
     * @return
     */
    public static Date getNextDayStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return getNextDayStart(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE));
    }


    /**
     * 取指定年,月,日的下一日的开始时间,小时,分,秒为00:00:00
     *
     * @param year    取指定年,月,日的下一日的开始时间,小时,分,秒为00:00:00
     * @param monthIn 取跨月份的日期
     * @param date    主要是用来取跨月份的日期
     * @return
     */
    public static Date getNextDayStart(int year, int monthIn, int date) {
        int month = monthIn - 1;
        boolean lastDayOfMonth = false;
        boolean lastDayOfYear = false;

        Calendar time = Calendar.getInstance();
        time.set(year, month, date, 0, 0, 0);
        Calendar nextMonthFirstDay = Calendar.getInstance();
        nextMonthFirstDay.set(year, month + 1, 1, 0, 0, 0);

        if (time.get(Calendar.DAY_OF_YEAR) + 1 == nextMonthFirstDay.get(Calendar.DAY_OF_YEAR)) {
            lastDayOfMonth = true;
        }

        if (time.get(Calendar.DAY_OF_YEAR) == time.getMaximum(Calendar.DATE)) {
            lastDayOfYear = true;
        }

        time.roll(Calendar.DATE, 1);

        if (lastDayOfMonth) {
            time.roll(Calendar.MONTH, 1);
        }

        if (lastDayOfYear) {
            time.roll(Calendar.YEAR, 1);
        }
        return time.getTime();
    }


    /**
     * 取指定日期的下一日的时间
     *
     * @param date 取指定日期
     * @return
     */
    public static Date nextDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }


    /**
     * 指定日期的下一日的开始时间,小时,分,秒为00:00:00
     *
     * @param date 指定日期
     * @return
     */
    public static Date getStartDateNext(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }


    /**
     * 指定日期的开始时间,小时,分,秒为00:00:00
     *
     * @param date 指定日期
     * @return
     */
    public static Date getStartDateDay(Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }


    /**
     * 指定日期的结束时间,小时,分,秒为23:59:59
     *
     * @param date 指定日期
     * @return
     */
    public static Date getEndDateDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }


    /**
     * 将指定日期,以指定pattern格式,输出String值
     *
     * @param date    将指定日期
     * @param pattern 指定pattern格式
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        if (null == date) {
            return "";
        } else {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(date);
        }
    }


    public static String dateToString1(Date date, String formatIn) {
        String format = formatIn;
        if (date == null) {
            return "";
        }
        if (format == null) {
            format = PATTERN_D;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }


    /**
     * 将指定年,月,日的日期 以指定pattern格式,输出String值
     *
     * @param year    指定年
     * @param month   指定月
     * @param day     指定日
     * @param pattern 指定pattern格式
     * @return
     */
    public static String dateToString(int year, int month, int day, String pattern) {
        return dateToString(getDate(year, month, day), pattern);
    }


    /**
     * 将指定字符型日期转为日期型,格式为指定的pattern
     *
     * @param string  将指定字符型日期
     * @param pattern 指定pattern格式
     * @return
     */
    public static Date stringToDate(String string, String pattern) {
        SimpleDateFormat format = (SimpleDateFormat) DateFormat.getDateInstance();
        format.applyPattern(pattern);
        try {
            return format.parse(string);
        } catch (ParseException e) {
            return null;
        }
    }


    /**
     * 将指定字符型日期转为日期型,指定格式为yyyy-MM-dd
     *
     * @param string 指定字符型日期
     * @return
     */
    public static Date stringToDate(String string) {
        return stringToDate(string, PATTERN_A);
    }


    /**
     * 获得两个日期之间间隔的天数
     *
     * @param startDate 起始年月日
     * @param endDate   结束年月日
     * @return int
     */
    public static int getDays(Date startDate, Date endDate) {
        int elapsed = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        Date d1 = cal.getTime();

        cal.setTime(endDate);
        Date d2 = cal.getTime();

        long daterange = d2.getTime() - d1.getTime();
        // 一天的毫秒数
        long time = 1000 * 3600 * 24;
        elapsed = (int) (daterange / time);
        return elapsed;
    }


    /**
     * 判断指定日期时间段是否是工作时间
     *
     * @param date        指定日期
     * @param startTimeIn 开始时间，格式为0800，表示上午8点00分
     * @param endTimeIn   截止时间，格式为2200
     * @return
     */
    public static boolean isWorkHour(Date date, String startTimeIn, String endTimeIn) {
        // 是否是工作时间
        String startTime = startTimeIn;
        String endTime = endTimeIn;
        if (StringUtils.isEmpty(startTime)) {
            startTime = "0800";
        }
        if (StringUtils.isEmpty(endTime)) {
            endTime = "2200";
        }
        int start = Integer.parseInt(startTime);
        int end = Integer.parseInt(endTime);
        int hour = getDateHour(date);
        int m = getDateMinute(date);
        String hstr = hour <= 9 ? "0" + hour : hour + "";
        String mstr = m <= 9 ? "0" + m : m + "";
        int dateInt = Integer.parseInt(hstr + mstr);
        if (dateInt >= start && dateInt <= end) {
            return true;
        }
        return false;
    }


    /**
     * 根据传入日期，返回此月有多少天
     *
     * @param date 指定日期，格式为 201408
     * @return
     */
    public static int getDayOfMonth(String date) {
        int year = Integer.parseInt(date.substring(0, 3));
        int month = Integer.parseInt(date.substring(date.length() - 1, date.length()));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        // Java月份才0开始算 6代表上一个月7月
        cal.set(Calendar.MONTH, month - 1);
        int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
        return dateOfMonth;
    }


    /**
     * 取指定日期月份前一月
     *
     * @param date 指定日期
     * @return
     */
    public static int getLastDateMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH);
    }


    /**
     * 取日期的当前月第一天
     *
     * @param date 指定日期
     * @return
     */
    public static Date getMonthFirstDay(Date date) {
        return getDate(getDateYear(date), getDateMonth(date), 1);
    }


    /**
     * 取日期的前月第一天
     *
     * @param date 指定日期
     * @return
     */
    public static Date getLastDateMonthDay(Date date) {
        return getDate(getDateYear(date), getLastDateMonth(date), 1);
    }


    /**
     * 取当前日期后的几天
     *
     * @param day     指定加（或减）天数
     * @param pattern 指定日期格式
     * @return
     */
    public static String getDateAfterTheCurrent(int day, String pattern) {
        return stringConversionToMsec(day * ONE_DAYM_MSEC + System.currentTimeMillis(), pattern);
    }


    /**
     * 毫秒数转字符串
     *
     * @param msec    毫秒数
     * @param pattern 指定日期格式
     * @return
     */
    public static String stringConversionToMsec(long msec, String pattern) {
        return new SimpleDateFormat(pattern).format(msec);
    }


    /**
     * 日期转时间戳
     *
     * @param pattern  时间格式
     * @param dateTime 时间
     * @return long 时间戳
     * @throws RuntimeException
     */
    public static long dateTimeStrToLong(String pattern, Date dateTime) {
        return dateTime.getTime();
    }


    /**
     * 日期后1天数
     *
     * @param date 日期
     * @return 日期
     * @throws RuntimeException
     */
    public static String dateLastDayStr(String pattern, String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(DateUtils.stringToDate(date).getTime() + ONE_DAYM_MSEC);
    }


    /**
     * 日期后1天数
     *
     * @param date 日期
     * @return 日期
     * @throws RuntimeException
     */
    public static String dateBeforeDayStr(String pattern, String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(DateUtils.stringToDate(date).getTime() - ONE_DAYM_MSEC);
    }


    /**
     * 将日期字符串转成定时任务 CRON 格式
     *
     * @param date 取到的时间数据
     * @return 定时任务 CRON 格式
     */
    public static String dateToCron(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_CRON);
        return simpleDateFormat.format(stringToDate(date, PATTERN_D).getTime());
    }


    /**
     * 取当天日期字符串
     *
     * @return 当天日期字符串
     */
    public static String nowStr() {
        return new SimpleDateFormat(PATTERN_D).format(DateUtils.getDate());
    }


    /**
     * 取当天日期字符串
     *
     * @return 当天日期字符串
     */
    public static String nowStrB() {
        return new SimpleDateFormat(PATTERN_B).format(DateUtils.getDate());
    }

    /**
     * 取当天日期字符串
     *
     * @return 当天日期字符串
     */
    public static String nowStrYmd() {
        return new SimpleDateFormat(PATTERN_A).format(DateUtils.getDate());
    }

    /**
     * 取当天日期字符串
     *
     * @return 当天日期字符串
     */
    public static String nowStrEData() {
        return new SimpleDateFormat(PATTERN_E).format(DateUtils.getDate());
    }

    /**
     * 取当天日期字符串
     *
     * @return 当天日期字符串
     */
    public static String toStr(Date date) {
        return new SimpleDateFormat(PATTERN_D).format(date);
    }

    /**
     * 取当天日期字符串
     *
     * @return 当天日期字符串
     */
    public static String toStrE(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(PATTERN_E).format(date);
    }

    /**
     * 获取当前系统的年月日;
     *
     * @return 返回格式为yyyy-MM-dd
     */
    public static String getCurrentDateStr() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
                + c.get(Calendar.DATE);
    }


    public static int getDateSpace(Date date1, Date date2) {
        Calendar calst = Calendar.getInstance();
        ;
        Calendar caled = Calendar.getInstance();

        calst.setTime(date1);
        caled.setTime(date2);

        //设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);
        //得到两个日期相差的天数
        int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;

        return days;
    }


    /**
     * 获取从当前时间到该天结束剩余的毫秒数
     *
     * @param date 时间
     * @return 剩余毫秒数
     */
    public static long getRemainingTime(Date date) {
        String dateStr = formatSimpleDate(date) + " 23:59:59";
        Date d = stringToDate(dateStr, PATTERN_D);
        return getTimeDelta(d, date);
    }


    /**
     * 将指定日期格式化为简单日期格式,
     * 即yyyy-MM-dd.
     *
     * @param date 待格式化的date对象，可以为null
     * @return 格式化后的日期，可能为null.
     */
    public static String formatSimpleDate(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_A);
            return dateFormat.format(date);
        }
        return null;
    }


    /**
     * 计算两个日期差
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 时间差
     */
    public static Long getTimeDelta(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0L;
        }
        return date1.getTime() - date2.getTime();
    }


    /**
     * 获取当天的开始时间
     *
     * @return Date
     */
    public static Date startTimeToday() {
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        return start.getTime();
    }


    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime   当前时间
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return true-在时间段内；false-不在时间段内
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 通过时间获取当前Date类型时间将指定日期格式化为简单日期格式,
     *
     * @param time hh:mm:ss
     * @return 格式化后的日期，可能为null.
     */
    public static String getCurDateFromTime(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_A);
        String curDate = dateFormat.format(new Date());
        return curDate + " " + time;
    }

}
