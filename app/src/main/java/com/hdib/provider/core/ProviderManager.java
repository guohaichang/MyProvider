package com.hdib.provider.core;

import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;

import com.hdib.provider.core.helper.Table;
import com.hdib.provider.core.table.TableFruit;
import com.hdib.provider.core.table.TableUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProviderManager {
    private List<Table> list = new ArrayList<>();
    private UriMatcher uriMatcher;

    private int dbVersion;

    public void initTables() {
        list.add(new TableUser());
        list.add(new TableFruit());
    }

    public ProviderManager() {
        initTables();
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        for (Table table : list) {
            Map<String, Integer> map = table.obtainURIMap();
            if (map == null) {
                continue;
            }
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                uriMatcher.addURI(Common.AUTHORITY, entry.getKey(), entry.getValue());
            }
            dbVersion += table.getVersion();
        }
    }

    public UriMatcher getUriMatcher() {
        return uriMatcher;
    }

    public int getDbVersion() {
        return dbVersion;
    }

    public void dealDb(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            db.execSQL(VersionManager.createTableSql());
            for (Table table : list) {
                VersionManager.Version version = VersionManager.getVersionBean(db, table.getTableName());
                if (version == null) {
                    boolean success = table.onCreate(db);
                    if (success) {
                        VersionManager.saveVersionInfo(db, table.getTableName(), table.getVersion());
                    }
                } else if (table.getVersion() > version.ver) {
                    boolean success = table.onUpgrade(db, version.ver, table.getVersion());
                    if (success) {
                        VersionManager.updateVersionInfo(db, version.tableName, table.getVersion());
                    }
                } else if (table.getVersion() < version.ver) {
                    boolean success = table.onDowngrade(db, version.ver, table.getVersion());
                    if (success) {
                        VersionManager.updateVersionInfo(db, version.tableName, table.getVersion());
                    }
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            VersionManager.clear();
            list.clear();
        }

    }

}
