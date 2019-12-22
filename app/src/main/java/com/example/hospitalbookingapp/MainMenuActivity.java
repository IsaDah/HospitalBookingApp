package com.example.hospitalbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity
{

    private Button RegisterActivityButton;
    private Button LoginActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        RegisterActivityButton = (Button)findViewById(R.id.button_register);
        LoginActivityButton = (Button)findViewById(R.id.button_login);

    }

    public void goToRegister(View view)
    {
        RegisterActivityButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i  = new Intent(MainMenuActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    public void goToLogin(View view)
    {
        LoginActivityButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i  = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
