package com.iit.rakshak;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SigninActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
String tab;
    private Toast mainToast;
    String []checkmobileStr;
    public static String mobileStr, usrStr, usrStrlow;
    UserDB check = new UserDB(this);
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        extras = getIntent().getExtras();
        tab = extras.getString("tab");
        System.out.println("tabsignin"+tab);
        System.out.println("inside SigninActivity");
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);


        //if user presses on login
        //calling the method login
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if (extras != null)
                {

                    if (tab.equals("online"))
                    {
                        userLoginonline();
                    }
                    else
                    {
                        userloginoffline();
                    }
                }
            }
        });

        //if user presses on not registered
        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                //startActivity(new Intent(getApplicationContext(), RegisterActiivity.class));

                System.out.println("tabsss"+tab);
               Intent i = new Intent(SigninActivity.this, RegisterActiivity.class);
                i.putExtra("tab", tab);
                startActivity(i);
            }
        });
    }

    private void userloginoffline()
    {
        usrStrlow = editTextUsername.getText().toString();
        mobileStr = editTextPassword.getText().toString();
        usrStr = usrStrlow.toLowerCase();
        if (TextUtils.isEmpty(usrStr)) {
            editTextUsername.setError("Please enter your username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mobileStr)) {
            editTextPassword.setError("Please enter your mobile number");
            editTextPassword.requestFocus();
            return;
        }

        checkmobileStr = check.checkMobile(usrStr);

        System.out.println("munni checkmobileStr 1"+checkmobileStr[0]);
        System.out.println("munni checkmobileStr 2"+checkmobileStr[1]);
        System.out.println("munni checkmobileStr 3"+checkmobileStr[2]);
        System.out.println("munni checkmobileStr 4"+checkmobileStr[3]);
        if (mobileStr.equals(checkmobileStr[1])) {

            Userinfo user = new Userinfo(
                    1,
                    usrStr,
                    checkmobileStr[3],
                    mobileStr, checkmobileStr[2]

            );

            //storing the user in shared preferences
            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
            Intent i = new Intent(SigninActivity.this, Primary.class);
            i.putExtra("Usr", usrStr);
            startActivity(i);
            finish();

        } else {
            //Toast something
            mainToast = Toast.makeText(getApplicationContext(), "Username/mobile is incorrect", Toast.LENGTH_SHORT);
            mainToast.show();
            // }
        }
    }

    private void userLoginonline() {
        //first getting the values
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your mobile number");
            editTextPassword.requestFocus();
            return;
        }

        //if everything is fine

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
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
                                userJson.getString("gender")

                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), Primary.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid username or mobile number", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("mobile", password);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }
}