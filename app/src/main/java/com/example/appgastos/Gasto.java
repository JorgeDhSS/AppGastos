package com.example.appgastos;

import java.util.Date;

public class Gasto {
    public String amount;
    public Date date;
    public String Category;
    public String tarjet;
    public String note;

    public Gasto(String amount, Date date, String category, String tarjet, String note) {
        this.amount = amount;
        this.date = date;
        this.Category = category;
        this.tarjet = tarjet;
        this.note = note;
    }
}
