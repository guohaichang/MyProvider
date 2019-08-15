package com.hdib.provider.core.table;

import com.hdib.provider.LogUtil;
import com.hdib.provider.core.Common;
import com.hdib.provider.core.helper.Table;

import java.util.HashMap;
import java.util.Map;

public abstract class TableBase implements Table {
    private int version;
    private String tableName;

    public TableBase(String tableName, int version) {
        this.tableName = tableName;
        this.version = version;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String createTableSql(String tableName, String columns) {
        String sql_create_table = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(tableName)
                .append("(")
                .append(Common.Columns._ID).append(" INTEGER PRIMARY KEY, ")
                .append(Common.Columns._COUNT).append(" INTEGER")
                .append(columns != null ? ", " + columns : "")
                .append(")")
                .toString();
        LogUtil.d(sql_create_table);
        return sql_create_table;
    }

    @Override
    public Map<String, Integer> obtainURIMap() {
        Map<String, Integer> map = new HashMap<>();
        return map;
    }
}
