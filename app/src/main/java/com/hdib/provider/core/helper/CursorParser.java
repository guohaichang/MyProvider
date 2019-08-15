package com.hdib.provider.core.helper;

import android.database.Cursor;

import com.hdib.provider.bean.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class CursorParser {

    public static <T extends BaseBean> T obtainFrom(Class<T> cls, Cursor cursor) {
        if (cursor == null || cursor.isClosed()) {
            return null;
        }
        try {
            if (cursor.moveToNext()) {
                T t = cls.newInstance();
                return (T) t.obtainBean(cursor);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    public static <T extends BaseBean> List<T> obtainListFrom(Class<T> cls, Cursor cursor) {
        if (cursor == null || cursor.isClosed()) {
            return null;
        }
        try {
            List<T> list = new ArrayList<>();
            while (cursor.moveToNext()) {
                T t = cls.newInstance();
                list.add((T) t.obtainBean(cursor));
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
}
