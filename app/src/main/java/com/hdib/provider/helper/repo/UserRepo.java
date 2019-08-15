package com.hdib.provider.helper.repo;

import android.content.Context;
import android.net.Uri;

import com.hdib.provider.bean.User;
import com.hdib.provider.helper.callback.Callback;
import com.hdib.provider.helper.callback.EntityListCallback;
import com.hdib.provider.helper.dao.UserDao;

import java.util.List;

public class UserRepo extends RepoBase {

    private final UserDao userDao;

    public UserRepo(Context context) {
        super();
        userDao = new UserDao(context);
    }

    public void insert(final User user, final Callback callback) {
        childPost(new Runnable() {
            @Override
            public void run() {
                final Uri uri = userDao.insert(user);
                commonDeal(callback, Integer.parseInt(uri.getLastPathSegment()));
            }
        });
    }

    public void insert(final List<User> users, final Callback callback) {
        childPost(new Runnable() {
            @Override
            public void run() {
                final int rows = userDao.insert(users);
                commonDeal(callback, rows);
            }
        });
    }

    public void deleteByAge(final int age, final Callback callback) {
        childPost(new Runnable() {
            @Override
            public void run() {
                final int rows = userDao.deleteByAge(age);
                commonDeal(callback, rows);
            }
        });
    }

    public void updateColumnByRow(final int row, final String column, final String value, final Callback callback) {
        childPost(new Runnable() {
            @Override
            public void run() {
                final int rows = userDao.updateColumnByRow(row, column, value);
                commonDeal(callback, rows);
            }
        });
    }

    public void queryAll(final EntityListCallback<User> callback) {
        childPost(new Runnable() {
            @Override
            public void run() {
                final List<User> users = userDao.queryAll();
                if (callback != null) {
                    mainPost(new Runnable() {
                        @Override
                        public void run() {
                            callback.callback(users);
                        }
                    });
                }
            }
        });
    }
}
