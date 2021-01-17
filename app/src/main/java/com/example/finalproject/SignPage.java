package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.provider.FontsContractCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignPage extends AppCompatActivity implements View.OnClickListener {
    EditText signName, signEmail, signPassword, signConfirm_pass, signPhone;
    TextInputLayout tilName, tilEmail, tilPassword, tilConfirm_pass, tilPhone;
    String name, email, password, phone;

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

        signName            = (EditText) findViewById(R.id.name);
        signEmail           = (EditText) findViewById(R.id.email);
        signPassword        = (EditText) findViewById(R.id.password);
        signPhone           = (EditText) findViewById(R.id.phone);
        signConfirm_pass    = (EditText) findViewById(R.id.confirm_pass);

        tilName             = (TextInputLayout) findViewById(R.id.TILname);
        tilEmail            = (TextInputLayout) findViewById(R.id.TILemail);
        tilPassword         = (TextInputLayout) findViewById(R.id.TILpassword);
        tilPhone            = (TextInputLayout) findViewById(R.id.TILphone);
        tilConfirm_pass     = (TextInputLayout) findViewById(R.id.TILconfirm_pass);


        signName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateName();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signEmail.addTextChangedListener(new TextWatcher() {
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
        signPassword.addTextChangedListener(new TextWatcher() {
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
        signPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePhone();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signConfirm_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateConfirm();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        mAuth = FirebaseAuth.getInstance();
        data = FirebaseFirestore.getInstance();

        submit.setOnClickListener(this);
        signUpActivity.setOnClickListener(this);

    }// onCreate end

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    public void loginChange() {
        Intent logPage = new Intent(SignPage.this, LoginPage.class);
        startActivity(logPage);
    }// loginChange end

    public void signup() {

        if (!validateName() | !validateEmail() | !validatePassword() | !validatePhone() | !validateConfirm()) {
            return;
        }

        name        = tilName.getEditText().getText().toString().trim();
        email       = tilEmail.getEditText().getText().toString().trim();
        password    = tilPassword.getEditText().getText().toString().trim();
        phone       = tilPhone.getEditText().getText().toString().trim();

        createEmailUser();
    }// signup end

    private void createEmailUser() {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Users users = new Users(name, email, password, phone, "0");
                        Log.i("LOGGER", "Get here");
                        data.collection("users").document(email)
                            .set(users)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(SignPage.this, "Sign up successfully!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignPage.this, LoginPage.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    } else {
                        Toast.makeText(SignPage.this, "Network Error! Please re-open your FLAMENCO", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }// createEmailUser end

    private Boolean validateName() {
        String val = tilName.getEditText().getText().toString();
        String nameVal = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";

        if (val.isEmpty()) {
            tilName.setError("Name cannot be empty");
            tilName.setErrorEnabled(true);
            return false;
        } else if (!val.matches(nameVal)) {
            tilName.setError("Numbers are not allowed");
            tilName.setErrorEnabled(true);
            return false;
        } else {
            tilName.setError(null);
            tilName.setErrorEnabled(false);
            return true;
        }
    }//validateName end

    private Boolean validateEmail() {
        String val = tilEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            tilEmail.setError("Email cannot be empty");
            tilEmail.setErrorEnabled(true);
            return false;
        } else if (!val.matches(emailPattern)) {
            tilEmail.setError("Invalid email, try again");
            tilEmail.setErrorEnabled(true);
            return false;
        } else {
            tilEmail.setError(null);
            tilEmail.setErrorEnabled(false);
            return true;
        }
    }//validateEmail end

    private Boolean validatePassword() {
        String val = tilPassword.getEditText().getText().toString();
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
            tilPassword.setError("Password cannot be empty");
            tilPassword.setErrorEnabled(true);
            return false;
        } else if (!val.matches(passVal)) {
            tilPassword.setError("Password is too weak");
            tilPassword.setErrorEnabled(true);
            return false;
        } else {
            tilPassword.setError(null);
            tilPassword.setErrorEnabled(false);
            return true;
        }
    }//validatePassword end

    private boolean validateConfirm() {
        String val = tilConfirm_pass.getEditText().getText().toString();
        String val2 = tilPassword.getEditText().getText().toString();

        if (val.isEmpty()) {
            tilConfirm_pass.setError("Confirm Password cannot be empty");
            tilConfirm_pass.setErrorEnabled(true);
            return false;
        } else if (!val.equals(val2)) {
            tilConfirm_pass.setError("Confirm Password must be the same as Password");
            tilConfirm_pass.setErrorEnabled(true);
            return false;
        } else {
            tilConfirm_pass.setError(null);
            tilConfirm_pass.setErrorEnabled(false);
            return true;
        }

    }//validateConfirm end

    private Boolean validatePhone() {
        String val = tilPhone.getEditText().getText().toString();
        String phoneVal = "^[0-9]+$";

        if (val.isEmpty()) {
            tilPhone.setError("Phone cannot be empty");
            tilPhone.setErrorEnabled(true);
            return false;
        } else if (!val.matches(phoneVal)) {
            tilPhone.setError("Phone must be numbers");
            tilPhone.setErrorEnabled(true);
            return false;
        } else if (val.length() > 11) {
            tilPhone.setError("Phone must be less than 12 digits");
            tilPhone.setErrorEnabled(true);
            return false;
        } else if (val.length() < 9) {
            tilPhone.setError("Phone must be more than 8 digits");
            tilPhone.setErrorEnabled(true);
            return false;
        } else {
            tilPhone.setError(null);
            tilPhone.setErrorEnabled(false);
            return true;
        }
    }//validatePhone end
}
