package com.example.login_signup;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class  MyDataBaseHelper extends SQLiteOpenHelper {


    private Context context;
    private static final String DATABASE_NAME = "Work_order.db";
    private static final int DATABASE_VERSION = 1;


    // Table Names
    private static final String TABLE_NAME = "My_Address_Books";
    private static final String TABLE_NAME1 = "My_Work_Orders";
    private static final String TABLE_NAME2 = "Settings";
    private static final String TABLE_NAME3 = "Details ";


    // Common column names
    private static final String COLUMN_ID = "_id";

    // My_Address_Books - column names

    private static final String COLUMN_USERID= "user_id";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ADDRESSBOOK = "address_Book";


    // My_Registration" - column names

//    private static final String COLUMN_NAME1 = "Name";
//    private static final String COLUMN_ADDRESSBOOK1 = "Address_Book";
//    private static final String COLUMN_EMAIL = "Email";
//    private static final String COLUMN_PASSWORD1 = "Password";
//    private static final String COLUMN_CONFIRM_PASSWORD = "Confirm_password";
//    private static final String COLUMN_CONFIRM_PASSWORD1 = "Confirm_password";

  // My_Work_Orders - column names
    private static final String COLUMN_TYPE = "Type";
    private static final String COLUMN_SOUS_TYPE = "Sous_Type";
    private static final String COLUMN_ORDERNUMBER = "Order_Number";
    private static final String COLUMN_STARTDATE = "Start_Date";
    private static final String COLUMN_DESCRIPTION = "Description";
    private static final String COLUMN_ORDERDATE = "Order_Date";
    private static final String COLUMN_PRIORITY = "Priority";
    private static final String COLUMN_WORK_STATUS = "Work_Status";
    private static final String COLUMN_OBSERVATION = "Observation";
//    private static final String COLUMN_EQUIPMENT_NUMBER = "Equipment_Number";
//    private static final String COLUMN_EQUIPMENT_DESCRIPTION = "Equipment_Description";
//    private static final String COLUMN_CLIENT_NAME = "Client_Name";
//    private static final String COLUMN_CLIENT_NUMBER= "Client_Number";


   //private static final String COLUMN_ESTIMATEDHOURS = "Estimated_Hours";


    // Settings   - column names
    private static final String COLUMN_VALUE= "Value";

    //Details - column names
    private static final String COLUMN_CODE= "Code";
    private static final String COLUMN_TYPE1= "Type";

    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createuser );
        sqLiteDatabase.execSQL(creatworkorder );
        //sqLiteDatabase.execSQL(createsettings);
        sqLiteDatabase.execSQL(createdetails );

    }

    private static final String createuser = "CREATE TABLE " + TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERID + " TEXT, " +
            COLUMN_PASSWORD + " TEXT, " +
            COLUMN_ADDRESSBOOK + " INTEGER); ";


    private static final String creatworkorder = "CREATE TABLE " + TABLE_NAME1 +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERID + " INTEGER, "+
            COLUMN_TYPE + " TEXT, " +
            COLUMN_SOUS_TYPE + " TEXT, " +
            COLUMN_ORDERNUMBER+ " INTEGER, " +
            COLUMN_STARTDATE + " TEXT, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_ORDERDATE + " TEXT, " +
            COLUMN_PRIORITY + " TEXT, " +
            COLUMN_WORK_STATUS + " TEXT); ";


    private static final String createsettings  = "CREATE TABLE " + TABLE_NAME2 +
            " (" +
            COLUMN_CODE + " TEXT, " +
            COLUMN_TYPE1 + " TEXT, " +
            COLUMN_VALUE + " TEXT, "+
            COLUMN_USERID + " INTEGER, "+
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT); ";

    private static final String createdetails = "CREATE TABLE " + TABLE_NAME3 +
            " (" +
            COLUMN_CODE + " TEXT PRIMARY KEY , " +
            COLUMN_TYPE + " TEXT, " +
            COLUMN_OBSERVATION + " TEXT); ";


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
       // sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
    }

    void addUser(String user_id, String Password, String AddressBook) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERID, user_id);
        cv.put(COLUMN_PASSWORD, Password);
        cv.put(COLUMN_ADDRESSBOOK, AddressBook);
        long result = db.insertWithOnConflict(TABLE_NAME, null, cv,SQLiteDatabase.CONFLICT_REPLACE);

        if (result == -1) {
            Toast.makeText(context, "Échoué", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();
        }
    }

//    void addUserRegistration(String Name, String AddressBook, String Email, String Password , String ConfirmPassword) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_NAME1, Name);
//        cv.put(COLUMN_ADDRESSBOOK1, AddressBook);
//        cv.put(COLUMN_EMAIL, Email);
//        cv.put(COLUMN_PASSWORD1, Password);
//        cv.put(COLUMN_CONFIRM_PASSWORD , ConfirmPassword);
//
//        long result = db.insertWithOnConflict(TABLE_NAME, null, cv,SQLiteDatabase.CONFLICT_REPLACE);
//
//        if (result == -1) {
//            Toast.makeText(context, "Échoué", Toast.LENGTH_SHORT).show();
//        } else {
//            //Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();
//        }
//    }

    public boolean validate(String Username , String Password){
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorUser = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ COLUMN_USERID + "=?" + " AND "+ COLUMN_PASSWORD + "=?", new String[]{Username,Password});
        // on below line we are creating a new array list.
        T_User UserModal = null;
        // moving our cursor to first position.
        if (cursorUser.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                UserModal = new T_User(cursorUser.getInt(0),
                        cursorUser.getString(1),
                        cursorUser.getString(2),
                        cursorUser.getString(3)
                );
            } while (cursorUser.moveToNext());
            cursorUser.close();
            // moving our cursor to next.
            if (UserModal == null) {
                return false;
            } else {
                return true;
            }

        }
        // at last closing our cursor
        // and returning our array list.
        else {
            return false;
        }
    }

//    public ArrayList<T_Orders> readOrder_Validee(Integer User_Id) {
//        // on below line we are creating a
//        // database for reading our database.
//        SQLiteDatabase db = this.getReadableDatabase();
//        ArrayList<T_Orders> tourneeArrayList = new ArrayList<>();
//        // on below line we are creating a cursor with query to read data from database.
//        try {
//            Cursor cursorOrder = db.rawQuery("SELECT * FROM " + TABLE_NAME1 + " WHERE " + COLUMN_STATUS_COLLECTOR + "=?" + " AND " + COLUMN_USERID + "=?", new String[]{"1", String.valueOf(User_Id)});
//
//            // on below line we are creating a new array list.
//            // moving our cursor to first position.
//            if (cursorOrder.moveToFirst()) {
//                do {
//                    // on below line we are adding the data from cursor to our array list.
//                    T_Orders OrderModal = new T_Orders(cursorOrder.getInt(0),
//                            cursorOrder.getInt(1),
//                            cursorOrder.getInt(2),
//                            cursorOrder.getString(3),
//                            cursorOrder.getString(4),
//                            cursorOrder.getString(5),
//                            cursorOrder.getInt(6),
//                            cursorOrder.getString(7),
//                            cursorOrder.getString(8),
//                            cursorOrder.getInt(9),
//                            cursorOrder.getString(10),
//                            cursorOrder.getInt(11),
//                            cursorOrder.getString(12)
//                    );
//                    tourneeArrayList.add(OrderModal);
//                } while (cursorOrder.moveToNext());
//
//                // moving our cursor to next.
//                return tourneeArrayList;
//            }
//            // at last closing our cursor
//            // and returning our array list.
//            else {
//                return tourneeArrayList;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return tourneeArrayList;
//        }
//    }

    public Integer get_User_Id(String Username , String Password) {
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorUser = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ COLUMN_USERID + "=?" + " AND "+ COLUMN_PASSWORD + "=?", new String[]{Username,Password});
        // on below line we are creating a new array list.
        // moving our cursor to first position.
        if (cursorUser.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                return cursorUser.getInt(0);
            } while (cursorUser.moveToNext());

            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        else {
            return 0;
        }
    }
    public String get_User_AddressBook(Integer Id) {
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorUser = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ COLUMN_ID + "=?" , new String[]{String.valueOf(Id)});
        // on below line we are creating a new array list.
        // moving our cursor to first position.
        if (cursorUser.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                Integer ab= cursorUser.getInt(3);
                return ab.toString();

            } while (cursorUser.moveToNext());

            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        else {
            return null;
        }
    }

    public boolean validate_username(String Username){
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorUser = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ COLUMN_USERID + "=?", new String[]{Username});
        // on below line we are creating a new array list.
        T_User UserModal = null;
        // moving our cursor to first position.
        if (cursorUser.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                UserModal = new T_User(cursorUser.getInt(0),
                        cursorUser.getString(1),
                        cursorUser.getString(2),
                        cursorUser.getString(3)
                );
            } while (cursorUser.moveToNext());
            cursorUser.close();
            // moving our cursor to next.
            if (UserModal == null) {
                return false;
            } else {
                return true;
            }

        }


        // at last closing our cursor
        // and returning our array list.
        else {
            return false;
        }

    }

    public boolean validate_password(String Password){
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorUser = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ COLUMN_PASSWORD + "=?", new String[]{Password});
        // on below line we are creating a new array list.
        T_User UserModal = null;
        // moving our cursor to first position.
        if (cursorUser.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                UserModal = new T_User(cursorUser.getInt(0),
                        cursorUser.getString(1),
                        cursorUser.getString(2),
                        cursorUser.getString(3)
                );
            } while (cursorUser.moveToNext());
            cursorUser.close();
            // moving our cursor to next.
            if (UserModal == null) {
                return false;
            } else {
                return true;
            }

        }

        // at last closing our cursor
        // and returning our array list.
        else {
            return false;
        }

    }
    void addOrder( String type,String sous_type ,Integer order_number,String start_date, String description,String order_date, String priority , String work_status,Integer user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_SOUS_TYPE, sous_type);
        cv.put(COLUMN_ORDERNUMBER, order_number);
        cv.put(COLUMN_STARTDATE, start_date);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_ORDERDATE, order_date);
        cv.put(COLUMN_PRIORITY, priority);
        cv.put(COLUMN_USERID,user_id);
        cv.put(COLUMN_WORK_STATUS, work_status);



        long result = db.insertWithOnConflict(TABLE_NAME1, null, cv,SQLiteDatabase.CONFLICT_REPLACE);

        if (result == -1) {
            Toast.makeText(context, "Échoué", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();
        }
    }


        public T_Orders readOrder_ById( Integer UserId , Integer order_id) {
            SQLiteDatabase db = this.getReadableDatabase();
            // on below line we are creating a cursor with query to read data from database.
            Cursor cursorOrder = db.rawQuery("SELECT * FROM " + TABLE_NAME1 + " WHERE "+ COLUMN_USERID + "=? AND "+ COLUMN_ID + "=?",new String[]{String.valueOf(UserId ),String.valueOf(order_id)});

            // on below line we are creating a new array list.
            T_Orders Order = null;

            Log.i("cursor", cursorOrder.toString());
            // moving our cursor to first position.
            if (cursorOrder.moveToFirst()) {

                    // on below line we are adding the data from cursor to our array list.
                Order= new T_Orders(cursorOrder.getInt(0),
                            cursorOrder.getInt(1),
                            cursorOrder.getString(2),
                            cursorOrder.getString(3),
                            cursorOrder.getInt(4),
                            cursorOrder.getString(5),
                            cursorOrder.getString(6),
                            cursorOrder.getString(7),
                            cursorOrder.getString(8),
                            cursorOrder.getString(9)

                    );
                };
                // moving our cursor to next.

            // at last closing our cursor
            // and returning our array list.
            cursorOrder.close();
            return Order;

    }

    public ArrayList<T_Orders> readOrder(Integer User_Id) {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorOrder = db.rawQuery("SELECT * FROM " + TABLE_NAME1 + " WHERE "+ COLUMN_USERID + "=?",new String[]{String.valueOf(User_Id)});

        // on below line we are creating a new array list.
        ArrayList<T_Orders> OrderModalArrayList = new ArrayList<>();

        Log.i("cursor", cursorOrder.toString());
        // moving our cursor to first position.
        if (cursorOrder.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                OrderModalArrayList.add(new T_Orders(cursorOrder.getInt(0),
                        cursorOrder.getInt(1),
                        cursorOrder.getString(2),
                        cursorOrder.getString(3),
                        cursorOrder.getInt(4),
                        cursorOrder.getString(5),
                        cursorOrder.getString(6),
                        cursorOrder.getString(7),
                        cursorOrder.getString(8),
                        cursorOrder.getString(9)

                ));
            } while (cursorOrder.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorOrder.close();
        return OrderModalArrayList;
    }

    public void delete_User(Integer User_Id){
        SQLiteDatabase db = this.getReadableDatabase();
        String whereClause = COLUMN_ID+"=?";
        String whereArgs[] = {String.valueOf(User_Id)};
        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();

    }

    public void delete_order( Integer User_Id) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<T_Orders> orders=this.readOrder(User_Id);
        for(int i=0;i<orders.size();i++) {
            String whereClause = COLUMN_USERID + "=?";
            String whereArgs[] = {String.valueOf(orders.get(i).getUser_id())};
            db.delete(TABLE_NAME1, whereClause, whereArgs);
        }
    }

    public ArrayList<T_Details> readDetails(String Type) {
        // on below line we are creating a
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorUser = db.rawQuery("SELECT * FROM " + TABLE_NAME3 + " WHERE " + COLUMN_TYPE + "=?" , new String[]{Type}  );
        // on below line we are creating a new array list.
        ArrayList<T_Details> UserModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorUser.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                UserModalArrayList.add(new T_Details(
                        cursorUser.getString(0),
                        cursorUser.getString(1),
                        cursorUser.getString(2)
                ));
            } while (cursorUser.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorUser.close();
        return UserModalArrayList;
    }

    public void add_settings(String type, String code, String value, Integer UserId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CODE, code);
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_VALUE, value);
        cv.put(COLUMN_USERID, UserId);

        long result = db.insertWithOnConflict(TABLE_NAME2, null, cv,SQLiteDatabase.CONFLICT_REPLACE);

        if (result == -1) {

            Toast.makeText(context, "Échoué", Toast.LENGTH_SHORT).show();
        } else {
            // Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();

        }

    }

    public void add_Details(String type, String code, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CODE, code);
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_OBSERVATION, description);

        long result = db.insertWithOnConflict(TABLE_NAME3, null, cv,SQLiteDatabase.CONFLICT_REPLACE);

        if (result == -1) {

            Toast.makeText(context, "Échoué", Toast.LENGTH_SHORT).show();
        } else {
            // Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();

        }

    }

//
//    public String getSettings_Value(String Type , String Code , Integer UserId) {
//        // on below line we are creating a
//        // on below line we are creating a
//        // database for reading our database.
//        SQLiteDatabase db = this.getReadableDatabase();
//        // on below line we are creating a cursor with query to read data from database.
//        Cursor cursorUser = db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE " + COLUMN_TYPE + "=?" +" AND "+ COLUMN_USERNAME + "=?", new String[]{Type , String.valueOf(UserId)});
//        // on below line we are creating a new array list.
//        String Description,code;
//        // moving our cursor to first position.
//        if (cursorUser.moveToFirst()) {
//            do {
//                // on below line we are adding the data from cursor to our array list.
//                Description= cursorUser.getString(2);
//                code= cursorUser.getString(0);
//                if(code.equals(Code) ){
//                    return Description;
//                }
//                //Log.i("code / description" , code + "/" + Description);
//            } while (cursorUser.moveToNext());
//            // moving our cursor to next.
//        }
//        return new String();
//        // at last closing our cursor
//        // and returning our array list.
//    }
//    @SuppressLint("LongLogTag")
//    public void update_Settings(String Type, String Code , String Value , Integer UserId ){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        Log.i("update type / code / value / id", "ok" + Type + "/"+ Code + "/" + Value + "/" + UserId);
//
////        values.put(COLUMN_TYPE, Type);
////        values.put(COLUMN_CODE, Code);
//
//        values.put(COLUMN_VALUE, Value);
//
//        db.update(TABLE_NAME2,values, COLUMN_TYPE + "=?" + " AND " + COLUMN_CODE + "=?" + " AND "+ COLUMN_USERNAME + "=?" ,new String[]{Type,Code, String.valueOf(UserId)});
//        db.close();
//    }

//    public String getDetails_code(String Type , String detail) {
//        // on below line we are creating a
//        // database for reading our database.
//        SQLiteDatabase db = this.getReadableDatabase();
//        // on below line we are creating a cursor with query to read data from database.
//        Cursor cursorUser = db.rawQuery("SELECT * FROM " + TABLE_NAME3 + " WHERE " + COLUMN_TYPE + "=?" + " AND " + COLUMN_OBSERVATION +"=?", new String[]{Type , detail}  );
//        // on below line we are creating a new array list.
//        String UserModal ;
//
//        // moving our cursor to first position.
//        if (cursorUser.moveToFirst()) {
//            do {
//                // on below line we are adding the data from cursor to our array list.
//                UserModal= cursorUser.getString(0);
//            } while (cursorUser.moveToNext());
//            // moving our cursor to next.
//            return UserModal;
//        }
//        else {
//            return null;
//        }
//        // at last closing our cursor
//        // and returning our array list.
//
//    }

    public String getDetails_description(String Type , String Code) {
        // on below line we are creating a
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorUser = db.rawQuery("SELECT * FROM " + TABLE_NAME3 + " WHERE " + COLUMN_TYPE + "=?", new String[]{Type});
        // on below line we are creating a new array list.
        String Description = new String();
        String code= new String();
        // moving our cursor to first position.
        if (cursorUser.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                Description= cursorUser.getString(2);
                code= cursorUser.getString(0);
                Log.i("Description+Code",String.valueOf(code));
                Log.i("Description+Code",code.trim());
                if(code.trim().equals(Code.trim()) ){
                    return Description;
                }
                else {
                }
                //Log.i("code / description" , code + "/" + Description);
            } while (cursorUser.moveToNext());
            // moving our cursor to next.
        }
        return new String();
        // at last closing our cursor
        // and returning our array list.
    }

    public String getDetails_code(String Type , String detail) {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorUser = db.rawQuery("SELECT * FROM " + TABLE_NAME3 + " WHERE " + COLUMN_TYPE + "=?" + " AND " + COLUMN_OBSERVATION +"=?", new String[]{Type , detail}  );
        // on below line we are creating a new array list.
        String UserModal ;

        // moving our cursor to first position.
        if (cursorUser.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                UserModal= cursorUser.getString(0);
            } while (cursorUser.moveToNext());
            // moving our cursor to next.
            return UserModal;
        }
        else {
            return null;
        }
        // at last closing our cursor
        // and returning our array list.

    }

    public void delete_order_byId(Integer UserId) {
        SQLiteDatabase db = getWritableDatabase();

//        String whereClause = COLUMN_CASH_TOURNEE_ID+"=?";
//        String whereArgs[] = {String.valueOf(cashtourId)};
//        db.delete(TABLE_NAME2, whereClause, whereArgs);

        String whereClause1 = COLUMN_ID+"=?";
        String whereArgs1[] = {String.valueOf(UserId)};
        db.delete(TABLE_NAME1, whereClause1, whereArgs1);
    }



}
