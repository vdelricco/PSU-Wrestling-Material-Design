package com.delricco.vince.psuwrestlingmaterial;

public class News {
    private String title;
    private String date;
    private int id;

    public News(String title, String date, int id) {
        this.title = title;
        this.date = date;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public int getId()  {return id; }
}
