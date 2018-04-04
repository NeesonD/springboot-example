package com.neeson.example.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/3/29
 * Time: 17:32
 * Description:
 */
public class TimeUtils {

    public static String getUTCTime(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ofPattern("YYYY-MM-DD'T'hh:mm:ss'Z'"));
    }

}
