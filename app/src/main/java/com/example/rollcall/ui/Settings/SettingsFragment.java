package com.example.rollcall.ui.Settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.rollcall.LogInScreen;
import com.example.rollcall.R;
import com.example.rollcall.ui.home.HomeFragment;
import com.example.rollcall.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SettingsFragment extends Fragment {

    private SettingsViewModel SettingsViewModel;
    String email;
    String uid;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    private CollectionReference usersRef =db.collection("users");

    public Button goback;

    private Context globalContext=null;
    Button logout;
    String user_id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       SettingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
       EditText mail =(EditText)root.findViewById(R.id.email);
       final EditText firstName=(EditText)root.findViewById(R.id.editTextTextPersonName);
       //getData();

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
                        String firstname = user.getFirstName();
                        String lastname = user.getLastName();
                        String mail = user.getMail();
                        String SudentNumber = user.getStudentNumber();
                       // Log.d("firstname",firstname);

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}