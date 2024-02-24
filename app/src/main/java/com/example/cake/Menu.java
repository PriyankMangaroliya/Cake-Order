package com.example.cake;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_dashboard) {
            Intent i = new Intent(this, HomePage.class);
            startActivity(i);
            return true;
        } else if (id == R.id.menu_user_profile) {
            Intent i = new Intent(this, UserProfile.class);
            startActivity(i);
            return true;
        } else if (id == R.id.menu_cake_info) {
            Intent i = new Intent(this, CakeFragments.class);
            startActivity(i);
            return true;
        } else if (id == R.id.menu_addCategory) {
            Intent i = new Intent(this, AddCakeCategory.class);
            startActivity(i);
            return true;
        } else if (id == R.id.menu_viewCategory) {
            Intent i = new Intent(this, ViewCakeCategory.class);
            startActivity(i);
            return true;
        } else if (id == R.id.menu_view_customers) {
            Intent i = new Intent(this, Contact.class);
            startActivity(i);
            return true;
        } else if (id == R.id.menu_reports) {
            return true;
        } else if (id == R.id.menu_settings) {
            Intent j = new Intent(getApplicationContext(), Setting.class);
            startActivity(j);
            return true;
        } else if (id == R.id.menu_logout) {
            if (sharedPreferences == null) {
                sharedPreferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);
            }
            Logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);

        builder.setTitle("Logout");
        builder.setMessage("Are you sure want to exit?");
        builder.setIcon(R.drawable.cakelogo);

        builder.setCancelable(false);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Intent l = new Intent(Menu.this, SignIn.class);
                startActivity(l);
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
