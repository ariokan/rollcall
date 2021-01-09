package com.example.rollcall.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.rollcall.Course;
import com.example.rollcall.CourseAdapter;
import com.example.rollcall.MainActivity;
import com.example.rollcall.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView listView;
    //SearchView searchView;
   // ArrayAdapter<String> adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
   // String[] courseName={"Veri Madenciliği","Bilişim Tasarım Projesi","Paralel Bilgisayarlar","Bulut Bilişim"};
    String[] teacherName={"Arzu Kakışım","Arzu Kakışım","Turgay Altılar","Alper Özpınar"};
    String[] courseCode={"BIL235","BIL222","BIL131","BIL333"};
    int images[]={R.drawable.ic_home_black_24dp};
    final ArrayList<Course> courses = new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        listView=(ListView)root.findViewById(R.id.listView1);

        courses.add(new Course("Computer Networks","B123","Halim Zaim"));
        courses.add(new Course("Computer Networks","B123","Halim Zaim"));
        courses.add(new Course("Computer Networks","B123","Halim Zaim"));
        courses.add(new Course("Computer Networks","B123","Halim Zaim"));
        courses.add(new Course("Computer Networks","B123","Halim Zaim"));
        courses.add(new Course("Computer Networks","B123","Halim Zaim"));
        courses.add(new Course("Computer Networks","B123","Halim Zaim"));
        courses.add(new Course("Computer Networks","B123","Halim Zaim"));
        courses.add(new Course("Computer Networks","B123","Halim Zaim"));
        courses.add(new Course("Computer Networks","B123","Halim Zaim"));
        courses.add(new Course("Computer Networks","B123","Halim Zaim"));
        courses.add(new Course("Computer Networks","B123","Halim Zaim"));
        courses.add(new Course("Computer Networks","B123","Halim Zaim"));
        courses.add(new Course("Computer Networks","B123","Halim Zaim"));
        courses.add(new Course("Computer Networks","B123","Halim Zaim"));

        CourseAdapter courseAdapter=new CourseAdapter(inflater,courses);
        if(listView!=null){
            listView.setAdapter(courseAdapter);
        }

        //adapter=new ArrayAdapter<String >(getActivity(),android.R.layout.simple_list_item_1,courseName);

        //listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"Clicked item at position" +position,Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }




}