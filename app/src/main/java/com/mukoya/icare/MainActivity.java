package com.mukoya.icare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//assigning variable
        drawerLayout = findViewById(R.id.main_menu);

    }

    public void ClickHome (View view) {
//opening drawer
        openDrawer(drawerLayout);

    }

    private static void openDrawer(DrawerLayout drawerLayout) {

        //open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo (View view){
        //close drawer

        closeDrawer (drawerLayout);

    }

    private static void closeDrawer(DrawerLayout drawerLayout) {

        //close drawer
        //check condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){

            //when drawer is open
            //close drawer

            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void ClickHospitals (View view){
        //redirect activity to hospitals
        redirectActivity(this, hospitals.class);


    }

    public void ClickPharmacy (View view){
        //redirect activity
        redirectActivity(this, Pharmacy.class);
    }

    public void ClickAmbulance(View view){
        //redirect activity
        redirectActivity(this, Ambulance.class);
    }

    public void ClickDoctor (View view){
        //redirect activity
        Activity activity;
        redirectActivity(this, Doctor.class);
    }

    public void ClickLogOut (View view){
        //close app
        logout(this);
    }

    private static void logout(final Activity activity) {

        //initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //set title
        builder.setTitle("Logout");
        //set message
        builder.setMessage("Are you sure you want to Logout ?");

        //positive yes button
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish activity
                activity.finishAffinity();
                //exit app
                System.exit(0);
            }
        });

        //negative no button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dismiss dialog
                dialog.dismiss();
            }
        });

        //show dialogue
        builder.show();
    }

    private static void redirectActivity(Activity activity, Class aClass) {
        //initialize intent

        Intent intent = new Intent(activity, aClass);

        //set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //start activity

        activity.startActivity(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        closeDrawer(drawerLayout);
    }
}
