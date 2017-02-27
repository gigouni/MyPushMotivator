package com.totomasterdevw.pushmotivator.mypushmotivator.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.Switch;

import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsSettingsActivity extends AppCompatActivity {

    @BindView(R.id.notifications_scroll_view) ScrollView scroll_view;
    @BindView(R.id.notifications_switch_enable) Switch switch_enabler;

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private final static Logger logger = Logger.getLogger("NotificationsSettings");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Basic content
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_settings);
        ButterKnife.bind(this);

        // Values in the Shared Preferences
        SharedPreferences preferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Boolean stored_allows_notifications = preferences.getBoolean("allows_notifications", false);

        // Auto setting of the switch checked value depending on the stored one
        switch_enabler.setChecked(stored_allows_notifications);

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
    }
}
