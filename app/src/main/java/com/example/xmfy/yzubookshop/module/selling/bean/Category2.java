package com.example.xmfy.yzubookshop.module.selling.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by xmfy on 2018/1/18.
 */
public class Category2 implements IPickerViewData{

    private int id;
    private String name;

    public Category2() {
    }

    public Category2(int id, String name) {
        this.id = id;
        this.name = name;
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
        return "Category2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
