package com.example.login_signup;

import java.util.Comparator;

public class T_Orders implements Comparable<T_Orders>{


    private Integer id;
    private Integer user_id;
    private String type;
    private String sous_type;
    private String work_status;
    private Integer order_number;
    private String start_date;
    private String description;
    private String order_date;
    private String priority;

    //private Integer estiamted_hour;
   // private Integer asset_number;
//    private String Status_Collector;
//    private String Client_Name;
//    private Integer Client_Number;
//    private String Equipment_Description;
//    private String Equipment_Number;
    private  boolean isSectionHeader;
//    private String date;
    public T_Orders(Integer id,Integer user_id,String type , String sous_type,Integer order_number  , String start_date, String description  , String order_date ,String priority ,String work_status ) {
        this.id = id;
        this.user_id = user_id;
        this.type = type;//z_TYPS_10
        this.sous_type = sous_type;
        this.order_number = order_number;//z_DOCO_7
        this.start_date = start_date;//z_STRT_18
        this.description = description;//z_DL01_217
        this.order_date=order_date;//z_TRDJ_19
        this.priority = priority;//z_PRTS_11
        this.work_status = work_status;
        isSectionHeader=false;
//        this.Equipment_Number=Equipment_Number;//z_ASII_129
//        this.Equipment_Description=Equipment_Description;//z_DL01_487
//        this.Client_Name=Client_Name;//z_DL01_475
//        this.Client_Number=Client_Number;//z_AN8_24


        //this.estiamted_hour = estiamted_hour;//z_HRSO_21



        //this.asset_number=asset_number;







    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrder_number() {
        return order_number;
    }

    public void setOrder_number(Integer order_number) {
        this.order_number = order_number;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }
    public String getSous_type() {
        return sous_type;
    }

    public void setSous_type(String sous_type) {
        this.sous_type = sous_type;
    }

    public String getWork_status() {
        return work_status;
    }

    public void setWork_status(String work_status) {
        this.work_status = work_status;
    }

    public void setSectionHeader(boolean sectionHeader) {
        isSectionHeader = sectionHeader;
    }

//    public Integer getEstiamted_hour() {
//        return estiamted_hour;
//    }
//
//    public void setEstiamted_hour(Integer estiamted_hour) {
//        this.estiamted_hour = estiamted_hour;
//    }

//    public Integer getAsset_number() {
//        return asset_number;
//    }
//
//    public void setAsset_number(Integer asset_number) {
//        this.asset_number = asset_number;
//    }

//    public String getStatus_Collector() {
//        return Status_Collector;
//    }
//
//    public void setStatus_Collector(String status_Collector) {
//        Status_Collector = status_Collector;
//    }
//
//    public String getEquipment_Description() {
//        return Equipment_Description;
//    }
//
//    public void setEquipment_Description(String equipment_Description) {
//        Equipment_Description = equipment_Description;
//    }
//
//    public String getEquipment_Number() {
//        return Equipment_Number;
//    }
//
//    public void setEquipment_Number(String equipment_Number) {
//        Equipment_Number = equipment_Number;
//    }
//
//    public String getClient_Name() {
//        return Client_Name;
//    }
//
//    public void setClient_Name(String client_Name) {
//        Client_Name = client_Name;
//    }
//
//    public Integer getClient_Number() {
//        return Client_Number;
//    }
//
//    public void setClient_Number(Integer client_Number) {
//        Client_Number = client_Number;
//    }

    @Override
    public int compareTo(T_Orders o) {
        return this.start_date.compareTo(o.start_date);
    }

    public static class Comparators{
        public static Comparator<T_Orders> Start_Date = new Comparator<T_Orders>() {
            @Override
            public int compare(T_Orders o1, T_Orders o2) {
                return o1.start_date.compareTo(o2.start_date);
            }


        };
    }

    public boolean isSectionHeader() {
        return isSectionHeader;
    }
    public void setToSectionHeader() {
        isSectionHeader = true;
    }


}