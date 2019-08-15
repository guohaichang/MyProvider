package com.hdib.provider.core.wrapper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.os.CancellationSignal;

public interface Database {
    Cursor query(boolean distinct, String table, String[] columns, String selection,
                 String[] selectionArgs, String groupBy, String having,
                 String orderBy);

    Cursor query(boolean distinct, String table, String[] columns,
                 String selection, String[] selectionArgs, String groupBy,
                 String having, String orderBy, String limit);

    Cursor query(String table, String[] columns, String selection,
                 String[] selectionArgs, String groupBy, String having,
                 String orderBy);

    Cursor query(String table, String[] columns,
                 String selection, String[] selectionArgs, String groupBy,
                 String having, String orderBy, String limit);


    Cursor rawQuery(String sql, String[] selectionArgs);

    Cursor rawQuery(String sql, String[] selectionArgs, CancellationSignal cancellationSignal);

    long insert(String table, String nullColumnHack, ContentValues values);

    long insertOrThrow(String table, String nullColumnHack, ContentValues values)
            throws SQLException;

    long insertWithOnConflict(String table, String nullColumnHack,
                              ContentValues initialValues, int conflictAlgorithm);

    int update(String table, ContentValues values, String whereClause, String[] whereArgs);

    int updateWithOnConflict(String table, ContentValues values,
                             String whereClause, String[] whereArgs, int conflictAlgorithm);

    int delete(String table, String whereClause, String[] whereArgs);

    void execSQL(String sql) throws SQLException;

    void beginTransaction();

    void endTransaction();

    boolean inTransaction();

    void setTransactionSuccessful();

    void execSQL(String sql, Object[] bindArgs) throws SQLException;

    DatabaseStatement compileStatement(String sql);

    boolean isDbLockedByCurrentThread();

    void close();

    Object getRawDatabase();
}