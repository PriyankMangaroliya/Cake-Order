package com.example.cake;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class OrderHistory extends Menu {

    TextView nameTextView, addressTextView, cityTextView, contactTextView, emailTextView, categoryTextView, cakeTextView, quantityTextView, priceTextView, amountTextView;
    Button btnBill;

    private static final String CHANNEL_ID = "hy";
    private static final int NOTIFICATION_ID = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        nameTextView = findViewById(R.id.nameTextView);
        addressTextView = findViewById(R.id.addressTextView);
        cityTextView = findViewById(R.id.cityTextView);
        contactTextView = findViewById(R.id.contactTextView);
        emailTextView = findViewById(R.id.emailTextView);
        categoryTextView = findViewById(R.id.categoryTextView);
        cakeTextView = findViewById(R.id.cakeTextView);
        quantityTextView = findViewById(R.id.quantityTextView);
        priceTextView = findViewById(R.id.priceTextView);
        amountTextView = findViewById(R.id.amountTextView);

        btnBill = findViewById(R.id.btnBill);

        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String address = i.getStringExtra("address");
        String city = i.getStringExtra("city");
        String contact = i.getStringExtra("contact");
        String email = i.getStringExtra("email");
        String category = i.getStringExtra("categoryName");
        String cake = i.getStringExtra("cakeName");
        String price = i.getStringExtra("price");
        String qty = i.getStringExtra("qty");
        String amount = i.getStringExtra("amount");

        nameTextView.setText(name);
        addressTextView.setText(address);
        cityTextView.setText(city);
        contactTextView.setText(contact);
        emailTextView.setText(email);
        categoryTextView.setText(category);
        cakeTextView.setText(cake);
        quantityTextView.setText(qty);
        priceTextView.setText(price);
        amountTextView.setText(amount);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(OrderHistory.this);
        boolean sms = pref.getBoolean("sms_send", true);
        boolean notification = pref.getBoolean("amount_alert", true);


        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (notification) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CharSequence name = "Notifictions...";
                    String description = "Alert";
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                    channel.setDescription(description);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.createNotificationChannel(channel);
                }

                    Intent i = new Intent(OrderHistory.this, OrderHistory.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    PendingIntent pendingIntent = PendingIntent.getActivity(OrderHistory.this, 0, i, PendingIntent.FLAG_MUTABLE);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(OrderHistory.this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.cakelogo)
                            .setContentTitle("Cake Order")
                            .setContentText("You have collected bill amount from " + name)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Much longer text that cannot fit one line..."))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(OrderHistory.this);
                    if (ActivityCompat.checkSelfPermission(OrderHistory.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    notificationManager.notify(NOTIFICATION_ID, builder.build());
                }else {
                    Toast.makeText(OrderHistory.this, "Not Send Notification", Toast.LENGTH_SHORT).show();
                }


                // SMS Send
                if (sms) {
                    String number = contact;
                    String msg = "Thank you for visiting our Cake shop.\n" +
                            "Have a nice day";
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(number, null, msg, null, null);
                        Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Message Not Sent", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(OrderHistory.this, "Not Send SMS", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}