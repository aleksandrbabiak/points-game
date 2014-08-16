package ua.startandroid.myapplication.data_base_to_point_game;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Alex on 01.05.14.
 */
public class MySqliteSourse extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDB";
    private static final int DATABASE_VERSION = 1;
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_POINTS = "points";
    public static final String COLUMN_WIN_GAME = "win_game";
    public static final String COLUMN_LOSE_GAME = "lose_game";
    public static final String TABLE_NAME = "players";

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_NAME
            + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_LOGIN + " TEXT, "
            + COLUMN_POINTS + " INTEGER, "
            + COLUMN_WIN_GAME + " INTEGER, "
            + COLUMN_LOSE_GAME + " INTEGER )";

    public MySqliteSourse(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
