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
    ArrayList<T_Details> Site_desc = new ArrayList<>();
    ArrayList<T_Details> Commune_desc = new ArrayList<>();
    ArrayList<T_Details> Quartier_desc = new ArrayList<>();
    ArrayList<T_Details> Status_Equipments = new ArrayList<>();
    ArrayList<T_Details> Types = new ArrayList<>();
    ArrayList<T_Details> Sous_Types = new ArrayList<>();
    ArrayList<T_Details> Priorities= new ArrayList<>();
    ArrayList<T_Orders> Orders= new ArrayList<>();
    ArrayList<T_Orders> ListElements= new ArrayList<>();
    Boolean finish=false;
    Boolean check_orders=false ,check_site=false , check_communne= false , check_quartier=false,  check_Status = false , check_types =false , check_sous_type =false , check_priority =false , check_status_Equipment=false;
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
                                                    generateTokenStatus_workorder();
                                                    generateTokenSite_desc();
                                                    generateTokenCommune_desc();
                                                    generateTokenQuartier_desc();
                                                    generateTokenStatus_Equipment();
                                                    generateTokenTypeWO();
                                                    generateTokenSousTypeWO();
                                                    generateTokenPriorites();
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
                            JSONArray rowSet = list_work_orders.getJSONArray("rowset");



                            for (int i = 0; i < rowSet.length(); i++) {

                                JSONArray equipment_description =response.getJSONArray("DR_MWO_EQUIPMENT_DESCRIPTION_Repeating").getJSONObject(i).getJSONObject("DR_MWO_EQUIPMENT_DESCRIPTION").getJSONArray("rowset");
                                JSONArray contact_Details = new JSONArray(response.getString("BF_CONTACT_DETAILS_Repeating"));
                                JSONArray installaltion_Details =  new JSONArray(response.getString("BF_INSTALLATION_DETAILS_Repeating"));
                                String Type = new String();
                                String Sous_Type = new String();
                                Integer Client_Number = new Integer(0);
                                Integer Order_Number = new Integer(0);
                                Integer Equipment_Number = new Integer(0);
                                String Start_Date = new String();
                                String Description = new String();
                                String Order_Date = new String();
                                String Priority = new String();
                                String Client_Name = new String();
                                String Problem = new String();
                                Double Longitude = new Double(0.0);
                                Double Latitude = new Double(0.0);
                                String Status = new String();
                                Integer Service_Address = new Integer(0);
                                String Equipment_description = new String();
                                String Equipment_status= new String();
                                String Phone_Area_Code1= new String();
                                String Phone_Number= new String();
                                String Email_Address= new String();
                                String Address_Line1= new String();
                                String Address_Line2= new String();
                                String Installation_Code= new String();
                                String Puissance= new String();
                                String Tarif= new String();
                                String Amperage= new String();
                                String Site= new String();
                                String Commune= new String();
                                String Quartier= new String();

//                                MyDataBaseHelper mydb = new MyDataBaseHelper(Page_Login.this);
//                                mydb.addTournee(elementFromRow.getString("ProfilingUDC6"), elementFromRow.getString("FiscalYear"), elementFromRow.getString("PerNo"), elementFromRow.getString("fromStatus"), elementFromRow.getString("CountRead"), elementFromRow.getString("CountInstallation"), User_Id , elementFromRow.getInt("Max Consumption Eau"), elementFromRow.getInt("Max Consumption Elec"));
                                JSONObject elementFromRow = new JSONObject(rowSet.getString(i));
                                try {
                                    Type = elementFromRow.getString("Type");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Sous_Type = elementFromRow.getString("Sous Type");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Client_Number = elementFromRow.getInt("Client Number ");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Order_Number = elementFromRow.getInt("Order Number ");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    Equipment_Number = elementFromRow.getInt("Equipment Number ");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    Start_Date = elementFromRow.getString("Start Date ");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    Description = elementFromRow.getString("Description ");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    Order_Date = elementFromRow.getString("Order Date ");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Priority = elementFromRow.getString("Priority  ");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    Client_Name= elementFromRow.getString("Client Name ");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Problem= elementFromRow.getString("Problem  ");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    Longitude = elementFromRow.getDouble("Longitude ");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Latitude = elementFromRow.getDouble("Latitude");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    Status = elementFromRow.getString("Status");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    Service_Address = elementFromRow.getInt("Service Address");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                for (int j = 0; j < equipment_description.length(); j++) {
                                    Log.i("ok", "fet 3l while j");
                                    JSONObject descrepeating = equipment_description.getJSONObject(j) ;
                                    Log.i("descrepeating", descrepeating.toString());
                                    //Log.i("DESCRIPTIONREPEATING", descrepeating.getString("Equipment Number"));
                                    Log.i("DESCRIPTIONREPEATING",Equipment_Number+"");
                                    if (!descrepeating.isNull("Equipment Number")) {
                                        Log.i("EquipmentNumber", descrepeating.getString("Equipment Number"));
                                        if (descrepeating.getInt("Equipment Number") == Equipment_Number) {
                                            Equipment_description = descrepeating.getString("Description");
                                            Log.i("LogEquipment_description",descrepeating.getString("Description"));
                                            Equipment_status = descrepeating.getString("EquipmentStatus");
                                            Log.i("EquipmentDescEquipmentStatus", Equipment_description + Equipment_status);

                                            break;

                                        }

                                    }

                                }

                                for (int K = 0; K < contact_Details.length(); K ++) {
                                    Log.i("ok", "fet 3l while K");
                                    JSONObject contactrepeating = new JSONObject(contact_Details.getString(K)) ;
                                    Log.i("contact_Details", contactrepeating.toString());
                                    //Log.i("DESCRIPTIONREPEATING", descrepeating.getString("Equipment Number"));
                                    //Log.i("contact_DetailsREPEATING",Equipment_Number+"");
                                    if (!contactrepeating.isNull("Order_Number")) {
                                        Log.i("OrderNumber", contactrepeating.getString("Order_Number"));
                                        if (contactrepeating.getInt("Order_Number") == Order_Number) {
                                            try {
                                                Phone_Area_Code1 = contactrepeating.getString("Phone_Area_Code1");
                                            }catch (Exception e){
                                                Puissance="";
                                            }

                                            Log.i("Phone_Area_Code1",contactrepeating.getString("Phone_Area_Code1"));
                                            try {
                                                Phone_Number = contactrepeating.getString("Phone_Number");
                                            }catch (Exception e){
                                                Puissance="";
                                            }

                                            Log.i("Phone_Number", Equipment_description + Equipment_status);

                                            try {
                                                Email_Address = contactrepeating.getString("Email_Address");
                                            }catch (Exception e){
                                                Puissance="";
                                            }

                                            Log.i("Email_Address",contactrepeating.getString("Email_Address"));


                                            try {
                                                Address_Line1 = contactrepeating.getString("Address_Line1");
                                            }catch (Exception e){
                                                Puissance="";
                                            }

                                            Log.i("Address_Line1",contactrepeating.getString("Address_Line1"));

                                            try {
                                                Address_Line2 = contactrepeating.getString("Address_Line2");
                                            }catch (Exception e){
                                                Puissance="";
                                            }

                                            Log.i("Address_Line2",contactrepeating.getString("Address_Line2"));

                                            break;

                                        }

                                    }

                                }
                                for (int L = 0; L < installaltion_Details.length(); L ++) {
                                    Log.i("ok", "fet 3l while L");
                                    JSONObject installationrepeating = new JSONObject(installaltion_Details.getString(L)) ;
                                    Log.i("descrepeating", installationrepeating.toString());
                                    //Log.i("DESCRIPTIONREPEATING", descrepeating.getString("Equipment Number"));
                                    Log.i("DESCRIPTIONREPEATING",Order_Number+"");
                                    if (!installationrepeating.isNull("Order_Number")) {
                                        Log.i("Installation_Code", installationrepeating.getString("Installation_Code "));
                                        if (installationrepeating.getInt("Order_Number") == Order_Number) {
                                            Installation_Code = installationrepeating.getString("Installation_Code ");
                                            Log.i("Installation_Code",installationrepeating.getString("Installation_Code "));
                                            try {
                                                Puissance = installationrepeating.getString("Puissance ");
                                            }catch (Exception e){
                                                Puissance="";
                                            }

                                            try {
                                                Tarif = installationrepeating.getString("Tarif ");
                                            }catch (Exception e){
                                                Tarif="";
                                            }

                                            try {
                                                Amperage = installationrepeating.getString("Amperage  ");
                                            }catch (Exception e){
                                                Amperage="";
                                            }

                                            try {
                                                Site = installationrepeating.getString("Site");
                                            }catch (Exception e){
                                                Site="";
                                            }

                                            try {
                                                Commune = installationrepeating.getString("Commune");
                                            }catch (Exception e){
                                                Commune="";
                                            }

                                            try {
                                                Quartier = installationrepeating.getString("Quartier");
                                            }catch (Exception e){
                                                Quartier="";
                                            }

                                            break;

                                        }

                                    }


                                }
                                i_order++;

                                Orders.add(new T_Orders(
                                        i_order,
                                        1,
                                        Type,
                                        Sous_Type,
                                        Client_Number,
                                        Order_Number,
                                        Equipment_Number,
                                        Start_Date,
                                        Description,
                                        Order_Date,
                                        Priority,
                                        Client_Name,
                                        Problem,
                                        Longitude,
                                        Latitude,
                                        Status,
                                        Service_Address,
                                        Equipment_description,
                                        Equipment_status,
                                        Phone_Area_Code1,
                                        Phone_Number,
                                        Email_Address,
                                        Address_Line1,
                                        Address_Line2,
                                        Installation_Code,
                                        Puissance,
                                        Tarif,
                                        Amperage,
                                        Site,
                                        Commune,
                                        Quartier,
                                        null,
                                        "",
                                        0
                                ));
                                Log.i("Response orders" , "ok");

                                if(i==rowSet.length()-1){
                                    check_orders=true;
                                    Log.i("check_orders" , "ok");
                                }
                            }


                            if(check_communne==true && check_quartier==true && check_site==true && check_Status == true && check_orders==true && check_status_Equipment==true && check_priority==true && check_types==true && check_sous_type==true){
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


    private void generateTokenStatus_workorder() {

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
                        generateStatus_workorder(token);
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

    private void generateStatus_workorder(String tokenStatus) {
        //RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().stop();
        // RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().start();
        Url=new Url(getApplicationContext());
        String url = Url.getUrl() + "orchestrator/ORCH_GetUDC";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                bodyForStatus_workorderRequest(tokenStatus),
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

                                }
                            }
                            if( check_communne==true && check_quartier==true && check_site==true && check_Status == true && check_orders==true && check_status_Equipment==true && check_priority==true && check_types==true && check_sous_type==true){
                                Insert_Data_database();
                                Log.i("Insert", "done");

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

    private JSONObject bodyForStatus_workorderRequest(String token) {
        JSONObject body = new JSONObject();
        try {
            body.put("token", token);
            body.put("Product_Code", "55");
            body.put("UserDefinedCodes", "S1");
            return body;
        } catch (JSONException e) {

            Toast.makeText(this, "erreur de connexion", Toast.LENGTH_SHORT).show();
            Log.i("bodyForStatus_workorderRequest", e.getMessage());
            return null;
        }
    }


    private void generateTokenSite_desc() {

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
                        generateSite_desc(token);
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

    private void generateSite_desc(String tokenStatus) {
        //RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().stop();
        // RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().start();
        Url=new Url(getApplicationContext());
        String url = Url.getUrl() + "orchestrator/ORCH_GetUDC";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                bodyForSite_descRequest(tokenStatus),
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

                                Site_desc.add(new T_Details("UB", elementFromRow.getString("Code"), elementFromRow.getString("Description")));
                                if(i==rowSet.length()-1){
                                    check_site=true;

                                }
                            }
                            if( check_communne==true && check_quartier==true && check_site==true && check_Status == true && check_orders==true && check_status_Equipment==true && check_priority==true && check_types==true && check_sous_type==true){
                                Insert_Data_database();
                                Log.i("Insert", "done");

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

    private JSONObject bodyForSite_descRequest(String token) {
        JSONObject body = new JSONObject();
        try {
            body.put("token", token);
            body.put("Product_Code", "90CA");
            body.put("UserDefinedCodes", "UB");
            return body;
        } catch (JSONException e) {

            Toast.makeText(this, "erreur de connexion", Toast.LENGTH_SHORT).show();
            Log.i("bodyForSite_descRequest", e.getMessage());
            return null;
        }
    }


    private void generateTokenCommune_desc() {

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
                        generateCommune_desc(token);
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

    private void generateCommune_desc(String tokenStatus) {
        //RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().stop();
        // RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().start();
        Url=new Url(getApplicationContext());
        String url = Url.getUrl() + "orchestrator/ORCH_GetUDC";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                bodyForCommune_descRequest(tokenStatus),
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

                                Commune_desc.add(new T_Details("UC", elementFromRow.getString("Code"), elementFromRow.getString("Description")));
                                if(i==rowSet.length()-1){
                                    check_communne=true;

                                }
                            }
                            if( check_communne==true && check_quartier==true && check_site==true && check_Status == true && check_orders==true && check_status_Equipment==true && check_priority==true && check_types==true && check_sous_type==true){
                                Insert_Data_database();
                                Log.i("Insert", "done");

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

    private JSONObject bodyForCommune_descRequest(String token) {
        JSONObject body = new JSONObject();
        try {
            body.put("token", token);
            body.put("Product_Code", "90CA");
            body.put("UserDefinedCodes", "UC");
            return body;
        } catch (JSONException e) {

            Toast.makeText(this, "erreur de connexion", Toast.LENGTH_SHORT).show();
            Log.i("bodyForCommune_descRequest", e.getMessage());
            return null;
        }
    }



    private void generateTokenQuartier_desc() {

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
                        generateQuartier_desc(token);
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

    private void generateQuartier_desc(String tokenStatus) {
        //RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().stop();
        // RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().start();
        Url=new Url(getApplicationContext());
        String url = Url.getUrl() + "orchestrator/ORCH_GetUDC";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                bodyForQuartier_descRequest(tokenStatus),
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

                                Quartier_desc.add(new T_Details("UD", elementFromRow.getString("Code"), elementFromRow.getString("Description")));
                                if(i==rowSet.length()-1){
                                    check_quartier=true;

                                }
                            }
                            if( check_communne==true && check_quartier==true && check_site==true && check_Status == true && check_orders==true && check_status_Equipment==true && check_priority==true && check_types==true && check_sous_type==true){
                                Insert_Data_database();
                                Log.i("Insert", "done");

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

    private JSONObject bodyForQuartier_descRequest(String token) {
        JSONObject body = new JSONObject();
        try {
            body.put("token", token);
            body.put("Product_Code", "90CA");
            body.put("UserDefinedCodes", "UD");
            return body;
        } catch (JSONException e) {

            Toast.makeText(this, "erreur de connexion", Toast.LENGTH_SHORT).show();
            Log.i("bodyForQuartier_descRequest", e.getMessage());
            return null;
        }
    }



    private void generateTokenStatus_Equipment() {

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
                        generateStatus_Equipment(token);
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

    private void generateStatus_Equipment(String tokenStatus) {
        //RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().stop();
        // RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().start();
        Url=new Url(getApplicationContext());
        String url = Url.getUrl() + "orchestrator/ORCH_GetUDC";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                bodyForStatus_EquipmentRequest(tokenStatus),
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

                                Status_Equipments.add(new T_Details("ES", elementFromRow.getString("Code"), elementFromRow.getString("Description")));
                                if(i==rowSet.length()-1){
                                    check_status_Equipment=true;

                                }
                            }
                            if(check_communne==true && check_quartier==true && check_site==true && check_Status == true && check_orders==true && check_status_Equipment==true && check_priority==true && check_types==true && check_sous_type==true){
                                Insert_Data_database();
                                Log.i("Insert", "done");

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

    private JSONObject bodyForStatus_EquipmentRequest(String token) {
        JSONObject body = new JSONObject();
        try {
            body.put("token", token);
            body.put("Product_Code", "12");
            body.put("UserDefinedCodes", "ES");
            return body;
        } catch (JSONException e) {

            Toast.makeText(this, "erreur de connexion", Toast.LENGTH_SHORT).show();
            Log.i("bodyForStatus_EquipmentRequest", e.getMessage());
            return null;
        }
    }





    private void generateTokenTypeWO() {

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
                        generateTypeWO(token);
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

    private void generateTypeWO(String tokenStatus) {
        //RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().stop();
        // RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().start();
        Url=new Url(getApplicationContext());
        String url = Url.getUrl() + "orchestrator/ORCH_GetUDC";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                bodyForTypeWORequest(tokenStatus),
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

                                Types.add(new T_Details("DT", elementFromRow.getString("Code"), elementFromRow.getString("Description")));
                                if(i==rowSet.length()-1){
                                    check_types=true;

                                }
                            }

                            if( check_communne==true && check_quartier==true && check_site==true && check_Status == true && check_orders==true && check_status_Equipment==true && check_priority==true && check_types==true && check_sous_type==true){
                                Insert_Data_database();
                                Log.i("Insert", "done");

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

    private JSONObject bodyForTypeWORequest(String token) {
        JSONObject body = new JSONObject();
        try {
            body.put("token", token);
            body.put("Product_Code", "00");
            body.put("UserDefinedCodes", "DT");
            return body;
        } catch (JSONException e) {

            Toast.makeText(this, "erreur de connexion", Toast.LENGTH_SHORT).show();
            Log.i("bodyForTypeWORequest", e.getMessage());
            return null;
        }
    }





    private void generateTokenSousTypeWO() {

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
                        generateSousTypeWO(token);
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

    private void generateSousTypeWO(String tokenStatus) {
        //RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().stop();
        // RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().start();
        Url=new Url(getApplicationContext());
        String url = Url.getUrl() + "orchestrator/ORCH_GetUDC";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                bodyForSousTypeWORequest(tokenStatus),
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

                                Sous_Types.add(new T_Details("TY", elementFromRow.getString("Code"), elementFromRow.getString("Description")));
                                if(i==rowSet.length()-1){
                                    check_sous_type=true;

                                }
                            }
                            if( check_communne==true && check_quartier==true && check_site==true && check_Status == true && check_orders==true && check_status_Equipment==true && check_priority==true && check_types==true && check_sous_type==true){
                                Insert_Data_database();
                                Log.i("Insert", "done");

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

    private JSONObject bodyForSousTypeWORequest(String token) {
        JSONObject body = new JSONObject();
        try {
            body.put("token", token);
            body.put("Product_Code", "00");
            body.put("UserDefinedCodes", "TY");
            return body;
        } catch (JSONException e) {

            Toast.makeText(this, "erreur de connexion", Toast.LENGTH_SHORT).show();
            Log.i("bodyForSousTypeWORequest", e.getMessage());
            return null;
        }
    }





    private void generateTokenPriorites() {

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
                        generatePriorites(token);
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

    private void generatePriorites(String tokenStatus) {
        //RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().stop();
        // RequestQueueSingleton.getInstance(LoginActivity.this).getRequestQueue().start();
        Url=new Url(getApplicationContext());
        String url = Url.getUrl() + "orchestrator/ORCH_GetUDC";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                bodyForPrioritesRequest(tokenStatus),
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

                                Priorities.add(new T_Details("PR", elementFromRow.getString("Code"), elementFromRow.getString("Description")));
                                if(i==rowSet.length()-1){
                                    check_priority=true;

                                }
                            }
                            if(check_communne==true && check_quartier==true && check_site==true && check_Status == true && check_orders==true && check_status_Equipment==true && check_priority==true && check_types==true && check_sous_type==true){
                                Insert_Data_database();
                                Log.i("Insert", "done");

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

    private JSONObject bodyForPrioritesRequest(String token) {
        JSONObject body = new JSONObject();
        try {
            body.put("token", token);
            body.put("Product_Code", "00");
            body.put("UserDefinedCodes", "PR");
            return body;
        } catch (JSONException e) {

            Toast.makeText(this, "erreur de connexion", Toast.LENGTH_SHORT).show();
            Log.i("bodyForPrioritesRequest", e.getMessage());
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
                        Orders.get(i).getClient_number(),
                        Orders.get(i).getOrder_number(),
                        Orders.get(i).getEquipment_number(),
                        Orders.get(i).getStart_date(),
                        Orders.get(i).getDescription(),
                        Orders.get(i).getOrder_date(),
                        Orders.get(i).getPriority(),
                        Orders.get(i).getClient_name(),
                        Orders.get(i).getProblem(),
                        Orders.get(i).getLongitude(),
                        Orders.get(i).getLatitude(),
                        Orders.get(i).getstatus(),
                        Orders.get(i).getService_address(),
                        Orders.get(i).getEquipment_description(),
                        Orders.get(i).getEquipment_status(),
                        Orders.get(i).getPhone_area_code(),
                        Orders.get(i).getPhone_number(),
                        Orders.get(i).getEmail_address(),
                        Orders.get(i).getAddress_line1(),
                        Orders.get(i).getAddress_line2(),
                        Orders.get(i).getInstallation_code(),
                        Orders.get(i).getPuissance(),
                        Orders.get(i).getTarif(),
                        Orders.get(i).getAmperage(),
                        Orders.get(i).getSite(),
                        Orders.get(i).getCommune(),
                        Orders.get(i).getQuartier(),
                        Orders.get(i).getAddress_Installation(),
                        "",
                        Orders.get(i).getEdited(),
                        Db_UserId);




                if(i == Orders.size()-1){
                    Intent in  = new Intent(Page_Login.this, Page_Order.class);
                    in.putExtra("userId",Db_UserId);
                    Log.i("USERID", Db_UserId.toString());
                    startActivity(in);
                }

            }

            // insert status Udc
            Log.i("Statussize", Status.size() + "  ");
            for (int j = 0; j < Status.size(); j++) {
                Log.i("Status_workorder_j", Status.get(j).getType() +"/"+ Status.get(j).getCode() +"/"+Status.get(j).getObservation()+ "  ");
                db.add_Details(Status.get(j).getType(), Status.get(j).getCode(), Status.get(j).getObservation());

            }


            for (int j = 0; j < Types.size(); j++) {
                db.add_Details(Types.get(j).getType(), Types.get(j).getCode(), Types.get(j).getObservation());

            }


            for (int j = 0; j < Sous_Types.size(); j++) {
                db.add_Details(Sous_Types.get(j).getType(), Sous_Types.get(j).getCode(), Sous_Types.get(j).getObservation());

            }


            for (int j = 0; j < Status_Equipments.size(); j++) {
                db.add_Details(Status_Equipments.get(j).getType(), Status_Equipments.get(j).getCode(), Status_Equipments.get(j).getObservation());

            }


            for (int j = 0; j < Priorities.size(); j++) {
                db.add_Details(Priorities.get(j).getType(), Priorities.get(j).getCode(), Priorities.get(j).getObservation());

            }

            for (int j = 0; j < Site_desc.size(); j++) {
                db.add_Details(Site_desc.get(j).getType(), Site_desc.get(j).getCode(), Site_desc.get(j).getObservation());

            }

            for (int j = 0; j < Quartier_desc.size(); j++) {
                db.add_Details(Quartier_desc.get(j).getType(), Quartier_desc.get(j).getCode(), Quartier_desc.get(j).getObservation());

            }

            for (int j = 0; j < Commune_desc.size(); j++) {
                db.add_Details(Commune_desc.get(j).getType(), Commune_desc.get(j).getCode(), Commune_desc.get(j).getObservation());

            }
        }
    }

    public void NavigateToSingUp1(View view) {
        startActivity(new Intent(this,Page_Register.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}