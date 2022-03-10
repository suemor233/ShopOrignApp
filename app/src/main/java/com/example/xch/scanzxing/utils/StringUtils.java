package com.example.xch.scanzxing.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

//String工具类
public class StringUtils {
    public static boolean isEmpty(String str){
        if (str == null || str.length() <= 0){
            return true;
        }else {
            return false;
        }
    }


    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    public static String subTime(String s) {
        String substring = s.substring(0, 16);
        String str = StringUtils.removeCharAt(substring, 10);
        StringBuffer stringBuffer = new StringBuffer(str);
        stringBuffer.insert(10," ");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        Date date =  simpleDateFormat.parse(stringBuffer.toString(),new ParsePosition(0));
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return  simpleDateFormat.format(date).toString();
    }

}
