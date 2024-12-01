package com.iit.rakshak;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Primary extends AppCompatActivity {

    private String user;
    private int p;
    String tab;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rakshakprimaryiit);
        extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("Usr");
            tab = extras.getString("tab");
            System.out.println("tabprimary"+tab);
            //The key argument here must match that used in the other activity
        }
        LinearLayout HeartRate = this.findViewById(R.id.HR);
        LinearLayout BloodPressure = this.findViewById(R.id.BP);
        LinearLayout Ox2 = this.findViewById(R.id.O2);
        LinearLayout RRate = this.findViewById(R.id.RR);
        LinearLayout VitalSigns = this.findViewById(R.id.VS);
        LinearLayout Abt = this.findViewById(R.id.profile);



        Abt.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), ProfileActivity.class);
            i.putExtra("tab", tab);
            startActivity(i);
            finish();
        });


        //Every Test Button sends the username + the test number, to go to the wanted test after the instructions activity
        HeartRate.setOnClickListener(v -> {
            p = 1;
            Intent i = new Intent(v.getContext(), StartVitalSigns.class);
            i.putExtra("Usr", user);
            i.putExtra("Page", p);
            i.putExtra("tab", tab);
            startActivity(i);
            finish();
        });

        BloodPressure.setOnClickListener(v -> {
            p = 2;
            Intent i = new Intent(v.getContext(), StartVitalSigns.class);
            i.putExtra("Usr", user);
            i.putExtra("Page", p);
            i.putExtra("tab", tab);
            startActivity(i);
            finish();
        });

        RRate.setOnClickListener(v -> {
            p = 3;
            Intent i = new Intent(v.getContext(), StartVitalSigns.class);
            i.putExtra("Usr", user);
            i.putExtra("Page", p);
            i.putExtra("tab", tab);
            startActivity(i);
            finish();
        });

        Ox2.setOnClickListener(v -> {
            p = 4;
            Intent i = new Intent(v.getContext(), StartVitalSigns.class);
            i.putExtra("Usr", user);
            i.putExtra("Page", p);
            i.putExtra("tab", tab);
            startActivity(i);
            finish();

        });

        VitalSigns.setOnClickListener(v -> {
            p = 5;
            Intent i = new Intent(v.getContext(), StartVitalSigns.class);
            i.putExtra("Usr", user);
            i.putExtra("Page", p);
            i.putExtra("tab", tab);
            startActivity(i);
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (arg0, arg1) -> {

                    Primary.super.onBackPressed();
                    finish();
                    System.exit(0);
                }).create().show();
    }


}

