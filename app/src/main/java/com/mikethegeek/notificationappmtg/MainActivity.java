package com.mikethegeek.notificationappmtg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mikethegeek.notificationappmtg.activities.ActivitySplashScreen;

public class MainActivity extends AppCompatActivity {

    TextView tvPrincipal;
    Button btnSignOut;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        initComponents();

        //si esta logueado que muestre boton y nombre
        if(mAuth.getCurrentUser() == null){
            btnSignOut.setVisibility(View.INVISIBLE);
        }else{
            tvPrincipal.setText(mAuth.getCurrentUser().getEmail());
        }

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
            }
        });




    }

    private void initComponents() {

        tvPrincipal = findViewById(R.id.tvPrincipalXml);
        btnSignOut = findViewById(R.id.btnSignOut);

    }


}