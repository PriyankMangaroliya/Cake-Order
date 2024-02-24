package com.example.cake;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CakeShop.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createCakeCategoryTable = "CREATE TABLE CakeCategory (" +
                "Category_Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Category_Name TEXT," +
                "Description TEXT)";
        db.execSQL(createCakeCategoryTable);


        String createCakeDetailTable = "CREATE TABLE Cake (" +
                "Cake_Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Cake_Name TEXT," +
                "Category_Id INTEGER," +
                "Cake_Price REAL," +
                "Cake_Description TEXT," +
                "FOREIGN KEY (Category_Id) REFERENCES CakeCategory(Category_Id))";
        db.execSQL(createCakeDetailTable);

        String SQL_CREATE_ORDERS_TABLE =
                "CREATE TABLE Orders (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "user_name TEXT NOT NULL, " +
                        "user_address TEXT NOT NULL, " +
                        "user_city TEXT NOT NULL, " +
                        "user_contact TEXT NOT NULL, " +
                        "user_email TEXT NOT NULL, " +
                        "cake_category TEXT NOT NULL, " +
                        "cake_name TEXT NOT NULL, " +
                        "quantity INTEGER NOT NULL, " +
                        "price INTEGER NOT NULL, " +
                        "amount INTEGER NOT NULL);";
        db.execSQL(SQL_CREATE_ORDERS_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS CakeCategory");
        db.execSQL("DROP TABLE IF EXISTS CakeDetail");
        db.execSQL("DROP TABLE IF EXISTS Orders");
        onCreate(db);
    }

}
