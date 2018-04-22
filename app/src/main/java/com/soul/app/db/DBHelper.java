package com.soul.app.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by omji on 29/10/15.
 */
public abstract class DBHelper {
    /**
     * Abstract method to get an instance of DB
     *
     * @return An instance of Sqlite DB
     */
    public abstract SQLiteDatabase openDB();
}
