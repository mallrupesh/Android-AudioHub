package com.rupesh.audiohubapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CurrentDate {
    private Date currentDate;

    public CurrentDate(){}

    public String getDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = dateFormat.format(currentDate);
        return date;
    }
}
