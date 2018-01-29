package com.example.xmfy.yzubookshop.module.buy.searchTab;

/**
 * Created by xmfy on 2018/1/28.
 */
public class SortBean {
    private String text;
    private Boolean isChecked;

    public SortBean(String text, Boolean isChecked) {
        this.text = text;
        this.isChecked = isChecked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "SortBean{" +
                "text='" + text + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
