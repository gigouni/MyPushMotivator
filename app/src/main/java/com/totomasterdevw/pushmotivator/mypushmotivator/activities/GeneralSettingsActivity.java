package com.totomasterdevw.pushmotivator.mypushmotivator.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.logging.Logger;

public class GeneralSettingsActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static final int RADIO_BUTTON_MALE = 2131558528;
    public static final int RADIO_BUTTON_FEMALE = 2131558529;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Basic content
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_settings);

        // Values in the Shared Preferences
        SharedPreferences preferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String stored_gender = preferences.getString("gender", null);
        String stored_display_name = preferences.getString("display_name", null);

        final Logger logger = Logger.getAnonymousLogger();

        // If existing display name, fetch it in the Activity
        if (stored_display_name != null) {
            logger.info("Display name isn't empty in the SharedPreferences: " + stored_display_name);
            EditText display_name = (EditText) findViewById(R.id.general_settings_display_name_hint_et);
            display_name.setText(stored_display_name);
        }

        // If existing gender, check it in the Activity
        if (stored_gender != null) {
            logger.info("Gender isn't empty in the SharedPreferences: " + stored_gender);
            if( stored_gender.equals("female") ) {
                RadioButton btn = (RadioButton) findViewById(R.id.general_settings_radio_button_female);
                btn.setChecked(true);
            } else {
                RadioButton btn = (RadioButton) findViewById(R.id.general_settings_radio_button_male);
                btn.setChecked(true);
            }
        }

        // Click listener event on CANCEL button
        Button cancel_button = (Button) findViewById(R.id.general_settings_button_cancel);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GeneralSettingsActivity.this, MainActivity.class));
            }
        });

        // Click listener event on SAVE button
        Button save_button = (Button) findViewById(R.id.general_settings_button_save);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText display_name = (EditText) findViewById(R.id.general_settings_display_name_hint_et);
                String display_name_value = display_name.getText().toString();

                RadioGroup genderRadioGroup = (RadioGroup) findViewById(R.id.general_settings_gender_group);
                int selectedRadioButton = genderRadioGroup.getCheckedRadioButtonId();

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("display_name", display_name_value);
                if(selectedRadioButton == RADIO_BUTTON_MALE){
                    editor.putString("gender", "male");
                    logger.info("New gender (male) saved");
                } else if(selectedRadioButton == RADIO_BUTTON_FEMALE){
                    editor.putString("gender", "female");
                    logger.info("New gender (female) saved");
                } else {
                    logger.info("Button radio selected: " + selectedRadioButton);
                }
                editor.apply();

                // Notify and redirect
                Snackbar.make(findViewById(android.R.id.content), R.string.data_saved, Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
    }
}
