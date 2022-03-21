package com.example.login_signup;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Token {
    IResult mResultCallback = null;
    Context mContext;
    public Token(IResult resultCallback,Context context) {
        mResultCallback = resultCallback;
        mContext = context;
        Url=new Url(mContext);
        RequestQueue queue = Volley.newRequestQueue(mContext);

        JSONObject body = new JSONObject();
        try {
            // FOR REGIDESO
//            body.put("username", "JDE");
//            body.put("password", "JDE");
//            body.put("environment" , "JDV920");


            // FOR SBEE
//            body.put("username", "AIS");
//            body.put("password", "T@5AISX");
//            body.put("environment" , "JDV920");

            body.put("username", Url.get_Jde_user());
            body.put("password", Url.get_Jde_pass());
            body.put("environment" , Url.get_Jde_environment());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url= Url.getUrl()+"tokenrequest";
        Log.i("url", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        try {
//                            Log.i("Body" , body.toString());
//                            //txtResponse.setText(response.getString("userInfo"));
//                            JSONObject userInfoObject = new JSONObject(response.getString("userInfo"));
//                            String token_addr_book= userInfoObject.getString("token");
//                            // Toast.makeText(LoginActivity.this, "Token generated", Toast.LENGTH_SHORT).show();
//                            Log.i("Token" , "t"+token);
//
//                            if(token_addr_book!=null)
//                            {
//                              setToken(token_addr_book);
//                            }
//                            else {
//                                Toast.makeText(context,"error", Toast.LENGTH_SHORT).show();
//                            }
//
//                        } catch (JSONException e) {
//                            Toast.makeText(context, "An error has occured", Toast.LENGTH_SHORT).show();
//                        }

                        if(mResultCallback != null)
                            mResultCallback.notifySuccess(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(, "connexion error", Toast.LENGTH_SHORT).show();
                Log.i("Error" ,error.toString());
                if(mResultCallback != null)
                    mResultCallback.notifyError(error);
            }
        });

        queue.add(jsonObjectRequest);

        //jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( 500000000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //RequestQueueSingleton.getInstance(context).addToRequestQueue1(jsonObjectRequest);
    }

    private String token;
    private Url Url;
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
