package com.hdib.provider.core.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.hdib.provider.LogUtil;
import com.hdib.provider.core.Common;
import com.hdib.provider.core.ProviderManager;
import com.hdib.provider.core.wrapper.Database;
import com.hdib.provider.core.wrapper.DatabaseOpenHelper;

public class SdbOpenHelper extends DatabaseOpenHelper {
    public static ProviderManager MANAGER = new ProviderManager();

    public SdbOpenHelper(Context context) {
        super(context, Common.DB_NAME, MANAGER.getDbVersion());
    }

    public SdbOpenHelper(Context context, CursorFactory factory) {
        super(context, Common.DB_NAME, factory, MANAGER.getDbVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtil.d("数据库创建：" + db.getPath());
        MANAGER.dealDb(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.d("数据库升级：" + "oldVersion：" + oldVersion + "newVersion：" + newVersion);
        MANAGER.dealDb(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.d("数据库升级：" + "oldVersion：" + oldVersion + "newVersion：" + newVersion);
        MANAGER.dealDb(db);
    }

    @Override
    public Database getWritableDb() {
        return super.getWritableDb();
//        return super.getEncryptedWritableDb(DB_CIPHER);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }
}
