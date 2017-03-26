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

import com.totomasterdevw.pushmotivator.mypushmotivator.R;
import com.totomasterdevw.pushmotivator.mypushmotivator.utils.EscapeChars;

import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralSettingsActivity extends AppCompatActivity {

    @BindView(R.id.general_settings_display_name_hint_et) EditText display_name;
    @BindView(R.id.general_settings_gender_group) RadioGroup genderRadioGroup;
    @BindView(R.id.general_settings_radio_button_male) RadioButton radio_button_male;
    @BindView(R.id.general_settings_radio_button_female) RadioButton radio_button_female;
    @BindView(R.id.general_settings_button_save) Button save_button;
    @BindView(R.id.general_settings_button_cancel) Button cancel_button;

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private static final Logger logger = Logger.getLogger("GeneralSettings");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Basic content
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_settings);
        ButterKnife.bind(this);

        // Values in the Shared Preferences
        SharedPreferences preferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String stored_gender = preferences.getString("gender", null);
        String stored_display_name = preferences.getString("display_name", null);

        // If existing display name, fetch it in the Activity
        if (stored_display_name != null) {
            logger.info("Display name isn't empty in the SharedPreferences: " + stored_display_name);
            display_name.setText(stored_display_name);
        }

        // If existing gender, check it in the Activity
        if (stored_gender != null) {
            logger.info("Gender isn't empty in the SharedPreferences: " + stored_gender);
            if( stored_gender.equals("female") ) {
                radio_button_female.setChecked(true);
            } else {
                radio_button_male.setChecked(true);
            }
        }

        // Click listener event on CANCEL button
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GeneralSettingsActivity.this, MainActivity.class));
            }
        });

        // Click listener event on SAVE button
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get display name value
                String display_name_value = EscapeChars.forHTML(display_name.getText().toString());
                logger.info("New display name (" + display_name_value + ") saved");

                // Get the gender value within the RadioGroup
                int selectedRadioButton = genderRadioGroup.getCheckedRadioButtonId();
                RadioButton selected_radio_button = (RadioButton) findViewById(selectedRadioButton);
                Object radio_button_tag = selected_radio_button.getTag();
                logger.info("New gender (" + radio_button_tag + ") saved");

                // Put it it the SharedPreferences
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("display_name", display_name_value);
                editor.putString("gender", "" + radio_button_tag);
                editor.apply();

                // Notify
                Snackbar.make(findViewById(android.R.id.content), R.string.data_saved, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
