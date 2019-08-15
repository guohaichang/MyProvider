package com.hdib.provider.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.hdib.provider.bean.Fruit;
import com.hdib.provider.core.Common;

import java.util.List;

public class FruitDao extends DaoBase {
    public FruitDao(Context context) {
        super(context);
    }

    public Uri insert(Fruit fruit) {
        ContentValues values = new ContentValues();
        values.put(Common.Columns.FruitColumns.NAME, fruit.getName());
        values.put(Common.Columns.FruitColumns.COLOR, fruit.getColor());
        return super.insert(Common.URI.TABLE_FRUIT_URI, values);
    }

    public int insert(List<Fruit> fruits) {
        if (fruits == null || fruits.size() == 0) {
            return 0;
        }
        ContentValues[] values = new ContentValues[fruits.size()];
        for (int i = 0; i < fruits.size(); i++) {
            Fruit fruit = fruits.get(i);
            values[i].put(Common.Columns.FruitColumns.NAME, fruit.getName());
            values[i].put(Common.Columns.FruitColumns.COLOR, fruit.getColor());
        }
        return super.insert(Common.URI.TABLE_FRUIT_URI, values);
    }

    public int deleteByColor(String color) {
        return super.delete(Common.URI.TABLE_FRUIT_URI, Common.Columns.FruitColumns.COLOR + "=?", new String[]{color});
    }

    public int updateColumnByRow(int row, String column, String value) {
        ContentValues values = new ContentValues();
        values.put(column, value);
        return super.update(Common.URI.TABLE_FRUIT_URI, values, Common.Columns._COUNT + "=?", new String[]{row + ""});
    }

    public List<Fruit> queryAll() {
        return super.queryAll(Common.URI.TABLE_FRUIT_URI, Fruit.class);
    }
}
