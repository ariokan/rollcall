package com.example.rollcall;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterScreen extends AppCompatActivity {

    ImageView studentPhoto;
    public static final int PICK_IMAGE = 1;
    private FirebaseAuth mAuth;
    private EditText registerName;
    private EditText registerSurname;
    private EditText registerEmail;
    private EditText registerStudentNumber;
    private EditText registerPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        mAuth=FirebaseAuth.getInstance();
        registerName=findViewById(R.id.textName);


        studentPhoto=findViewById(R.id.student_photo);
        studentPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null){
            Uri imageData = data.getData();

            studentPhoto.setImageURI(imageData);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}