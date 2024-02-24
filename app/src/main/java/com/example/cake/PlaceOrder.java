package com.example.cake;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrder extends Menu {

    EditText name, address, city, contact, email, qty;
    Spinner cake;
    TextView price, category, totalprice;
    Button placeorder;
    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        name = findViewById(R.id.etName);
        address = findViewById(R.id.etAddress);
        city = findViewById(R.id.etCity);
        contact = findViewById(R.id.etContact);
        email = findViewById(R.id.etEmail);

        cake = findViewById(R.id.etCakeName);

        category = findViewById(R.id.etCategoryName);
        qty = findViewById(R.id.etQty);
        price = findViewById(R.id.etPrice);

        dbHelper = new DatabaseHelper(this);

        totalprice = findViewById(R.id.totalprice);

        placeorder = findViewById(R.id.btnOrder);

        List<String> allCakes = getAllCakes();
        ArrayAdapter<String> cakeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allCakes);
        cakeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cake.setAdapter(cakeAdapter);

        // Set an item selected listener for the cake spinner
        cake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCake = parent.getItemAtPosition(position).toString();
                CakeInfo cakeInfo = getCakeInfo(selectedCake);

                category.setText(cakeInfo.category);
                price.setText(String.valueOf(cakeInfo.price));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateTotalAmount();
            }
        });

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = name.getText().toString();
                String uaddress = address.getText().toString();
                String ucity = city.getText().toString();
                String ucontact = contact.getText().toString();
                String uemail = email.getText().toString();
                String cakeName = cake.getSelectedItem().toString();

                String ccategory = category.getText().toString();

                calculateTotalAmount();

                int cqty = Integer.parseInt(qty.getText().toString());
                int cprice = Integer.parseInt(price.getText().toString());
                int amount = cqty * cprice;

                Intent i = new Intent(PlaceOrder.this, OrderHistory.class);
                i.putExtra("name", uname);
                i.putExtra("address", uaddress);
                i.putExtra("city", ucity);
                i.putExtra("contact", ucontact);
                i.putExtra("email", uemail);
                i.putExtra("cakeName", cakeName);
                i.putExtra("categoryName", ccategory);
                i.putExtra("qty", String.valueOf(cqty));
                i.putExtra("price", String.valueOf(cprice));
                i.putExtra("amount", String.valueOf(amount));
                startActivity(i);
                finish();

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("user_name", uname);
                values.put("user_address", uaddress);
                values.put("user_city", ucity);
                values.put("user_contact", ucontact);
                values.put("user_email", uemail);
                values.put("cake_category", ccategory);
                values.put("cake_name", cakeName);
                values.put("quantity", cqty);
                values.put("price", cprice);
                values.put("amount", amount);

                long newRowId = db.insert("Orders", null, values);
                db.close();

                if (newRowId != -1) {
                    Toast.makeText(PlaceOrder.this, "Order added!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PlaceOrder.this, "Not Cake Order", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void calculateTotalAmount() {
        String quantityStr = qty.getText().toString();
        String cakePriceStr = price.getText().toString();

        if (!quantityStr.isEmpty() && !cakePriceStr.isEmpty()) {
            int quantity = Integer.parseInt(quantityStr);
            int cakePrice = Integer.parseInt(cakePriceStr);
            int amount = quantity * cakePrice;
            totalprice.setText("Total Amount ₹ " + amount);
        }
    }

    private List<String> getAllCakes() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<String> cakes = new ArrayList<>();

        String[] projection = {"Cake_Name"};

        Cursor cursor = db.query("Cake",projection,null,null,null,null,null);

        while (cursor.moveToNext()) {
            String cakeName = cursor.getString(cursor.getColumnIndexOrThrow("Cake_Name"));
            cakes.add(cakeName);
        }
        cursor.close();
        db.close();
        return cakes;
    }

    private CakeInfo getCakeInfo(String cakeName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String category = "";
        int price = 0;

        String[] projection = {"Category_Id","Cake_Price"};
        String selection = "Cake_Name" + " = ?";
        String[] selectionArgs = {cakeName};

        Cursor cursor = db.query("Cake",projection,selection,selectionArgs,null,null,null);

        if (cursor.moveToFirst()) {
            category = cursor.getString(cursor.getColumnIndexOrThrow("Category_Id"));
            price = cursor.getInt(cursor.getColumnIndexOrThrow("Cake_Price"));
        }
        cursor.close();
        db.close();
        return new CakeInfo(category, price);
    }



    public static class CakeInfo {
        public String category;
        public int price;

        public CakeInfo(String category, int price) {
            this.category = category;
            this.price = price;
        }
    }
}