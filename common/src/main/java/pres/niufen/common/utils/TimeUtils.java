package pres.niufen.common.utils;

/**
 * @Description 时间相关工具类
 * @Author niufenjava
 * @Date 2019-01-19 11:38
 **/
public class TimeUtils {

    public static Integer timeToInt(String time) {
        if (!time.contains(":")) {
            return Integer.parseInt(time);
        } else {
            String[] arr = time.split(":");
            return Integer.parseInt(arr[0]) * 60 + Integer.parseInt(arr[1]);
        }
    }

    public static String intToTime(Integer num) {

        if (num >= 0 && num < 10) {
            return "00:0" + num;
        } else if (num < 60 && num >= 10) {
            return "00:" + num;
        } else if (num >= 60) {
            int hour = num / 60;
            int min = num % 60;
            if (min >= 0 && min < 10) {
                return hour + ":0" + min;
            } else {
                return hour + ":" + min;
            }
        } else {
            return "00:00";
        }
    }
}
