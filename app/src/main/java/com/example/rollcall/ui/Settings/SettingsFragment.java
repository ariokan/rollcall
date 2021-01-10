package com.example.rollcall.ui.Settings;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.example.rollcall.LogInScreen;
import com.example.rollcall.MainActivity;
import com.example.rollcall.R;
import com.example.rollcall.ui.home.HomeFragment;
import com.example.rollcall.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends Fragment {

    private SettingsViewModel SettingsViewModel;
    String email;
    String uid;
    private CircleImageView imageView;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    private CollectionReference usersRef =db.collection("users");

    private StorageReference mStorageReference;


    public Button goback;
    private EditText firstName;
    private EditText lastName;
    public Button apply;
    private TextView studentNumber;



    private Context globalContext=null;
    Button logout;
    String user_id;
    String firstname;
    String lastname;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       SettingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
       EditText mail =(EditText)root.findViewById(R.id.email);
       firstName=(EditText)root.findViewById(R.id.editTextTextPersonName);
       lastName=(EditText)root.findViewById(R.id.surname);
       studentNumber=(TextView)root.findViewById(R.id.StudentNumber);
       imageView=(CircleImageView)root.findViewById(R.id.student_photo);
       apply=(Button)root.findViewById(R.id.ApplyChanges);
       apply.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               updateData();
           }
       });




       globalContext=this.getActivity();
       logout=(Button)root.findViewById(R.id.logOut);
       logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mAuth.signOut();
               Toast.makeText(globalContext,"Oturum Kapatıldı",Toast.LENGTH_SHORT).show();
               Intent loginIntent=new Intent(getActivity(), LogInScreen.class);
               startActivity(loginIntent);

           }
       });
       getImage();


        db= FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        user_id=mAuth.getCurrentUser().getUid();
       goback =(Button)root.findViewById(R.id.Goback);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
             email = user.getEmail();
            boolean emailVerified = user.isEmailVerified();
             uid = user.getUid();
        }


        mail.setText(email);
        getData();
        return root;
    }




    public void getData(){

        try {
            usersRef.whereEqualTo("mail",email)
                    .get().
                    addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    String data ="";
                    for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                        user user = documentSnapshot.toObject(user.class);
                        firstname = user.getFirstName();
                        lastname = user.getLastName();
                        String mail = user.getMail();
                        String SudentNumber = user.getStudentNumber();

                        firstName.setText(firstname);
                        lastName.setText(lastname);
                        studentNumber.setText(SudentNumber);

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public void getImage(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        String path=user.getEmail();
        mStorageReference=FirebaseStorage.getInstance().getReference().child("images/"+path);

        try {
            final File localFile=File.createTempFile(path,"jpg");
            mStorageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(globalContext,"Picture retrieved",Toast.LENGTH_SHORT).show();
                    Bitmap bitmap=BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imageView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(globalContext,"Error Occurred",Toast.LENGTH_SHORT).show();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void updateData(){

        final DocumentReference docRef=db.collection("users").document(user_id);


        try {
            usersRef.whereEqualTo("mail",email)
                    .get().
                    addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                            for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                                user user = documentSnapshot.toObject(user.class);
                                String SudentNumber = user.getStudentNumber();
                                lastname = user.getLastName();
                                String password=user.getPassword();

                                Editable changedFirstName=firstName.getText();
                                Editable changedLastname=lastName.getText();
                                Map<String, Object> userData = new HashMap<>();
                                userData.put("firstName",changedFirstName.toString());
                                userData.put("lastName",changedLastname.toString());





                                db.collection("users").document(user_id).update(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(globalContext, "Güncellendi.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }). addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(globalContext, "Güncellenemedi.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}