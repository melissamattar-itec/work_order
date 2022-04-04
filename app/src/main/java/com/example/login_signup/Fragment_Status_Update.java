package com.example.login_signup;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Status_Update#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Status_Update extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Integer User_Id ,Order_Id;
    Spinner status_workorder;
    Spinner status_equipment;
    LinearLayout layoutstatusequipment;
    LinearLayout parent;
    EditText notes;
    Button save, cancel;
    Url Url;
    T_Orders w_order;
    MyDataBaseHelper db;
    private View rootView;
    public Fragment_Status_Update() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Status_Update.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Status_Update newInstance(String param1, String param2) {
        Fragment_Status_Update fragment = new Fragment_Status_Update();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);



            User_Id = getArguments().getInt("UserId",0);
            Order_Id = getArguments().getInt("Order_Id",0);
            db=new MyDataBaseHelper(getContext());
            w_order=db.readOrder_ById(User_Id,Order_Id);
            Log.i("userid/orderid" , User_Id + "/" + Order_Id);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment__status__update,container,false);


        status_workorder = rootView.findViewById(R.id.status_workorder);
        status_equipment = rootView.findViewById(R.id.status_equipment);
        notes = rootView.findViewById(R.id.notes);
        layoutstatusequipment = rootView.findViewById(R.id.layoutstatusequipment);
        parent = rootView.findViewById(R.id.parent);
        cancel = rootView.findViewById(R.id.cancel);
        save = rootView.findViewById(R.id.save);


        ArrayList<T_Details> statusw = db.readDetails("S1");
        ArrayList<String> statusworkorderArray = new ArrayList<>();
        for(int i=0 ; i<statusw.size();i++){
            statusworkorderArray.add(statusw.get(i).getObservation());
        }
        ArrayAdapter<String> statusworkorderArrayAdapter = new ArrayAdapter<String>
                (getActivity(),R.layout.simple_spinner_dropdown_item,statusworkorderArray );
        status_workorder.setAdapter(statusworkorderArrayAdapter);
        status_workorder.setSelection(statusworkorderArray.lastIndexOf(db.getDetails_description("S1",w_order.getstatus())));

//
//
//
//
        ArrayList<T_Details> status_equipmentArray=db.readDetails("ES");
        ArrayList<String> statusArray = new ArrayList<>();
        for(int i=0 ; i<status_equipmentArray.size();i++){
            statusArray.add(status_equipmentArray.get(i).getObservation());
        }
        ArrayAdapter<String> statusArrayAdapter = new ArrayAdapter<String>
                (getActivity(),R.layout.simple_spinner_dropdown_item,statusArray );
        status_equipment.setAdapter(statusArrayAdapter);
        Log.i("w_order.getEquipment_status())",w_order.getEquipment_status());
        status_equipment.setSelection(statusArray.lastIndexOf(db.getDetails_description("ES",w_order.getEquipment_status())));


if(w_order.getEquipment_number().equals(0)){
    parent.removeView(layoutstatusequipment);
}

     notes.setText(w_order.getNotes());

save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Log.i("save", "clicked");
        Log.i("savedata", String.valueOf( User_Id)+"/"+String.valueOf( Order_Id)+"/"+notes.getText().toString()+"/"+db.getDetails_code("ES",status_equipment.getSelectedItem().toString())+"/"+db.getDetails_code("S1",status_workorder.getSelectedItem().toString()));
        db.updateWorkOrder(String.valueOf( User_Id),String.valueOf( Order_Id),notes.getText().toString(),db.getDetails_code("ES",status_equipment.getSelectedItem().toString()).trim(),db.getDetails_code("S1",status_workorder.getSelectedItem().toString()).trim());
        w_order=db.readOrder_ById(User_Id,Order_Id);
        Intent in=new Intent(getActivity(),Page_DataOrder.class);
        in.putExtra("UserId",User_Id);
        in.putExtra("Order_Id",Order_Id);
        startActivity(in);
    }
});


cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        status_workorder.setSelection(statusworkorderArray.lastIndexOf(db.getDetails_description("S1",w_order.getstatus())));
        status_equipment.setSelection(statusArray.lastIndexOf(db.getDetails_description("ES",w_order.getEquipment_status())));
        notes.setText(w_order.getNotes());

    }
});
        return rootView;
    }



}