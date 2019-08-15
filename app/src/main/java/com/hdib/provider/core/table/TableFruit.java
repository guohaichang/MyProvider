package com.hdib.provider.core.table;

import android.database.sqlite.SQLiteDatabase;

import com.hdib.provider.core.Common;

import java.util.Map;

public class TableFruit extends TableBase {
    public static final String TABLE_NAME = Common.TableName.TABLE_FRUIT_NAME;
    public static final int TABLE_VERSION = 1;

    public TableFruit() {
        super(TABLE_NAME, TABLE_VERSION);
    }

    @Override
    public String createTableSql() {
        StringBuilder columns = new StringBuilder()
                .append(Common.Columns.FruitColumns.NAME).append(" TEXT, ")
                .append(Common.Columns.FruitColumns.COLOR).append(" TEXT");
        return super.createTableSql(TABLE_NAME, columns.toString());
    }

    /**
     * '*'表示匹配任意字符，'#'表示匹配任意数字
     */
    @Override
    public Map<String, Integer> obtainURIMap() {
        Map<String, Integer> map = super.obtainURIMap();
        map.put(TABLE_NAME, Common.URIValue.VALUE_FRUIT_DIR);
        map.put(TABLE_NAME + "/#", Common.URIValue.VALUE_FRUIT_ITEM);
        return map;
    }

    @Override
    public boolean onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(createTableSql());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return false;
    }

    @Override
    public boolean onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return false;
    }
}
