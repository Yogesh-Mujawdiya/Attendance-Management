package com.yk.attendancemanagement.Class;

public class Course {
    String DepartmentId, CourseId, DepartmentName, CourseName, Qualification_Code;
    int StudentCount, SectionCount;
    boolean Status;

    public Course(String departmentId, String courseId, String courseName, String qualification_Code, int studentCount, int sectionCount) {
        DepartmentId = departmentId;
        CourseId = courseId;
        CourseName = courseName;
        Qualification_Code = qualification_Code;
        StudentCount = studentCount;
        SectionCount = sectionCount;
    }

    public String getQualification_Code() {
        return Qualification_Code;
    }

    public void setQualification_Code(String qualification_Code) {
        Qualification_Code = qualification_Code;
    }

    public Course(String departmentId, String courseId, String courseName, int sectionCount) {
        DepartmentId = departmentId;
        CourseId = courseId;
        CourseName = courseName;
        SectionCount = sectionCount;
    }

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

    public int getSectionCount() {
        return SectionCount;
    }

    public void setSectionCount(int sectionCount) {
        SectionCount = sectionCount;
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
