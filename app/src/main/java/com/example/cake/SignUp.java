package com.example.cake;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

    Calendar calendar;
    EditText edBirthDate, edFullName, edContactNumber, edEmail, edPassword, edConfirmPassword;
    RadioGroup radioGroupGender;
    TextView clickSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edBirthDate = findViewById(R.id.edbDate);
        calendar = Calendar.getInstance();
        edFullName = findViewById(R.id.edname);
        edEmail = findViewById(R.id.edemail);
        edContactNumber = findViewById(R.id.edcontact);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        edPassword = findViewById(R.id.edPassword);
        edConfirmPassword = findViewById(R.id.edCPassword);

        clickSignIn = findViewById(R.id.clickSignIn);
        clickSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn = new Intent(SignUp.this, SignIn.class);
                startActivity(signIn);
            }
        });
    }


    public void showDatePickerDialog(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Update birthdate field after selecting the date
                updateBirthdateField(calendar);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Restrict the date picker to show only dates 18 years ago and before
        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.YEAR, -18);
        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
        datePickerDialog.show();
    }

    private void updateBirthdateField(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        edBirthDate.setText(dateFormat.format(calendar.getTime()));
    }

    public void Register(View view) {

        String fullName = edFullName.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String contactNumber = edContactNumber.getText().toString().trim();
        String gender = radioGroupGender.getCheckedRadioButtonId() == R.id.rbMale ? "Male" : "Female";
        String birthdate = edBirthDate.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String confirmPassword = edConfirmPassword.getText().toString().trim();

        if (fullName.isEmpty()) {
            edFullName.setError("Full Name is required");
            return;
        }

        if (email.isEmpty()) {
            edEmail.setError("Email is required");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.setError("Invalid email format");
            return;
        }

        if (contactNumber.isEmpty()) {
            edContactNumber.setError("Contact Number is required");
            return;
        }

        if (contactNumber.length() != 10) {
            edContactNumber.setError("Invalid contact number");
            return;
        }

        if (birthdate.isEmpty()) {
            edBirthDate.setError("Birthdate is required");
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

        if (!confirmPassword.equals(password)) {
            edConfirmPassword.setError("Passwords do not match");
            return;
        }

//        Toast.makeText(this, "Error : com.android.volley.TimeoutError", Toast.LENGTH_LONG).show();
        Intent i = new Intent(SignUp.this, SignIn.class);
        i.putExtra("Contact", contactNumber);
        i.putExtra("Password", password);

        i.putExtra("Name", fullName);
        i.putExtra("Email", email);
        i.putExtra("Gender", gender);
        i.putExtra("Birthdate", birthdate);
        startActivity(i);
    }

}