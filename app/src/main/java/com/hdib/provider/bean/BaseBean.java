package com.hdib.provider.bean;

import android.database.Cursor;

public abstract class BaseBean<T extends BaseBean> {
    public abstract T obtainBean(Cursor cursor);
}
