package com.example.cake;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ViewCake extends Menu implements CakeDetailsAdapter.OnItemClickListener {
    private RecyclerView cakeDetailsRecyclerView;
    private CakeDetailsAdapter adapter;
    private List<CakeDetails> cakeDetailsList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cake);

        cakeDetailsRecyclerView = findViewById(R.id.cakeView);
        dbHelper = new DatabaseHelper(this);

        cakeDetailsList = fetchAllCakes();

        adapter = new CakeDetailsAdapter(this, cakeDetailsList, getSupportFragmentManager(), this);
        cakeDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cakeDetailsRecyclerView.setAdapter(adapter);
    }

    private List<CakeDetails> fetchAllCakes() {
        List<CakeDetails> cakes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {"Cake_Id", "Cake_Name", "Category_Id", "Cake_Price", "Cake_Description"};

        Cursor cursor = db.query("Cake", projection, null, null, null, null, null);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("Cake_Id"));
            String cakeName = cursor.getString(cursor.getColumnIndexOrThrow("Cake_Name"));
            String cakeCategory = cursor.getString(cursor.getColumnIndexOrThrow("Category_Id"));
            double cakePrice = cursor.getDouble(cursor.getColumnIndexOrThrow("Cake_Price"));
            String cakeDescription = cursor.getString(cursor.getColumnIndexOrThrow("Cake_Description"));

            cakes.add(new CakeDetails(id, cakeName, cakeCategory, cakePrice, cakeDescription));
        }

        cursor.close();
        db.close();

        return cakes;
    }

    @Override
    public void onItemClick(CakeDetails cakeDetails) {
        // Handle item click here
        // Create a new CakeDetailsFragment and pass the selected cakeDetails to it
        CakeUpdateFragment fragment = CakeUpdateFragment.newInstance(cakeDetails);

        // Start a fragment transaction to replace the current fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null); // Allow back navigation
        transaction.commit();
    }



    public static class CakeDetails implements Parcelable {
        private long id;
        private String cakeName;
        private String cakeCategory;
        private double cakePrice;
        private String cakeDescription;

        public CakeDetails(long id, String cakeName, String cakeCategory, double cakePrice, String cakeDescription) {
            this.id = id;
            this.cakeName = cakeName;
            this.cakeCategory = cakeCategory;
            this.cakePrice = cakePrice;
            this.cakeDescription = cakeDescription;
        }

        public long getId() {
            return id;
        }

        public String getCakeName() {
            return cakeName;
        }

        public String getCakeCategory() {
            return cakeCategory;
        }

        public double getCakePrice() {
            return cakePrice;
        }

        public String getCakeDescription() {
            return cakeDescription;
        }

        protected CakeDetails(Parcel in) {
            id = in.readLong();
            cakeName = in.readString();
            cakeCategory = in.readString();
            cakePrice = in.readLong();
            cakeDescription = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(id);
            dest.writeString(cakeName);
            dest.writeString(cakeCategory);
            dest.writeDouble(cakePrice);
            dest.writeString(cakeDescription);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<CakeDetails> CREATOR = new Creator<CakeDetails>() {
            @Override
            public CakeDetails createFromParcel(Parcel in) {
                return new CakeDetails(in);
            }

            @Override
            public CakeDetails[] newArray(int size) {
                return new CakeDetails[size];
            }
        };
    }
}
