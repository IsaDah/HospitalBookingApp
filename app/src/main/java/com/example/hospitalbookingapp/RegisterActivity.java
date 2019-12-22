package com.example.hospitalbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hospitalbookingapp.Database.RegisterUserDBHelper;
import com.example.hospitalbookingapp.Model.User;

public class RegisterActivity extends AppCompatActivity
{

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText emailEditText;
    private EditText phoneNumberEditText;

    private Button addUserRegisterButton;

    RegisterUserDBHelper registerUserDBHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUserDBHelper = new RegisterUserDBHelper(this);

        firstNameEditText = (EditText)findViewById(R.id.edit_text_first_name);
        lastNameEditText = (EditText)findViewById(R.id.edit_text_last_name);
        passwordEditText = (EditText)findViewById(R.id.edit_text_password_register);
        confirmPasswordEditText = (EditText)findViewById(R.id.edit_text_password_confirm);
        emailEditText = (EditText)findViewById(R.id.edit_text_email);
        phoneNumberEditText = (EditText)findViewById(R.id.edit_text_phone_register);

        addUserRegisterButton = (Button)findViewById(R.id.button_register_add_user);

        addUserRegisterButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(firstNameEditText.getText().toString().isEmpty() || lastNameEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty() || confirmPasswordEditText.getText().toString().isEmpty() || emailEditText.getText().toString().isEmpty() || phoneNumberEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(RegisterActivity.this,"Field or fields can not be empty",Toast.LENGTH_LONG).show();
                }
                else if ((passwordEditText.getText().toString().matches(confirmPasswordEditText.getText().toString())))
                {
                    String firstName = firstNameEditText.getText().toString();
                    String lastName = lastNameEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String phoneNumber = phoneNumberEditText.getText().toString();

                    if(!registerUserDBHelper.isPhoneExists(phoneNumber))
                    {
                        registerUserDBHelper.addUser(new User(null,firstName,lastName,password,email,phoneNumber));
                        Toast.makeText(RegisterActivity.this,"Register Successful",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(RegisterActivity.this, MainMenuActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this,"User Already Exists !",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"Passwords do not match !",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
