package com.example.login_signup;

public class T_Details {


    private String Type;
    private String Code;
    private String Observation ;


    public T_Details(String type, String code, String observation) {
        Type = type;
        Code = code;
        Observation = observation;
    }


    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getObservation() {
        return Observation;
    }

    public void setObservation(String observation) {
        Observation = observation;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }


}
