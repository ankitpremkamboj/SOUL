package com.soul.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.soul.app.db.*;

/**
 * Created by omji on 22/9/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "salaam_swipe.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static DatabaseHelper databaseHelperInstance;

    private DatabaseHelper(Context paramContext) {
        super(paramContext, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "DatabaseHelper Constructor called");
    }

    public static synchronized DatabaseHelper getDatabaseHelperInstance(
            Context context) {
        if (databaseHelperInstance == null)
            databaseHelperInstance = new DatabaseHelper(context);

        return databaseHelperInstance;
    }

    public static int getDataBaseVersion() {
        return DATABASE_VERSION;
    }

    @Override
    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
        paramSQLiteDatabase.execSQL(com.soul.app.db.ChatHistoryDao.CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "DatabaseHelper onUpgrade called");
    }

}
