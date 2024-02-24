package com.example.cake;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AddCake extends AppCompatActivity {

    EditText etCakeName, etPrice, etCakeDescription;
    Button btnSaveCake;
    Spinner etCategoryName;
    TextView cakeNameTextView, categoryNameTextView, priceTextView, cakeDescTextView;
    DatabaseHelper dbHelper;
    List<CakeCategory> cakeCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cake);

        etCakeName = findViewById(R.id.etCakeName);
        etCategoryName = findViewById(R.id.etCategoryName);
        etPrice = findViewById(R.id.etPrice);
        etCakeDescription = findViewById(R.id.etCakeDescription);
        btnSaveCake = findViewById(R.id.btnSaveCake);

        cakeNameTextView = findViewById(R.id.cakeNameTextView1);
        categoryNameTextView = findViewById(R.id.categoryNameTextView1);
        priceTextView = findViewById(R.id.priceTextView1);
        cakeDescTextView = findViewById(R.id.cakeDescTextView1);

        dbHelper = new DatabaseHelper(this);

        cakeCategories = getAllCakeCategories();
        List<String> categoryNames = new ArrayList<>();
        for (CakeCategory category : cakeCategories) {
            categoryNames.add(category.getCategoryName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etCategoryName.setAdapter(adapter);

        btnSaveCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cakeName = etCakeName.getText().toString();
                String categoryName = etCategoryName.getSelectedItem().toString();
                String price = etPrice.getText().toString();
                String cakedec = etCakeDescription.getText().toString();

//                Intent i = new Intent(AddCake.this, ViewCake.class);
//                i.putExtra("cakeName", cakeName);
//                i.putExtra("categoryName", categoryName);
//                i.putExtra("price", price);
//                i.putExtra("cakedes", cakedec);
//                startActivity(i);

                //Sqlite
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("Cake_Name", cakeName);
                values.put("Category_Id", categoryName);
                values.put("Cake_Price", price);
                values.put("Cake_Description", cakedec);
                long newRowId = db.insert("Cake", null, values);

                if (newRowId != -1) {
                    Toast.makeText(AddCake.this, "Cake Add!", Toast.LENGTH_SHORT).show();
                }

                // Write data External
                saveCredentials(AddCake.this, cakeName, categoryName, price, cakedec);

                // Read data External
                String[] credentials = getSavedCredentials(AddCake.this);
                cakeNameTextView.setText("Cake Name : " + credentials[0]);
                categoryNameTextView.setText("Category Name : " + credentials[1]);
                priceTextView.setText("Cake Price : " + credentials[2]);
                cakeDescTextView.setText("Cake Description : " + credentials[3]);
            }
        });
    }

    private List<CakeCategory> getAllCakeCategories() {
        List<CakeCategory> cakeCategories = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"Category_Id", "Category_Name"};

        Cursor cursor = db.query("CakeCategory", projection, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("Category_Id"));
            String categoryName = cursor.getString(cursor.getColumnIndexOrThrow("Category_Name"));
            cakeCategories.add(new CakeCategory(categoryId, categoryName));
        }

        cursor.close();
        db.close();

        return cakeCategories;
    }

    public void saveCredentials(Context context, String cakeName, String categoryName, String price, String cakedec) {
        String filename = "cake.txt";

        try {
            File externalFile = new File(context.getExternalFilesDir(null), filename);
            FileOutputStream outputStream = new FileOutputStream(externalFile);
            String data = cakeName + "\n" + categoryName + "\n" + price + "\n" + cakedec;
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getSavedCredentials(Context context) {
        String[] credentials = new String[4];
        String filename = "cake.txt";

        try {
            File externalFile = new File(context.getExternalFilesDir(null), filename);
            FileInputStream inputStream = new FileInputStream(externalFile);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            credentials[0] = bufferedReader.readLine();
            credentials[1] = bufferedReader.readLine();
            credentials[2] = bufferedReader.readLine();
            credentials[3] = bufferedReader.readLine();

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return credentials;
    }
}