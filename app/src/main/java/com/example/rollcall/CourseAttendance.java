package com.example.rollcall;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CourseAttendance {
    private String courseName;
    private String attendance;

    public CourseAttendance()
    {}
    public CourseAttendance(String courseName, String attendance)
    {
        this.courseName=courseName;
        this.attendance = attendance;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }



}
