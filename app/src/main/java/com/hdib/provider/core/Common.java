package com.hdib.provider.core;

import android.net.Uri;

public interface Common {
    public static final String DB_NAME = "hdib.db";
    public static final String DB_CIPHER = "123456";
    public static final String AUTHORITY = "com.hdib.provider";
    public static final int VLAUE_BASE = 0;
    public static final Uri URI_BASE = Uri.parse("content://" + AUTHORITY);

    public static interface Columns {
        /**
         * The unique ID for a row.
         * <P>Type: INTEGER (long)</P>
         */
        public static final String _ID = "_id";

        /**
         * The count of rows in a directory.
         * <P>Type: INTEGER</P>
         */
        public static final String _COUNT = "_count";

        public static interface UserColumns {
            public static final String NAME = "name";
            public static final String AGE = "age";
        }

        public static interface FruitColumns {
            public static final String NAME = "name";
            public static final String COLOR = "color";
        }
    }

    public static interface TableName {
        public static final String TABLE_USER_NAME = "table_user";
        public static final String TABLE_FRUIT_NAME = "table_fruit";

    }

    public static interface URI {
        public static final Uri TABLE_USER_URI = Uri.withAppendedPath(URI_BASE, TableName.TABLE_USER_NAME);
        public static final Uri TABLE_FRUIT_URI = Uri.withAppendedPath(URI_BASE, TableName.TABLE_FRUIT_NAME);

    }

    public static interface URIValue {
        public static final int VALUE_DIR = VLAUE_BASE + 10000;
        public static final int VALUE_ITEM = VLAUE_BASE + 20000;
        public static final int VALUE_UNKNOW = VLAUE_BASE + 30000;

        //USER
        public static final int VALUE_USER_DIR = VALUE_DIR - 1;
        public static final int VALUE_USER_ITEM = VALUE_ITEM - 1;

        //FRUIT
        public static final int VALUE_FRUIT_DIR = VALUE_DIR - 2;
        public static final int VALUE_FRUIT_ITEM = VALUE_ITEM - 2;
    }
}
