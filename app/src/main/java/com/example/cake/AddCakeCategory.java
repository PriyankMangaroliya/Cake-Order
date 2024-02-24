package com.example.cake;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddCakeCategory extends AppCompatActivity {

    EditText etCategoryName, etCategoryDescription;
    Button btnSaveCategory;
    TextView categoryNameTextView1, categoryDescTextView1;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cake_category);

        etCategoryName = findViewById(R.id.etCategoryName);
        etCategoryDescription = findViewById(R.id.etCategoryDescription);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);
        categoryNameTextView1 = findViewById(R.id.categoryNameTextView1);
        categoryDescTextView1 = findViewById(R.id.categoryDescTextView1);

        dbHelper = new DatabaseHelper(this);

        btnSaveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = etCategoryName.getText().toString();
                String categoryDescription = etCategoryDescription.getText().toString();

//                Intent i = new Intent(AddCakeCategory.this, ViewCakeCategory.class);
//                i.putExtra("categoryName", categoryName);
//                i.putExtra("categoryDescription", categoryDescription);
//                startActivity(i);

                //Sqlite
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("Category_Name", categoryName);
                values.put("Description", categoryDescription);
                long newRowId = db.insert("CakeCategory", null, values);

                if (newRowId != -1) {
                    Toast.makeText(AddCakeCategory.this, "Cake Category Add!", Toast.LENGTH_SHORT).show();
                }

                // Write Data Internal
                saveCategory(AddCakeCategory.this, categoryName, categoryDescription);

                // Read Data Internal
                String[] categorys = getCategory(AddCakeCategory.this);
                if (categorys[0] != null && categorys[1] != null) {
                    categoryNameTextView1.setText("Category Name : " + categorys[0]);
                    categoryDescTextView1.setText("Category Description : " + categorys[1]);
                }
            }
        });
    }

    public void saveCategory(Context context, String categoryName, String categoryDescription) {
        String filename = "categoty.txt";
        try {
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            String data = categoryName + "\n" + categoryDescription;
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getCategory(Context context) {
        String[] categorys = new String[2];
        String filename = "categoty.txt";

        try {
            FileInputStream inputStream = context.openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            categorys[0] = bufferedReader.readLine();
            categorys[1] = bufferedReader.readLine();

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categorys;
    }
}
