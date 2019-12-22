package com.example.hospitalbookingapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.provider.BaseColumns;

public class BookingDBHandler extends SQLiteOpenHelper
{
    private static final String DB_NAME = "appointmenttable.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_APPOINTMENTS = "appointments";
    public static final String COLUMN_AID = "appointmentID";
    public static final String COLUMN_DOCTOR = "doctor";
    public static final String COLUMN_DEPARTMENT = "department";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_APPOINTMENTS + " (" +
                    COLUMN_AID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DOCTOR + " TEXT, " +
                    COLUMN_DEPARTMENT + " TEXT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_TIME + " TEXT " + ") ";

    public BookingDBHandler(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }
}
