package com.weiyi.wx.order.domain;

public class Statistics<T> implements Comparable<Statistics<T>>{
    private String year;

    private T value;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int compareTo(Statistics<T> o) {
        return this.year.compareTo(o.getYear());
    }
}
