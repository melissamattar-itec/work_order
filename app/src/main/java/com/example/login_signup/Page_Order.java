package com.example.login_signup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.Image;
import android.media.metrics.Event;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.login_signup.ui.main.Page_DataOrder;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.navigation.NavigationView;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Page_Order extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    ProgressDialog progress;
    private DrawerLayout drawer;
    ImageButton navigator;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    ListView listView;
    ArrayList<T_Orders> ListElements = new ArrayList<>();
    ArrayList<T_Orders> arraysort = new ArrayList<>();
    ArrayList<T_Orders> ListElements_filter = new ArrayList<>();
    ArrayList<T_Orders> Orders = new ArrayList<>();
    TextView headerName;
    ImageButton from;
    ImageButton to;
    ImageButton calendar;
    //    Button btnfrom , btnto;
    TextView Date;
    String filter_date = new String();
    Boolean finish = false;
    public Integer i_orders = 1;
    Adapter_Order adapter;
    Intent i;
    MyDataBaseHelper db = new MyDataBaseHelper(this);
    Integer Order_Id = 0;
    Integer User_Id;
    WebView webView;
    Url Url;
    DatePickerDialog datePickerDialog;
    String[] items;
    JSONArray data = new JSONArray();
    private SlidingUpPanelLayout mLayout;
    SlidingUpPanelLayout.PanelState p_State_List;
    Float offset = new Float(0);
    ArrayList<String> photos = new ArrayList<>();
    //ArrayList<T_Client> Clients =new ArrayList<>();
    //ArrayList<T_Client> clients_list_for_Photos =new ArrayList<>();
    public boolean check_orders = false, check_folderpath = false, check_clients = false, check_sequence_changed = true, check_missing_meter = true, check_sync = false, check_tourneevalidees = true;
    boolean finish_photo;
    boolean finish_sync = false;
    String sync_setting_on = "off";
    private EditText etsearch;
    ImageView searchimage;
    String filter = new String();
    String Formatted_date = new String();
//    Spinner dropdown;
    private MenuItem item;
    private static final String TAG = "SlideList";
    private static final String PREF_LOCALE = "locale";
    private static final String DEFAULT_LOCALE_STRING = "en_US";
    private Context context;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.nav_profile:
////                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
////                        new ProfileFragment()).commit();
//                break;

            case R.id.nav_inbox:
                Intent intent2 = new Intent(this, Page_Calendar.class);
                startActivity(intent2);
                break;
            case R.id.nav_logout:
                Intent intent1 = new Intent(this, Page_Login.class);
                startActivity(intent1);
                break;
            case R.id.nav_delete_cache:
                AlertDialog diabox = AskOption();
                diabox.show();
                break;
//            case R.id.settings:
//                Intent setting_activity = new Intent(this, Page_TimeSettings.class);
//                setting_activity.putExtra("userId", User_Id);
//                startActivity(setting_activity);
//                break;
            case R.id.preference:
                Log.i("preferences Clicked", "True");
                Intent preference_activity = new Intent(this, Page_Preferences.class);
                startActivity(preference_activity);
                break;
            case R.id.calendar:
//                Log.i("preferences Clicked", "True");
//                Intent calendar_activity = new Intent(this, Page_Calendar.class);
//                startActivity(calendar_activity);
                Log.i("Calendar Clicked", "True");
//                String url = "http://129.159.198.129:7001/bryntum_calendar/examples/basic/index.html";
                String url = "https://bryntum.com/examples/calendar/basic/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
        }
        return true;
    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Supprimer")
                .setMessage("Vous voulez supprimer ?")
                .setIcon(R.drawable.ic_message)
                .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        MyDataBaseHelper db = new MyDataBaseHelper(getApplicationContext());
                        db.delete_order(User_Id);
                        Log.i("deleted order", User_Id.toString());
                        db.delete_User(User_Id);
                        Log.i("deleted User", User_Id.toString());
                        Intent login = new Intent(getApplicationContext(), Page_Login.class);
                        startActivity(login);
                        //your deleting code
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_work_order);

        SwipeRefreshLayout swipeRefreshLayout;
        from = (ImageButton) findViewById(R.id.imgFrom);
        to = (ImageButton) findViewById(R.id.imgTo);
        //calendar = (ImageButton) findViewById(R.id.imgcalendar);
        navigator=(ImageButton) findViewById(R.id.opennav);

//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(false);
//                // User defined method to shuffle the array list items
//                shuffleListItems();
//            }
//        });




        Calendar c = Calendar.getInstance();

        Log.i("Current time => ", c.getTime().toString());

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = df.format(c.getTime());
        filter_date = formattedDate;
        Date = (TextView) findViewById(R.id.Date);


        Date.setText(formattedDate.substring(0,4)+"-"+formattedDate.substring(4,6)+"-"+formattedDate.substring(6,8));
        Date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filter(etsearch.getText().toString() , filter_date);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(Page_Order.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
//

                                int month = monthOfYear + 1;
                                String fm = "" + month;
                                String fd = "" + dayOfMonth;
                                if (month < 10) {
                                    fm = "0" + month;
                                }
                                if (dayOfMonth < 10) {
                                    fd = "0" + dayOfMonth;
                                }

                                filter_date = year+fm+fd;
                                Date.setText(year+"-"+fm+"-"+fd);
                                Log.i("filter_date", filter_date);
                                //etsearch.setText(etsearch.getText());
                                filter(etsearch.getText().toString() , filter_date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        from.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Log.i("CLicked_from" , "Ok");
                                                Log.i("filter_date" , filter_date+ " ");

                                                String oldDate = filter_date;
                                                System.out.println("Date before Substraction: "+oldDate);
                                                //Specifying date format that matches the given date
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                                                Calendar c = Calendar.getInstance();
                                                try{
                                                    //Setting the date to the given date
                                                    c.setTime(sdf.parse(oldDate));
                                                }catch(ParseException e){
                                                    e.printStackTrace();
                                                }

                                                //Number of Days to add
                                                c.add(Calendar.DAY_OF_MONTH, -1);
                                                //Date after adding the days to the given date
                                                String newDate = sdf.format(c.getTime());
                                                //Displaying the new Date after addition of Days
                                                System.out.println("Date after substraction: "+newDate);

                                                filter_date =newDate;
                                                Log.i("yesterday",filter_date);
                                                Date.setText(newDate.substring(0,4)+"-"+newDate.substring(4,6)+"-"+newDate.substring(6,8));
                                            }
                                        }
                );


//        calendar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // calender class's instance and get current date , month and year from calender
//                final Calendar c = Calendar.getInstance();
//                int mYear = c.get(Calendar.YEAR); // current year
//                int mMonth = c.get(Calendar.MONTH); // current month
//                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
//
//                datePickerDialog = new DatePickerDialog(Page_Order.this,
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @RequiresApi(api = Build.VERSION_CODES.O)
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
////
//
//                                int month = monthOfYear + 1;
//                                String fm = "" + month;
//                                String fd = "" + dayOfMonth;
//                                if (month < 10) {
//                                    fm = "0" + month;
//                                }
//                                if (dayOfMonth < 10) {
//                                    fd = "0" + dayOfMonth;
//                                }
//
//                                filter_date = year+fm+fd;
//                                Date.setText(year+fm+fd);
//                                Log.i("filter_date", filter_date);
//                                //etsearch.setText(etsearch.getText());
//                                filter(etsearch.getText().toString() , filter_date);
//
//                            }
//                        }, mYear, mMonth, mDay);
//                datePickerDialog.show();
//            }
//        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /// final Calendar c = Calendar.getInstance();
                Log.i("filter_date" , filter_date+ " ");

                String oldDate = filter_date;
                System.out.println("Date before Addition: "+oldDate);
                //Specifying date format that matches the given date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Calendar c = Calendar.getInstance();
                try{
                    //Setting the date to the given date
                    c.setTime(sdf.parse(oldDate));
                }catch(ParseException e){
                    e.printStackTrace();
                }

                //Number of Days to add
                c.add(Calendar.DAY_OF_MONTH, 1);
                //Date after adding the days to the given date
                String newDate = sdf.format(c.getTime());
                //Displaying the new Date after addition of Days
                System.out.println("Date after addition: "+newDate);

                filter_date =newDate;
                Log.i("tomorrow",filter_date);
                Date.setText(newDate.substring(0,4)+"-"+newDate.substring(4,6)+"-"+newDate.substring(6,8));

            }
        });

        Url = new Url(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        headerName = (TextView) findViewById(R.id.headerName);


        //ArrayList<T_Orders> itemsList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listview);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent details=new Intent(Page_Order.this, Page_DataOrder.class);
                details.putExtra("UserId",User_Id);
                details.putExtra("Order_Id",arraysort.get(position).getId());
                startActivity(details);
            }
        });
        //itemsList = sortAndAddSections(ListElements);
        //adapter = new Adapter_Order(Page_Order.this,0, itemsList);
        //listView.setAdapter(adapter);

        webView = (WebView) findViewById(R.id.map);
        webView.setWebChromeClient(new WebChromeClient());
        //searchimage = (ImageView) findViewById(R.id.search_boutton);


//        dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        //items = new String[]{"None", "TYPE", "ORDER NUMBER"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        //ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
//        dropdown.setAdapter(adapter1);
//        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (position) {
//                    case 1:
//                        if (items[1].equals(dropdown.getSelectedItem().toString().toUpperCase())) {
//                            etsearch.setVisibility(View.VISIBLE);
//                            etsearch.setHint("Type");
//                            filter = "TYPE";
//                        }
//                        break;
//
//                    case 2:
//                        if (items[2].equals(dropdown.getSelectedItem().toString().toUpperCase()))
//                            etsearch.setVisibility(View.VISIBLE);
//                        etsearch.setHint("Order Number");
//                        filter = "ORDER NUMBER";
//                        break;
//
//                    default:
//                        if ("None".equals(dropdown.getSelectedItem().toString()))
//                            etsearch.setVisibility(View.INVISIBLE);
//                        filter = "";
//
//                        break;
//                }
//            }

//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


//        searchimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showCustomDialog();
//
//            }
//        });


        etsearch = (EditText) findViewById(R.id.etSearch);
        etsearch.setText("");

        etsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

              filter(s.toString() , filter_date);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        WebSettings websettings = webView.getSettings();
        websettings.setJavaScriptEnabled(true);
        Log.i("packagecodepath", getPackageCodePath() + "");


        try {
            Intent intent = getIntent();
            User_Id = intent.getIntExtra("userId", 0);
            Log.i("userid order", User_Id + "mm");
        } catch (Exception e) {

        }

        Log.i("Location", "ana bi aleb el Order Activity");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.headerName);
            i = getIntent();
            String navname = i.getStringExtra("Name");
            txtProfileName.setText(navname);
        } catch (Exception e) {

        }

        navigator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    drawer.openDrawer(GravityCompat.START);

            }
        });

        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(Page_Order.this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerToggle.setHomeAsUpIndicator(R.drawable.icon_nav);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(true);
        generateListOrder();


        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setAnchorPoint(0.5f);
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        //mLayout.setElevation(0.5f);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

                if (slideOffset != 0) {
                    offset = slideOffset;
                }
                p_State_List = mLayout.getPanelState();
                Log.i(TAG, "panel state  " + mLayout.getPanelState() + " slideoffset  " + slideOffset + " offset " + offset);


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "previous state / new state   " + previousState + "/" + newState);


            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }

        });


    }

    private void filter(String s, String filter_date) {
        Log.i("filter_s" , s);
        arraysort.clear();
        for (int i=0 ; i<ListElements.size() ; i++)
        {
            String type = ListElements.get(i).getType();
            String order_number = String.valueOf(ListElements.get(i).getOrder_number());
            Log.i("typeListelements" , type + "");
            Log.i("ordernumberListelements" , order_number + "");
            Boolean test = ((type.toLowerCase().contains(s.toLowerCase()) || type.toUpperCase().contains(s.toUpperCase())) || order_number.startsWith(s)) && filter_date.equals(ListElements.get(i).getStart_date());
            if(test){
                Log.i("filterdate" , filter_date);
                Log.i("test" , type);
                Log.i("test" , order_number);
                arraysort.add(ListElements.get(i));
            }
        }
        Log.i("arraysort_size" , arraysort.size() + "");
        adapter =new Adapter_Order(Page_Order.this , 0 , arraysort);
        listView.setAdapter(adapter);
    }


    // Refresh the list view on each drag
//    private void shuffleListItems() {
//        // Shuffling the arraylist items on the basis of system time
//        ArrayList<T_Orders> arraysort = new ArrayList<>();
//        MyDataBaseHelper db=new MyDataBaseHelper(getApplicationContext());
//        ListElements=db.readOrder(User_Id);
//
//        for (int i = 0; i < ListElements.size(); i++) {
//            T_Orders a = new T_Orders(ListElements.get(i).getId(),ListElements.get(i).getUser_id(),ListElements.get(i).getOrder_number(),ListElements.get(i).getPriority(),ListElements.get(i).getType(),ListElements.get(i).getDescription(),ListElements.get(i).getOrder_date(),ListElements.get(i).getStart_date(),ListElements.get(i).getEstiamted_hour(),ListElements.get(i).getAsset_number());
//            arraysort.add(a);
//
//        }
//        Collections.shuffle(arraysort, new Random(System.currentTimeMillis()));
//        adapter = new Adapter_Order(Page_Order.this, 0, arraysort);
//        listView.setAdapter(adapter);
//    }
//    private ArrayList<T_Orders>  getItems(){
//
//        ArrayList<T_Orders> items = new ArrayList<>();
//        items.add(new T_Orders(1,1,1992005,"a","A","hello","2021-11-12","2021-11-13",7,5));
//        items.add(new T_Orders(2,1,1992006,"a","A","hello","2021-11-12","2021-11-13",7,5));
//        items.add(new T_Orders(3,1,1992007,"a","A","hello","2021-11-12","2021-11-14",7,5));
//        items.add(new T_Orders(4,1,1992008,"a","A","hello","2021-11-12","2021-11-15",7,5));
//
//
//
//        return  items;
//    }


//    private ArrayList sortAndAddSections(ArrayList<T_Orders> itemList)
//    {
//
//        ArrayList<T_Orders> tempList = new ArrayList<>();
//        //First we sort the array
//        Collections.sort(itemList);
//
//        //Loops thorugh the list and add a section before each sectioncell start
//        String header = "";
//        for(int i = 0; i < itemList.size(); i++)
//        {
//            //If it is the start of a new section we create a new listcell and add it to our array
//            if(!(header.equals(itemList.get(i).getStart_date()))) {
//                T_Orders sectionCell = new T_Orders(0, 0,0,null,null,null,null,itemList.get(i).getStart_date(),0,0);
//                sectionCell.setToSectionHeader();
//                tempList.add(sectionCell);
//                header = itemList.get(i).getStart_date();
//            }
//            tempList.add(itemList.get(i));
//        }
//
//        return tempList;
//    }


//    private ArrayList<T_Orders>  getItems(){
//
//        ArrayList<T_Orders> items = new ArrayList<>();
//        items.add(new T_Orders(1,1,158745,"U","D","Data","2021-10-13","2021-10-14",7,8));
//        items.add(new T_Orders(1,1,158745,"","D","Data","2021-10-13","2021-10-14",7,8));
//        items.add(new T_Orders(1,1,158745,"U","D","Data","2021-10-13","2021-10-14",7,8));
//        items.add(new T_Orders(1,1,158745,"U","D","Data","2021-10-13","2021-10-14",7,8));
//        items.add(new T_Orders(1,1,158745,"U","D","Data","2021-10-13","2021-10-14",7,8));
//
//
//        items.add(new T_Orders(1,1,158745,"","D","Data","2021-10-14","2021-10-14",7,8));
//        items.add(new T_Orders(1,1,158745,"","D","Data","2021-10-14","2021-10-14",7,8));
//        items.add(new T_Orders(1,1,158745,"U","D","Data","2021-10-14","2021-10-14",7,8));
//
//        return  items;
//    }


//    private ArrayList sortAndAddSections(ArrayList<T_Orders> itemList)
//    {
//
//        ArrayList<T_Orders> tempList = new ArrayList<>();
//        //First we sort the array
//        Collections.sort(itemList);
//
//        //Loops thorugh the list and add a section before each sectioncell start
//        String header = "";
//        for(int i = 0; i < itemList.size(); i++)
//        {
//            //If it is the start of a new section we create a new listcell and add it to our array
//            if(!(header.equals(itemList.get(i).getOrder_date()))) {
//                T_Orders sectionCell = new T_Orders(null,null,null,null,null,null,itemList.get(i).getOrder_date(),null,null,null);
//                sectionCell.setToSectionHeader();
//                tempList.add(sectionCell);
//                header = itemList.get(i).getOrder_date();
//            }
//            tempList.add(itemList.get(i));
//        }
//
//        return tempList;
//    }

//    private void initToolbar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_menu);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Custom");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        //Tools.setSystemBarColor(this);
//    }

//    private void displayDataResult(Model_Event event) {
//        ((TextView) findViewById(R.id.tv_Type)).setText(event.Type);
//        ((TextView) findViewById(R.id.tv_location)).setText(event.location);
//        ((TextView) findViewById(R.id.tv_from)).setText(event.from);
//        ((TextView) findViewById(R.id.tv_to)).setText(event.to);
//        ((TextView) findViewById(R.id.tv_allday)).setText(event.is_allday.toString());
//        ((TextView) findViewById(R.id.tv_timezone)).setText(event.timezone);
//    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

//        MyDataBaseHelper db = new MyDataBaseHelper(Page_Order.this);
//       // ArrayList<T_Client> clients_for_sync = db.readClients_forSync(User_Id);
//        ArrayList<T_Orders> tournee_validees = db.readOrder_Validee(User_Id);
//        if ( tournee_validees.size() != 0) {
//////            Log.i("client  ", clients_for_sync.size() + " size");
//            getMenuInflater().inflate(R.menu.sync_menu_wait, menu);
//        } else {
////            Log.i("client size ", "0");
//            getMenuInflater().inflate(R.menu.sync_menu, menu);
//        }
//

        return true;

//
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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // close app
            // close app
        }
    }

    @Override
    public void onMapReady(GoogleMap var1) {

    }

    public static Date addDay(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);
        return cal.getTime();
    }
    public static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }
    public static Date addYear(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, i);
        return cal.getTime();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateListOrder() {

        MyDataBaseHelper db = new MyDataBaseHelper(Page_Order.this);
        try {
            ArrayList<T_Orders> orders = db.readOrder(User_Id);
            ListElements = orders;
            Log.i("oncreatefilterdate" , filter_date + "");
            filter("" ,filter_date);
            for (int i = 0; i < orders.size(); i++) {
                if (i == 0) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    } else
                        // regideo maktab
                        // webView.loadUrl("http://192.168.100.60:83/meter_reading/meter_reading.html?year=" + tournees.get(i).getYear() + "&month=" + tournees.get(i).getPeriod() + "&round=" + tournees.get(i).getTournee_ID());
                        //Log.i("url", "http://192.168.100.60:83/meter_reading/meter_reading.html?year=" + tournees.get(i).getYear() + "&month=" + tournees.get(i).getPeriod() + "&round=" + tournees.get(i).getTournee_ID());

                        // regideso africa
                        //webView.loadUrl("http://102.134.98.158:7001/meter_reading/meter_reading.html?year=" + tournees.get(i).getYear() + "&month=" + tournees.get(i).getPeriod() + "&round=" + tournees.get(i).getTournee_ID());
                        //Log.i("url", "http://102.134.98.158:7001/meter_reading/meter_reading.html?year=" + tournees.get(i).getYear() + "&month=" + tournees.get(i).getPeriod() + "&round=" + tournees.get(i).getTournee_ID());


                        // sbee
                        Url = new Url(getApplicationContext());
                    webView.loadUrl(Url.get_Url_Map() + "?year=" + "2021" + "&month=" + "7" + "&round=" + "2041633");
                    webView.loadUrl(Url.get_Url_Map() + "?year=" + "2021" + "&month=" + "7" + "&round=" + "2041633");
                    Log.i("url", Url.get_Url_Map() + "?year=" + "2021" + "&month=" + "7" + "&round=" + "2041633");

                }

            }
            db.close();

//            Adapter_Order adapter = new Adapter_Order(Page_Order.this, 0, ListElements);
//            listView.setAdapter(adapter);
//            listView.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

//    private ArrayList sortAndAddSections(ArrayList<T_Orders> itemList)
//    {
//
//        ArrayList<T_Orders> tempList = new ArrayList<>();
//        //First we sort the array
//        Collections.sort(itemList);
//
//        //Loops thorugh the list and add a section before each sectioncell start
//        String header = "";
//        for(int i = 0; i < itemList.size(); i++)
//        {
//            //If it is the start of a new section we create a new listcell and add it to our array
//            if(!(header.equals(itemList.get(i).getOrder_date()))) {
//                T_Orders sectionCell = new T_Orders(null,null, null,null,null,null,itemList.get(i).getOrder_date(),null,null,null);
//                sectionCell.setToSectionHeader();
//                tempList.add(sectionCell);
//                header = itemList.get(i).getOrder_date();
//            }
//            tempList.add(itemList.get(i));
//        }
//
//        return tempList;
//    }


//    private void showCustomDialog() {
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
//        dialog.setContentView(R.layout.dialog_event);
//        dialog.setCancelable(true);
//
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(dialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//        final Button spn_from_date = (Button) dialog.findViewById(R.id.spn_from_date);
//        //final Button spn_from_time = (Button) dialog.findViewById(R.id.spn_from_time);
//        final Button spn_to_date = (Button) dialog.findViewById(R.id.spn_to_date);
//        // final Button spn_to_time = (Button) dialog.findViewById(R.id.spn_to_time);
////        final TextView tv_email = (TextView) dialog.findViewById(R.id.tv_Type);
//        final EditText et_location = (EditText) dialog.findViewById(R.id.et_location);
//        final CheckBox cb_allday = (CheckBox) dialog.findViewById(R.id.cb_allday);
//        // final Spinner spn_timezone = (Spinner) dialog.findViewById(R.id.spn_timezone);
//
//        spn_from_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // calender class's instance and get current date , month and year from calender
//                final Calendar c = Calendar.getInstance();
//                int mYear = c.get(Calendar.YEAR); // current year
//                int mMonth = c.get(Calendar.MONTH); // current month
//                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
//
//                datePickerDialog = new DatePickerDialog(Page_Order.this,
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @RequiresApi(api = Build.VERSION_CODES.O)
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//                                // set day of month , month and year value in the edit text
//
////                                ArrayList<T_Orders> arraysort = new ArrayList<>();
////                                MyDataBaseHelper db=new MyDataBaseHelper(getApplicationContext());
////                                ListElements=db.readOrder(User_Id);
////                                Log.i("size of listelements1", ListElements.size() + "");
////                                for (int i = 0; i < ListElements.size(); i++) {
////                                    String data1 = ListElements.get(i).getStart_date();
////                                    if ( data1.contains(spn_from_date.getText().toString()))  {
////                                        T_Orders or= new T_Orders(ListElements.get(i).getId(),ListElements.get(i).getUser_id(),ListElements.get(i).getOrder_number(),ListElements.get(i).getPriority(),ListElements.get(i).getType(),ListElements.get(i).getDescription(),ListElements.get(i).getOrder_date(),ListElements.get(i).getStart_date(),ListElements.get(i).getEstiamted_hour(),ListElements.get(i).getAsset_number());
////                                        arraysort.add(or);
////                                    }
////                                }
////                                ListElements=arraysort;
////                                adapter = new Adapter_Order(Page_Order.this, 0, arraysort);
////                                listView.setAdapter(adapter);
//
//                                spn_from_date.setText(year + "-"
//                                        + (monthOfYear + 1) + "-" + dayOfMonth);
//
////                               Date.setText(year + "-"
////                                       + (monthOfYear + 1) + "-" + dayOfMonth);
//                                Log.i("Dialog From Date", spn_from_date.toString());
//
//                            }
//                        }, mYear, mMonth, mDay);
//                datePickerDialog.show();
//            }
//        });
//
//        spn_to_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // calender class's instance and get current date , month and year from calender
//                final Calendar c = Calendar.getInstance();
//                int mYear = c.get(Calendar.YEAR); // current year
//                int mMonth = c.get(Calendar.MONTH); // current month
//                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
//
//                datePickerDialog = new DatePickerDialog(Page_Order.this,
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//                                // set day of month , month and year value in the edit text
////                                spn_to_date.setText(year + "-"
////                                        + (monthOfYear + 1) + "-" + dayOfMonth);
////                                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
////                                String strdate = dateFormat.format(c);
////                                spn_to_date.setText(strdate);
////                                Log.i("Dialog to Date" , strdate);
//                                spn_to_date.setText(year + "-"
//                                        + (monthOfYear + 1) + "-" + dayOfMonth);
//
//
//                            }
//                        }, mYear, mMonth, mDay);
//                datePickerDialog.show();
//
//            }
//        });
//
//        String[] timezones = getResources().getStringArray(R.array.timezone);
//        ArrayAdapter<String> array = new ArrayAdapter<>(this, R.layout.simple_spinner_item, timezones);
//        array.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        //spn_timezone.setAdapter(array);
//        //spn_timezone.setSelection(0);
//
//        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        ((Button) dialog.findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Model_Event event = new Model_Event();
//                event.Type = et_location.getText().toString();
//                event.from = spn_from_date.getText().toString();
//                event.to = spn_to_date.getText().toString();
//                event.is_allday = cb_allday.isChecked();
//                //event.timezone = spn_timezone.getSelectedItem().toString();
//                etsearch.setText(et_location.getText().toString());
//
//                Date date = Calendar.getInstance().getTime();
//                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                String strDate = dateFormat.format(date);
//
//
////                spn_from_date.setText(spn_from_date.getText().toString());
//                spn_from_date.setText(strDate);
//
////                displayDataResult(event);
//
//
//                if (cb_allday.isChecked()) {
//                    spn_from_date.setText(spn_from_date.getText().toString());
//                    Log.i("Spin From Date", spn_from_date.getText().toString());
//                    spn_to_date.setText(spn_to_date.getText().toString());
//                    Log.i("Spin To Date", spn_to_date.getText().toString());
//                }
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//        dialog.getWindow().setAttributes(lp);
//    }


//    public void FilterData(){
//        String Start = from.getText().toString().trim();
//        String EndDate =SortDate2.getText().toString().trim();
//
//
//        if(!Start.equals("")||!EndDate.equals("")||!Start.equals(null)||!EndDate.equals(null)){
//            try {
//                String dateFormatter=new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//                Date strDate = dateFormatter.parse(Start);
//                Date endDate = dateFormatter.parse(EndDate);
//                adapter.filterDateRange(strDate,endDate);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }else {
//
//        }
//    }

//    public String getIncrementedDate(String selectedDate) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar c = Calendar.getInstance();
//        c.setTime(sdf.parse(selectedDate));
//        c.add(Calendar.DATE, 1);  // number of days to add
//        selectedDate = sdf.format(c.getTime());  // selectedDate is now the new date
//        return selectedDate;
//    }
}
