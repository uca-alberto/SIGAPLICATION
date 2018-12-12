package com.example.hacker.sigaplication.Model;

import java.sql.Date;

public class CommentsModel {
    private String User;
    private String Comment;
    private String Place_visit;
    private String date;


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

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getPlace_visit() {
        return Place_visit;
    }

    public void setPlace_visit(String place_visit) {
        Place_visit = place_visit;
    }
}
