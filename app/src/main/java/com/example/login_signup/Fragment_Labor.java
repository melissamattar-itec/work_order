package com.example.login_signup;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Labor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Labor extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList listofpriorities=new ArrayList();
    private ArrayList listofstatusequipment=new ArrayList();
    private ArrayList listofstatusworkorder=new ArrayList();
    private Integer User_Id ,Order_Id;
    private TextView bt_order_number;
    private TextView problem;
    private TextView equipment;
    private TextView description;
    private TextView client;
    private TextView nom_client;
    private TextView typewo;
    private TextView soustypewo_description;
    private TextView sous_typewo;
    private TextView priorite ;
    private TextView status_equipment ;
    private TextView status_workoder ;
    private TextView site_description;
    private TextView quartier_description;
    private ImageButton info;
    private ImageButton site_info;
    private LinearLayout layout_equipment;
    private LinearLayout parent_layout;
    Boolean check_permission=false;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View rootView;
    public Fragment_Labor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Notes.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Labor newInstance(String param1, String param2) {
        Fragment_Labor fragment = new Fragment_Labor();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            User_Id = getArguments().getInt("UserId",0);
            Order_Id = getArguments().getInt("Order_Id",0);
            Log.i("userid/orderid" , User_Id + "/" + Order_Id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment__labor, container, false);

        MyDataBaseHelper db= new MyDataBaseHelper(getActivity());

        bt_order_number= rootView.findViewById(R.id.bt_order_number);
        problem= rootView.findViewById(R.id.problem);
        equipment= rootView.findViewById(R.id.equipment);
        description= rootView.findViewById(R.id.description);
        client= rootView.findViewById(R.id.client);
        nom_client= rootView.findViewById(R.id.nom_client);
        priorite = rootView.findViewById(R.id.priorite);
        status_equipment = rootView.findViewById(R.id.status_equipment);
        status_workoder = rootView.findViewById(R.id.status_workorder);
        equipment = rootView.findViewById(R.id.equipment);
        nom_client = rootView.findViewById(R.id.nom_client);
        typewo = rootView.findViewById(R.id.type);
        sous_typewo = rootView.findViewById(R.id.sous_type);
        soustypewo_description = rootView.findViewById(R.id.sous_type_description);
        quartier_description = rootView.findViewById(R.id.quartier_description);
        site_description = rootView.findViewById(R.id.site_description);
        layout_equipment = rootView.findViewById(R.id.layout_equipment);
        parent_layout = rootView.findViewById(R.id.parent_layout);
        info = rootView.findViewById(R.id.info);
        site_info = rootView.findViewById(R.id.site_info);



//        Button mapbtn = (Button) findViewById(R.id.map);
//        mapbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Page_DataClient.this, Page_Maps_Update_Location.class);
//                i.putExtra("userId",User_Id);
//
//                i.putExtra("Longitude", longi.getText());
//                i.putExtra("Latitude", latti.getText());
//                try {
//                    i.putExtra("NouvelLecture", Integer.valueOf(new_reading.getText().toString()));
//                } catch (NumberFormatException e) {
//                    Log.i("NouvelleLecture", "pas de valeur ");
//                }
//                i.putExtra("Observation",db.getDetails_code("OB",t_observation.getSelectedItem().toString()));
//                i.putExtra("Remark",tremark.getText().toString());
//
//                /////
//
//                i.putExtra("Id", id);
//                i.putExtra("address",address);
//                i.putExtra("name",name);
//                i.putExtra("Tournee",tournee);
//                i.putExtra("Number",number);
//                i.putExtra("Sequence", sequence);
//                i.putExtra("AncienLecture",ancientlecture);
//                i.putExtra("DateNouvelLecture",nouveldatelecture);
//                //anciennedatelecture = i.getStringExtra("DateAncienLecture");
//                i.putExtra("DateAncienLecture",anciennedatelecture);
//                i.putExtra("Installation",installation);
//                i.putExtra("cashtourid", tourneeId);
//                i.putExtra("max_consom_eau", max_consom_eau);
//                i.putExtra("max_consom_elec", max_consom_elec);
//                i.putExtra("nbr_roux", nbr_roux);
//                /////
//                startActivity(i);
//            }
//        });

//        Button Go = (Button)findViewById(R.id.go) ;
//        Go.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                if ((!longi.getText().equals("") && !latti.getText().equals("") && !longi.getText().equals(null) && !latti.getText().equals(null) && !longi.getText().equals("null") && !latti.getText().equals("null") )) {
//                    Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir//" + latti.getText() + "," + longi.getText() + "/");
//// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
//                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//// Make the Intent explicit by setting the Google Maps package
//                    mapIntent.setPackage("com.google.android.apps.maps");
//
//// Attempt to start an activity that can handle the Intent
//                    startActivity(mapIntent);
////                    Intent i = new Intent(DataClient.this, Directions.class);
////                    i.putExtra("userId",User_Id);
////
////                    i.putExtra("Longitude", longi.getText());
////                    i.putExtra("Latitude", latti.getText());
////                    i.putExtra("NouvelLecture",new_reading.getText().toString());
////                    i.putExtra("Observation",t_observation.getSelectedItem().toString());
////                    i.putExtra("Remark",tremark.getText().toString());
////
////                    /////
////
////                    i.putExtra("Id", id);
////                    i.putExtra("address",address);
////                    i.putExtra("name",name);
////                    i.putExtra("Tournee",tournee);
////                    i.putExtra("Number",number);
////                    i.putExtra("Sequence", sequence);
////                    i.putExtra("AncienLecture",ancientlecture);
////                    i.putExtra("DateNouvelLecture",nouveldatelecture);
////                    i.putExtra("Installation",installation);
////                    i.putExtra("cashtourid", tourneeId);
////                    i.putExtra("min", min);
////                    i.putExtra("max", max);
////
////                    /////
////                    startActivity(i);
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "pas d'emplacement pour ce compteur", Toast.LENGTH_LONG).show();
//                }
//            }
//
//        });

        db=new MyDataBaseHelper(getContext());
        T_Orders ORDER= db.readOrder_ById(User_Id,Order_Id);
        Log.i("bt_order_number",ORDER.getOrder_number()+"" );

        status_equipment.setText(db.getDetails_description("ES",ORDER.getEquipment_status()));
        status_workoder.setText(db.getDetails_description("S1",ORDER.getstatus()));
        priorite.setText(db.getDetails_description("PR",ORDER.getPriority()));

        bt_order_number.setText(ORDER.getOrder_number()+"");
        //problem.setText(ORDER.get());
        //equipment.setText(ORDER.get());
        description.setText(ORDER.getDescription()+"");
        sous_typewo.setText(ORDER.getSous_type()+"");
        soustypewo_description.setText(db.getDetails_description("TY",ORDER.getSous_type()));
        typewo.setText(ORDER.getType());

        equipment.setText(ORDER.getEquipment_number()+"");
        description.setText(ORDER.getEquipment_description()+"");
        client.setText(ORDER.getClient_number()+"");
        nom_client.setText(ORDER.getClient_name()+"");
        problem.setText(ORDER.getProblem());

        //site_numero.setText(ORDER.getSiteNumero()+"");
        //site.setText(ORDER.getSite()+"");

        quartier_description.setText(db.getDetails_description("UD" , ORDER.getSite()));
        site_description.setText(db.getDetails_description("UB",ORDER.getSite()));

        info.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {

                Log.i("infoclicked","hgcvnfj");
                PopupMenu popupMenu = new PopupMenu(getContext() ,v,  Gravity.RIGHT);

                popupMenu.getMenuInflater().inflate(R.menu.infos,popupMenu.getMenu());
                popupMenu.getMenu().getItem(0).setTitle(ORDER.getPhone_area_code() + "-"+ORDER.getPhone_number() +"");
                popupMenu.getMenu().getItem(1).setTitle(ORDER.getEmail_address()+"");
                popupMenu.setForceShowIcon(true);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.appeler:
                            {

                                if (ContextCompat.checkSelfPermission(getActivity(),
                                        Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED)
                                {
                                    Log.i("has the permission","no");
                                    // request the permission
                                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                            200);
                                }
                                else {
                                    Log.i("has the permission","yes");
                                    // has the permission.
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + ORDER.getPhone_area_code() + ORDER.getPhone_number()));//change the number
                                    startActivity(callIntent);
                                }
                                break;
                            }
                            case R.id.mail:
                            {
                                break;
                            }
                            case R.id.message:
                            {

                                if (ContextCompat.checkSelfPermission(getActivity(),
                                        Manifest.permission.SEND_SMS)
                                        != PackageManager.PERMISSION_GRANTED)
                                {
                                    Log.i("has the permission","no");
                                    // request the permission
                                    requestPermissions(new String[]{Manifest.permission.SEND_SMS},123
                                    );
                                }
                                else {
                                    Log.i("has the permission","yes");
                                    // has the permission.

                                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                                    smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
                                    smsIntent.setType("vnd.android-dir/mms-sms");
                                    smsIntent.setData(Uri.parse("sms:"+ORDER.getPhone_area_code() +  ORDER.getPhone_number()));
                                    startActivity(smsIntent);
                                }
                                break;

                            }
                            default: break;

                        }
                        return true;
                    }
                });

                popupMenu.show();




            }
        });

        site_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext() ,v,  Gravity.RIGHT);

                popupMenu.getMenuInflater().inflate(R.menu.infos,popupMenu.getMenu());
                popupMenu.getMenu().getItem(0).setTitle(ORDER.getAddress_line1() + "   "+ORDER.getAddress_line2() +"");
                popupMenu.getMenu().removeItem(R.id.mail);
                popupMenu.show();

            }
        });
        if(ORDER.getEquipment_number().equals(0)){
            parent_layout.removeView(layout_equipment);
        }
        return rootView;
    }
}