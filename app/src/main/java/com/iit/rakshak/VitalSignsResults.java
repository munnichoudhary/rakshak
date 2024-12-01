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

public class VitalSignsResults extends AppCompatActivity {

    private String user, Date;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date today = Calendar.getInstance().getTime();
    int VBP1, VBP2, VRR, VHR, VO2;
    Button done;
    String currentDatestr;
    String currentTimestr;

    String idstr,namestr,emailstr,mobilestr,genderstr,heartratestr,rpratestr,bpstr,osaturationstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_signs_results);
        done=(Button) findViewById(R.id.done);
        Date = df.format(today);
        TextView VSRR = this.findViewById(R.id.RRV);
        TextView VSBPS = this.findViewById(R.id.BP2V);
        TextView VSHR = this.findViewById(R.id.HRV);
        TextView VSO2 = this.findViewById(R.id.O2V);
        ImageButton All = this.findViewById(R.id.SendAll);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            VRR = bundle.getInt("breath");
            VHR = bundle.getInt("bpm");
            VBP1 = bundle.getInt("SP");
            VBP2 = bundle.getInt("DP");
            VO2 = bundle.getInt("O2R");
            user = bundle.getString("Usr");
            VSRR.setText(String.valueOf(VRR));
            VSHR.setText(String.valueOf(VHR));
            VSBPS.setText(VBP1 + " / " + VBP2);
            VSO2.setText(String.valueOf(VO2));
        }

        All.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Health Watcher");
            i.putExtra(Intent.EXTRA_TEXT, user + "'s new measuerment " + "\n" + " at " + Date + " are :" + "\n" + "Heart Rate = " + VHR + "\n" + "Blood Pressure = " + VBP1 + " / " + VBP2 + "\n" + "Respiration Rate = " + VRR + "\n" + "Oxygen Saturation = " + VO2);
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(VitalSignsResults.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        });
        done.setOnClickListener(v -> {
            Userinfo user = SharedPrefManager.getInstance(this).getUser();
            currentDatestr = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            currentTimestr = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            System.out.println("currentDate"+currentDatestr);
            System.out.println("currentTime"+currentTimestr);
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
            heartratestr= String.valueOf(VHR);
            System.out.println("heartrate"+heartratestr);

           rpratestr= String.valueOf(VRR);
            System.out.println("rpratestr"+rpratestr);


            bpstr=VBP1 + "/" + VBP2;
            System.out.println("bpstr"+bpstr);


            osaturationstr= String.valueOf(VO2);
            System.out.println("osaturationstr"+osaturationstr);
            allvsigninsert();

        });


    }

    private void allvsigninsert()
    {
        final  String userid=idstr;
        final String username = namestr;
        final String email = emailstr;
        final String mobile = mobilestr;
        final String gender = genderstr;
        final String heartrate=heartratestr;
        final String rprate=rpratestr;
        final String bp=bpstr;
        final String osaturation=osaturationstr;
        final String currentDate=currentDatestr;
        final String currentTime=currentTimestr;
        //first we will do the validations
        //if it passes all the validations

        class vsigninsert extends AsyncTask<Void, Void, String> {

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
                params.put("mobile", mobile);
                params.put("gender", gender);
                params.put("heartrate", heartrate);
                params.put("rprate", rprate);
                params.put("bp", bp);
                params.put("osaturation", osaturation);
                params.put("currentDate", currentDate);
                params.put("currentTime", currentTime);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_Vsign, params);
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
                                userJson.getString("heartrate"),
                                userJson.getString("rprate"),
                                userJson.getString("bp"),
                                userJson.getString("osaturation"),
                                userJson.getString("currentDate"),
                                userJson.getString("currentTime")

                        );


                        System.out.println("user"+user);
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
        vsigninsert ru = new vsigninsert();
        ru.execute();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(VitalSignsResults.this, Primary.class);
        i.putExtra("Usr", user);
        startActivity(i);
        finish();
    }
}
