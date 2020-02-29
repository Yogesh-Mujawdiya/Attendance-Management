package com.yk.attendancemanagement.Class;

public class Course {
    String DepartmentId, CourseId, DepartmentName, CourseName;
    int StudentCount;
    boolean Status;

    public Course(String departmentId, String courseId, String courseName) {
        DepartmentId = departmentId;
        CourseId = courseId;
        CourseName = courseName;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public Course(String departmentId, String courseId, String departmentName, String courseName, int studentCount, boolean status) {
        DepartmentId = departmentId;
        CourseId = courseId;
        DepartmentName = departmentName;
        CourseName = courseName;
        StudentCount = studentCount;
        Status = status;
    }

    public Course(String departmentId, String courseId, String departmentName, String courseName, int studentCount) {
        DepartmentId = departmentId;
        CourseId = courseId;
        DepartmentName = departmentName;
        CourseName = courseName;
        StudentCount = studentCount;
    }

    public Course() {
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String departmentId) {
        DepartmentId = departmentId;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public int getStudentCount() {
        return StudentCount;
    }

    public void setStudentCount(int studentCount) {
        StudentCount = studentCount;
    }
}
