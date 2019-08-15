package com.hdib.provider.helper.repo;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;

import com.hdib.provider.bean.Fruit;
import com.hdib.provider.helper.callback.Callback;
import com.hdib.provider.helper.callback.EntityListCallback;
import com.hdib.provider.helper.dao.FruitDao;

import java.util.List;

public class FruitRepo extends RepoBase {

    private final FruitDao fruitDao;

    public FruitRepo(Context context) {
        super();
        fruitDao = new FruitDao(context);
    }

    public void insert(final Fruit fruit, final Callback callback) {
        childPost(new Runnable() {
            @Override
            public void run() {
                final Uri uri = fruitDao.insert(fruit);
                commonDeal(callback, Integer.parseInt(uri.getLastPathSegment()));
            }
        });
    }

    public void insert(final List<Fruit> fruits, final Callback callback) {
        childPost(new Runnable() {
            @Override
            public void run() {
                final int rows = fruitDao.insert(fruits);
                commonDeal(callback, rows);
            }
        });
    }

    public void deleteByColor(final String color, final Callback callback) {
        childPost(new Runnable() {
            @Override
            public void run() {
                final int rows = fruitDao.deleteByColor(color);
                commonDeal(callback, rows);
            }
        });
    }

    public void updateColumnByRow(final int row, final String column, final String value, final Callback callback) {
        childPost(new Runnable() {
            @Override
            public void run() {
                final int rows = fruitDao.updateColumnByRow(row, column, value);
                commonDeal(callback, rows);
            }
        });
    }

    public void queryAll(final EntityListCallback<Fruit> callback) {
        childPost(new Runnable() {
            @Override
            public void run() {
                final List<Fruit> fruits = fruitDao.queryAll();
                if (callback != null) {
                    mainPost(new Runnable() {
                        @Override
                        public void run() {
                            callback.callback(fruits);
                        }
                    });
                }
            }
        });
    }
}
