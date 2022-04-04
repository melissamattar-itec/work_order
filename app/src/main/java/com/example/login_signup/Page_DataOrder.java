package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

public class Page_DataOrder extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    ViewPager viewPager;
    private Integer User_Id ,Order_Id;
    private int[] tabIcons = {
            R.drawable.ic_details,
            R.drawable.ic_status,
            R.drawable.ic_notes,
            R.drawable.ic_etude
    };
    private MyDataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_data_order);




        Intent page_order=getIntent();
        User_Id= page_order.getIntExtra("UserId",0);
        Order_Id= page_order.getIntExtra("Order_Id",0);

        db=new MyDataBaseHelper(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in  = new Intent(Page_DataOrder.this, Page_Order.class);
                in.putExtra("userId",User_Id);
                Log.i("USERIDpagedataorder", User_Id+"");
                startActivity(in);
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    @Override
    public void onBackPressed(){

        Intent in  = new Intent(Page_DataOrder.this, Page_Order.class);
        in.putExtra("userId",User_Id);
        in.putExtra("startdate",db.readOrder_ById(User_Id, Order_Id).getStart_date());
        Log.i("USERIDpagedataorder", User_Id+"");
        startActivity(in);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
//        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle args = new Bundle();
        args.putInt("UserId", User_Id);
        args.putInt("Order_Id", Order_Id);

        Fragment_Detail Fr_details = new Fragment_Detail();
        Fr_details.setArguments(args);
        adapter.addFragment(Fr_details, "Détail");

        Fragment_Status_Update Fragment_Status_Update = new Fragment_Status_Update();
        Fragment_Status_Update.setArguments(args);
        adapter.addFragment(Fragment_Status_Update, "mise à jour");

//        adapter.addFragment(new Fragment_Notes(), "Notes");

        Fragment_Etude Fragment_Etude = new Fragment_Etude();
        Fragment_Etude.setArguments(args);
        adapter.addFragment(Fragment_Etude, "Etude");

        Fragment_Labor Fragment_Labor = new Fragment_Labor();
        Fragment_Labor.setArguments(args);
        adapter.addFragment(Fragment_Labor, "Labor");

        viewPager.setAdapter(adapter);


    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

}