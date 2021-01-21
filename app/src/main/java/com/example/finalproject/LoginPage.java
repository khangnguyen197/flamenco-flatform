package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class LoginPage extends AppCompatActivity implements  View.OnClickListener{

    TextInputLayout tilEmail, tilPass, tilReset; EditText edEmail, edPass;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Globals globals = new Globals();
        globals.transStatus(getWindow());

        Button submit = (Button) findViewById(R.id.submit_btn);
        TextView signUpActivity = (TextView) findViewById(R.id.sign_link);
        TextView changePassActive = (TextView) findViewById(R.id.forget_pass);

        tilEmail    = (TextInputLayout) findViewById(R.id.TILemail);
        tilPass     = (TextInputLayout) findViewById(R.id.TILpassword);
        edEmail     = (EditText) findViewById(R.id.email);
        edPass      = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        edEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateEmail();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
        String val = tilEmail.getEditText().getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            tilEmail.setError("Email must not be empty.");
            tilEmail.setErrorEnabled(true);
            return false;
        } else if (!val.matches(emailPattern)) {
            tilEmail.setError("Invalid email, please try again.");
            tilEmail.setErrorEnabled(true);
            return false;
        }else{
            tilEmail.setError(null);
            tilEmail.setErrorEnabled(false);
            return true;
        }
    }// validEmail end

    private Boolean validatePassword(){
        String val = tilPass.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            tilPass.setError("Password must not be empty.");
            return false;
        }
        else{
            tilPass.setError(null);
            return true;
        }
    } // Pvalidassword end

    public void loginUser(){
        if(!validateEmail() | !validatePassword()){
            return;
        }
        else
            isUser();

    } // loginUser end

    private void isUser(){
        final String email        =  tilEmail.getEditText().getText().toString().trim();
        final String password     =  tilPass.getEditText().getText().toString().trim();
        fs =  FirebaseFirestore.getInstance();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("password",password);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            fs.collection("users").document(email).update("password", password);
                            fs.collection("users").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()){
                                        DocumentSnapshot document = task.getResult();
                                        String isAdmin = document.getString("isAdmin");
                                        Intent intent = new Intent(LoginPage.this, Home.class);
                                        intent.putExtra("isAdmin", isAdmin);
                                        intent.putExtra("mail",email);
                                        LoginPage.this.startActivity(intent);
                                        LoginPage.this.finish();
                                    }
                                }
                            });
                        } else {
                            tilPass.setError("User is not found. Please check your email and your password");

                            Log.e("Error", "Login Failed");
                        }
                    }
                });
    } // isUser end

    private Boolean validateEmailReset(){
        String val = tilReset.getEditText().getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            tilReset.setError("Email must not be empty.");
            tilReset.setErrorEnabled(true);
            return false;
        } else if (!val.matches(emailPattern)) {
            tilReset.setError("Invalid email, please try again.");
            tilReset.setErrorEnabled(true);
            return false;
        }else{
            tilReset.setError(null);
            tilReset.setErrorEnabled(false);
            return true;
        }
    }// validEmailReset end
    void switchToForgetLayout() {
        setContentView(R.layout.change_pass_layout);
        changePassActive();
    }

    void changePassActive(){
        Button resetPass         = (Button) findViewById(R.id.reset_pass_btn) ;
        final TextView notice    = (TextView) findViewById(R.id.reset_description);
        final TextView previous  = (TextView) findViewById(R.id.return_label);
        final EditText edReset   = (EditText) findViewById(R.id.reset_email);
        tilReset                 = (TextInputLayout) findViewById(R.id.TILreset_email);

        edReset.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateEmailReset();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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