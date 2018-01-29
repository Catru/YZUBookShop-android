package com.example.xmfy.yzubookshop.module.selling.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by xmfy on 2018/1/18.
 */
public class Category1 extends Category implements IPickerViewData{
    private int id;
    private String name;
    private List<Category2> cList;

    public Category1() {
    }

    public Category1(int id, String name, List<Category2> cList) {
        this.id = id;
        this.name = name;
        this.cList = cList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
