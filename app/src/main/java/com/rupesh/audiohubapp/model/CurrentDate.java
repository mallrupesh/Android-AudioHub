package com.rupesh.audiohubapp.model;

import java.text.DateFormat;
import java.util.Date;

public class CurrentDate {

    /**
     * Empty constructor for Firebase operation.
     */
    public CurrentDate(){}

    /**
     *
     * @return String of date instance in date format.
     */
    public String getDate() {
        return DateFormat.getDateTimeInstance().format(new Date());
    }
}
