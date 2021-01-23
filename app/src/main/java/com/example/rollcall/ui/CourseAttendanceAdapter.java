package com.example.rollcall.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.rollcall.Course;
import com.example.rollcall.R;

import java.util.ArrayList;

public class CourseAttendanceAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private ArrayList<Course> CourseAttendanceArrayList;

    public CourseAttendanceAdapter(LayoutInflater mInflater, ArrayList<Course> CourseAttendanceArrayList){
        this.mInflater = mInflater;
        this.CourseAttendanceArrayList = CourseAttendanceArrayList;
    }
    @Override
    public int getCount() {
        return CourseAttendanceArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return CourseAttendanceArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.custom_listview_item, null);
        TextView courseName =(TextView)convertView.findViewById(R.id.dersAdiA);
        TextView courseCode =(TextView)convertView.findViewById(R.id.dersKoduA);
        TextView lecturer =(TextView)convertView.findViewById(R.id.akademisyenAdiA);
        Course course =CourseAttendanceArrayList.get(position);
        courseName.setText(course.getLectureName());
        courseCode.setText(course.getLectureCode());
        lecturer.setText(course.getLecturerName());
        Button katilbtn = (Button) convertView.findViewById(R.id.katilbtnA);

        Course courseAttendance = CourseAttendanceArrayList.get(position);
        courseName.setText(courseAttendance.getLectureName());


        return convertView;
    }
}
