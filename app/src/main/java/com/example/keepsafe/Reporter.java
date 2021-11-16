package com.example.keepsafe;

public class Reporter {

    String kejadian;
    String date;
    String loc;

    public Reporter(String kejadian, String date, String loc) {
        this.kejadian = kejadian;
        this.date = date;
        this.loc = loc;
    }



    public String getKejadian() {
        return kejadian;
    }

    public String getDate() {
        return date;
    }

    public String getLoc() {
        return loc;
    }
}
