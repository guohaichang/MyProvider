package com.hdib.provider.bean;

import android.database.Cursor;

import com.hdib.provider.core.Common;

public class Fruit extends BaseBean<Fruit> {
    private String name;
    private String color;

    public Fruit() {
    }

    public Fruit(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    @Override
    public Fruit obtainBean(Cursor cursor) {
        this.setName(cursor.getString(cursor.getColumnIndex(Common.Columns.FruitColumns.NAME)));
        this.setColor(cursor.getString(cursor.getColumnIndex(Common.Columns.FruitColumns.COLOR)));
        return this;
    }
}
