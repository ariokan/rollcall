package com.example.rollcall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;

    //checks whete
    public Boolean ValidateEmail (String mailadress){
        if (!Patterns.EMAIL_ADDRESS.matcher(mailadress).matches() && mailadress.equals(""))
            return false;
        else
            return true;
    }
    public Boolean ValidatePass(String pass){
        if(pass.equals(""))
            return false;
        else
            return true;
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);
        //initilazing
        final EditText email =(EditText)findViewById(R.id.Email);
        final EditText pass =(EditText)findViewById(R.id.Password);
        final String mail =email.getText().toString().trim();
        final String pass_= pass.getText().toString();
        Button LogIn =(Button) findViewById(R.id.LoginButton);
        //handling button click
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();


                String EmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(ValidateEmail(mail)) {
                    Toast.makeText(LogInScreen.this, "You did not enter a email or not valid e-mail address", Toast.LENGTH_SHORT).show();
                }
                else if(ValidatePass(pass_)){
                    Toast.makeText(LogInScreen.this, "You did not enter pass ", Toast.LENGTH_SHORT).show();
                }
                else {
                mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnCompleteListener(LogInScreen.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("success", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent intent = new Intent(LogInScreen.this, MainActivity.class);
                                        startActivity(intent);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(LogInScreen.this, "Authentication failed. try again",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    // ...
                                }

                        });
                }
            }
        });


    }
}
