package com.example.rollcall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterScreen extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
<<<<<<< HEAD
    private FirebaseAuth mAuth;
    private EditText registerName;
    private EditText registerSurname;
    private EditText registerEmail;
    private EditText registerStudentNumber;
    private EditText registerPassword;
    private Button registerButton;
=======
    private FirebaseFirestore db;
    private FirebaseAuth myAuth ;
    ImageView studentPhoto;




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
>>>>>>> de446a34722e8e9ebb9f5612ca262ac0e36a10a8

        return true;
    }
    return true;
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
/*
        mAuth=FirebaseAuth.getInstance();
        registerName=findViewById(R.id.textName);
        registerSurname=findViewById(R.id.textSurname);
        registerEmail=findViewById(R.id.Email);
        registerPassword=findViewById(R.id.textPassword);
        registerStudentNumber=findViewById(R.id.StudentNumber);
        registerButton=findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=registerName.getText().toString();
                String surname=registerSurname.getText().toString();
                String email=registerEmail.getText().toString();
                String studentNumber=registerStudentNumber.getText().toString();
                String password=registerPassword.getText().toString();
            }
        });
=======*/
        myAuth = FirebaseAuth.getInstance();
        final EditText name = (EditText) findViewById(R.id.editTextTextPersonName);
        final EditText surname = (EditText) findViewById(R.id.surname);
        final EditText mail = (EditText) findViewById(R.id.email);
        final EditText stdonumber = (EditText) findViewById(R.id.StudentNumber);
        final  EditText pass1 =(EditText)findViewById(R.id.editTextTextPassword);
        final  EditText pass2 =(EditText)findViewById(R.id.editTextTextPassword2);



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
                                        Intent intent = new Intent(RegisterScreen.this, MainActivity.class);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null){
            Uri imageData = data.getData();

            studentPhoto.setImageURI(imageData);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}