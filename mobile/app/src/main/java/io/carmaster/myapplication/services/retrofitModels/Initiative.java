package io.carmaster.myapplication.services.retrofitModels;

public class Initiative {
    String description = "acdsa";
    String name = "acdsa";
    int positive = 1;
    int negative = 1;
    int neutral = 1;
    String submittedByUserId = "39a5b28e-f2c8-40f4-9fa6-f4605cac8e48";
    String token = "";
    String socialInitiativeId = "";
    double latitude = 0.0;
    double longitude = 0.0;
    String city = "Debica";
    String province = "Podkarpackie";
    String region = "Polnocna";


    public void setDescription(String description) {
        this.description = description;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCoords(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
