package com.hdib.provider.core.helper;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.hdib.provider.LogUtil;
import com.hdib.provider.core.Common;
import com.hdib.provider.core.wrapper.Database;
import com.hdib.provider.core.wrapper.DatabaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyContentProvider extends ContentProvider {

    private UriMatcher uriMatcher;

    private DatabaseOpenHelper dbOpenHelper;

    /**
     * true if the provider was successfully loaded, false otherwise
     *
     * @return
     */
    @Override
    public boolean onCreate() {
        try {
            uriMatcher = SdbOpenHelper.MANAGER.getUriMatcher();
            dbOpenHelper = new SdbOpenHelper(getContext());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取MIME(类型)
     * 描述内容 URI 参数返回的数据类型。Uri 参数可以是模式，而不是特定 URI；在这种情况下，您应该返回与匹配该模式的内容 URI 关联的数据类型。
     *
     * @param uri
     * @return MIME 格式的 String
     */
    @Override
    public String getType(Uri uri) {
        int uriValue = uriMatcher.match(uri);
        if (uriValue < Common.URIValue.VALUE_DIR) {
            return "vnd.android.cursor.dir/vnd." + Common.AUTHORITY + "." + uri.getPathSegments().get(0);
        } else if (uriValue < Common.URIValue.VALUE_ITEM) {
            return "vnd.android.cursor.item/vnd." + Common.AUTHORITY + "." + uri.getPathSegments().get(0);
        } else {
            return null;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        LogUtil.d("query: " + Thread.currentThread().getName());
        Database database = dbOpenHelper.getWritableDb();
        Cursor cursor = null;
        try {
            String table = uri.getPathSegments().get(0);
            int uriValue = uriMatcher.match(uri);
            if (uriValue < Common.URIValue.VALUE_DIR) {
                cursor = database.query(table, projection, selection, selectionArgs, null, null, sortOrder);
            } else if (uriValue < Common.URIValue.VALUE_ITEM) {
                String id = uri.getPathSegments().get(1);
                cursor = database.query(table, projection, Common.Columns._ID + "=?", new String[]{id}, null, null, sortOrder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return cursor;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        LogUtil.d("insert: " + Thread.currentThread().getName());
        Database database = dbOpenHelper.getWritableDb();
        Uri uriNew = null;
        try {
            String table = uri.getPathSegments().get(0);
            if (!values.containsKey(Common.Columns._COUNT)) {
                values.put(Common.Columns._COUNT, getCount(database, table) + 1);
            }
            int uriValue = uriMatcher.match(uri);
            if (uriValue < Common.URIValue.VALUE_DIR) {
                long id = database.insert(table, null, values);
                uriNew = ContentUris.withAppendedId(uri, id);
                getContext().getContentResolver().notifyChange(uri, null);
            } else if (uriValue < Common.URIValue.VALUE_ITEM) {
                values.put(Common.Columns._ID, uri.getPathSegments().get(1));
                long id = database.insert(table, null, values);
                uriNew = ContentUris.withAppendedId(uri, id);
                getContext().getContentResolver().notifyChange(uri, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return uriNew;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        LogUtil.d("delete: " + Thread.currentThread().getName());
        Database database = dbOpenHelper.getWritableDb();
        int rows = 0;
        try {
            String table = uri.getPathSegments().get(0);
            int uriValue = uriMatcher.match(uri);
            if (uriValue < Common.URIValue.VALUE_DIR) {
                updateColumnCountForDelete(database, table, selection, selectionArgs);
                rows = database.delete(table, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
            } else if (uriValue < Common.URIValue.VALUE_ITEM) {
                String id = uri.getPathSegments().get(1);
                selection = Common.Columns._ID + "=?";
                selectionArgs = new String[]{id};
                updateColumnCountForDelete(database, table, selection, selectionArgs);
                rows = database.delete(table, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return rows;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        LogUtil.d("update: " + Thread.currentThread().getName());
        Database database = dbOpenHelper.getWritableDb();
        int rows = 0;
        try {
            String table = uri.getPathSegments().get(0);
            int uriValue = uriMatcher.match(uri);
            if (uriValue < Common.URIValue.VALUE_DIR) {
                rows = database.update(table, values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
            } else if (uriValue < Common.URIValue.VALUE_ITEM) {
                String id = uri.getPathSegments().get(1);
                rows = database.update(table, values, Common.Columns._ID + "=?", new String[]{id});
                getContext().getContentResolver().notifyChange(uri, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return rows;
        }
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        LogUtil.d("applyBatch: " + Thread.currentThread().getName());
        if (operations == null) {
            return null;
        }
        Database database = dbOpenHelper.getWritableDb();
        ContentProviderResult[] results = null;
        try {
            database.beginTransaction();//开始事务
            results = super.applyBatch(operations);
            database.setTransactionSuccessful();//设置事务标记为successful

            for (ContentProviderOperation operation : operations) {
                getContext().getContentResolver().notifyChange(operation.getUri(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();//结束事务
            return results;
        }
    }

//******************************************************************************************************************************************************
//******************************************************************************************************************************************************

    public int getCount(Database database, String tableName) {
        Cursor cursor = null;
        try {
            cursor = database.query(tableName, new String[]{"MAX(" + Common.Columns._COUNT + ")"},
                    null, null, null, null, null);
            if (cursor == null || cursor.isClosed()) {
                return 0;
            } else if (cursor.moveToNext()) {
                return cursor.getInt(0);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    private void updateColumnCountForDelete(Database database, String tableName, String selection, String[] selectionArgs) throws RuntimeException {
        Cursor cursor = null;
        List<Integer> counts = new ArrayList<>();
        try {
            cursor = database.query(tableName, new String[]{Common.Columns._COUNT}, selection, selectionArgs, null, null, null);
            if (cursor == null || cursor.isClosed()) {
                return;
            }
            while (cursor.moveToNext()) {
                counts.add(cursor.getInt(0));
            }
            cursor.close();
            if (counts.size() == 0) {
                return;
            }
            int lastCount = counts.get(0);
            int offset = 1;
            if (counts.size() > 1) {
                for (int i = 1; i < counts.size(); i++) {
                    int curCount = counts.get(i);
                    if (curCount - lastCount > 1) {
                        cursor = database.query(tableName, new String[]{Common.Columns._ID, Common.Columns._COUNT},
                                Common.Columns._COUNT + "> ? AND " + Common.Columns._COUNT + "< ?", new String[]{lastCount + "", curCount + ""},
                                null, null, null);
                        updateColumnCountForDelete(database, tableName, cursor, i);
                    }
                    lastCount = curCount;
                }
                offset = counts.size();
            }
            cursor = database.query(tableName, new String[]{Common.Columns._ID, Common.Columns._COUNT},
                    Common.Columns._COUNT + ">?", new String[]{lastCount + ""},
                    null, null, null);
            updateColumnCountForDelete(database, tableName, cursor, offset);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    private void updateColumnCountForDelete(Database database, String tableName, Cursor cursor, int offset) {
        if (cursor == null || cursor.isClosed()) {
            return;
        }
        int[] ids = new int[cursor.getCount()];
        int[] cnts = new int[cursor.getCount()];
        int pos = 0;
        while (cursor.moveToNext()) {
            ids[pos] = cursor.getInt(0);
            cnts[pos] = cursor.getInt(1);
            pos++;
        }
        for (int i = 0; i < ids.length; i++) {
            ContentValues values = new ContentValues();
            values.put(Common.Columns._COUNT, cnts[i] - offset);
            database.update(tableName, values, Common.Columns._ID + "=?", new String[]{ids[i] + ""});
        }
    }
}
