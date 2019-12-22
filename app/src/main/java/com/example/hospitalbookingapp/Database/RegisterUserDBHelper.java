package com.example.hospitalbookingapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hospitalbookingapp.Model.User;

public class RegisterUserDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "registerUserDB";

    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";

    public static final String KEY_ID = "id";

    public static final String KEY_USER_FIRST_NAME = "firstname";

    public static final String KEY_USER_LAST_NAME = "lastname";

    public static final String KEY_PASSWORD = "password";

    public static final String KEY_EMAIL = "email";

    public static final String KEY_PHONE = "phone";

    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_USER_FIRST_NAME + " TEXT, " + KEY_USER_LAST_NAME + " TEXT, " + KEY_PASSWORD + " TEXT, " + KEY_EMAIL + " TEXT, " + KEY_PHONE + " TEXT" + " )  ";

    public RegisterUserDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_USER_FIRST_NAME, user.firstname);
        values.put(KEY_USER_LAST_NAME, user.lastname);
        values.put(KEY_PASSWORD, user.password);
        values.put(KEY_EMAIL, user.email);
        values.put(KEY_PHONE, user.phone);

        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_FIRST_NAME, KEY_USER_LAST_NAME, KEY_PASSWORD, KEY_EMAIL, KEY_PHONE}, KEY_PHONE + "=?", new String[]{user.phone}, null, null, null);


        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password))
            {
                return user1;
            }
        }

        return null;


    }

    public boolean isPhoneExists(String phone)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID,KEY_USER_FIRST_NAME,KEY_USER_LAST_NAME,KEY_PASSWORD,KEY_EMAIL,KEY_PHONE},KEY_PHONE + "=?", new String[]{phone},null,null,null);

        if(cursor !=null && cursor.moveToFirst()&&cursor.getCount()>0)
        {
            return true;
        }

        return false;
    }
}