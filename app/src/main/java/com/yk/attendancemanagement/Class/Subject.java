package com.yk.attendancemanagement.Class;

import java.util.ArrayList;

public class Subject {
    String ID, Name;
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

    public Subject(String ID, String name) {
        this.ID = ID;
        Name = name;
        Status = false;
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
