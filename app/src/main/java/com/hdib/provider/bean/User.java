package com.hdib.provider.bean;

import android.database.Cursor;

import com.hdib.provider.core.Common;

public class User extends BaseBean<User> {
    private String name;
    private int age;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public User obtainBean(Cursor cursor) {
        this.setName(cursor.getString(cursor.getColumnIndex(Common.Columns.UserColumns.NAME)));
        this.setAge(cursor.getInt(cursor.getColumnIndex(Common.Columns.UserColumns.AGE)));
        return this;
    }
}
