package com.example.cake;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends Menu {

    private int loginAttempts = 1;
    TextView clickSignUp;
    Button SignIn;
    EditText edContactNumber, edPassword;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        clickSignUp = findViewById(R.id.clickSignUp);
        SignIn = findViewById(R.id.SignIn);

        clickSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(SignIn.this, SignUp.class);
                startActivity(signUp);
            }
        });

        Intent i = getIntent();
        String Contact = i.getStringExtra("Contact");
        String Password = i.getStringExtra("Password");
        String Name = i.getStringExtra("Name");
        String Email = i.getStringExtra("Email");
        String Gender = i.getStringExtra("Gender");
        String Birthdate = i.getStringExtra("Birthdate");

        edContactNumber = findViewById(R.id.edlContact);
        edPassword = findViewById(R.id.edlPassword);

        edContactNumber.setText(Contact);
        edPassword.setText(Password);

        sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        if (sharedPreferences.contains("Contact") && sharedPreferences.contains("Password")) {
            Intent a = new Intent(SignIn.this, HomePage.class);
            startActivity(a);
            finish();
        }


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contactNumber = edContactNumber.getText().toString();
                String password = edPassword.getText().toString();

                if (contactNumber.isEmpty()) {
                    edContactNumber.setError("Contact Number is required");
                    return;
                }

                if (contactNumber.length() != 10) {
                    edContactNumber.setError("Invalid contact number");
                    return;
                }

                if (password.isEmpty()) {
                    edPassword.setError("Password is required");
                    return;
                }

                if (password.length() < 8) {
                    edPassword.setError("Password must be at least 8 characters long");
                    return;
                }

                if (contactNumber.equals(Contact) && password.equals(Password)) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Name", Name);
                    editor.putString("Email", Email);
                    editor.putString("Gender", Gender);
                    editor.putString("Birthdate", Birthdate);
                    editor.putString("Contact", contactNumber);
                    editor.putString("Password", password);
                    editor.commit();

                    Intent i = new Intent(SignIn.this, HomePage.class);
                    i.putExtra("Name", Name);
                    i.putExtra("Email", Email);
                    i.putExtra("Contact", Contact);
                    i.putExtra("Gender", Gender);
                    i.putExtra("Birthdate", Birthdate);
                    startActivity(i);
                    finish();
                } else {
                    if (loginAttempts >= 3) {
                        Toast.makeText(com.example.cake.SignIn.this, "You have exceeded the maximum limit for login attempts. Kindly contact your administrator!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(com.example.cake.SignIn.this, "Please enter valid data", Toast.LENGTH_SHORT).show();
                    }
                    loginAttempts++;
                }
            }
        });
    }
}