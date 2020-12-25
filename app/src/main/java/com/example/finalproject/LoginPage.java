package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        TextView changePassActive = (TextView) findViewById(R.id.forget_pass);

        logEmail    = (EditText) findViewById(R.id.email);
        logPass     = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(this);
        signUpActivity.setOnClickListener(this);
        changePassActive.setOnClickListener(this);
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
            case R.id.forget_pass:
                switchToForgetLayout();
            
                break;        
            default:
                break;
        }
    } // onClick end

    public void signupChange(){
        Intent signPage = new Intent(LoginPage.this, SignPage.class);
        startActivity(signPage);
    } // signupChange end

    private Boolean validateEmail(){
        String val = logEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            logEmail.setError("Email must not be empty.");
            return false;
        } else if (!val.matches(emailPattern)) {
            logEmail.setError("Invalid email, please try again.");
            return false;
        }else{
            logEmail.setError(null);
            return true;
        }
    }// validEmail end

    private Boolean validatePassword(){
        String val = logPass.getText().toString().trim();
        if(val.isEmpty()){
            logPass.setError("Password must not be empty.");
            return false;
        }
        else{
            logPass.setError(null);
            return true;
        }
    } // Pvalidassword end

    public void loginUser(){
        if(!validateEmail() | !validatePassword()){
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
                            logPass.setError("User is not found. Please check your email and your password");

                            Log.e("Error", "Login Failed");
                        }
                    }
                });
    } // isUser end

    
    void switchToForgetLayout() {
             setContentView(R.layout.change_pass_layout);
             changePassActive();      
    }

    void changePassActive(){
        Button resetPass = (Button) findViewById(R.id.reset_pass_btn) ;
        final TextView notice = (TextView) findViewById(R.id.reset_description);
        final TextView previous = (TextView) findViewById(R.id.return_label);

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setText(
                    "Please check your email and wait at least 60 seconds before sending your new reset request by pressing the reset button.");
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnPrevious = new Intent(LoginPage.this, LoginPage.class);
                startActivity(returnPrevious);
            }});
    }  
}