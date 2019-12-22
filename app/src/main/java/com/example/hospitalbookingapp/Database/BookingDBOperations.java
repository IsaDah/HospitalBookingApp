package com.example.hospitalbookingapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.hospitalbookingapp.Model.Appointment;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class BookingDBOperations
{
    public static final String LOGTAG = "APPOINT_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns =
            {
                    BookingDBHandler.COLUMN_AID,
                    BookingDBHandler.COLUMN_DOCTOR,
                    BookingDBHandler.COLUMN_DEPARTMENT,
                    BookingDBHandler.COLUMN_DATE,
                    BookingDBHandler.COLUMN_TIME
            };

    public BookingDBOperations(Context context)
    {
        dbhandler = new BookingDBHandler(context);
    }

    public void open()
    {
        Log.i(LOGTAG, "Database Opened");
        database = dbhandler.getWritableDatabase();
    }

    public void close()
    {
        Log.i(LOGTAG,"Database Closed");
        dbhandler.close();
    }

    public Appointment addAppointment(Appointment Appointment)
    {
        ContentValues values = new ContentValues();
        values.put(BookingDBHandler.COLUMN_DOCTOR, Appointment.getDoctor());
        values.put(BookingDBHandler.COLUMN_DEPARTMENT, Appointment.getDepartment());
        values.put(BookingDBHandler.COLUMN_DATE, Appointment.getDate());
        values.put(BookingDBHandler.COLUMN_TIME, Appointment.getTime());
        long insertAID = database.insert(BookingDBHandler.TABLE_APPOINTMENTS, null,values);
        Appointment.setAppointmentID(insertAID);
        return Appointment;
    }

    public Appointment getAppointment(long id)
    {
        Cursor cursor = database.query(BookingDBHandler.TABLE_APPOINTMENTS,allColumns, BookingDBHandler.COLUMN_AID + "=?", new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor!= null && cursor.moveToFirst())
            cursor.moveToFirst();
        Appointment e = new Appointment(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
        return e;
    }

    public List<Appointment> getAllAppointments()
    {
        Cursor cursor = database.query(BookingDBHandler.TABLE_APPOINTMENTS,allColumns, null,null,null,null,null);

        List<Appointment> appointments = new ArrayList<>();
        if(cursor.getCount() > 0 && cursor !=null)
        {
            while (cursor.moveToNext())
            {
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(cursor.getLong(cursor.getColumnIndex(BookingDBHandler.COLUMN_AID)));
                appointment.setDoctor(cursor.getString(cursor.getColumnIndex(BookingDBHandler.COLUMN_DOCTOR)));
                appointment.setDepartment(cursor.getString(cursor.getColumnIndex(BookingDBHandler.COLUMN_DEPARTMENT)));
                appointment.setDate(cursor.getString(cursor.getColumnIndex(BookingDBHandler.COLUMN_DATE)));
                appointment.setTime(cursor.getString(cursor.getColumnIndex(BookingDBHandler.COLUMN_TIME)));

                appointments.add(appointment);
            }
        }
        return appointments;
    }

    public int updateAppointment(Appointment appointment)
    {
        ContentValues values = new ContentValues();

        values.put(BookingDBHandler.COLUMN_DOCTOR, appointment.getDoctor());
        values.put(BookingDBHandler.COLUMN_DEPARTMENT, appointment.getDepartment());
        values.put(BookingDBHandler.COLUMN_DATE, appointment.getDate());
        values.put(BookingDBHandler.COLUMN_TIME, appointment.getTime());

        return database.update(BookingDBHandler.TABLE_APPOINTMENTS, values, BookingDBHandler.COLUMN_AID + "=?", new String[] {String.valueOf(appointment.getAppointmentID())});
    }

    public void removeAppointment(Appointment appointment)
    {
        database.delete(BookingDBHandler.TABLE_APPOINTMENTS, BookingDBHandler.COLUMN_AID + "=" + appointment.getAppointmentID(), null);
    }


}
