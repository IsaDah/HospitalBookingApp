package com.example.hospitalbookingapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.hospitalbookingapp.Database.BookingDBOperations;
import com.example.hospitalbookingapp.Model.Appointment;

import java.util.List;

public class ViewAllAppointments extends ListActivity
{
    private BookingDBOperations bookingOps;
    List<Appointment> appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_appointments);

        bookingOps = new BookingDBOperations(this);
        bookingOps.open();
        appointments = bookingOps.getAllAppointments();
        bookingOps.close();

        ArrayAdapter<Appointment> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,appointments);
        setListAdapter(adapter);
    }
}
