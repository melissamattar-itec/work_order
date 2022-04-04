package com.example.login_signup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//import com.anychart.AnyChart;
//import com.anychart.AnyChartView;
//import com.anychart.charts.Pie;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.navigation.NavigationView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Page_Order extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ProgressDialog progress;
    private DrawerLayout drawer;
    ImageButton navigator;
    ImageButton refresh;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    ListView listView;
    //AnyChartView anyChartView;
    ArrayList<T_Orders> ListElements = new ArrayList<>();
    ArrayList<T_Orders> arraysort = new ArrayList<>();
    ArrayList<T_Orders> ListElements_filter = new ArrayList<>();
    ArrayList<T_Orders> Orders = new ArrayList<>();
    TextView headerName;
    ImageButton from;
    ImageButton to;
    ImageButton calendar;
    LinearLayout values_of_chart , pie;
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
    private TextView txtinfo;
    LinearLayout lvOne, lvTwo, lvThree, lvFour, lvFive, lvparent;
    TextView txtOne, txtTwo, txtThree, txtFour, txtFive;
    Button btnundo, btnsave;
    PieView pieView;
    Uri outputFileUri;
    OutputStream outStream = null;
    ArrayList<String> priorities_values = new ArrayList<>();
    ArrayList<Integer> percentage_priorities=new ArrayList<>();
    ArrayList<Integer> numbers=new ArrayList<>();
    ArrayList<Integer> colors=new ArrayList<>();
    PieChartView pieChartView;
    ArrayList<com.example.login_signup.T_Details> priorities;
    List<SliceValue> pieData = new ArrayList<>();
    int green = Color.rgb(0, 130, 0);
    int red = Color.rgb(230, 0, 00);
    int orange = Color.rgb(230,172,0);
    int black = Color.rgb(0, 0, 0);
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
        //anyChartView = findViewById(R.id.any_chart_view);
        from = (ImageButton) findViewById(R.id.imgFrom);
        to = (ImageButton) findViewById(R.id.imgTo);
        //calendar = (ImageButton) findViewById(R.id.imgcalendar);
        navigator=(ImageButton) findViewById(R.id.opennav);
        refresh=(ImageButton) findViewById(R.id.refresh);
        //pieView = (PieView) findViewById(R.id.pie_view);

        lvparent = (LinearLayout) findViewById(R.id.lvparent);
        values_of_chart = (LinearLayout) findViewById(R.id.values_of_chart);
        //pie = (LinearLayout) findViewById(R.id.pie);


        colors.add(black);
        colors.add(orange);
        colors.add(green);
        colors.add(red);

        lvparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        db=new MyDataBaseHelper(this);
        priorities_values = new ArrayList<>();
        priorities = db.readDetails("PR");
        for(int i=0 ; i<priorities.size();i++) {
            priorities_values.add(i,priorities.get(i).getObservation());
        }

        LinearLayout layout = new LinearLayout(getApplicationContext());
        for (int i = 0; i < priorities_values.size(); i++) {

            if (i == 0 || i % 2 == 0) {
                if (i != 0) {
                    values_of_chart.addView(layout);
                }
                layout = new LinearLayout(getApplicationContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                if (i == 0)
                    layoutParams.setMargins(0, 80, 0, 0);
                else layoutParams.setMargins(0, 10, 0, 0);
                layout.setLayoutParams(layoutParams);
                layout.setOrientation(LinearLayout.HORIZONTAL);
            }

            LinearLayout layout1 = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(40, 40);
            layoutParams1.setMargins(20, 0, 0, 0);
            layoutParams1.gravity = Gravity.CENTER_VERTICAL;
            layout1.setBackgroundColor(colors.get(i));
            layout1.setLayoutParams(layoutParams1);
            layout1.setOrientation(LinearLayout.HORIZONTAL);
            layout1.setPadding(2, 2, 2, 2);

            layout.addView(layout1);

            TextView t = new TextView(getApplicationContext());
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams2.gravity = Gravity.CENTER_VERTICAL;
            t.setLayoutParams(layoutParams2);
            t.setPadding(2, 2, 2, 2);
            t.setText(priorities_values.get(i));
            t.setGravity(Gravity.CENTER_VERTICAL);
            t.setTextSize(20);

            layout.addView(t);

            if (i % 2 != 0 && i == priorities_values.size() - 1) {
                Log.i("ok", i + "");
                values_of_chart.addView(layout);
            }
        }

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                // User defined method to shuffle the array list items
                filter(etsearch.getText().toString() , filter_date, "" );
            }
        });




        Calendar c = Calendar.getInstance();

        Log.i("Current time => ", c.getTime().toString());

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = df.format(c.getTime());

        try {
            Intent intent = getIntent();
            User_Id = intent.getIntExtra("userId", 0);
            Log.i("userid order", User_Id + "mm");
            filter_date = intent.getStringExtra("startdate");
            Log.i("intent.getStringExtra(\"date\")", filter_date + "");

        } catch (Exception e) {
            filter_date = formattedDate;
        }

        Date = (TextView) findViewById(R.id.Date);



        Date.setText(formattedDate.substring(0,4)+"-"+formattedDate.substring(4,6)+"-"+formattedDate.substring(6,8));
        Date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filter(etsearch.getText().toString() , filter_date , "");

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
                                filter(etsearch.getText().toString() , filter_date, "");

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

              filter(s.toString() , filter_date, "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Log.i("packagecodepath", getPackageCodePath() + "");




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
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter(etsearch.getText().toString() , filter_date, "" );
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

        //setupPieChart();

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


    }

    private void filter(String s, String filter_date ,  String priority) {

        if (priority.equals("")) {
            Log.i("filter_s", s);
            arraysort.clear();
            for (int i = 0; i < ListElements.size(); i++) {
                String type = ListElements.get(i).getType();
                String order_number = String.valueOf(ListElements.get(i).getOrder_number());
                Log.i("typeListelements", type + "");
                Log.i("ordernumberListelements", order_number + "");
                Boolean test = ((type.toLowerCase().contains(s.toLowerCase()) || type.toUpperCase().contains(s.toUpperCase())) || order_number.startsWith(s)) && filter_date.equals(ListElements.get(i).getStart_date());
                if (test) {
                    Log.i("filterdate", filter_date);
                    Log.i("test", type);
                    Log.i("test", order_number);
                    arraysort.add(ListElements.get(i));
                }
            }
            Log.i("arraysort_size", arraysort.size() + "");
            adapter = new Adapter_Order(Page_Order.this, 0, arraysort);
            listView.setAdapter(adapter);
            //set_chart();

            pieData.clear();
            numbers = new ArrayList<>();
            for (int i = 0; i < priorities.size(); i++) {
                numbers.add(i, 0);
            }
            Log.i("ListElemetssize", arraysort.size() + "");
            Log.i("pieHelperArrayListsize", pieData.size() + "");
            Log.i("priorities_values", priorities_values.size() + "");
            Log.i("numbers", numbers.size() + "");
            for (int i = 0; i < arraysort.size(); i++) {
                Log.i("ListElemeti", arraysort.get(i).getPriority() + "/" + priorities_values.get(0));
                if (arraysort.get(i).getPriority().equals(db.getDetails_code("PR", priorities_values.get(0)).trim())) {
                    numbers.set(0, numbers.get(0) + 1);
                } else if (arraysort.get(i).getPriority().equals(db.getDetails_code("PR", priorities_values.get(1)).trim())) {
                    numbers.set(1, numbers.get(1) + 1);
                } else if (arraysort.get(i).getPriority().equals(db.getDetails_code("PR", priorities_values.get(2)).trim())) {
                    numbers.set(2, numbers.get(2) + 1);
                } else if (arraysort.get(i).getPriority().equals(db.getDetails_code("PR", priorities_values.get(3)).trim())) {
                    numbers.set(3, numbers.get(3) + 1);
                }
            }
            if (arraysort.size() != 0) {
                for (int i = 0; i < priorities_values.size(); i++) {
                    if (numbers.get(i).equals(0)) {
                        pieData.add(new SliceValue(numbers.get(i) * 100 / arraysort.size(), colors.get(i)).setLabel(""));
                    } else {
                        pieData.add(new SliceValue(numbers.get(i) * 100 / arraysort.size(), colors.get(i)).setLabel(priorities_values.get(i) + " / " + numbers.get(i) * 100 / arraysort.size() + "%"));
                    }
                }
            }

            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setSlicesSpacing(0);
            pieChartData.setHasLabels(true);
            pieChartData.setHasLabels(true).setValueLabelTextSize(14);
            //pieChartData.setHasCenterCircle(true).setCenterText1("priorités").setCenterText1FontSize(20).setCenterText1Color(black);

//            if(){
//
//            }

            pieChartView = findViewById(R.id.chart);

            pieChartView.setPieChartData(pieChartData);
            pieChartView.setOnValueTouchListener(new PieChartOnValueSelectListener() {
                @Override
                public void onValueSelected(int arcIndex, SliceValue value) {
                    db=new MyDataBaseHelper(getApplicationContext());
                    String priority_=String.valueOf( value.getLabel()).substring(0, String.valueOf( value.getLabel()).indexOf(" / "));
                    Log.i("index", arcIndex + "/" +priority_  );
                    Log.i("priority", priority_);
                    Log.i("priority_code", db.getDetails_code("PR",priority_).trim() + "");
                    filter(etsearch.getText().toString() , filter_date, db.getDetails_code("PR",priority_).trim() );
                }

                @Override
                public void onValueDeselected() {
                    filter(etsearch.getText().toString() , filter_date,"" );

                }
            });
            pieChartView.setChartRotationEnabled(false);

            pieChartView.setPieChartData(pieChartData);


//        PieView pieview1 = new PieView(getApplicationContext());
            //set(pieView);
        }
        else {
            Log.i("filter_s", s);
            arraysort.clear();
            for (int i = 0; i < ListElements.size(); i++) {
                String type = ListElements.get(i).getType();
                String order_number = String.valueOf(ListElements.get(i).getOrder_number());
                Log.i("typeListelements", type + "");
                Log.i("ordernumberListelements", order_number + "");
                Boolean test = ((type.toLowerCase().contains(s.toLowerCase()) || type.toUpperCase().contains(s.toUpperCase())) || order_number.startsWith(s)) && filter_date.equals(ListElements.get(i).getStart_date())  && ListElements.get(i).getPriority().equals(priority);
                if (test) {
                    Log.i("filterdate", filter_date);
                    Log.i("test", type);
                    Log.i("test", order_number);
                    arraysort.add(ListElements.get(i));
                }
            }
            Log.i("arraysort_size", arraysort.size() + "");
            adapter = new Adapter_Order(Page_Order.this, 0, arraysort);
            listView.setAdapter(adapter);
        }
    }

    private void set_chart() {

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

    public void SaveImage() {
        lvparent.buildDrawingCache();
        Bitmap bm = lvparent.getDrawingCache();

        try {
            File root = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "PieChartsExample" + File.separator);
            root.mkdirs();
            File sdImageMainDirectory = new File(root, "piechart.jpg");
            outputFileUri = Uri.fromFile(sdImageMainDirectory);
            outStream = new FileOutputStream(sdImageMainDirectory);
        } catch (Exception e) {
            Toast.makeText(this, "Error occured. Please try again later.",
                    Toast.LENGTH_SHORT).show();
        }

        try {
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            Toast.makeText(this, "Created successfully!", Toast.LENGTH_SHORT)
                    .show();
        } catch (Exception e) {
        }
    }

    private void set(PieView pieView) {
        //pie.removeAllViews();
//        pieView = new PieView(getApplicationContext());
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
        ArrayList<com.example.login_signup.T_Details> priorities = db.readDetails("PR");
        priorities_values = new ArrayList<>();
        numbers=new ArrayList<>();
        for(int i=0 ; i<priorities.size();i++) {
            priorities_values.add(i,priorities.get(i).getObservation());
            numbers.add(i,0);
        }



        Log.i("ListElemetssize", arraysort.size()+"");
        Log.i("pieHelperArrayListsize", pieHelperArrayList.size()+"");
        Log.i("priorities_values", priorities_values.size()+"");
        Log.i("numbers", numbers.size()+"");

        for (int i=0;i<arraysort.size();i++){
            Log.i("ListElemeti", arraysort.get(i).getPriority()+"/" + priorities_values.get(0));
            if(arraysort.get(i).getPriority().equals(db.getDetails_code("PR", priorities_values.get(0)).trim())){
                numbers.set(0,numbers.get(0)+1);
            }
            else if (arraysort.get(i).getPriority().equals(db.getDetails_code("PR", priorities_values.get(1)).trim())){
                numbers.set(1,numbers.get(1)+1);
            }
            else if(arraysort.get(i).getPriority().equals(db.getDetails_code("PR", priorities_values.get(2)).trim())){
                numbers.set(2,numbers.get(2)+1);
            }
            else if(arraysort.get(i).getPriority().equals(db.getDetails_code("PR", priorities_values.get(3)).trim())){
                numbers.set(3,numbers.get(3)+1);
            }
        }

        for(int i=0 ; i<priorities.size();i++) {
            Log.i("priorities"+i,priorities_values.get(i) + "/"+numbers.get(i));
        }
if(arraysort.size()!=0) {

    values_of_chart.removeAllViews();
    for (int i = 0; i < priorities_values.size(); i++) {
        percentage_priorities.add(numbers.get(i) * 100 / arraysort.size());
//        PieHelper pieHelper = new PieHelper(percentage_priorities.get(i), colors.get(i));
//        pieHelperArrayList.add(pieHelper);
    }


    pieHelperArrayList.add(new PieHelper(percentage_priorities.get(0), colors.get(0)));
    pieHelperArrayList.add(new PieHelper(percentage_priorities.get(1), colors.get(1)));
    pieHelperArrayList.add(new PieHelper(percentage_priorities.get(2), colors.get(2)));
    pieHelperArrayList.add(new PieHelper(percentage_priorities.get(3), colors.get(3)));

//
    LinearLayout layout = new LinearLayout(getApplicationContext());
    for (int i = 0; i < priorities_values.size(); i++) {

        if (i == 0 || i % 2 == 0) {
            if (i != 0) {
                values_of_chart.addView(layout);
            }
            layout = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i == 0)
                layoutParams.setMargins(0, 80, 0, 0);
            else layoutParams.setMargins(0, 10, 0, 0);
            layout.setLayoutParams(layoutParams);
            layout.setOrientation(LinearLayout.HORIZONTAL);
        }

        LinearLayout layout1 = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(40, 40);
        layoutParams1.setMargins(20, 0, 0, 0);
        layoutParams1.gravity = Gravity.CENTER_VERTICAL;
        layout1.setBackgroundColor(colors.get(i));
        layout1.setLayoutParams(layoutParams1);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.setPadding(2, 2, 2, 2);

        layout.addView(layout1);


        TextView t = new TextView(getApplicationContext());
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams2.gravity = Gravity.CENTER_VERTICAL;
        t.setLayoutParams(layoutParams2);
        t.setPadding(2, 2, 2, 2);
        t.setText(priorities_values.get(i));
        t.setGravity(Gravity.CENTER_VERTICAL);
        t.setTextSize(20);

        layout.addView(t);

        if (i % 2 != 0 && i == priorities_values.size() - 1) {
            Log.i("ok", i + "");
            values_of_chart.addView(layout);
        }
    }

    Log.i("pieHelperArrayListsizesize", pieHelperArrayList.size()+"");
    for(int i=0;i<pieHelperArrayList.size();i++){
        Log.i("pieHelperArrayList percent/color" + i, pieHelperArrayList.get(i).getPercentStr()+"/" + pieHelperArrayList.get(i).getColor());
    }

    pieView.setDate(pieHelperArrayList);
    pieView.setOnPieClickListener(new PieView.OnPieClickListener() {
        @Override
        public void onPieClick(int index) {
//                if (index != PieView.NO_SELECTED_INDEX) {
//                    txtinfo.setText(percentage[index] + "% owns "
//                            + petNames[index] + ".");
//                } else {
//                    txtinfo.setText("No selected pie");
//                }
        }
    });

   //pie.addView(pieView);
}
    }
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
            Log.i("filter" , filter_date + "");
            filter("" ,filter_date, "");
            db.close();

//            Adapter_Order adapter = new Adapter_Order(Page_Order.this, 0, ListElements);
//            listView.setAdapter(adapter);
//            listView.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //set(pieView);

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
