package com.yk.attendancemanagement.Class;

public class Student {
    String RollNo;
    boolean Status;


    public Student(String rollNo, boolean status) {
        RollNo = rollNo;
        Status = status;
    }

    public boolean getStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public Student(String rollNo) {
        RollNo = rollNo;
        Status = false;
    }

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String rollNo) {
        RollNo = rollNo;
    }
}
