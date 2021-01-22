package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserInfo extends AppCompatActivity {

    private FirebaseFirestore fs;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private static final String DATABASE_ROOT_COLLECTION = "users";

    private ImageButton btnBack;
    private TextView tvName, tvPhone, tvMail;
    private String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Globals globals = new Globals();
        globals.transStatus(getWindow());

        fs = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        btnBack = findViewById(R.id.imageButton);

        tvName = findViewById(R.id.user_name);
        tvPhone = findViewById(R.id.user_phone);
        tvMail = findViewById(R.id.user_email);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.putExtra("isAdmin","0");
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        mail = intent.getStringExtra("mail");

        setInfo();
        doSignout();
    }



    private void setInfo() {
        fs.collection(DATABASE_ROOT_COLLECTION).document(mail).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();

                    String name = document.getString("name");
                    String email = document.getString("email");
                    String phone = document.getString("phone");

                    tvName.setText(name);
                    tvPhone.setText(phone);
                    tvMail.setText(email);
                }
            }
        });
    }

    /** Do sign out */
    private void doSignout(){
        TextView signOut = (TextView) findViewById(R.id.logout_link);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(UserInfo.this, Home.class);
                startActivity(intent);
            }
        });
    }
}