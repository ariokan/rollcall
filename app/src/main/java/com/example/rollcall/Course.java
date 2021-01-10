package com.example.rollcall;

public class Course {

    private String lectureName;
    private String lectureCode;
    private String lecturerName;


    public Course() {
    }
    public Course(String lectureName, String lectureCode, String lecturerName) {
        this.lectureName = lectureName;
        this.lectureCode = lectureCode;
        this.lecturerName = lecturerName;
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
}
