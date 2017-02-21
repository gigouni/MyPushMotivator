package com.totomasterdevw.pushmotivator.mypushmotivator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ContactActivity extends AppCompatActivity {

    public static final String WEBSITE_URL = "http://nicolas-gigou.fr";
    public static final String EMAIL_ADDRESS = "totomaster.dev@gmail.com";
    public static final String SUBJECT = "[My Push Motivator] Prise de contact";
    public static final String MESSAGE = "Bonjour, je voudrais prendre contact avec toi concernant l'application 'My Push Motivator'. A très vite !";

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
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(WEBSITE_URL));
                startActivity(browserIntent);
            }
        });

        // Click listener event
        Button email_button = (Button) findViewById(R.id.contact_button_email);
        email_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
                emailIntent.putExtra(Intent.EXTRA_TEXT, MESSAGE);
                Intent mailer = Intent.createChooser(emailIntent, null);
                startActivity(mailer);
            }
        });
    }
}
