package pres.niufen.common.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    /**
     * add by fzg 2012-2-16
     *
     * @param year 10位日期字符串 即2012-02-16
     * @return日期相加，只加年份，即2012-02-16+1=2013-02-16，用于计算样式表中的现金价值表处。
     */
    public static String yearAdd(String year) {
        if (!year.matches("^\\d{1,}$")) {
            return getCur10Date();
        }
        String date = getCur10Date();
        String[] strs = new String[2];
        strs = date.split("-");
        Integer dateYear = Integer.parseInt(strs[0]);
        dateYear += Integer.parseInt(year);
        return new StringBuffer(dateYear + "").append("-")
                .append(strs[1]).append("-")
                .append(strs[2]).toString();

    }


    /**
     * @param pDateString
     * @param pDateFormat
     * @return Date
     * 将pDateString按照pDateFormat格式解析，返回解析得到的java.util.Date。解析失败，返回null。
     * 列 date = DateUtil.parseDate("201-111-21", "yyy-mmm-dd");
     */
    public static Date parseDate(String pDateString, String pDateFormat) {
        try {
            return new SimpleDateFormat(pDateFormat).parse(pDateString);
        } catch (ParseException ex) {
            System.out.println("日期-格式不匹配！" + pDateString + ":" + pDateFormat);
            return null;
        }
    }

    public static String formatTrans(String pDate, String pOldFormat, String pNewFormat) throws Exception {
        if (null == pDate || "".equals(pDate)) {
            return pDate;
        }
        return new SimpleDateFormat(pNewFormat).format(parseDate(pDate, pOldFormat));
    }

    /**
     * yyyy-MM-dd(yyyy/MM/dd, yyyy.MM.dd) --> yyyyMMdd
     */
    public static String date10to8(String pDate) {
        if (null == pDate || "".equals(pDate) || "0".equals(pDate)) {
            return "";
        }

        return pDate.substring(0, 4) + pDate.substring(5, 7) + pDate.substring(8);
    }

    /**
     * yyyyMMdd --> yyyy-MM-dd
     */
    public static String date8to10(String pDate) {
        if (null == pDate || "".equals(pDate) || "0".equals(pDate)) {
            return "";
        }

        char[] mChars = pDate.toCharArray();

        return new StringBuilder()
                .append(mChars, 0, 4).append('-')
                .append(mChars, 4, 2).append('-')
                .append(mChars, 6, 2).toString();
    }

    /**
     * HH:mm:ss --> HHmmss
     */
    public static String time8to6(String pTime) {
        if (null == pTime || "".equals(pTime)) {
            return pTime;
        }
        for (int i = pTime.length(); i < 8; i++) {    //9:34:23 --> 093423
            pTime = '0' + pTime;
        }

        return pTime.substring(0, 2) + pTime.substring(3, 5) + pTime.substring(6);
    }

    /**
     * HHmmss --> HH:mm:ss
     */
    public static String time6to8(String pTime) {
        if (null == pTime || "".equals(pTime)) {
            return pTime;
        }
        for (int i = pTime.length(); i < 6; i++) {    //93423 --> 9:34:23
            pTime = '0' + pTime;
        }

        char[] mChars = pTime.toCharArray();
        return new StringBuilder()
                .append(mChars, 0, 2).append(':')
                .append(mChars, 2, 2).append(':')
                .append(mChars, 4, 2).toString();
    }

    /**
     * yyyy-MM-dd
     */
    public static String getCur10Date() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * yyyyMMdd
     */
    public static int getCur8Date() {
        return Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
    }

    /**
     * HH:mm:ss
     */
    public static String getCur8Time() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    /**
     * HHmmss
     */
    public static int getCur6Time() {
        return Integer.parseInt(new SimpleDateFormat("HHmmss").format(new Date()));
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getCurDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getCurDate(String pDateFormat) {
        return new SimpleDateFormat(pDateFormat).format(new Date());
    }

    /**
     * yyyy-MM-dd
     */
    public static String get10Date(Calendar pCalendar) {
        return new SimpleDateFormat("yyyy-MM-dd").format(pCalendar.getTime());
    }

    /**
     * yyyy-MM-dd
     */
    public static String get10Date(long pMillis) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(pMillis));
    }

    /**
     * yyyy-MM-dd
     */
    public static String get10Date(Date pDate) {
        return new SimpleDateFormat("yyyy-MM-dd").format(pDate);
    }

    /**
     * yyyyMMdd
     */
    public static int get8Date(Calendar pCalendar) {
        return Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(pCalendar.getTime()));
    }

    /**
     * yyyyMMdd
     */
    public static int get8Date(long pMillis) {
        return Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date(pMillis)));
    }

    /**
     * yyyyMMdd
     */
    public static int get8Date(Date pDate) {
        return Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(pDate));
    }

    /**
     * HH:mm:ss
     */
    public static String get8Time(Calendar pCalendar) {
        return new SimpleDateFormat("HH:mm:ss").format(pCalendar.getTime());
    }

    /**
     * HH:mm:ss
     */
    public static String get8Time(long pMillis) {
        return new SimpleDateFormat("HH:mm:ss").format(new Date(pMillis));
    }

    /**
     * HH:mm:ss
     */
    public static String get8Time(Date pDate) {
        return new SimpleDateFormat("HH:mm:ss").format(pDate);
    }

    /**
     * HHmmss
     */
    public static int get6Time(Calendar pCalendar) {
        return Integer.parseInt(new SimpleDateFormat("HHmmss").format(pCalendar.getTime()));
    }

    /**
     * HHmmss
     */
    public static int get6Time(long pMillis) {
        return Integer.parseInt(new SimpleDateFormat("HHmmss").format(new Date(pMillis)));
    }

    /**
     * HHmmss
     */
    public static int get6Time(Date pDate) {
        return Integer.parseInt(new SimpleDateFormat("HHmmss").format(pDate));
    }

    public static String getDateStr(Calendar pCalendar, String pFormat) {
        return new SimpleDateFormat(pFormat).format(pCalendar.getTime());
    }

    public static String getDateStr(long pMillis, String pFormat) {
        return new SimpleDateFormat(pFormat).format(new Date(pMillis));
    }

    public static String getDateStr(Date pDate, String pFormat) {
        return new SimpleDateFormat(pFormat).format(pDate);
    }

    public static int getAge(Date pBirthday) {
        GregorianCalendar mBirthdayCalendar = new GregorianCalendar();
        mBirthdayCalendar.setTime(pBirthday);

        GregorianCalendar mNowCalendar = new GregorianCalendar();

        int mAge = mNowCalendar.get(Calendar.YEAR) - mBirthdayCalendar.get(Calendar.YEAR);

        mBirthdayCalendar.add(Calendar.YEAR, mAge);
        if (mNowCalendar.before(mBirthdayCalendar)) {
            mAge--;
        }
        return mAge;
    }

    /**
     * p8Birthday格式yyyyMMdd
     */
    public static int getAge(String p8Birthday) {
        return getAge(parseDate(p8Birthday, "yyyyMMdd"));
    }

    /**
     * 计算周年。自动判断两参数大小，始终返回正值。
     */
    public static int compareYear(Date pFirDate, Date pSecDate) {
        Date mSmlDate = pFirDate;
        Date mBigDate = pSecDate;
        if (pSecDate.before(pFirDate)) {
            mSmlDate = pSecDate;
            mBigDate = pFirDate;
        }

        GregorianCalendar mSmlCalendar = new GregorianCalendar();
        mSmlCalendar.setTime(mSmlDate);

        GregorianCalendar mBigCalendar = new GregorianCalendar();
        mBigCalendar.setTime(mBigDate);

        int mDifYear =
                mBigCalendar.get(Calendar.YEAR) - mSmlCalendar.get(Calendar.YEAR);

        mSmlCalendar.add(Calendar.YEAR, mDifYear);
        if (mBigCalendar.before(mSmlCalendar)) {
            mDifYear--;
        }

        return mDifYear;
    }

    /**
     * 计算周年，格式yyyyMMdd。自动判断两参数大小，始终返回正值。
     */
    public static int compareYear(String pFir8Date, String pSec8Date) {
        return compareYear(
                parseDate(pFir8Date, "yyyyMMdd"), parseDate(pSec8Date, "yyyyMMdd"));
    }

    /**
     * 计算周月。自动判断两参数大小，始终返回正值。
     */
    public static int compareMonth(Date pFirDate, Date pSecDate) {
        Date mSmlDate = pFirDate;
        Date mBigDate = pSecDate;
        if (pSecDate.before(pFirDate)) {
            mSmlDate = pSecDate;
            mBigDate = pFirDate;
        }

        GregorianCalendar mSmlCalendar = new GregorianCalendar();
        mSmlCalendar.setTime(mSmlDate);

        GregorianCalendar mBigCalendar = new GregorianCalendar();
        mBigCalendar.setTime(mBigDate);

        int mDifYear =
                mBigCalendar.get(Calendar.YEAR) - mSmlCalendar.get(Calendar.YEAR);

        int mDifMonth =
                mBigCalendar.get(Calendar.MONTH) - mSmlCalendar.get(Calendar.MONTH);
        if (mDifMonth < 0) {
            mDifYear--;
            mDifMonth += 12;
        }

        mDifMonth += mDifYear * 12;

        mSmlCalendar.add(Calendar.MONTH, mDifMonth);
        if (mBigCalendar.before(mSmlCalendar)) {
            mDifMonth--;
        }

        return mDifMonth;
    }

    /**
     * 计算周月，格式yyyyMMdd。自动判断两参数大小，始终返回正值。
     */
    public static int compareMonth(String pFir8Date, String pSec8Date) {
        return compareMonth(
                parseDate(pFir8Date, "yyyyMMdd"), parseDate(pSec8Date, "yyyyMMdd"));
    }

    /**
     * 计算周日。自动判断两参数大小，始终返回正值。
     */
    public static int compareDay(Date pFirDate, Date pSecDate) {
        Date mSmlDate = pFirDate;
        Date mBigDate = pSecDate;
        if (pSecDate.before(pFirDate)) {
            mSmlDate = pSecDate;
            mBigDate = pFirDate;
        }

        long mMillisecond = mBigDate.getTime() - mSmlDate.getTime();

        return (int) (mMillisecond / (24 * 60 * 60 * 1000));
    }

    /**
     * 计算周日，格式yyyyMMdd。自动判断两参数大小，始终返回正值。
     */
    public static int compareDay(String pFir8Date, String pSec8Date) {
        return compareDay(
                parseDate(pFir8Date, "yyyyMMdd"), parseDate(pSec8Date, "yyyyMMdd"));
    }

    public static String getLastDayOfMonth(int pYear, int pMonth) {
        Calendar mFirDayOfNextMonth = new GregorianCalendar(pYear, pMonth + 1, 1);
        mFirDayOfNextMonth.add(Calendar.DAY_OF_MONTH, -1);
        return getDateStr(mFirDayOfNextMonth, "yyyy-MM-dd");
    }

    public static boolean checkDate(String sourceDate) {
        if (sourceDate == null) {
            return false;
        }
        try {
            System.out.println(sourceDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            dateFormat.parse(sourceDate);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static List<Date> countDays(String start, String end) {

        Date startDate = DateUtil.parseDate(start, "yyyy-MM-dd");
        Date endDate = DateUtil.parseDate(end, "yyyy-MM-dd");

        List<Date> dayList = new ArrayList<Date>();

        long startValue = startDate.getTime();

        long endValue = endDate.getTime();

        if (startValue > endValue) {
            long temp = endValue;
            endValue = startValue;
            startValue = temp;
        }

        dayList.add(startDate);
        // 计算天数差
        long result = (endDate.getTime() - startDate.getTime()) / (3600 * 24 * 1000);

//	       dayList.add(startDate);

        Calendar cal = Calendar.getInstance();

        cal.setTime(startDate);

        // 计算期间的每一天
        for (int index = 1; index < result; index++) {
            cal.add(Calendar.DATE, 1);
            dayList.add(cal.getTime());
        }

        if (!startDate.equals(endDate)) {
            dayList.add(endDate);
        }

        return dayList;
    }


    /**
     * 输入8位或10位日期,返回10位日期
     */
    public static String nextDay(String date) {
        if (date.length() == 8) {
            date = DateUtil.date8to10(date);
        }
        Date sDate = DateUtil.parseDate(date, "yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sDate);
        cal.add(Calendar.DATE, 1);
        return DateUtil.get10Date(cal.getTime());
    }

    public static String nextDay(int date) {
        return nextDay(String.valueOf(date));
    }

    /**
     * 实现生日转换：19850709——>1985年07月09日
     *
     * @param birth 参数如“19850709”
     * @return
     */
    public static String get11Birthday(String birth) {
        if (birth.length() > 10 || birth.length() < 1) {
            return "";
        } else {
            return birth.substring(0, 4) + "年"
                    + birth.substring(5, 7) + "月"
                    + birth.substring(8, 10) + "日";
        }
    }

    /**
     * 实现生日转换：1985-07-09 00:00:0——>1985年07月09日
     *
     * @param birth 参数如“19850709”
     * @return
     */
    public static String getNew11Birthday(String birth) {
        if (birth.length() > 30 || birth.length() < 1) {
            return "";
        } else {
            return birth.substring(0, 4) + "年"
                    + birth.substring(5, 7) + "月"
                    + birth.substring(8, 10) + "日";
        }
    }

    /**
     * 输入8位日期,返回10位日期,若输入99991231则返回“长期”
     */
    public static String getGovtTermDate(String date) {

        if ("99991231".equals(date.substring(0, 8))) {
            return "长期";
        } else {
            return DateUtil.date8to10(date);
        }

    }

    /**
     * 输入8位或10位日期，返回XMLGregorianCalendar格式
     */
    public static XMLGregorianCalendar getXMLGregorianCalendar(String date) {

        if (date.length() == 8) {
            date = DateUtil.date8to10(date);
        }
        Date tDate = DateUtil.parseDate(date, "yyyy-MM-dd");
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(tDate);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return gc;
    }

    /**
     * 日期加法
     *
     * @param date
     * @param add
     * @return
     */
    public static String AddDay(String date, int add) {
        if (date.length() == 8) {
            date = DateUtil.date8to10(date);
        }
        Date sDate = DateUtil.parseDate(date, "yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sDate);
        cal.add(Calendar.DATE, add);
        return DateUtil.get10Date(cal.getTime());
    }


}
	 

