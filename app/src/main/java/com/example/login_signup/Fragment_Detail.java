package com.example.login_signup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Detail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Detail extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Integer User_Id ,Order_Id;
    private TextView bt_order_number;
    private TextView problem;
    private TextView equipment;
    private TextView description;
    private TextView client;
    private TextView nom_client;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View rootView;

    public Fragment_Detail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Detail.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Detail newInstance(String param1, String param2) {
        Fragment_Detail fragment = new Fragment_Detail();
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
        rootView = inflater.inflate(R.layout.fragment__detail,container,false);
        MyDataBaseHelper db= new MyDataBaseHelper(getActivity());

        bt_order_number= rootView.findViewById(R.id.bt_order_number);
        //problem= rootView.findViewById(R.id.problem);
        equipment= rootView.findViewById(R.id.equipment);
        description= rootView.findViewById(R.id.description);
        client= rootView.findViewById(R.id.client);
        nom_client= rootView.findViewById(R.id.nom_client);

        T_Orders ORDER= db.readOrder_ById(User_Id,Order_Id);
        Log.i("bt_order_number",ORDER.getOrder_number()+"" );
        bt_order_number.setText(ORDER.getOrder_number()+"");
        //problem.setText(ORDER.get());
        //equipment.setText(ORDER.get());
        description.setText(ORDER.getDescription()+"");
        //client.setText(ORDER.ge());
        //nom_client.setText(ORDER.getOrder_number());

        return rootView;
    }
}