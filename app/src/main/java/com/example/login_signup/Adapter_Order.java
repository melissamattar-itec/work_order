package com.example.login_signup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Adapter_Order extends ArrayAdapter<T_Orders> {
    LayoutInflater inflater;
    List<T_Orders> Orders;
    Context l_context;
    int l_resource;
    ArrayList<T_Orders> arrayorder = new ArrayList<>();

    public Adapter_Order(@NonNull Context context, int resource, @NonNull List<T_Orders> orders) {
        super(context, resource, orders);
        Orders=orders;
        l_context=context;
        l_resource=resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        T_Orders cell = getItem(position);
       //If the cell is a section header we inflate the header layout
//        if(cell.isSectionHeader())
//        {
//            convertView = inflater.inflate(R.layout.section_header, null);
//
//            convertView.setClickable(false);
//
//            TextView header = (TextView) convertView.findViewById(R.id.section_header);
//            header.setText(cell.getStart_date());
//            //Log.i("Header Section" , cell.getOrder_date());
//        }
//        else
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_order, parent, false);
        }

        {
            convertView = inflater.inflate(R.layout.layout_item_order, null);
            String urired = "@drawable/red_ball";
            String urigreen = "@drawable/green_ball";
            String normal="@drawable/normal_priority";
            String moyen="@drawable/moyen_priority";
            String urgent="@drawable/urgent_priority";

            int imagered = convertView.getResources().getIdentifier(urired, null, convertView.getContext().getPackageName());
            int imagegreen = convertView.getResources().getIdentifier(urigreen, null, convertView.getContext().getPackageName());
            int imagenormal = convertView.getResources().getIdentifier(normal, null, convertView.getContext().getPackageName());
            int imagemoyen = convertView.getResources().getIdentifier(moyen, null, convertView.getContext().getPackageName());
            int imageurgent = convertView.getResources().getIdentifier(urgent, null, convertView.getContext().getPackageName());

            //TextView order_date = (TextView) convertView.findViewById(R.id.section_header);
            TextView order_number = (TextView) convertView.findViewById(R.id.ordernumber);
            TextView type = (TextView) convertView.findViewById(R.id.type);
            TextView soustype = (TextView) convertView.findViewById(R.id.subtype);
            TextView status = (TextView) convertView.findViewById(R.id.status_travail);
           // TextView client_number= (TextView) convertView.findViewById(R.id.client_number);
            //TextView client_name= (TextView) convertView.findViewById(R.id.client_name);
           // TextView equipment_number= (TextView) convertView.findViewById(R.id.equipment_number);
            //TextView equipment_description= (TextView) convertView.findViewById(R.id.equipment_description);
            TextView description = (TextView) convertView.findViewById(R.id.description);
            //ImageView stat=(ImageView) convertView.findViewById(R.id.img_green_red);
            ImageView imp=(ImageView) convertView.findViewById(R.id.important);


//            order_date.setText(cell.getOrder_date());
            order_number.setText(""+cell.getOrder_number());
            Log.i("cell.getOrder_number()", cell.getOrder_number() + "");
            type.setText(cell.getType());
            Log.i("cell.getType()", cell.getType() + "");
            soustype.setText(cell.getSous_type());
            Log.i("cell.getSous_type()", cell.getSous_type() + "");

            //client_number.setText(""+cell.getClient_Number());
//            Log.i("cell.getClient_Number()", cell.getClient_Number() + "");
//            Log.i("cell.getClient_Name()", cell.getClient_Name() + "");
//            Log.i("cell.getEquipment_Number()", cell.getEquipment_Number() + "");
//            Log.i("cell.getEquipment_Description()", cell.getEquipment_Description() + "");


            //client_name.setText(cell.getClient_Name());
           // equipment_number.setText(""+cell.getEquipment_Number());
            //equipment_description.setText(cell.getEquipment_Description());
            description.setText(cell.getDescription());
            MyDataBaseHelper db = new MyDataBaseHelper(l_context);

            status.setText(db.getDetails_description("S1",cell.getWork_status()));
            Log.i("WORKORDERSTATUSCODE/DESCRIPTION", db.getDetails_description("S1",cell.getWork_status())+ "");
            //start_date.setText(String.valueOf(order.getStart_date()));



//            if(!cell.getOrder_date().equals(0))
//            {
//                // red photo
//                stat.setImageResource(imagered);
//                // Log.i("color","red");
//            }
//            else if(cell.getOrder_date().equals(0))
//            {
//                // green photo
//                stat.setImageResource(imagegreen);
//                // Log.i("color","green");
//            }
            // Populate the data into the template view using the data object
            // Return the completed view to render on screen

            if(cell.getPriority().equals("N")){
                Log.i("TypeLecture",cell.getPriority());
                imp.setImageResource(imagenormal);
            }
            else if ((cell.getPriority().equals("M")))
            {
            Log.i("TypeLecture",cell.getPriority());
            imp.setImageResource(imagemoyen);
            }
            else if ((cell.getPriority().equals("U")))
            {
            Log.i("TypeLecture",cell.getPriority());
            imp.setImageResource(imageurgent);
              }

        }
        // Check if an existing view is being reused, otherwise inflate the view
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_order, parent, false);
//        }






        // Lookup view for data population

        //TextView start_date = (TextView) convertView.findViewById(R.id.startdate);

        //TextView addr=(TextView)convertView.findViewById(R.id.addr);
        //addr.setText(client.address);



        return convertView;
    }


}