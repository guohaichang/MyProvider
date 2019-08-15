package com.hdib.provider.helper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.hdib.provider.LogUtil;
import com.hdib.provider.bean.BaseBean;
import com.hdib.provider.core.helper.CursorParser;

import java.util.List;

public class DaoBase {
    private final Context context;

    public DaoBase(Context context) {
        this.context = context;
    }

    public Uri insert(Uri uri, ContentValues values) {
        long startTime = System.currentTimeMillis();
        Uri insertUri = context.getContentResolver().insert(uri, values);
        LogUtil.d(insertUri.toString() + ",花费时间：" + (System.currentTimeMillis() - startTime));
        return insertUri;
    }

    public int insert(Uri uri, ContentValues[] values) {
        long startTime = System.currentTimeMillis();
        int rows = context.getContentResolver().bulkInsert(uri, values);
        LogUtil.d(rows + ",花费时间：" + (System.currentTimeMillis() - startTime));
        return rows;
    }

    public int delete(Uri uri, String where, String[] whereArgs) {
        long startTime = System.currentTimeMillis();
        int rows = context.getContentResolver().delete(uri, where, whereArgs);
        LogUtil.d("删除行数：" + rows + ",花费时间：" + (System.currentTimeMillis() - startTime));
        return rows;
    }

    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        long startTime = System.currentTimeMillis();
        int rows = context.getContentResolver().update(uri, values, where, whereArgs);
        LogUtil.d("更新行数：" + rows + ",花费时间：" + (System.currentTimeMillis() - startTime));
        return rows;
    }

    public <T extends BaseBean> List<T> queryAll(Uri uri, Class<T> cls) {
        long startTime = System.currentTimeMillis();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        List<T> list = CursorParser.obtainListFrom(cls, cursor);
        for (int i = 0; list != null && i < list.size(); i++) {
            LogUtil.d(list.get(i).toString());
        }
        LogUtil.d("花费时间：" + (System.currentTimeMillis() - startTime));
        return list;
    }
}
