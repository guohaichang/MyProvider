package com.hdib.provider.core.helper;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

public interface Table {
    String createTableSql(String tableName, String columns);

    int getVersion();

    String getTableName();

    String createTableSql();

    Map<String, Integer> obtainURIMap();

    boolean onCreate(SQLiteDatabase db);

    boolean onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    boolean onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
