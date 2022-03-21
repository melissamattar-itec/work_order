package com.example.login_signup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Page_Preferences extends AppCompatActivity {

    MyDataBaseHelper db;
    String Value;
    TextView text;
    Button Save;
    EditText ipadresse;
    EditText username;
    EditText password;
    EditText environment;
    Url url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        ipadresse = (EditText) findViewById(R.id.ipadresse);
        username = (EditText) findViewById(R.id.jdeuser);
        password = (EditText) findViewById(R.id.jdepassword);
        environment = (EditText) findViewById(R.id.jdeenvironment);

        Save = (Button) findViewById(R.id.btnSave);

        url=new Url(this);
        ipadresse.setText(url.getUrl());
        username.setText(url.get_Jde_user());
        password.setText(url.get_Jde_pass());
        environment.setText(url.get_Jde_environment());


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert();
            }
        });

    }

    private void alert() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("êtes-vous sûr d'enregistrer cet addresse ip ? ")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        Log.i("IP ADDRESS" , ipadresse.getText().toString());
                        File root = new File(getApplicationContext().getExternalFilesDir("preferences"+"/")+"");
                        File gpxfile = new File(root.getAbsolutePath()+ "/"+"http_config.txt");
                        FileWriter writer = null;
                        try {
                            writer = new FileWriter(gpxfile);
                            writer.append(ipadresse.getText());
                            writer.flush();
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        File gpxfile1 = new File(root.getAbsolutePath()+ "/"+"jde_conf.txt");
                        FileWriter writer1 = null;
                        try {
                            writer1 = new FileWriter(gpxfile1);
                            writer1.append(username.getText());
                            writer1.flush();
                            writer1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        File gpxfile2 = new File(root.getAbsolutePath()+ "/"+"jdepass_conf.txt");
                        FileWriter writer2 = null;
                        try {
                            writer2 = new FileWriter(gpxfile2);
                            writer2.append(password.getText());
                            writer2.flush();
                            writer2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        File gpxfile3 = new File(root.getAbsolutePath()+ "/"+"jdeenv_conf.txt");
                        FileWriter writer3 = null;
                        try {
                            writer3 = new FileWriter(gpxfile3);
                            writer3.append(environment.getText());
                            writer3.flush();
                            writer3.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                })
                .show();



    }
}