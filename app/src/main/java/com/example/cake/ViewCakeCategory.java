package com.example.cake;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewCakeCategory extends AppCompatActivity {

    private List<CakeCategory> cakeCategories;
    private RecyclerView recyclerView;
    private CakeCategoryAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cake_category);

//        categoryNameTextView = findViewById(R.id.categoryNameTextView);
//        categoryDescriptionTextView = findViewById(R.id.categoryDescriptionTextView);

        recyclerView = findViewById(R.id.categoryView);

        dbHelper = new DatabaseHelper(this);
        cakeCategories = getAllCakeCategories();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CakeCategoryAdapter(cakeCategories);
        recyclerView.setAdapter(adapter);
    }


    public List<CakeCategory> getAllCakeCategories() {
        List<CakeCategory> cakeCategories = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM CakeCategory";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("Category_Id"));
                String categoryName = cursor.getString(cursor.getColumnIndexOrThrow("Category_Name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("Description"));

                CakeCategory cakeCategory = new CakeCategory(categoryId, categoryName, description);
                cakeCategories.add(cakeCategory);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return cakeCategories;
    }
}