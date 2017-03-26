package com.totomasterdevw.pushmotivator.mypushmotivator.activities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TimePicker;

import com.totomasterdevw.pushmotivator.mypushmotivator.R;
import com.totomasterdevw.pushmotivator.mypushmotivator.services.NotificationPublisher;

import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsSettingsActivity extends AppCompatActivity {

    @BindView(R.id.notifications_scroll_view) ScrollView scroll_view;
    @BindView(R.id.notifications_switch_enable) Switch switch_enabler;
    @BindView(R.id.notifications_button_save) Button button_save;
    @BindView(R.id.notifications_time_picker) TimePicker time_picker;

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private final static Logger logger = Logger.getLogger("NotificationsSettings");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Basic content
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_settings);
        ButterKnife.bind(this);

        // Values in the Shared Preferences
        SharedPreferences preferences = getSharedPreferences(GeneralSettingsActivity.MY_PREFS_NAME, MODE_PRIVATE);
        Boolean stored_allows_notifications = preferences.getBoolean("allows_notifications", false);

        // Auto setting of the switch checked value depending on the stored one
        switch_enabler.setChecked(stored_allows_notifications);
        time_picker.setIs24HourView(true);

        // Disable the content of the scrollView if the user previously
        // disallows the notifications or haven't allow it yet
        if( !stored_allows_notifications ){ scroll_view.setVisibility(View.INVISIBLE); }

        // Switch changing value event
        switch_enabler.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Boolean checked = switch_enabler.isChecked();
                if( checked ){
                    logger.info("We show the content of the ScrollView");
                    scroll_view.setVisibility(View.VISIBLE);
                } else {
                    logger.info("We hide the ScrollView content to avoid useless displayed views");
                    scroll_view.setVisibility(View.INVISIBLE);
                }
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean("allows_notifications", checked);
                editor.apply();
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If we can click on the save button, the notifications are enabled
                // We get the hours of the time picker, the ringtone
                // We create a periodic task in X seconds and every day (for now on)

                // Get the time
                String hour = "" + time_picker.getHour();
                if(hour.length() <= 1){ hour = "0" + hour; }
                String minutes = "" + time_picker.getMinute();
                if(minutes.length() <= 1){ minutes = "0" + minutes; }
                String time = hour + "h" + minutes;

                // Put it it the SharedPreferences
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("hours_notifications", "" + time);
                editor.apply();

                // Create the PeriodicTask
                scheduleNotification(getNotification(), 30000);

                // Notify
                Snackbar.make(findViewById(android.R.id.content), time, Snackbar.LENGTH_SHORT).show();
            }
        });
    }  // -- end of onCreate(...)



    private void scheduleNotification(Notification notification, int delay) {

        logger.info("ScheduleNotification is running...");
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        logger.info("ScheduleNotification: delay equal to " + delay);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        logger.info("ScheduleNotification: futureInMillis equal to " + futureInMillis);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification() {
        logger.info("getNotification is running...");
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_menu_manage)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        return builder.build();
    }
}
