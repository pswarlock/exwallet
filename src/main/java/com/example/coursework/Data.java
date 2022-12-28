package com.example.coursework;

public class Data {
    public int id;
    public String type;
    public String category;
    public int value;
    public String note;

    public Data() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Data(int id, String type, String category, int value, String note) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.value = value;
        this.note = note;
    }
}
