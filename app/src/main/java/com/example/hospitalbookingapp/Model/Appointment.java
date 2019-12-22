package com.example.hospitalbookingapp.Model;



public class Appointment
{
    public long appointmentID;

    private String doctor;
    private String department;
    private String date;
    private String time;


    public Appointment(long appointmentID, String doctor, String department, String date, String time)
    {
        this.appointmentID = appointmentID;
        this.doctor = doctor;
        this.department = department;
        this.date = date;
        this.time = time;
    }

    public Appointment()
    {

    }

    public long getAppointmentID()
    {
        return appointmentID;
    }

    public void setAppointmentID(long appointmentID)
    {
        this.appointmentID = appointmentID;
    }

    public String getDoctor()
    {
        return doctor;
    }

    public void setDoctor(String doctor)
    {
        this.doctor = doctor;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String toString()
    {
        return "Appointment ID: "+getAppointmentID() + "\n" +
                "Doctor: " +getDoctor() + "\n" +
                "Department: " + getDepartment() + "\n" +
                "Date: " + getDate() + "\n" +
                "Time: " + getTime();
    }
}
