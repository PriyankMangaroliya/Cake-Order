package com.example.cake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CakeCategoryInfo extends Menu {

    Button btnAddCakeCategory, btnViewCakeCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake_category);

        btnAddCakeCategory = findViewById(R.id.btnAddCakeCategory);
        btnViewCakeCategory = findViewById(R.id.btnViewCakeCategory);

        btnAddCakeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CakeCategoryInfo.this, AddCakeCategory.class));
            }
        });

        btnViewCakeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CakeCategoryInfo.this, ViewCakeCategory.class));
            }
        });
    }

}