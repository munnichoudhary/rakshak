package com.iit.rakshak;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewId, textViewUsername, textViewEmail, textViewGender,textViewmobile;
String tab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        System.out.println("inside ProfileActivityold");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tab = extras.getString("tab");
            System.out.println("tabprofile"+tab);
            //The key argument here must match that used in the other activity
        }
        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            //startActivity(new Intent(this, SigninActivity.class));
            Intent i = new Intent(ProfileActivity.this, SigninActivity.class);
            i.putExtra("tab", tab);
            startActivity(i);
        }


        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewGender = (TextView) findViewById(R.id.textViewGender);
        textViewmobile=(TextView) findViewById(R.id.textViewmobile);


        //getting the current user
        Userinfo user = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews
        textViewId.setText(String.valueOf(user.getId()));
        textViewUsername.setText(user.getUsername());
        textViewEmail.setText(user.getEmail());
        textViewmobile.setText(user.getMobileNumber());
        textViewGender.setText(user.getGender());


        //when the user presses logout button
        //calling the logout method
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();


            }
        });
    }
}