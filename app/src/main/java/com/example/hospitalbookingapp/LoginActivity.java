package com.example.hospitalbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospitalbookingapp.Database.RegisterUserDBHelper;
import com.example.hospitalbookingapp.Model.User;

public class LoginActivity extends AppCompatActivity
{
    private EditText phoneNumberLoginEditText;
    private EditText passwordLoginEditText;

    private Button LoginUserButton;

    RegisterUserDBHelper registerUserDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerUserDBHelper = new RegisterUserDBHelper(this);

        phoneNumberLoginEditText = (EditText)findViewById(R.id.edit_text_phone_login);
        passwordLoginEditText = (EditText)findViewById(R.id.edit_text_password_login);

        LoginUserButton = (Button)findViewById(R.id.button_login_enter_user);

        LoginUserButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               if(phoneNumberLoginEditText.getText().toString().isEmpty() || passwordLoginEditText.getText().toString().isEmpty())
               {
                   Toast.makeText(LoginActivity.this,"Wrong Login",Toast.LENGTH_LONG).show();
               }
               else
               {
                   String PhoneNum = phoneNumberLoginEditText.getText().toString();
                   String Password = passwordLoginEditText.getText().toString();

                   User currentUser = registerUserDBHelper.Authenticate(new User(null,null,null, Password,null, PhoneNum));

                   if(currentUser != null)
                   {
                       Intent i = new Intent(LoginActivity.this, MainActivity.class);
                       startActivity(i);
                   }
                   else
                   {
                       Toast.makeText(LoginActivity.this,"Wrong Login",Toast.LENGTH_LONG).show();
                   }
               }
            }
        });
    }
}
