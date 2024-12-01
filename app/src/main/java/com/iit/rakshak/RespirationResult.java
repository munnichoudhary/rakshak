package com.iit.rakshak;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class RespirationResult extends AppCompatActivity {

    private String user, Date;
    int RR;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date today = Calendar.getInstance().getTime();
Button done;
    String idstr,namestr,emailstr,mobilestr,genderstr,rpratestr;
    String currentDatestr;
    String currentTimestr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respiration_result);
        done=(Button) findViewById(R.id.done);
        Date = df.format(today);
        TextView RRR = (TextView) this.findViewById(R.id.RRR);
        ImageButton SRR = (ImageButton) this.findViewById(R.id.SendRR);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            RR = bundle.getInt("bpm");
            user = bundle.getString("Usr");
            RRR.setText(String.valueOf(RR));
        }

        SRR.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Health Watcher");
            i.putExtra(Intent.EXTRA_TEXT, user + "'s Respiration Rate " + "\n" + " at " + Date + " is :  " + RR);
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(RespirationResult.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        });

        done.setOnClickListener(v -> {
            Userinfo user = SharedPrefManager.getInstance(this).getUser();
            currentDatestr = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            currentTimestr = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            //setting the values to the textviews
            idstr=String.valueOf(user.getId());
            System.out.println("id"+idstr);
            namestr= user.getUsername();
            System.out.println("name"+namestr);
            emailstr=user.getEmail();
            System.out.println("email"+emailstr);
            mobilestr=user.getMobileNumber();
            System.out.println("mobile"+mobilestr);
            genderstr=user.getGender();
            System.out.println("gender"+genderstr);
            rpratestr= String.valueOf(RR);
            System.out.println("rprate"+rpratestr);
            RRinsert();

        });

    }

    private void RRinsert()
    {
        final  String userid=idstr;
        final String username = namestr;
        final String email = emailstr;
        final String password = mobilestr;
        final String gender = genderstr;
        final String rprate=rpratestr;
        final String currentDate=currentDatestr;
        final String currentTime=currentTimestr;
        //first we will do the validations
        //if it passes all the validations

        class RRinsert extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("userid", userid);
                params.put("username", username);
                params.put("email", email);
                params.put("mobile", password);
                params.put("gender", gender);
                params.put("rprate", rprate);
                params.put("currentDate", currentDate);
                params.put("currentTime", currentTime);



                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_Rprate, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        Userinfo user = new Userinfo(
                                userJson.getInt("id"),
                                userJson.getString("username"),
                                userJson.getString("email"),
                                userJson.getString("mobile"),
                                userJson.getString("gender"),
                                userJson.getString("rprate"),
                                userJson.getString("currentDate"),
                                userJson.getString("currentTime")

                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), Primary.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RRinsert ru = new RRinsert();
        ru.execute();
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(RespirationResult.this, Primary.class);
        i.putExtra("Usr", user);
        startActivity(i);
        finish();
        super.onBackPressed();

    }
}
