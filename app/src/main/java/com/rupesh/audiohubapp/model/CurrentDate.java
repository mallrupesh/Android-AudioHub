package com.rupesh.audiohubapp.model;

import java.text.DateFormat;
import java.util.Date;

public class CurrentDate {
    private Date currentDate;


    public CurrentDate(){}

    public String getDate() {
        /*Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = dateFormat.format(currentDate);
        return date;*/

        return DateFormat.getDateTimeInstance().format(new Date());
    }
}
