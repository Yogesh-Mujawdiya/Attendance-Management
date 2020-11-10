package com.yk.attendancemanagement.Class;

import java.util.ArrayList;

public class Subject {
    String ID, Name;
    int Total,Attend;
    Boolean Status;


    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public Subject(String ID, String name, Boolean status) {
        this.ID = ID;
        Name = name;
        Status = status;
    }


    public Subject(String ID, String name, int total, int attend) {
        this.ID = ID;
        Name = name;
        Total = total;
        Attend = attend;
        Status = false;
    }

    public Subject(String ID, String name) {
        this.ID = ID;
        Name = name;
        Status = false;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public int getAttend() {
        return Attend;
    }

    public void setAttend(int attend) {
        Attend = attend;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Subject(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
