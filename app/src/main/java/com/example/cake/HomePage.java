package com.example.cake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends Menu {

    Button Profile, Category, Cake, Order, History;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Profile = findViewById(R.id.btnUserProfile);
        Category = findViewById(R.id.btnCakeCategory);
        Cake = findViewById(R.id.btnCakes);
        Order = findViewById(R.id.btnPlaceOrder);
        History = findViewById(R.id.btnOrderHistory);

        Intent i = getIntent();
        String Contact = i.getStringExtra("Contact");
        String Name = i.getStringExtra("Name");
        String Email = i.getStringExtra("Email");
        String Gender = i.getStringExtra("Gender");
        String Birthdate = i.getStringExtra("Birthdate");

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, UserProfile.class);
                i.putExtra("Name", Name);
                i.putExtra("Email", Email);
                i.putExtra("Contact", Contact);
                i.putExtra("Gender", Gender);
                i.putExtra("Birthdate", Birthdate);
                startActivity(i);
            }
        });

        Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, CakeCategoryInfo.class);
                startActivity(i);
            }
        });

        Cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, CakeInfo.class);
                startActivity(i);
            }
        });

        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, PlaceOrder.class);
                startActivity(i);
            }
        });

        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, OrderHistory.class);
                startActivity(i);
            }
        });
    }
}