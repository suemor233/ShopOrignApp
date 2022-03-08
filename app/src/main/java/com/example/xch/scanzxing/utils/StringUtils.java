package com.example.xch.scanzxing.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

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

    public static StringBuffer subTime(String s) {
        String substring = s.substring(0, 16);
        String str = StringUtils.removeCharAt(substring, 10);
        StringBuffer stringBuffer = new StringBuffer(str);
        stringBuffer.insert(10," ");
        return stringBuffer;
    }

}
