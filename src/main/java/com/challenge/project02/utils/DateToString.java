package com.challenge.project02.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateToString {
    
    public static String transformDate(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = localDateTime.format(formatter);

        return formattedDate;
    }
}
