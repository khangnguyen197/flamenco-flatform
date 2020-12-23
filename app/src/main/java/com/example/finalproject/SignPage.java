package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignPage extends AppCompatActivity implements View.OnClickListener {
    EditText signName, signEmail, signPassword, signConfirm_pass, signPhone;
    String name, email, password, confirm_pass, phone;
    private FirebaseAuth mAuth;
    private FirebaseFirestore data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_page);
        Globals globals = new Globals();
        globals.transStatus(getWindow());

        Button submit = (Button) findViewById(R.id.submit_btn);
        TextView signUpActivity = (TextView) findViewById(R.id.login_link);

        signName = (EditText) findViewById(R.id.name);
        signEmail = (EditText) findViewById(R.id.email);
        signPassword = (EditText) findViewById(R.id.password);
        signPhone = (EditText) findViewById(R.id.phone);
        signConfirm_pass = (EditText) findViewById(R.id.confirm_pass);

        mAuth = FirebaseAuth.getInstance();
        data = FirebaseFirestore.getInstance();

        submit.setOnClickListener(this);
        signUpActivity.setOnClickListener(this);

    }// onCreate end

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_btn:
                signup();
                break;
            case R.id.login_link:
                loginChange();
                break;
            default:
                break;
        }
    } // onClick end

    public void loginChange(){
        Intent logPage = new Intent(SignPage.this, LoginPage.class);
        startActivity(logPage);
    }// loginChange end

    public void signup() {

        if (!validiateName() | !validiateEmail() | !validiatePassword() | !validiatePhone() | !validiateConfirm()) {
            return;
        }

        name = signName.getText().toString().trim();
        email = signEmail.getText().toString().trim();
        password = signPassword.getText().toString().trim();
        phone = signPhone.getText().toString().trim();

        createEmailUser();
        Log.i("LOGGER", "Sign up successfully");
        startActivity(new Intent(SignPage.this, LoginPage.class));
        finish();
    }// signup end

    private void createEmailUser() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Users users = new Users(name, email, password, phone, "0");

                            data.collection("users").document(email)
                                    .set(users)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(SignPage.this, "Your email is using for your account now", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
                        } else {
                            Toast.makeText(SignPage.this, "Your email has an error, try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }// createEmailUser end

    private Boolean validiateName() {
        String val = signName.getText().toString();
        String nameVal = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";

        if (val.isEmpty()) {
            signName.setError("Name cannot be empty");
            return false;
        } else if (!val.matches(nameVal)) {
            signName.setError("Numbers are not allowed");
            return false;
        } else {
            signName.setError(null);
            return true;
        }
    }//validiateName end

    private Boolean validiateEmail() {
        String val = signEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            signEmail.setError("Email cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            signEmail.setError("Invalid email, try again");
            return false;
        } else {
            signEmail.setError(null);
            return true;
        }
    }//validiateEmail end

    private Boolean validiatePassword() {
        String val = signPassword.getText().toString();
        String passVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +        //any letter
                "(?=.*[@#$%^&+=])" +      //at least 1 special character
                "(?=\\S+$)" +             //no white spaces
                ".{4,}" +                 //at least 4 characters
                "$";

        if (val.isEmpty()) {
            signPassword.setError("Password cannot be empty");
            return false;
        } else if (!val.matches(passVal)) {
            signPassword.setError("Password is too weak");
            return false;
        } else {
            signPassword.setError(null);
            return true;
        }
    }//validiatePassword end

    private boolean validiateConfirm() {
        String val = signConfirm_pass.getText().toString();
        String val2 = signPassword.getText().toString();

        if (val.isEmpty()) {
            signConfirm_pass.setError("Confirm Password cannot be empty");
            return false;
        } else if (val != val2) {
            signConfirm_pass.setError("Confirm Password must be the same as Password");
            return false;
        } else {
            signConfirm_pass.setError(null);
            return true;
        }

    }//validiateConfirm end

    private Boolean validiatePhone() {
        String val = signPhone.getText().toString();
        String phoneVal = "^[0-9]+$";

        if (val.isEmpty()) {
            signPhone.setError("Phone cannot be empty");
            return false;
        } else if(!val.matches(phoneVal)){
            signPhone.setError("Phone must be numbers");
            return false;
        } else if(val.length()>11){
            signPhone.setError("Phone must be less than 12 digits");
            return false;
        } else if(val.length()<9){
            signPhone.setError("Phone must be more than 8 digits");
            return false;
        }else{
            signPhone.setError(null);
            return true;
        }
    }//validiatePhone end


}