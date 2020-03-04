package com.yk.attendancemanagement.Class;

public class Student {
    String RollNo;
    int PresentClass, AbsentClass;
    boolean Status;


    public Student(String rollNo, boolean status) {
        RollNo = rollNo;
        Status = status;
    }

    public Student(String rollNo, int presentClass, int absentClass) {
        RollNo = rollNo;
        PresentClass = presentClass;
        AbsentClass = absentClass;
    }

    public int getPresentClass() {
        return PresentClass;
    }

    public void setPresentClass(int presentClass) {
        PresentClass = presentClass;
    }

    public int getAbsentClass() {
        return AbsentClass;
    }

    public void setAbsentClass(int absentClass) {
        AbsentClass = absentClass;
    }

    public boolean isStatus() {
        return Status;
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
