package com.totomasterdevw.pushmotivator.mypushmotivator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.totomasterdevw.pushmotivator.mypushmotivator.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout) DrawerLayout drawer_layout;
    @BindView(R.id.content_main) RelativeLayout content_main;
    @BindView(R.id.nav_view) NavigationView navigation_view;
    @BindView(R.id.main_fab_contact) FloatingActionButton main_fab_contact;
    @BindView(R.id.main_fab_catch_phrase) FloatingActionButton main_fab_catch_phrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        main_fab_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ContactActivity.class));
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        navigation_view.setNavigationItemSelectedListener(this);

        content_main.getBackground().setAlpha(80);
        main_fab_catch_phrase.getBackground().setAlpha(100);
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_button_global_settings) {
            // Handle the global settings button
            startActivity(new Intent(MainActivity.this, GeneralSettingsActivity.class));
        } else if (id == R.id.menu_button_notifications_settings) {
            // Handle the notifications settings button
            startActivity(new Intent(MainActivity.this, NotificationsSettingsActivity.class));
        } else if (id == R.id.menu_button_contact) {
            // Handle the contact button
            startActivity(new Intent(MainActivity.this, ContactActivity.class));
        }
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
