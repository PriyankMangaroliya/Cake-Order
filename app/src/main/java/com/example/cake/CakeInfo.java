package com.example.cake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CakeInfo extends Menu {

    Button btnAddCake, btnViewCake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake);

        btnAddCake = findViewById(R.id.btnAddCake);
        btnViewCake = findViewById(R.id.btnViewCake);

        btnAddCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CakeInfo.this, AddCake.class));
            }
        });

        btnViewCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CakeInfo.this, ViewCake.class));
            }
        });
    }
}