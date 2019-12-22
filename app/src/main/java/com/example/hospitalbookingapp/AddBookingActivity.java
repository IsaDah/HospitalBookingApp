package com.example.hospitalbookingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospitalbookingapp.Database.BookingDBOperations;
import com.example.hospitalbookingapp.Model.Appointment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddBookingActivity extends AppCompatActivity
{
    private static final String EXTRA_APPOINTMENT_ID = "com.example.AId";
    private static final String EXTRA_ADD_UPDATE = "com.example.add_update";
    private static final String DIALOG_DATE = "DialogDate";

    private ImageView calendarImage;

    private EditText AppointmentDate;

    private EditText DepartmentsEditText;
    private EditText DoctorsEditText;
    private EditText TimesEditText;


    private Appointment newAppointment;
    private Appointment oldAppointment;
    private String mode;
    private long appId;
    private BookingDBOperations bookingData;
    private Button addUpdateButton;

    final Calendar appointmentCalendar = Calendar.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);



        newAppointment = new Appointment();
        oldAppointment = new Appointment();
        calendarImage = (ImageView)findViewById(R.id.image_view_date);
        addUpdateButton = (Button)findViewById(R.id.button_add_appointment);
        AppointmentDate = (EditText)findViewById(R.id.edit_text_appointment_date);
        DepartmentsEditText = (EditText)findViewById(R.id.edit_text_departments);
        DoctorsEditText = (EditText)findViewById(R.id.edit_text_doctors);
        TimesEditText = (EditText)findViewById(R.id.edit_text_times);

        bookingData = new BookingDBOperations(this);
        bookingData.open();


        ArrayAdapter<CharSequence> adapterDepartments = ArrayAdapter.createFromResource(this, R.array.departments, R.layout.spinner_item);
        adapterDepartments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner departments = (Spinner) findViewById(R.id.spinner_departments);
        departments.setAdapter(adapterDepartments);
        departments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
               String departmentsText = parent.getItemAtPosition(position).toString();
               DepartmentsEditText.setText(departmentsText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });



        ArrayAdapter<CharSequence> adapterDoctors = ArrayAdapter.createFromResource(this, R.array.doctors, R.layout.spinner_item);
        adapterDoctors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner doctors = (Spinner) findViewById(R.id.spinner_doctors);
        doctors.setAdapter(adapterDoctors);
        doctors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String doctorsText = parent.getItemAtPosition(position).toString();
                DoctorsEditText.setText(doctorsText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });


        ArrayAdapter<CharSequence> adapterTimes = ArrayAdapter.createFromResource(this, R.array.times, R.layout.spinner_item);
        adapterTimes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner times = (Spinner) findViewById(R.id.spinner_times);
        times.setAdapter(adapterTimes);
        times.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String timesText = parent.getItemAtPosition(position).toString();
                TimesEditText.setText(timesText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });



        mode = getIntent().getStringExtra(EXTRA_ADD_UPDATE);
        if(mode.equals("Update"))
        {
            addUpdateButton.setText("Update Appointment");
            appId = getIntent().getLongExtra(EXTRA_APPOINTMENT_ID,0);
            initializeAppointment(appId);
        }

        addUpdateButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(mode.equals("Add"))
                {
                    newAppointment.setDepartment(DepartmentsEditText.getText().toString());
                    newAppointment.setDoctor(DoctorsEditText.getText().toString());
                    newAppointment.setDate(AppointmentDate.getText().toString());
                    newAppointment.setTime(TimesEditText.getText().toString());

                    if(AppointmentDate.getText().toString().isEmpty())
                    {
                        Toast.makeText(AddBookingActivity.this,"Select a date please",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        bookingData.addAppointment(newAppointment);
                        Toast.makeText(AddBookingActivity.this,"Appointment succssful",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(AddBookingActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                }

                else
                {
                    oldAppointment.setDepartment(DepartmentsEditText.getText().toString());
                    oldAppointment.setDoctor(DoctorsEditText.getText().toString());
                    oldAppointment.setDate(AppointmentDate.getText().toString());
                    oldAppointment.setTime(TimesEditText.getText().toString());

                    if(AppointmentDate.getText().toString().isEmpty())
                    {
                        Toast.makeText(AddBookingActivity.this,"Select a date please",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        bookingData.updateAppointment(oldAppointment);
                        Toast.makeText(AddBookingActivity.this,"Select a date please",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(AddBookingActivity.this,MainActivity.class);
                        startActivity(i);
                    }

                }
            }
        });



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
        {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                // TODO Auto-generated method stub
                appointmentCalendar.set(Calendar.YEAR, year);
                appointmentCalendar.set(Calendar.MONTH, monthOfYear);
                appointmentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        calendarImage.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new DatePickerDialog(AddBookingActivity.this, date, appointmentCalendar.get(Calendar.YEAR), appointmentCalendar.get(Calendar.MONTH),appointmentCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel()
    {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        AppointmentDate.setText(sdf.format(appointmentCalendar.getTime()));
    }

    private void initializeAppointment(long appId)
    {
        oldAppointment = bookingData.getAppointment(appId);

        DepartmentsEditText.setText(oldAppointment.getDepartment());
        DoctorsEditText.setText(oldAppointment.getDoctor());
        AppointmentDate.setText(oldAppointment.getDate());
        TimesEditText.setText(oldAppointment.getTime());
    }

}

