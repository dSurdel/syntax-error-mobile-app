package io.carmaster.myapplication.services.retrofitModels;

public class User {
    String email = "";
    String password = "";
    String firstName = "";
    String token = "";

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
