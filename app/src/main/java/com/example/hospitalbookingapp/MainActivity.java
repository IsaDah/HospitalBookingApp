package com.example.hospitalbookingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hospitalbookingapp.Database.BookingDBHandler;
import com.example.hospitalbookingapp.Database.BookingDBOperations;

public class MainActivity extends AppCompatActivity
{
    private Button GoToAddAppointmentButton;
    private Button deleteAppointmentButton;
    private Button ViewAllAppointmentsButton;

    private BookingDBOperations bookingOps;

    private static final String EXTRA_APPOINTMENT_ID = "com.example.AId";
    private static final String EXTRA_ADD_UPDATE = "com.example.add_update";

    private static final String TAG = "Appointment Exits";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoToAddAppointmentButton = (Button) findViewById(R.id.button_go_to_add_appointment);
        deleteAppointmentButton = (Button) findViewById(R.id.button_remove_appointment);
        ViewAllAppointmentsButton = (Button) findViewById(R.id.button_view_all_appointments);


        deleteAppointmentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getAppointmentIDAndRemoveAppointment();
            }
        });
    }

    public boolean check_existance(String appoint_ID)
    {
        SQLiteOpenHelper db = new BookingDBHandler(this);
        SQLiteDatabase database = db.getWritableDatabase();

        String select = "SELECT * FROM appointments WHERE appointmentID =" + appoint_ID;

        Cursor c = database.rawQuery(select, null);

        if (c.moveToNext())
        {
            Log.d(TAG, "Appointment Exists");
            return true;
        }
        if (c != null)
        {
            c.close();
        }

        database.close();
        return false;
    }

    public void goToAddAppointment(View view)
    {
        GoToAddAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, AddBookingActivity.class);
                i.putExtra(EXTRA_ADD_UPDATE, "Add");
                startActivity(i);
            }
        });
    }


    public void goToViewAllAppointments(View view)
    {
        ViewAllAppointmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, ViewAllAppointments.class);
                startActivity(i);
            }
        });
    }

    public void getAppointmentIDAndUpdateAppointment()
    {
        LayoutInflater li = LayoutInflater.from(this);
        final View getAppointmentIdView = li.inflate(R.layout.dialog_get_appointment_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(getAppointmentIdView);

        final EditText userInput = (EditText) getAppointmentIdView.findViewById(R.id.editTextDialogUserInput);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (userInput.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "User Input is Invalid", Toast.LENGTH_LONG).show();
                } else {
                    if (check_existance(userInput.getText().toString()) == true) {
                        Intent i = new Intent(MainActivity.this, AddBookingActivity.class);
                        i.putExtra(EXTRA_ADD_UPDATE, "Update");
                        i.putExtra(EXTRA_APPOINTMENT_ID, Long.parseLong(userInput.getText().toString()));
                        startActivity(i);
                    } else {
                        Toast.makeText(MainActivity.this, "Input is invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).create().show();
    }

    public void getAppointmentIDAndRemoveAppointment()
    {
        LayoutInflater li = LayoutInflater.from(this);
        final View getAppointmentIdView = li.inflate(R.layout.dialog_get_appointment_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(getAppointmentIdView);

        final EditText userInput = (EditText) getAppointmentIdView.findViewById(R.id.editTextDialogUserInput);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                if (userInput.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "User Input is Invalid", Toast.LENGTH_LONG).show();
                }
                else
                    {
                    if (check_existance(userInput.getText().toString()) == true)
                    {
                        bookingOps.removeAppointment(bookingOps.getAppointment(Long.parseLong(userInput.getText().toString())));
                        Toast.makeText(MainActivity.this, "Appointment has been canceled successfully", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Input is invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).create().show();
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        bookingOps = new BookingDBOperations(MainActivity.this);
        bookingOps.open();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        bookingOps.close();
    }

}