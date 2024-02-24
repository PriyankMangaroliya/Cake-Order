package com.example.cake;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Contact extends AppCompatActivity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        list = findViewById(R.id.contactNumberList);

        ArrayList<String> arContacts = new ArrayList<String>();

        // Define the columns you want to retrieve from the Contacts database
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        // Query the Contacts database
        Cursor curResult = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,projection, null, null, null);

        while (curResult.moveToNext()) {
            int idxName = curResult.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int idxNumber = curResult.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            String name = curResult.getString(idxName);
            String number = curResult.getString(idxNumber);
            arContacts.add(name + " => " + number);
        }

        ArrayAdapter<String> adp1 = new ArrayAdapter<>(Contact.this, android.R.layout.simple_list_item_1, arContacts);
        list.setAdapter(adp1);
    }
}
