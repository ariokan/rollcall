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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView listView;
    //SearchView searchView;
   // ArrayAdapter<String> adapter;


    int images[]={R.drawable.ic_home_black_24dp};
    final ArrayList<Course> courses = new ArrayList<>();
    FirebaseUser user;
    private CollectionReference usersRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        listView=(ListView)root.findViewById(R.id.listView1);

        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId=user.getUid();
        usersRef =db.collection("users").document(userId).collection("course");


        usersRef
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    Course course = documentSnapshot.toObject(Course.class);
                    courses.add(new Course(course.getLectureName(),course.getLectureCode(),course.getLecturerName(),course.getAttendance()));
                }
                CourseAdapter courseAdapter = new CourseAdapter(inflater,courses);
                listView.setAdapter(courseAdapter);
            }
    }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        return root;
    }




}