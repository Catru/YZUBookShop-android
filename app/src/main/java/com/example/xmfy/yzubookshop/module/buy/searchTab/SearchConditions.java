package com.example.xmfy.yzubookshop.module.buy.searchTab;

/**
 * Created by xmfy on 2018/1/29.
 */
public class SearchConditions {
    private String type = "title";
    private String value = "";
    private int sort = 0;
    private int c1 = -1;
    private int c2 = -1;
    private int min = 0;
    private int max = 200;
    private String account;

    public SearchConditions() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getC1() {
        return c1;
    }

    public void setC1(int c1) {
        this.c1 = c1;
    }

    public int getC2() {
        return c2;
    }

    public void setC2(int c2) {
        this.c2 = c2;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "SearchConditions{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", sort=" + sort +
                ", c1=" + c1 +
                ", c2=" + c2 +
                ", min=" + min +
                ", max=" + max +
                ", account='" + account + '\'' +
                '}';
    }
}
