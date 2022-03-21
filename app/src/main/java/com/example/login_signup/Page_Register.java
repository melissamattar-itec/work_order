package com.example.login_signup;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Page_Register extends AppCompatActivity {
    EditText fname,abook, email,password,confirmpassword;
    Button Signup;
    String userToken ;
    boolean result;
    boolean isNameValid,isLastNameValid ,isEmailValid, isPasswordValid,isConfirmPasswordValid;
    JSONObject body;
    Url Url;
    //  private DrawerLayout drawer;
//    private Toolbar toolbar;
//    private ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_register);
        Url = new Url(this);
        fname = (EditText) findViewById(R.id.editTextName);
        abook = (EditText) findViewById(R.id.editTextRepertoire);
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editMotDePasse);
        confirmpassword = (EditText) findViewById(R.id.editTextConfirmerMotDePasse);
        Signup = (Button) findViewById(R.id.cirRegisterButton);

//
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        setSupportActionBar(toolbar);
//        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
////        drawerToggle.setHomeAsUpIndicator(R.drawable.icon_nav);
//        drawer.addDrawerListener(drawerToggle);
//        drawerToggle.syncState();

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);



    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (drawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return super.onOptionsItemSelected(item);
//    }
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        // TODO Auto-generated method stub
//        super.onConfigurationChanged(newConfig);
//        drawerToggle.onConfigurationChanged(newConfig);
//    }
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//        super.onPostCreate(savedInstanceState);
//        drawerToggle.syncState();
//    }

    public void signUserUpBtnClicked(View v){
        Log.i("signupbutton","clicked");
        if(SetValidation()==true){
            generateToken();
        }
    }
    private void Try_To_Signup() {
        //Map<String, String> params = new HashMap<String, String>();
        Log.i("Name",fname.getText().toString()+"");
        Log.i("password",password.getText().toString()+"");
        body = new JSONObject();
        try {
            body.put("token",userToken);
            body.put("User_ID",fname.getText().toString());
            body.put("User_Password" ,password.getText().toString());
            body.put("Address_Number" , abook.getText().toString());
            body.put("Electronic_Address" , email.getText().toString());
            body.put("Mobile_app_type" ,"2");

            Log.i("Body",body.toString());

            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            //body.put("Update_Date_and_Time" ,formattedDate);


        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),"erreur de connexion", Toast.LENGTH_SHORT).show();
            body=null;
        }


        String url= Url.getUrl() +"orchestrator/ORCH_MOB_Registration_STD";
        Log.i("body",body.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("response", response.toString()+"");

                        //Log.i("before result","ok");
                        try {
                            //txtResponse.setText(response.getString("userInfo"));
                            //Log.i("error", response.getString("continuedOnError"));
                            String result = "false";
                            if (response.getString("Result").equals(result)) {
                                if(response.isNull("L")){
                                    Log.i("Response Registration", response+" ");
                                    String er= "";
                                    JSONArray ob1 = new JSONArray(response.getString("continuedOnError"));
                                    Log.i(" JSONArray ob1", ob1+" ");
                                    JSONObject error_RAB = new JSONObject(ob1.getString(1));
                                    Log.i(" error_RAB ", error_RAB+" ");
                                    //JSONObject error_EMAIL = new JSONObject(ob1.getString(2));
                                    // the email don't exists on our system
                                    if(error_RAB.getString("step").equals("DR_MOB_CHECK_MAIL")){
                                        Toast.makeText(getApplicationContext(), " cet email n'existe pas dans le systeme ", Toast.LENGTH_SHORT).show();
                                    }
//                                    else  if(error_RAB.getString("step").equals("DR_MOB_CHECK_RAB")){
//                                        Toast.makeText(getApplicationContext(), "le repertoire d'adresse n'existe pas dans le systeme ", Toast.LENGTH_SHORT).show();
//
//                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), " vérifiez votre nom  ", Toast.LENGTH_SHORT).show();

                                    }

                                    Log.i("ana hon" , "ok");
                                }
                                else{
                                    Log.i("ok","done");
                                    Intent in = new Intent(Page_Register.this , Page_Login.class);
                                    in.putExtra("Name",fname.getText().toString());
                                    in.putExtra("Password",password.getText().toString());
                                    destroyToken();
                                    startActivity(in);
                                }
                                Log.i("ana hon 1" , "ok");
                            }
                            else {
                                // result=true;
                                Toast.makeText(getApplicationContext(), "deja enregitré ! Connectez-Vous", Toast.LENGTH_SHORT).show();
                            }

                            // Toast.makeText(LoginActivity.this, "Token generated", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            Log.i("it was an error",e.toString());

                            // the email don't exists on our system

                        }


                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "erreur de connexion", Toast.LENGTH_SHORT).show();
                        Log.i("Error" ,error.toString());
                    }
                });
        new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        RequestQueueSingleton.getInstance(Page_Register.this).addToRequestQueue1(jsonObjectRequest);

    }

    private void generateToken() {

        IResult mResultCallback = new IResult() {
            @Override
            public void notifySuccess(JSONObject response) {
                Log.i("Volley ", "response" + response);
                try {
                    //txtResponse.setText(response.getString("userInfo"));
                    JSONObject userInfoObject = new JSONObject(response.getString("userInfo"));
                    userToken = userInfoObject.getString("token");
                    Log.i("token",userToken);
                    // Toast.makeText(LoginActivity.this, "Token generated", Toast.LENGTH_SHORT).show();
                    if(userToken!=null){
                        Try_To_Signup();
                    }
                    else {
                        Log.i("token ", "null");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(VolleyError error) {
                Toast.makeText(getApplicationContext(),"erreur de connexion", Toast.LENGTH_SHORT).show();
                Log.i("Error", error.toString());

            }
        };

        Token token = new Token(mResultCallback, this);




//        Map<String, String> params = new HashMap<String, String>();
//        params.put("username", "JDE");
//        params.put("password", "JDE");
//        params.put("environment", "JDV920");
//        String url = "http://192.168.100.60:94/jderest/tokenrequest";
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.POST,
//                url,
//                new JSONObject(params),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            //txtResponse.setText(response.getString("userInfo"));
//                            JSONObject userInfoObject = new JSONObject(response.getString("userInfo"));
//                            userToken = userInfoObject.getString("token");
//                            Log.i("token",userToken);
//                            // Toast.makeText(LoginActivity.this, "Token generated", Toast.LENGTH_SHORT).show();
//
//                                Try_To_Signup();
//
//
//                        } catch (JSONException e) {
//                            Toast.makeText(getApplicationContext(), "erreur de connexion", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(),"erreur de connexion", Toast.LENGTH_SHORT).show();
//                Log.i("Error", error.toString());
//            }
//        });
//        RequestQueueSingleton.getInstance(Page_SignUp.this).addToRequestQueue1(jsonObjectRequest);
    }
    private void destroyToken(){
        //RequestQueueSingleton.getInstance(SignUpActivity.this).getRequestQueue().stop();
        //RequestQueueSingleton.getInstance(SignUpActivity.this).getRequestQueue().start();

        Map<String, String> params = new HashMap<String, String>();

        params.put("token", userToken);

        String url= Url.getUrl() +"tokenrequest/logout";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //  Toast.makeText(LoginActivity.this, "token destroyed", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueueSingleton.getInstance(Page_Register.this).addToRequestQueue1(jsonObjectRequest);

    }
    public void onLoginClick(View v){
//        Intent in = new Intent(Page_Register.this , Page_Login.class);
//        in.putExtra("some","All Done");
//        startActivity(in);
        startActivity(new Intent(this,Page_Login.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
    public boolean SetValidation() {


        boolean valid = true;
        String Name = fname.getText().toString();
        String addressbook = abook.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String ConfirmPassword = confirmpassword.getText().toString();


        if (Name.isEmpty()) {
            fname.setError("Veuillez entrer un prénom valide");
            valid = false;
        } else {
            fname.setError(null);
        }


        if (addressbook.isEmpty() ) {
            abook.setError("Veuillez entrer un repertoire d'adresse valide");
            valid = false;
        } else {
            abook.setError(null);
        }

        if (Email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Veuillez entrer un Email valide");
            valid = false;
        } else {
            email.setError(null);
        }
        if (Password.isEmpty() && password.getText().length() < 6) {
            password.setError("Veuillez entrer un mot de passe valide");
            valid = false;
//        } else if (password.getText().length() < 6) {
//            password.setError("Veuillez entrer un mot de passe valide");
//            valid = false;
        } else  {
            password.setError(null);
        }


        if (ConfirmPassword.isEmpty() ) {
            confirmpassword.setError("Veuillez entrer le mot de passe à confirmer");
            valid = false;
        } else {
            confirmpassword.setError(null);
        }

        return valid;
    }


    @Override
    public void onBackPressed() {

        finishAffinity();
        // close app
    }
}

