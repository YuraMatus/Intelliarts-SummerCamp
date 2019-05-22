package com.company;

import java.util.Objects;

class DateOfPurchase implements Comparable<DateOfPurchase> {
    private int year;
    private int day;
    private int month;

    public DateOfPurchase(int year, int day, int month) {
        this.year = year;
        this.day = day;
        this.month = month;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateOfPurchase that = (DateOfPurchase) o;
        return year == that.year &&
                day == that.day &&
                month == that.month;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, day, month);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String toString() {
        return year + "-" + month + "-" + day;
    }

    @Override
    public int compareTo(DateOfPurchase o) {
        if (this.equals(o)) {
            return 0;
        } else if (year == o.year && month == o.month && day > o.day) {
            return 1;
        } else if (year == o.year && month > o.month) {
            return 1;
        } else if (year > o.year) {
            return 1;
        } else {
            return -1;
        }
    }
}