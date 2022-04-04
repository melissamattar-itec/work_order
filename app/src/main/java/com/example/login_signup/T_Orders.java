package com.example.login_signup;

import java.util.Comparator;

public class T_Orders implements Comparable<T_Orders>{

    private Integer id;
    private Integer user_id;
    private String type;
    private String sous_type;
    private Integer client_number;
    private Integer order_number;
    private Integer equipment_number;
    private String start_date;
    private String description;
    private String order_date;
    private String priority;
    private String client_name;
    private String problem;
    private Double longitude;
    private Double latitude;
    private String status;
    private Integer Service_address;
    private String equipment_description;
    private String equipment_status;
    private String phone_area_code;
    private String phone_number;
    private String email_address;
    private String address_line1;
    private String address_line2;
    private String installation_code;
    private String puissance;
    private String tarif;
    private String amperage;
    private String Site;
    private String Commune;
    private String Quartier;
    private String Address_Installation;
    private String Notes;
    private Integer edited;
    private  boolean isSectionHeader;


    public T_Orders(Integer id, Integer user_id, String type, String sous_type, Integer client_number, Integer order_number, Integer equipment_number, String start_date, String description, String order_date, String priority, String client_name, String problem, Double longitude, Double latitude, String status, Integer service_address, String equipment_description, String equipment_status, String phone_area_code, String phone_number, String email_address, String address_line1, String address_line2, String installation_code, String puissance, String tarif, String amperage, String site , String commune , String quartier,String address_Installation , String Notes ,Integer edited) {
        this.id = id;
        this.user_id = user_id;
        this.type = type;
        this.sous_type = sous_type;
        this.client_number = client_number;
        this.order_number = order_number;
        this.equipment_number = equipment_number;
        this.start_date = start_date;
        this.description = description;
        this.order_date = order_date;
        this.priority = priority;
        this.client_name = client_name;
        this.problem = problem;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;
        this.Service_address= service_address;
        this.equipment_description = equipment_description;
        this.equipment_status = equipment_status;
        this.phone_area_code = phone_area_code;
        this.phone_number = phone_number;
        this.email_address = email_address;
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.installation_code = installation_code;
        this.puissance = puissance;
        this.tarif = tarif;
        this.amperage = amperage;
        this.Site = site;
        this.Commune = commune;
        this.Quartier = quartier;
        this.Notes = Notes;
        this.edited = edited;
        this.Address_Installation = address_Installation;
        isSectionHeader=false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSous_type() {
        return sous_type;
    }

    public void setSous_type(String sous_type) {
        this.sous_type = sous_type;
    }

    public Integer getClient_number() {
        return client_number;
    }

    public void setClient_number(Integer client_number) {
        this.client_number = client_number;
    }

    public Integer getOrder_number() {
        return order_number;
    }

    public void setOrder_number(Integer order_number) {
        this.order_number = order_number;
    }

    public Integer getEquipment_number() {
        return equipment_number;
    }

    public void setEquipment_number(Integer equipment_number) {
        this.equipment_number = equipment_number;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getEquipment_description() {
        return equipment_description;
    }

    public void setEquipment_description(String equipment_description) {
        this.equipment_description = equipment_description;
    }

    public String getEquipment_status() {
        return equipment_status;
    }

    public void setEquipment_status(String equipment_status) {
        this.equipment_status = equipment_status;
    }

    public Integer getEdited() {
        return edited;
    }

    public void setEdited(Integer edited) {
        this.edited = edited;
    }

    public boolean isSectionHeader() {
        return isSectionHeader;
    }

    public void setSectionHeader(boolean sectionHeader) {
        isSectionHeader = sectionHeader;
    }

    public String getPhone_area_code() {
        return phone_area_code;
    }

    public void setPhone_area_code(String phone_area_code) {
        this.phone_area_code = phone_area_code;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getAddress_line2() {
        return address_line2;
    }

    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

    public String getInstallation_code() {
        return installation_code;
    }

    public void setInstallation_code(String installation_code) {
        this.installation_code = installation_code;
    }

    public String getPuissance() {
        return puissance;
    }

    public void setPuissance(String puissance) {
        this.puissance = puissance;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public String getAmperage() {
        return amperage;
    }

    public void setAmperage(String amperage) {
        this.amperage = amperage;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public String getCommune() {
        return Commune;
    }

    public void setCommune(String commune) {
        Commune = commune;
    }

    public String getQuartier() {
        return Quartier;
    }

    public void setQuartier(String quartier) {
        Quartier = quartier;
    }

    public Integer getService_address() {
        return Service_address;
    }


    public void setService_address(Integer service_address) {
        Service_address = service_address;
    }
    public String getAddress_Installation() {
        return Address_Installation;
    }

    public void setAddress_Installation(String address_Installation) {
        Address_Installation = address_Installation;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }



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


}
