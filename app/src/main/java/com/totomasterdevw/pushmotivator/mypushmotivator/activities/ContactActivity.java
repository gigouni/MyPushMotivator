package com.totomasterdevw.pushmotivator.mypushmotivator.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.logging.Logger;

public class ContactActivity extends AppCompatActivity {

    public static final String WEBSITE_URL = "http://nicolas-gigou.fr";
    public static final String SUBJECT = "[My Push Motivator] Prise de contact";
    public static final String MESSAGE = "Bonjour, je voudrais prendre contact avec toi concernant l'application 'My Push Motivator'. A très vite !";

    private final Logger logger = Logger.getLogger("ContactActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Basic content
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // Click listener event
        Button website_button = (Button) findViewById(R.id.contact_button_website);
        website_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( isNetworkAvailable() ) {
                    logger.info("Internet is available");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse(WEBSITE_URL));
                    startActivity(browserIntent);
                } else {
                    logger.info("Internet is down, sir");
                    Snackbar.make(findViewById(android.R.id.content), R.string.contact_internet_is_disabled, Snackbar.LENGTH_LONG).show();
                }
            }
        });

        // Click listener event
        Button email_button = (Button) findViewById(R.id.contact_button_email);
        email_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( isNetworkAvailable() ) {
                    logger.info("Internet is available");
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, MESSAGE);
                    Intent mailer = Intent.createChooser(emailIntent, null);
                    startActivity(mailer);
                } else {
                    logger.info("Internet is down, sir");
                    Snackbar.make(findViewById(android.R.id.content), R.string.contact_internet_is_disabled, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
