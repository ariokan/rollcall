package com.example.rollcall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rollcall.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterScreen extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;

    private FirebaseFirestore db;
    private FirebaseAuth myAuth ;
    ImageView studentPhoto;
    private Uri imageData;
    StorageReference storageReference;
    String user_id;
    final ArrayList<Course> courseArrayList= new ArrayList <Course>();
    private CollectionReference usersRef;





    protected Boolean IsDataValid(String Mail,String Pass1,String Pass2,String name,String surname, String stdonumber){
        if(TextUtils.isEmpty(Mail)){
            Toast.makeText(RegisterScreen.this, "Please Enter Your mail address.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(name)){
            Toast.makeText(RegisterScreen.this, "Please Enter Your Firstname.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if( TextUtils.isEmpty(surname)){
            Toast.makeText(RegisterScreen.this, "Please Enter Your Lastname.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(stdonumber) ){
            Toast.makeText(RegisterScreen.this, "Please Enter Your Student number.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.equals(Pass1,Pass2)&&TextUtils.isEmpty(Pass1)){


            return true;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        myAuth = FirebaseAuth.getInstance();

        final EditText name = (EditText) findViewById(R.id.editTextTextPersonName);
        final EditText surname = (EditText) findViewById(R.id.surname);
        final EditText mail = (EditText) findViewById(R.id.email);
        final EditText stdonumber = (EditText) findViewById(R.id.StudentNumber);
        final  EditText pass1 =(EditText)findViewById(R.id.editTextTextPassword);
        final  EditText pass2 =(EditText)findViewById(R.id.editTextTextPassword2);
        storageReference= FirebaseStorage.getInstance().getReference();

        courseArrayList.add(new Course("Computer Networks","B123","Halim Zaim"));
        courseArrayList.add(new Course("Data Mining","B125","Arzu Kakisim"));
        courseArrayList.add(new Course("Cloud Computing ","B145","AlperÖzpınar"));
        courseArrayList.add(new Course("Parallel Computing","B125","Turgay Altılar"));
        Map<String, Object> CourseData = new HashMap<>();
        for(int i=0;i<=3;i++) {
            CourseData.put("lectureName", courseArrayList.get(i).getLectureName());
            CourseData.put("lectureCode", courseArrayList.get(i).getLectureCode());
            CourseData.put("lecturerName", courseArrayList.get(i).getLecturerName());
        }


        final Button  rbtn = (Button) findViewById(R.id.rbtn);
        db= FirebaseFirestore.getInstance();
        studentPhoto=findViewById(R.id.student_photo);
        studentPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);*/
               selectImage(this);

            }
        });
        rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Surname=surname.getText().toString();
                final String Mail = mail.getText().toString();
                final String Stdonumber = stdonumber.getText().toString();
                final String Pass1 =pass1.getText().toString();
                final String Pass2 =pass2.getText().toString();
                final String Name=name.getText().toString();

                if(IsDataValid(Mail,Pass1,Pass2,Name,Surname,Stdonumber)){
                    myAuth.createUserWithEmailAndPassword(Mail, Pass1)
                            .addOnCompleteListener(RegisterScreen.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = myAuth.getCurrentUser();
                                        Log.d("Okanburayabak",myAuth.getUid() );
                                        addCourse(myAuth.getUid());
                                        Toast.makeText(RegisterScreen.this, "Authentication success.",
                                                Toast.LENGTH_SHORT).show();

                                        final Intent intent = new Intent(RegisterScreen.this, MainActivity.class);

                                        intent.putExtra("name",Name);
                                        intent.putExtra("surname",Surname);
                                        intent.putExtra("mail",Mail);
                                        intent.putExtra("stdonumber",Stdonumber);
                                        intent.putExtra("pass1",Pass1);
                                        uploadImage();
                                        startActivity(intent);


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("failed", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterScreen.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });
                }

            }
        });
    }

    private void addCourse(String path){
        usersRef =db.collection("users").document(path).collection("course");
        for(int i=0;i<=3;i++) {
            usersRef.add(courseArrayList.get(i)).
                    addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                        }
                    });
        }
    }
    private void selectImage(View.OnClickListener context){
        final CharSequence[] options={"Take photo","Choose from gallery","Cancel"};
        android.app.AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Choose your profile picture");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }


    private void uploadImage()
    {
        if (imageData != null) {

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog
                    = new ProgressDialog(RegisterScreen.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            user_id=myAuth.getCurrentUser().getUid();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/" ).child(user.getEmail());


            // adding listeners on upload
            // or failure of image
            ref.putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(
                        UploadTask.TaskSnapshot taskSnapshot)
                {

                    // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(RegisterScreen.this,
                            "Image Uploaded!!",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {

                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(RegisterScreen.this,"Failed"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterScreen.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                }
                            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null){
            imageData = data.getData();

            studentPhoto.setImageURI(imageData);



        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}