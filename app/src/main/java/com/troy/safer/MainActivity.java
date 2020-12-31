package com.troy.safer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test fragment = new test();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fram, fragment);
        transaction.commit();









    }
    //Notification by Firebase

    private void addNotification() {
        // Builds your notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Test")
                .setContentText("TEEEEEEESSSSSSSSST");

        // Creates the intent needed to show the notification
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
    public void openmenu(View view) {
        final ResideMenu  resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.cc);
        resideMenu.attachToActivity(this);
        resideMenu.setScaleValue(0.6f);
        resideMenu.setShadowVisible(false);



        ResideMenuItem itemHome     = new ResideMenuItem(this, R.drawable.ic_home_black_24dp,     "Home");
        ResideMenuItem itemcontact     = new ResideMenuItem(this, R.drawable.ic_perm_contact_calendar_black_24dp,     "Contact");
        ResideMenuItem iteminfo     = new ResideMenuItem(this, R.drawable.ic_info_black_24dp,     "Info");
        ResideMenuItem itemclose  = new ResideMenuItem(this, R.drawable.ic_close_black_24dp,  "Close");




        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemcontact, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(iteminfo, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemclose, ResideMenu.DIRECTION_RIGHT);



       itemHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                test fragment = new test();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fram, fragment);
                transaction.commit();

            }
        });


        itemcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (MainActivity.this, Contactt.class);
                startActivity(intent);

            }
        });

        iteminfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (MainActivity.this, Info.class);
                startActivity(intent);

            }
        });
       itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.closeMenu();

            }
        });
        resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
    }





}
