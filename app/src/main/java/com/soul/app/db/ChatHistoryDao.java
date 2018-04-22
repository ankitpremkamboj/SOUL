package com.soul.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.soul.app.Bean.SocketChatData;
import com.soul.app.db.*;

/**
 * Created by omji on 15/7/16.
 */
public class ChatHistoryDao extends com.soul.app.db.DBHelper {
    // Table Name
    public static final String TABLE_NAME = "chat_history_dao";
    // Column Names
    public static final String KEY_ID = "_id";
    public static final String KEY_RESPONSE_TYPE = "response_type";
    public static final String KEY_MESSAGE_ID = "messageid";
    public static final String KEY_FROM = "from";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TO = "to";
    public static final String KEY_TIME_STAMP = "timestamp";
    public static final String KEY_SIDE = "side";
    public static final String KEY_MESSAGE_TYPE = "message_type";
    public static final String KEY_MSG_UNIQUE_ID = "msguniqueid";
    public static final String KEY_CHAT_TIMESTAMP = "key_chat_timestamp";
    public static final String KEY_CHAT_IS_READ = "key_chat_is_read";
    private static final String KEY_CHAT_WITH_ID = "key_chat_with_id";

    // Create Table Query
    public static final String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_NAME
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ""
            + KEY_RESPONSE_TYPE + " TEXT, " + KEY_FROM + " TEXT, " + KEY_MESSAGE + " TEXT , "
            + KEY_MESSAGE_ID + " INTEGER, " + KEY_TO + " TEXT," + KEY_TIME_STAMP + " TEXT,"
            + KEY_SIDE + " TEXT,"
            + KEY_MESSAGE_TYPE + " TEXT," + KEY_MSG_UNIQUE_ID + " TEXT, "
            + KEY_CHAT_WITH_ID + " TEXT," + KEY_CHAT_TIMESTAMP + " TEXT," + KEY_CHAT_IS_READ + " TEXT)";


    SQLiteDatabase localSQLiteDatabase = null;
    private Context mContext = null;

    public ChatHistoryDao(Context paramContext) {
        this.mContext = paramContext;
        localSQLiteDatabase = openDB();
    }

    @Override
    public SQLiteDatabase openDB() {
        return DatabaseHelper.getDatabaseHelperInstance(mContext)
                .getReadableDatabase();
    }

    // FOR INSERT CHAT IN DB
    public synchronized void insertChatInDb(SocketChatData socketChatData) {
        try {
            ContentValues localContentValues = new ContentValues();
            localContentValues.put(ChatHistoryDao.KEY_RESPONSE_TYPE, socketChatData.getResponse_type());
            localContentValues.put(ChatHistoryDao.KEY_MESSAGE_ID, socketChatData.getMessageid());
            localContentValues.put(ChatHistoryDao.KEY_FROM, socketChatData.getFrom());
            localContentValues.put(ChatHistoryDao.KEY_TO, socketChatData.getTo());
            localContentValues.put(ChatHistoryDao.KEY_MESSAGE, socketChatData.getMessage());
            localContentValues.put(ChatHistoryDao.KEY_TIME_STAMP, socketChatData.getTimestamp());
            localContentValues.put(ChatHistoryDao.KEY_SIDE, socketChatData.getSide());
            localContentValues.put(ChatHistoryDao.KEY_MESSAGE_TYPE, socketChatData.getMessage_type());
            localContentValues.put(ChatHistoryDao.KEY_MSG_UNIQUE_ID, socketChatData.getMsguniqueid());

            long key = localSQLiteDatabase.insertWithOnConflict(
                    ChatHistoryDao.TABLE_NAME, null, localContentValues,
                    SQLiteDatabase.CONFLICT_IGNORE);
            System.out.println(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
