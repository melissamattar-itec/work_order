package com.example.login_signup;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;

import android.media.metrics.Event;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Page_Login extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    ProgressDialog progress;
    TextInputEditText edtLoginEmail, edtLoginPassword;
    String emailEntered = new String(), passwordEntered = new String();
    String remark = new String();
    String addr = new String();
    String status = new String();
    Double sum_clients=0.0;
    MyDataBaseHelper db;
    Url Url;
    int i_order=0;
    JSONArray data = new JSONArray();
    //    ArrayList<T_Tournee> arrayTournee = new ArrayList<>();
    boolean isNameValid, isPasswordValid;
    T_User User;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;
    ArrayList<T_Details> Status = new ArrayList<>();
    ArrayList<T_Orders> Orders= new ArrayList<>();
    ArrayList<T_Orders> ListElements= new ArrayList<>();
    Boolean finish=false;
    Boolean check_orders=false , check_Status = false;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Url = new Url(getApplicationContext());

        progress = new ProgressDialog(Page_Login.this);
        progress.setTitle("Loading ... ");
        progress.setCancelable(false);
        edtLoginEmail = findViewById(R.id.editTextEmail);
        edtLoginPassword = findViewById(R.id.editTextPassword);
        edtLoginPassword.setText("");
        edtLoginEmail.setText("");
        try {
            Intent in = getIntent();
            edtLoginEmail.setText(in.getStringExtra("Name"));
            edtLoginPassword.setText(in.getStringExtra("Password"));
        } catch (Exception e) {
            Log.i("error getextra", e.toString());
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerToggle.setHomeAsUpIndicator(R.drawable.icon_nav);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("vraiment sortir?")
                .setMessage("Êtes-vous sûr de vouloir quitter?")
                .setNegativeButton("Non", null)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        else {
                            finishAffinity();
                            // close app
                        }
                    }
                }).create().show();
    }




    public void NavigateToSingUp(View view) {
//        Intent i  = new Intent(Page_Login.this,Page_Register.class);
//        startActivity(i);
        startActivity(new Intent(this,Page_Register.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preference:
                Log.i("preferences Clicked","True");
                Intent preference_activity = new Intent(this, Page_Preferences.class);
                startActivity(preference_activity);
                break;
        }
        return true;
    }

    public void SignInBtnClicked(View view) {

//        Intent i = new Intent(Page_Login.this,Page_Order.class);
//        startActivity(i);
        Log.i("sign in ", "clicked");
        emailEntered = edtLoginEmail.getText().toString();
        passwordEntered = edtLoginPassword.getText().toString();
        if (SetValidation() == true) {
            Integer User_Id;
            MyDataBaseHelper db = new MyDataBaseHelper(Page_Login.this);
            if (db.validate(emailEntered, passwordEntered) == true) {
                User_Id = db.get_User_Id(emailEntered, passwordEntered);
                if (User_Id != 0) {
                    Intent intent = new Intent(Page_Login.this, Page_Order.class);
                    Log.i("userId", User_Id + "id");
                    intent.putExtra("userId", db.get_User_Id(emailEntered, passwordEntered));
                    intent.putExtra("Name" , emailEntered);
                    startActivity(intent);
                }
            } else if (db.validate_username(emailEntered) && db.validate_password(passwordEntered)) {
                Log.i("username and pass", "not match");
                Toast.makeText(getApplicationContext(), "le nom d'utilisateur et le mot de passe  n'existe pas", Toast.LENGTH_SHORT).show();

            }
            else {
                progress.show();
                generateToken_addressbook();
            }
        } else {
            Log.i("error", "validation");
        }
    }

    public boolean SetValidation() {
        // Check for a valid name.
        if (edtLoginEmail.getText().toString().isEmpty()) {
            edtLoginEmail.setError("Veuillez entrer un nom valide");
            isNameValid = false;
        } else {
            isNameValid = true;
        }

        // Check for a valid password.
        if (edtLoginPassword.getText().toString().isEmpty()) {
            edtLoginPassword.setError("Veuillez entrer le mot de passe valide");
            isPasswordValid = false;
        } else {
            isPasswordValid = true;
        }

        if (isNameValid && isPasswordValid) {
            //Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void generateToken_addressbook() {
        String name = edtLoginEmail.getText().toString();
        String password = edtLoginPassword.getText().toString();
        Log.i("name/password before", name + "/" + password);

        IResult mResultCallback = new IResult() {
            @Override
            public void notifySuccess(JSONObject response) {
                Log.i("Volley ", "response" + response);
                try {
                    JSONObject userInfoObject = new JSONObject(response.getString("userInfo"));
                    String token = userInfoObject.getString("token");
                    // Toast.makeText(LoginActivity.this, "Token generated", Toast.LENGTH_SHORT).show();
                    Log.i("Token", "t" + token);

                    if (token != null) {
                        authenticateUser(token);
                    } else {
                        Toast.makeText(getApplicationContext(), "erreur de connexion", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(VolleyError error) {
                Toast.makeText(getApplicationContext(), "erreur de connexion", Toast.LENGTH_SHORT).show();
                Log.i("Volley ", "response" + "That didn't work!");
                Intent i = new Intent(Page_Login.this, Page_Login.class);
                Log.i("name/password after", name + "/" + password);
                i.putExtra("Name", name);
                i.putExtra("Password", password);

                startActivity(i);

            }
        };

        Token token = new Token(mResultCallback, this);
//        String token_addr_book = new String();
//
//        Token token = new Token(this);
//
//        // token.Token(email, password, this, loginUrl,
//        Log.i("Token", "t_login" + token_addr_book);
//        if (token_addr_book != null && token_addr_book != "" && !token_addr_book.isEmpty()) {
//            authenticateUser(token_addr_book);
//        } else {
//            Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
//        }
    }


    private void authenticateUser(String auth_User_Token) {
        //RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().stop();
        //RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().start();

        // Map<String, String> params = new HashMap<String, String>();
        Url=new Url(getApplicationContext());
        String url = Url.getUrl() + "orchestrator/ORCH_USER_AUTHENTICATION";
        JSONObject body = new JSONObject();
        try {
            body.put("token", auth_User_Token);
            body.put("User", emailEntered);
            body.put("Password", passwordEntered);
            body.put("mobile_app_type" , "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("body for auth", body.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject auth = new JSONObject(response.getString("DR_USER_AUTHENTICATION"));
                            JSONArray rowSet = auth.getJSONArray("rowset");
                            if (rowSet.length() != 0) {
                                for (int i = 0; i < rowSet.length(); i++) {
                                    try {
                                        JSONObject elementFromRow = new JSONObject(rowSet.getString(i));
                                        if (!elementFromRow.isNull("Address Number")) {
                                            String address = elementFromRow.getString("Address Number");
//                                            MyDataBaseHelper db = new MyDataBaseHelper(Page_Login.this);
//                                            db.addUser(emailEntered, passwordEntered, address);
//
//                                            UserId = db.get_User_Id(emailEntered, passwordEntered);
//                                            db.close();

                                            User = new T_User(0,emailEntered, passwordEntered, address);
//                                            if (UserId != 0) {
//                                                db.add_settings("Timer","time","0/0/0",UserId);
//                                                String Value = db.getSettings_Value("Settings", "timer",UserId);
//                                                db.add_settings("Settings","timer","off",UserId);
//                                                String address_number = elementFromRow.getString("Address Number");
//                                                if (!address_number.equals(null)) {
//                                                    generateToken_tournee(address_number, UserId);
//                                                    generateTokenObservations();
//                                                    generateTokenstatus();
//                                                    Log.i("Address", address_number + "");
//                                                }
//                                            } else {
//
//                                                Toast.makeText(Page_Login.this, "erreur de connexion", Toast.LENGTH_SHORT).show();
//                                            }

                                            if (User != null) {

                                                String address_number = elementFromRow.getString("Address Number");
                                                if (!address_number.equals(null)) {
                                                    generateToken_orders(address_number);
                                                    generateTokenudc();
//                                                    generateTokenObservations();
//                                                    generateTokenstatus();
//                                                    generateTokentypelecture();
//                                                    generateToken_Folder_Path();
                                                    Log.i("Address", address_number + "");
                                                }
                                            } else {
                                                Toast.makeText(Page_Login.this, "erreur de connexion", Toast.LENGTH_SHORT).show();
                                            }


                                        } else {
                                            Log.i("Authentication error", "ok");
                                            Toast.makeText(Page_Login.this, "erreur d'authentication ", Toast.LENGTH_SHORT).show();
                                            Intent inn=new Intent(Page_Login.this, Page_Login.class);
                                            startActivity(inn);

                                        }
                                    } catch (Exception e) {

                                        Toast.makeText(Page_Login.this, "erreur d'authentication", Toast.LENGTH_SHORT).show();
                                        Intent inn=new Intent(Page_Login.this, Page_Login.class);
                                        startActivity(inn);
                                    }
                                }
                            } else {
                                Toast.makeText(Page_Login.this, "erreur d'authentication", Toast.LENGTH_SHORT).show();
                                Intent inn=new Intent(Page_Login.this, Page_Login.class);
                                startActivity(inn);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(Page_Login.this, "erreur d'authentication ", Toast.LENGTH_SHORT).show();
                            Intent inn=new Intent(Page_Login.this, Page_Login.class);
                            startActivity(inn);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Page_Login.this, "erreur de connexion", Toast.LENGTH_SHORT).show();
                Log.i("Error", error.toString());
                Intent inn=new Intent(Page_Login.this, Page_Login.class);
                startActivity(inn);
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(500000000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueueSingleton.getInstance(Page_Login.this).addToRequestQueue1(jsonObjectRequest);

    }


    private void generateToken_orders(String address_number) {

        IResult mResultCallback = new IResult() {
            @Override
            public void notifySuccess(JSONObject response) {
                Log.i("Volley ", "response" + response);
                try {
                    JSONObject userInfoObject = new JSONObject(response.getString("userInfo"));
                    String token = userInfoObject.getString("token");
                    // Toast.makeText(LoginActivity.this, "Token generated", Toast.LENGTH_SHORT).show();
                    Log.i("Token", token);

                    if (token != null) {
                        generateOrder(token, address_number);
                    } else {
                        Toast.makeText(getApplicationContext(), "erreur de connexion", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(VolleyError error) {

                Toast.makeText(getApplicationContext(), "erreur de connexion", Toast.LENGTH_SHORT).show();
                Log.i("Volley ", "response" + "That didn't work!");
                Intent i = new Intent(Page_Login.this, Page_Login.class);
                i.putExtra("Name", edtLoginEmail.getText());
                i.putExtra("Password", edtLoginPassword.getText());
                startActivity(i);
            }
        };

        Token token = new Token(mResultCallback, this);

//        Token token=new Token(this);
//
//        String token_tournee=token.getToken();
//        if(token_tournee!=null)
//                            {
//                                generateTournee(token_tournee, address_number,userId);
//                            }
//                            else {
//                                Toast.makeText( LoginActivity.this,"Connexion error", Toast.LENGTH_SHORT).show();
//                            }

    }

    private void generateOrder(String UserToken_Order, String addressNumber) throws JSONException {
        //RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().stop();
        //RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().start();
       JSONObject body = new JSONObject();
        body.put("token", UserToken_Order);
        body.put("Assigned_To", addressNumber);
        Log.i("AddressOrder",addressNumber);
        Log.i("body",body.toString());
        //params.put("Status", "MH");
        Url=new Url(getApplicationContext());
        String url = Url.getUrl() + "orchestrator/ORCH_MWO_LIST_WORK_ORDER2";
        Log.i("generate order", "function");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject list_work_orders =response.getJSONObject("DR_LIST_WORK_ORDER");
                             Log.i("list_work_orders",list_work_orders.toString());
                            // JSONArray forms= new JSONArray("forms");
                           // Log.i("forms",forms.toString());
                            JSONArray rowSet = list_work_orders.getJSONArray("rowset");
                             //Log.i("forms",forms.getJSONObject(0).toString());

                            //JSONArray rowSet =forms.getJSONObject(0).getJSONObject("fs_P48201_W48201F").getJSONObject("data").getJSONObject("gridData").getJSONArray("rowset");
//                            Log.i("rowset0",servicerequest.getJSONArray("rowset").getJSONObject(0).toString());

                          // JSONArray rowSet = new JSONArray(response.getJSONArray("forms"));
                            //StringBuilder stringToShow = new StringBuilder();
                            //Log.i("order rowset_lenght", rowSet.length()+"");


//                            if(rowSet.length()==0){
//                                check_orders=true;
//                               // check_clients=true;
//                                Log.i(" generate check_orders" , check_orders +"");
//                                //Insert_Data_database();
////                                if(check_orders==true ){
////                                    // insert data collected from the webservices into the database
////                                    Insert_Data_database();
////                                }
//                            }


                            for (int i = 0; i < rowSet.length(); i++) {
                                JSONObject elementFromRow = new JSONObject(rowSet.getString(i));
//                                MyDataBaseHelper mydb = new MyDataBaseHelper(Page_Login.this);
//                                mydb.addTournee(elementFromRow.getString("ProfilingUDC6"), elementFromRow.getString("FiscalYear"), elementFromRow.getString("PerNo"), elementFromRow.getString("fromStatus"), elementFromRow.getString("CountRead"), elementFromRow.getString("CountInstallation"), User_Id , elementFromRow.getInt("Max Consumption Eau"), elementFromRow.getInt("Max Consumption Elec"));
//
                                i_order++;

                                Orders.add(new T_Orders(
                                        i_order,
                                        1,
                                        elementFromRow.getString("Type"),
                                        elementFromRow.getString("Sous Type"),
                                        elementFromRow.getInt("Order Number "),
                                        elementFromRow.getString("Start Date "),
                                        elementFromRow.getString("Description "),
                                        elementFromRow.getString("Order Date "),
                                        elementFromRow.getString("Priority  "),
                                        elementFromRow.getString("Work Status ")
                                      ));
                                        Log.i("Response orders" , "ok");
                                if(i==rowSet.length()-1){
                                    check_orders=true;
                                    Log.i("check_orders" , "ok");
                                }
                            }


                                    if(check_orders==true && check_Status == true) {
                                        Insert_Data_database();
                                        Log.i("Insert", "done");

                                    }

                            //  destroyToken();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Page_Login.this, "erreur de connexion", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Page_Login.this, "erreur de connexion", Toast.LENGTH_SHORT).show();
                Log.i("Error", error.toString());
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(500000000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //requestQueue.add(jsonObjectRequest);
        RequestQueueSingleton.getInstance(Page_Login.this).addToRequestQueue1(jsonObjectRequest);

    }

    private void generateTokenudc() {

        IResult mResultCallback = new IResult() {
            @Override
            public void notifySuccess(JSONObject response) {
                Log.i("Volley ", "response" + response);
                try {
                    JSONObject userInfoObject = new JSONObject(response.getString("userInfo"));
                    String token = userInfoObject.getString("token");
                    // Toast.makeText(LoginActivity.this, "Token generated", Toast.LENGTH_SHORT).show();
                    Log.i("Token", "t" + token);

                    if (token != null) {
                        generateudc(token);
                    } else {
                        Toast.makeText(getApplicationContext(), "erreur de connexion", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(VolleyError error) {
                Toast.makeText(getApplicationContext(), "erreur de connexion", Toast.LENGTH_SHORT).show();
                Log.i("Volley ", "response" + "That didn't work!");
                Intent i = new Intent(Page_Login.this, Page_Login.class);
                i.putExtra("Name", edtLoginEmail.getText());
                i.putExtra("Password", edtLoginPassword.getText());
                startActivity(i);
            }
        };

        Token token = new Token(mResultCallback, this);
//        Token token=new Token(this);
//        String Tokenstat=token.getToken();
//       if(Tokenstat!=null)
//                            {
//                                generatestatus(Tokenstat);
//                            }
//                            else {
//
//                                Toast.makeText( LoginActivity.this,"Connexion error", Toast.LENGTH_SHORT).show();
//                            }
//


    }

    private void generateudc(String tokenStatus) {
        //RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().stop();
        // RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().start();
        Url=new Url(getApplicationContext());
        String url = Url.getUrl() + "orchestrator/ORCH_GetUDC";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                bodyForStatusRequest(tokenStatus),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject DR_GetUDC = new JSONObject(response.getString("DR_GetUDC"));
                            JSONArray rowSet = DR_GetUDC.getJSONArray("rowset");
                            Log.i("details", rowSet + "");
                            StringBuilder stringToShow = new StringBuilder();
                            for (int i = 0; i < rowSet.length(); i++) {
                                JSONObject elementFromRow = new JSONObject(rowSet.getString(i));


                                String Observation =
                                        "Code: " + (elementFromRow.getString("Code")) + " " +
                                                //"OperSeq: " + (elementFromRow.getString("OperSeq"))+ " "+
                                                "Description: " + (elementFromRow.getString("Description"));

                                Log.i("String details", Observation + "");
//                                MyDataBaseHelper db = new MyDataBaseHelper(Page_Login.this);
//                                db.add_Details("E", elementFromRow.getString("Code"), elementFromRow.getString("Description"));
//                                db.close();

                                Status.add(new T_Details("S1", elementFromRow.getString("Code"), elementFromRow.getString("Description")));
                                if(i==rowSet.length()-1){
                                    check_Status=true;

                                    if(check_orders==true && check_Status==true){
                                        // insert data collected from the webservices into the database
                                        Insert_Data_database();
                                    }
                                }
                            }

                        } catch (JSONException e) {

                            Toast.makeText(Page_Login.this, "erreur de connexion", Toast.LENGTH_SHORT).show();
                            Log.i("CACHE", e.getMessage() + "");
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Page_Login.this, "erreur de connexion", Toast.LENGTH_SHORT).show();
                Log.i("Error", error.toString());
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(500000000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueueSingleton.getInstance(Page_Login.this).addToRequestQueue1(jsonObjectRequest);
    }

    private JSONObject bodyForStatusRequest(String token) {
        JSONObject body = new JSONObject();
        try {
            body.put("token", token);
            body.put("Product_Code", "55");
            body.put("UserDefinedCodes", "S1");
            return body;
        } catch (JSONException e) {

            Toast.makeText(this, "erreur de connexion", Toast.LENGTH_SHORT).show();
            Log.i("bodyForStatusRequest", e.getMessage());
            return null;
        }
    }

    public void Insert_Data_database() {
        if (!finish) {
            Log.i("insert data", "function");
            MyDataBaseHelper db = new MyDataBaseHelper(this);
            // insert user

            db.addUser(User.getUserID(), User.getPassword(), User.getAddress_Book());
            db.close();
            db = new MyDataBaseHelper(this);
            Integer Db_UserId = db.get_User_Id(User.getUserID(), User.getPassword());
            Log.i("DbUserId", Db_UserId + "  ");
            // insert order
            for (int i = 0; i < Orders.size(); i++) {
                db.addOrder(
                        Orders.get(i).getType(),
                        Orders.get(i).getSous_type(),
                        Orders.get(i).getOrder_number(),
                        Orders.get(i).getStart_date(),
                        Orders.get(i).getDescription(),
                        Orders.get(i).getOrder_date(),
                        Orders.get(i).getPriority(),
                        Orders.get(i).getWork_status(),
                        Db_UserId);

                if(i == Orders.size()-1){
                    Intent in  = new Intent(Page_Login.this, Page_Order.class);
                    in.putExtra("userId",Db_UserId);
                    Log.i("USERID", Db_UserId.toString());
                    startActivity(in);
                }

            }
//            for(int i=0; i<ListElements.size() ; i++){
//                db.delete_order_byId(ListElements.get(i).getId());
//            }
            // insert status Udc
            for (int j = 0; j < Status.size(); j++) {
                db.add_Details(Status.get(j).getType(), Status.get(j).getCode(), Status.get(j).getObservation());

            }
        }
    }

    public void NavigateToSingUp1(View view) {
        startActivity(new Intent(this,Page_Register.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}