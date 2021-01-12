package com.example.rollcall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;

public class CourseAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Course> courseArrayList;

    public CourseAdapter(LayoutInflater mInflater, ArrayList<Course> courseArrayList) {
        this.mInflater = mInflater;
        this.courseArrayList = courseArrayList;
    }

    @Override
    public int getCount(){
    return courseArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.row_custom_list_item,null);
        TextView courseName =(TextView)convertView.findViewById(R.id.dersAdi);
        TextView courseCode =(TextView)convertView.findViewById(R.id.dersKodu);
        TextView lecturer =(TextView)convertView.findViewById(R.id.akademisyenAdi);
        Course course =courseArrayList.get(position);
        courseName.setText(course.getLectureName());
        courseCode.setText(course.getLectureCode());
        lecturer.setText(course.getLecturerName());


        return convertView ;
    }

}
