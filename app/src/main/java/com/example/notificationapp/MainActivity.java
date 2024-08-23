package com.example.notificationapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNAL_ID = "Notification Id";
    private static final int NOTIFICATION_ID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.message,null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        assert bitmapDrawable != null;
        Bitmap largeIcon = bitmapDrawable.getBitmap();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        Notification notification;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
             notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setContentTitle("New Message")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setSubText("You have got new message from Ankit Path")
                    .setChannelId(CHANNAL_ID)
                    .build();

             notificationManager.createNotificationChannel(new NotificationChannel(CHANNAL_ID,"Notification Id",NotificationManager.IMPORTANCE_HIGH));

        }else {
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setContentTitle("New Message")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setSubText("You have got new message from Ankit Path")
                    .build();
        }

        notificationManager.notify(NOTIFICATION_ID,notification);
    }
}