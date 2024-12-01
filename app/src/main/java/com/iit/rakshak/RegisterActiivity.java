package com.iit.rakshak;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActiivity extends AppCompatActivity {

    EditText editTextUsername, editTextEmail, editTextPassword;
    RadioGroup radioGroupGender;
    String tab;
    int check1 = 0;
    int c, y = 0;
    private Toast mainToast;
    public String  mobileStr, usrStr, usrStrlow, emailStr,genderstr;
    UserDB check = new UserDB(this);
    UserDB Data = new UserDB(this);
    public String m1 = "Male";
    public String m2 = "Female";
    Bundle extras;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        System.out.println("inside RegisterActiivity");
        extras = getIntent().getExtras();
        tab = extras.getString("tab");
        System.out.println("tabregistor"+tab);
        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            //startActivity(new Intent(this, Primary.class));

            return;
        }

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGender);


        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server

                if (extras != null)
                {


                    if (tab.equals("online"))
                    {
                        onlineregisterUser();
                    }
                    else
                    {
                        offlineregister();
                    }
                }


            }
        });

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on login
                //we will open the login screen
                finish();
                //startActivity(new Intent(RegisterActiivity.this, SigninActivity.class));
                i = new Intent(RegisterActiivity.this, SigninActivity.class);
                i.putExtra("tab", tab);
                startActivity(i);
            }
        });

    }

    private void offlineregister()
    {
        check1 = 0;

        usrStrlow = editTextUsername.getText().toString();
        mobileStr = editTextPassword.getText().toString();
        emailStr = editTextEmail.getText().toString();
        usrStr = usrStrlow.toLowerCase();


        c = check.checkUser(usrStr); //will check if username exists will return 0 otherwise it will be 1

        if (TextUtils.isEmpty(usrStr)) {
            editTextUsername.setError("Please enter username");
            editTextUsername.requestFocus();
            return;
        }



        if (TextUtils.isEmpty(emailStr)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mobileStr)) {
            editTextPassword.setError("Enter a mobile number");
            editTextPassword.requestFocus();
            return;
        }
        if (c == y) {
            check1 = 1;
            mainToast = Toast.makeText(getApplicationContext(), "Username already exist", Toast.LENGTH_SHORT);
            mainToast.show();

        }


        if (check1 == 0) {

            check1 = 0;

            String text = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();
            String k = "gender";

            if (text.equals(m1)) //If gender is male K = 1
                k = "male";
            if (text.equals(m2)) //If gender is female K = 2
                k = "female";

            user per = new user();
            per.setUsername(usrStr);
            per.setemail(emailStr);
            per.setMobile(mobileStr);
            per.setgender(k);
            Userinfo usern = new Userinfo(1,
                    usrStr,
                    emailStr,
                    mobileStr,
                    k
            );

            //storing the user in shared preferences
            SharedPrefManager.getInstance(getApplicationContext()).userLogin(usern);
            Data.addUser(per);
           // Intent i = new Intent(RegisterActiivity.this, Primary.class);
            i = new Intent(RegisterActiivity.this, Primary.class);
            i.putExtra("tab", tab);
            mainToast = Toast.makeText(getApplicationContext(), "Your account has been created", Toast.LENGTH_SHORT);
            mainToast.show();
            startActivity(i);
            finish();
        }
    }

    private void onlineregisterUser() {
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        final String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();

        //first we will do the validations

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a mobile number");
            editTextPassword.requestFocus();
            return;
        }

        //if it passes all the validations

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("mobile", password);
                params.put("gender", gender);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
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
                                userJson.getString("gender")

                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                        Toast.makeText(getApplicationContext(), "Your account has been created", Toast.LENGTH_SHORT);
                       // startActivity(new Intent(getApplicationContext(), Primary.class));
                        i = new Intent(RegisterActiivity.this, Primary.class);
                        i.putExtra("tab", tab);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }

}