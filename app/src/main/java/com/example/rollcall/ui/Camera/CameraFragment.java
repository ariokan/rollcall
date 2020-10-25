package com.example.rollcall.ui.Camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rollcall.R;

import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class CameraFragment extends Fragment {

    private CameraViewModel dashboardViewModel;
    Button button;
    private static final int Image_Capture_Code = 1;


    private RecyclerView chatRecylerview;
    View view;

    LinearLayoutManager manager;
    ArrayList<HashMap<String, String>> userDetail = new ArrayList<>();
    HashMap<String, String> data;
    ImageView imagCapture;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(CameraViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        imagCapture=(ImageView)root.findViewById(R.id.imageView2);
        button=(Button)root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cInt=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt,Image_Capture_Code);
            }
        });

        return root;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==Image_Capture_Code){
            if(resultCode== RESULT_OK){
                Bitmap bp=(Bitmap) data.getExtras().get("data");
                imagCapture.setImageBitmap(bp);
            }else if(requestCode==RESULT_CANCELED){
                Toast.makeText(getActivity(),"Cancelled",Toast.LENGTH_LONG).show();
            }

        }
    }
}