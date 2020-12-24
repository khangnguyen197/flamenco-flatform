package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class LoginPage extends AppCompatActivity implements  View.OnClickListener{

    EditText logEmail, logPass;
    private FirebaseAuth mAuth;
    private FirebaseFirestore data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Globals globals = new Globals();
        globals.transStatus(getWindow());


        Button submit = (Button) findViewById(R.id.submit_btn);
        TextView signUpActivity = (TextView) findViewById(R.id.sign_link);

        logEmail    = (EditText) findViewById(R.id.email);
        logPass     = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(this);
        signUpActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_btn:
                loginUser();
                break;
            case R.id.sign_link:
                signupChange();
                break;
            default:
                break;
        }
    } // onClick end

    public void signupChange(){
        Intent signPage = new Intent(LoginPage.this, SignPage.class);
        startActivity(signPage);
    } // signupChange end

    private Boolean validUsername(){
        String val = logEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            logEmail.setError("Username cannot be empty ");
            return false;
        } else if (!val.matches(emailPattern)) {
            logEmail.setError("Invalid email, try again");
            return false;
        }else{
            logEmail.setError(null);
            return true;
        }
    }// validUsername end

    private Boolean validPassword(){
        String val = logPass.getText().toString().trim();
        if(val.isEmpty()){
            logPass.setError("Password cannot be empty ");
            return false;
        }
        else{
            logPass.setError(null);
            return true;
        }
    } // validPassword end

    public void loginUser(){
        if(!validUsername() | !validPassword()){
            return;
        }
        else{
            isUser();
        }
    } // loginUser end

    private void isUser(){
        final String email        =  logEmail.getText().toString().trim();
        final String password     =  logPass.getText().toString().trim();
        data =  FirebaseFirestore.getInstance();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("password",password);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            data.collection("users").document(email).update("password", password);
                            LoginPage.this.startActivity(new Intent(LoginPage.this, Home.class));
                            LoginPage.this.finish();
                        } else {
                            logPass.setError("Pass or Email is wrong");
                            Log.e("Error", "Login Failed");
                        }
                    }
                });
    } // isUser end

    /*public void forgot(View view){
        EditText resetEmail = new EditText(view.getContext());
        AlertDialog.Builder passwordReset = new AlertDialog.Builder(view.getContext());
        passwordReset.setTitle("Reset Password?");
        passwordReset.setMessage("Enter Your Email: ");
        passwordReset.setView(resetEmail);

        passwordReset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mail = resetEmail.getText().toString();
                mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Login.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Reset link not sent "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        passwordReset.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        passwordReset.create().show();
    }*/
}