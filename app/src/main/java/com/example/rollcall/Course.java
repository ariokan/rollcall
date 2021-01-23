package com.example.rollcall;

import java.util.List;

public class Course {

    private String lectureName;
    private String lectureCode;
    private String lecturerName;
    List<String> attendance;


    public Course() {
    }
    public Course(String lectureName, String lectureCode, String lecturerName,List<String> attendance) {
        this.lectureName = lectureName;
        this.lectureCode = lectureCode;
        this.lecturerName = lecturerName;
        this.attendance = attendance;
    }




    public String getLectureName() {
        return lectureName;
    }

    public String getLectureCode() {
        return lectureCode;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public void setLectureCode(String lectureCode) {
        this.lectureCode = lectureCode;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public List<String> getAttendance() {
        return attendance;
    }
}
