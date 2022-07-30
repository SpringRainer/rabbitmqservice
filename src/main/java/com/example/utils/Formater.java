package com.example.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/24 17:57
 * 转换日期格式
 */
//@Component
public class Formater {
    public static String transferDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }
}
