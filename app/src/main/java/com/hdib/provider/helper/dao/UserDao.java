package com.hdib.provider.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.hdib.provider.bean.User;
import com.hdib.provider.core.Common;

import java.util.List;

public class UserDao extends DaoBase {
    public UserDao(Context context) {
        super(context);
    }

    public Uri insert(User user) {
        ContentValues values = new ContentValues();
        values.put(Common.Columns.UserColumns.NAME, user.getName());
        values.put(Common.Columns.UserColumns.AGE, user.getAge());
        return super.insert(Common.URI.TABLE_USER_URI, values);
    }

    public int insert(List<User> users) {
        if (users == null || users.size() == 0) {
            return 0;
        }
        ContentValues[] values = new ContentValues[users.size()];
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            values[i].put(Common.Columns.UserColumns.NAME, user.getName());
            values[i].put(Common.Columns.UserColumns.AGE, user.getAge());
        }
        return super.insert(Common.URI.TABLE_USER_URI, values);
    }

    public int deleteByAge(int age) {
        return super.delete(Common.URI.TABLE_USER_URI, Common.Columns.UserColumns.AGE + "=?", new String[]{age + ""});
    }

    public int updateColumnByRow(int row, String column, String value) {
        ContentValues values = new ContentValues();
        values.put(column, value);
        return super.update(Common.URI.TABLE_USER_URI, values, Common.Columns._COUNT + "=?", new String[]{row + ""});
    }

    public List<User> queryAll() {
        return super.queryAll(Common.URI.TABLE_USER_URI, User.class);
    }
}
