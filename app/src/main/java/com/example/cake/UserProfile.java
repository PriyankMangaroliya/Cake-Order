package com.example.cake;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile extends Menu {

    TextView fullNameTextView, emailTextView, contactNumberTextView, genderTextView, birthdateTextView;
    EditText hobbiesEditText, nicknameEditText;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        fullNameTextView = findViewById(R.id.fullNameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        contactNumberTextView = findViewById(R.id.contactNumberTextView);
        genderTextView = findViewById(R.id.genderTextView);
        birthdateTextView = findViewById(R.id.birthdateTextView);
        hobbiesEditText = findViewById(R.id.hobbiesEditText);
        nicknameEditText = findViewById(R.id.nicknameEditText);

        sharedPreferences = getSharedPreferences("LoginData", MODE_PRIVATE);
        fullNameTextView.setText(sharedPreferences.getString("Name", null));
        emailTextView.setText(sharedPreferences.getString("Email", null));
        contactNumberTextView.setText(sharedPreferences.getString("Contact", null));
        genderTextView.setText(sharedPreferences.getString("Gender", null));
        birthdateTextView.setText(sharedPreferences.getString("Birthdate", null));
        hobbiesEditText.setText(sharedPreferences.getString("Hobby", null));
        nicknameEditText.setText(sharedPreferences.getString("Nickname", null));

        Intent intent = getIntent();
        String fullName = intent.getStringExtra("Name");
        String email = intent.getStringExtra("Email");
        String contactNumber = intent.getStringExtra("Contact");
        String gender = intent.getStringExtra("Gender");
        String birthdate = intent.getStringExtra("Birthdate");

//        fullNameTextView.setText(fullName);
//        emailTextView.setText(email);
//        contactNumberTextView.setText(contactNumber);
//        genderTextView.setText(gender);
//        birthdateTextView.setText(birthdate);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hobbies = hobbiesEditText.getText().toString();
                String nickname = nicknameEditText.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Hobby", hobbies);
                editor.putString("Nickname", nickname);
                editor.commit();

                String message = "Hobbies: " + hobbies + "\nNickname: " + nickname;
                Toast.makeText(UserProfile.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}