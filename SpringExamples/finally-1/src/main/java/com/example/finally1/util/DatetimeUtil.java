package com.example.finally1.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DatetimeUtil {

    public static LocalDateTime getCurrentDateAndTime() {
        DateTimeFormatter dtm = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return now;
    }

    public static String convertDatetimeFormat(String date) {
        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            d = sdf.parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        sdf.applyPattern("dd/MM/yyyy HH:mm:ss");
        return sdf.format(d);
    }
}
