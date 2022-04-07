package com.example.epidemicreporting;

public class StoreData {

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNid() {
        return Nid;
    }

    public void setNid(String nid) {
        Nid = nid;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getUpazila() {
        return Upazila;
    }

    public void setUpazila(String upazila) {
        Upazila = upazila;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }



     private  String Name;
      private String  Email;
      private String  Nid;
      private  String Division;
      private String  District;
      private String  Upazila;
      private String  Gender;

    public StoreData(String name, String email, String nid, String division, String district, String upazila, String gender) {
        Name = name;
        Email = email;
        Nid = nid;
        Division = division;
        District = district;
        Upazila = upazila;
        Gender = gender;

    }



}
