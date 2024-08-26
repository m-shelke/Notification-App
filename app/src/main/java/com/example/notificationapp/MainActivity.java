package com.example.notificationapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.PendingIntentCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNAL_ID = "Notification Id";
    private static final int NOTIFICATION_ID = 100;
    private static final int REQ_CODE = 120;

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
        Bitmap largeIcon = bitmapDrawable.getBitmap();

        //Big picture style
        Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle()
                .bigPicture(((BitmapDrawable) (ResourcesCompat.getDrawable(getResources(),R.drawable.jpg_img,null))).getBitmap())
                .bigLargeIcon(largeIcon)
                .setBigContentTitle("Big Content Title..")
                .setSummaryText("Hello Im the Summary Text of the Drag Notification content. That is.......");

        //Inbox style
        Notification.InboxStyle inboxStyle = new Notification.InboxStyle()
                .addLine("A")
                .addLine("A")
                .addLine("A")
                .addLine("A")
                .addLine("A")
                .addLine("A")
                .addLine("A")
                .addLine("A")
                .addLine("A")
                .setBigContentTitle("InboxStyle Big Content Style")
                .setSummaryText("Inbox Summary Text ");              //Not show in the Notification, its support below the 8 version Of Android OS

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        Intent intent = new Intent(getApplicationContext(), PendingIntentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

      //  PendingIntent pendingIntent = PendingIntent.getActivity(this,REQ_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent pendingIntentCompat = PendingIntentCompat.getActivity(this,REQ_CODE,intent, Intent.FILL_IN_DATA,true);

        Notification notification;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
             notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setContentTitle("New Message")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setSubText("You have got new message from Ankit Path")
                    .setChannelId(CHANNAL_ID)
                    .setContentIntent(pendingIntentCompat)
                    .setStyle(bigPictureStyle)
                   .setOngoing(false)
                     .setAutoCancel(false)
                    .build();

             notificationManager.createNotificationChannel(new NotificationChannel(CHANNAL_ID,"Notification Id",NotificationManager.IMPORTANCE_HIGH));

        }else {
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setContentTitle("New Message")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setSubText("You have got new message from Ankit Path")
                   .setContentIntent(pendingIntentCompat)
                    .setStyle(bigPictureStyle)
                    .setOngoing(false)
                    .setAutoCancel(false)
                    .build();
        }

        notificationManager.notify(NOTIFICATION_ID,notification);
    }
}