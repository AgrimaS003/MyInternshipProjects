// Expense.java
package com.example.expensetracker;
import java.io.Serializable;

public class Expense implements Serializable {
    private String amount;
    private String date;
    private String category;
    private String notes;

    public Expense(String amount, String date, String category, String notes) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.notes = notes;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
