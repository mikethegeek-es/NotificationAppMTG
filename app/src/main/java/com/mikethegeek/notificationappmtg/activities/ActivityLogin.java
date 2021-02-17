package com.mikethegeek.notificationappmtg.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.mikethegeek.notificationappmtg.tools.FirebaseMethods;
import com.mikethegeek.notificationappmtg.R;

public class ActivityLogin extends AppCompatActivity {

    private Button btLogIn;
    private EditText txtLogIn, txtPass;
    private FirebaseMethods firebaseMethods;
    private TextView goToRegister, txtErrorLogIn, txtForgotPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();

        firebaseAuth = FirebaseAuth.getInstance();

        //boton login
        btLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseMethods.logIn(txtLogIn.getText().toString(), txtPass.getText().toString(), getApplicationContext());

            }
        });

        //boton ir a registro

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentRegistro = new Intent(ActivityLogin.this, ActivityRegister.class);
                ActivityLogin.this.startActivity(intentRegistro);

            }
        });


    }


    private void initComponents() {

        //inicializamos componentes visuales
        firebaseMethods = new FirebaseMethods(this);
        btLogIn = findViewById(R.id.btLogIn);
        txtErrorLogIn = findViewById(R.id.txtErrorLogIn);
        txtPass = findViewById(R.id.txtLoginPasswd);
        goToRegister = findViewById(R.id.txtGoToRegister);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);


    }
}