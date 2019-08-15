package com.hdib.provider.core;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hdib.provider.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class VersionManager {

    public static final String VERSION_TABLE_NAME = "version";
    private static List<Version> vers;

    public static void clear() {
        if (vers != null) {
            vers.clear();
        }
    }

    public static interface VersionColumns {
        public static final String TABLE_NAME = "table_name";
        public static final String VER = "ver";
        public static final String UPDATE_TIME = "update_time";
    }

    public static String createTableSql() {
        String sql_create_table = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(VERSION_TABLE_NAME)
                .append("(")
                .append(VersionColumns.TABLE_NAME).append(" TEXT PRIMARY KEY, ")
                .append(VersionColumns.VER).append(" INTEGER, ")
                .append(VersionColumns.UPDATE_TIME).append(" INTEGER")
                .append(")")
                .toString();
        LogUtil.d(sql_create_table);
        return sql_create_table;
    }

    public static List<Version> obtainListFrom(Cursor cursor) {
        if (cursor == null || cursor.isClosed()) {
            return null;
        }
        try {
            List<Version> list = new ArrayList<>();
            while (cursor.moveToNext()) {
                Version version = Version.class.newInstance();
                version.tableName = cursor.getString(cursor.getColumnIndex(VersionColumns.TABLE_NAME));
                version.ver = cursor.getInt(cursor.getColumnIndex(VersionColumns.VER));
                version.updateTime = cursor.getLong(cursor.getColumnIndex(VersionColumns.UPDATE_TIME));
                list.add(version);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    public static Version getVersionBean(SQLiteDatabase database, String tableName) {
        if (vers == null) {
            Cursor cursor = database.query(VersionManager.VERSION_TABLE_NAME, null, null, null, null, null, null);
            vers = VersionManager.obtainListFrom(cursor);
        }
        if (tableName == null || vers == null || vers.size() == 0) {
            return null;
        }
        for (Version version : vers) {
            if (tableName.equals(version.tableName)) {
                return version;
            }
        }
        return null;
    }

    public static void saveVersionInfo(SQLiteDatabase database, String tableName, int version) {
        ContentValues values = new ContentValues();
        values.put(VersionColumns.TABLE_NAME, tableName);
        values.put(VersionColumns.VER, version);
        values.put(VersionColumns.UPDATE_TIME, System.currentTimeMillis());
        database.insert(VersionManager.VERSION_TABLE_NAME, null, values);
    }

    public static void updateVersionInfo(SQLiteDatabase database, String tableName, int newVersion) {
        ContentValues values = new ContentValues();
        values.put(VersionColumns.VER, newVersion);
        values.put(VersionColumns.UPDATE_TIME, System.currentTimeMillis());
        database.update(VERSION_TABLE_NAME, values, VersionColumns.TABLE_NAME + "=?", new String[]{tableName});
    }

    public static class Version {
        public String tableName;
        public int ver;
        public long updateTime;

        @Override
        public String toString() {
            return "Version{" +
                    "tableName='" + tableName + '\'' +
                    ", ver=" + ver +
                    ", updateTime=" + updateTime +
                    '}';
        }
    }
}
