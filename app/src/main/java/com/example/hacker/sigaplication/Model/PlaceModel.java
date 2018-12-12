package com.example.hacker.sigaplication.Model;

public class PlaceModel {
    private String Place_Visit;
    private String User;
    private String date;
    private String DangerNivel;

    public String getPlace_Visit() {
        return Place_Visit;
    }

    public void setPlace_Visit(String place_Visit) {
        Place_Visit = place_Visit;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDangerNivel() {
        return DangerNivel;
    }

    public void setDangerNivel(String dangerNivel) {
        DangerNivel = dangerNivel;
    }
}
