package com.example.login_signup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Etude#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Etude extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View rootView;
    private Integer User_Id ,Order_Id;
    Url Url;
    T_Orders w_order;
    MyDataBaseHelper db;
    public Fragment_Etude() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Etude.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Etude newInstance(String param1, String param2) {
        Fragment_Etude fragment = new Fragment_Etude();
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
        rootView = inflater.inflate(R.layout.fragment__etude,container,false);


        // http://102.134.98.158:7001/gis_parts/parts.html?on=1990667&long=29.9787712097168&lat=-3.9160625365916593&edit=no&zone=%20#15/-3.9160625365916593/29.9787712097168
        WebView webView=(WebView) rootView.findViewById(R.id.etude);
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings websettings = webView.getSettings();
        websettings.setJavaScriptEnabled(true);

        Url=new Url(getContext());
        webView.loadUrl(Url.get_Url_Map()+"/gis_parts/parts.html?on="+w_order.getOrder_number()+"&long=" + w_order.getLongitude()+ "&lat=" + w_order.getLatitude() + "&edit=no&zone=%20#15/" +w_order.getLatitude()+"/" + w_order.getLongitude() );
        Log.i("url",  Url.get_Url_Map()+"/gis_parts/parts.html?on="+w_order.getOrder_number()+"");

        return rootView;
    }
}