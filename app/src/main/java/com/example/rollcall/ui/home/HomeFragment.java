package com.example.rollcall.ui.home;

import android.content.Context;
import android.os.Bundle;
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

import com.example.rollcall.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView listView;
    SearchView searchView;
    ArrayAdapter<String> adapter;
    String[] courseName={"Veri Madenciliği","Bilişim Tasarım Projesi","Paralel Bilgisayarlar","Bulut Bilişim"};
    String[] teacherName={"Arzu Kakışım","Arzu Kakışım","Turgay Altılar","Alper Özpınar"};
    int images[]={R.drawable.ic_home_black_24dp};


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/



        listView=(ListView)root.findViewById(R.id.listView1);
        adapter=new ArrayAdapter<String >(getActivity(),android.R.layout.simple_list_item_1,courseName);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"Clicked item at position" +position,Toast.LENGTH_SHORT).show();
            }
        });




        return root;
    }


}