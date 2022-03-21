package com.example.login_signup;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Url {
    public String getUrl() {
        return Url;
    }
    public String get_PhotosUrl() {
        return Url_Photos;
    }
    public String get_Jde_user() {
        return Jde_user;
    }
    public String get_Jde_pass() {
        return Jde_pass;
    }
    public String get_Jde_environment() {
        return Jde_environment;
    }
    public String get_Url_Map() {
        return Url_Map;
    }
    public Url(Context Context) {
        String test_url = new String();
        File root = new File(Context.getExternalFilesDir("preferences"+"/")+"");
        if (!root.exists()) {
            root.mkdirs();
        }
        else {
            Log.i("folder ","exists");
            try{
                File conf=new File(root.getAbsolutePath()+ "/"+"http_config.txt");
                if (conf.exists()){
                    Log.i("files http_config ","exists");
                    // read the ip from the file

                    String line = null;

                    try {
                        FileInputStream fileInputStream = new FileInputStream (conf);
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuilder stringBuilder = new StringBuilder();

                        while ( (line = bufferedReader.readLine()) != null )
                        {
                            stringBuilder.append(line + System.getProperty("line.separator"));
                        }
                        fileInputStream.close();
                        line = stringBuilder.toString();

                        bufferedReader.close();
                    }
                    catch(FileNotFoundException ex) {
                        Log.d("error", ex.getMessage());
                    }
                    catch(IOException ex) {
                        Log.d("error", ex.getMessage());
                    }

                    Log.i("line ",line);
                    Url=line.trim();
                }
                else {
                    File gpxfile = new File(root.getAbsolutePath()+ "/"+"http_config.txt");
                    FileWriter writer = new FileWriter(gpxfile);
                    writer.append("http://102.134.98.158:8009/jderest/");
                    writer.flush();
                    writer.close();
                    Toast.makeText(Context, "Saved", Toast.LENGTH_SHORT).show();
                    Url="http://102.134.98.158:8009/jderest/";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try{
                File conf=new File(root.getAbsolutePath()+ "/"+"photos_config.txt");
                if (conf.exists()){
                    Log.i("files photos_config ","exists");
                    // read the ip from the file

                    String line1 = null;

                    try {
                        FileInputStream fileInputStream = new FileInputStream (conf);
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuilder stringBuilder = new StringBuilder();

                        while ( (line1 = bufferedReader.readLine()) != null )
                        {
                            stringBuilder.append(line1 + System.getProperty("line.separator"));
                        }
                        fileInputStream.close();
                        line1 = stringBuilder.toString();

                        bufferedReader.close();
                    }
                    catch(FileNotFoundException ex) {
                        Log.d("error", ex.getMessage());
                    }
                    catch(IOException ex) {
                        Log.d("error", ex.getMessage());
                    }

                    Log.i("line1 ",line1);
                    Url_Photos=line1.trim();
                }
                else {
                    File gpxfile = new File(root.getAbsolutePath()+ "/"+"photos_config.txt");
                    FileWriter writer = new FileWriter(gpxfile);
                    writer.append("file://192.168.100.80/e920/mediaobj/Misc Images/");
                    writer.flush();
                    writer.close();
                    //Toast.makeText(Context, "Saved", Toast.LENGTH_SHORT).show();
                    Url_Photos ="file://192.168.100.80/e920/mediaobj/Misc Images/";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try{
                File conf=new File(root.getAbsolutePath()+ "/"+"jde_conf.txt");
                if (conf.exists()){
                    Log.i("files jde_conf ","exists");
                    // read the ip from the file

                    String line1 = null;

                    try {
                        FileInputStream fileInputStream = new FileInputStream (conf);
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuilder stringBuilder = new StringBuilder();

                        while ( (line1 = bufferedReader.readLine()) != null )
                        {
                            stringBuilder.append(line1 + System.getProperty("line.separator"));
                        }
                        fileInputStream.close();
                        line1 = stringBuilder.toString();

                        bufferedReader.close();
                    }
                    catch(FileNotFoundException ex) {
                        Log.d("error", ex.getMessage());
                    }
                    catch(IOException ex) {
                        Log.d("error", ex.getMessage());
                    }

                    Log.i("line1 ",line1);
                    Jde_user=line1.trim();
                }
                else {
                    File gpxfile = new File(root.getAbsolutePath()+ "/"+"jde_conf.txt");
                    FileWriter writer = new FileWriter(gpxfile);
                    writer.append("AISMOB");
                    writer.flush();
                    writer.close();
                    //Toast.makeText(Context, "Saved", Toast.LENGTH_SHORT).show();
                    Jde_user ="AISMOB";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try{
                File conf=new File(root.getAbsolutePath()+ "/"+"jdepass_conf.txt");
                if (conf.exists()){
                    Log.i("files jdepass_conf ","exists");
                    // read the ip from the file

                    String line1 = null;

                    try {
                        FileInputStream fileInputStream = new FileInputStream (conf);
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuilder stringBuilder = new StringBuilder();

                        while ( (line1 = bufferedReader.readLine()) != null )
                        {
                            stringBuilder.append(line1 + System.getProperty("line.separator"));
                        }
                        fileInputStream.close();
                        line1 = stringBuilder.toString();

                        bufferedReader.close();
                    }
                    catch(FileNotFoundException ex) {
                        Log.d("error", ex.getMessage());
                    }
                    catch(IOException ex) {
                        Log.d("error", ex.getMessage());
                    }

                    Log.i("line1 ",line1);
                    Jde_pass=line1.trim();
                }
                else {
                    File gpxfile = new File(root.getAbsolutePath()+ "/"+"jdepass_conf.txt");
                    FileWriter writer = new FileWriter(gpxfile);
                    writer.append("AISMOB");
                    writer.flush();
                    writer.close();
                    //Toast.makeText(Context, "Saved", Toast.LENGTH_SHORT).show();
                    Jde_user ="AISMOB";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try{
                File conf=new File(root.getAbsolutePath()+ "/"+"jdeenv_conf.txt");
                if (conf.exists()){
                    Log.i("files jdeenv_conf ","exists");
                    // read the ip from the file

                    String line1 = null;

                    try {
                        FileInputStream fileInputStream = new FileInputStream (conf);
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuilder stringBuilder = new StringBuilder();

                        while ( (line1 = bufferedReader.readLine()) != null )
                        {
                            stringBuilder.append(line1 + System.getProperty("line.separator"));
                        }
                        fileInputStream.close();
                        line1 = stringBuilder.toString();

                        bufferedReader.close();
                    }
                    catch(FileNotFoundException ex) {
                        Log.d("error", ex.getMessage());
                    }
                    catch(IOException ex) {
                        Log.d("error", ex.getMessage());
                    }

                    Log.i("line1 ",line1);
                    Jde_environment=line1.trim();
                }
                else {
                    File gpxfile = new File(root.getAbsolutePath()+ "/"+"jdeenv_conf.txt");
                    FileWriter writer = new FileWriter(gpxfile);
                    writer.append("JDV920");
                    writer.flush();
                    writer.close();
                    //Toast.makeText(Context, "Saved", Toast.LENGTH_SHORT).show();
                    Jde_user ="JDV920";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try{
                File conf=new File(root.getAbsolutePath()+ "/"+"Map_conf.txt");
                if (conf.exists()){
                    Log.i("files Map_conf ","exists");
                    // read the ip from the file

                    String line1 = null;

                    try {
                        FileInputStream fileInputStream = new FileInputStream (conf);
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuilder stringBuilder = new StringBuilder();

                        while ( (line1 = bufferedReader.readLine()) != null )
                        {
                            stringBuilder.append(line1 + System.getProperty("line.separator"));
                        }
                        fileInputStream.close();
                        line1 = stringBuilder.toString();

                        bufferedReader.close();
                    }
                    catch(FileNotFoundException ex) {
                        Log.d("error", ex.getMessage());
                    }
                    catch(IOException ex) {
                        Log.d("error", ex.getMessage());
                    }

                    Log.i("line1 ",line1);
                    Url_Map=line1.trim();
                }
                else {
                    File gpxfile = new File(root.getAbsolutePath()+ "/"+"Map_conf.txt");
                    FileWriter writer = new FileWriter(gpxfile);
                    writer.append("http://129.159.198.129:7001/meter_reading/meter_reading.html");
                    writer.flush();
                    writer.close();
                    //Toast.makeText(Context, "Saved", Toast.LENGTH_SHORT).show();
                    Url_Map ="http://129.159.198.129:7001/meter_reading/meter_reading.html";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        //Url_Photos ="file://192.168.100.80/e920/mediaobj/Misc Images/";
    }

    private String Url;
    private String Url_Photos;
    private String Url_Map;
    private String Jde_user;
    private String Jde_pass;
    private String Jde_environment;

}
