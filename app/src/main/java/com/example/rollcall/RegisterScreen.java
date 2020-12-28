package com.example.rollcall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
        user_id=myAuth.getCurrentUser().getUid();



        final Button  rbtn = (Button) findViewById(R.id.rbtn);
        db= FirebaseFirestore.getInstance();
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
                                        Log.d("yiyodo", "createUserWithEmail:success");
                                        FirebaseUser user = myAuth.getCurrentUser();
                                        Toast.makeText(RegisterScreen.this, "Authentication success.",
                                                Toast.LENGTH_SHORT).show();
                                        final Intent intent = new Intent(RegisterScreen.this, MainActivity.class);
                                        Map<String, Object> userData = new HashMap<>();
                                        userData.put("firstName", Name);
                                        userData.put("lastName", Surname);
                                        userData.put("mail", Mail);
                                        userData.put("studentNumber", Stdonumber);
                                        userData.put("password", Pass1);

                                        // Add a new document with a generated ID
                                        db.collection("users")
                                                .add(userData)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Log.d("user_data_save", "DocumentSnapshot added with ID: " + documentReference.getId());
                                                        Toast.makeText(RegisterScreen.this, "Firestore success.",
                                                                Toast.LENGTH_SHORT).show();
                                                        uploadImage();

                                                        startActivity(intent);

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w("user_data_save_failed", "Error adding document", e);
                                                        Toast.makeText(RegisterScreen.this, "Firestore failed.",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                });


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


    private void uploadImage()
    {
        if (imageData != null) {

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog
                    = new ProgressDialog(RegisterScreen.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/"+user_id );


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