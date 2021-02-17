package com.mikethegeek.notificationappmtg.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mikethegeek.notificationappmtg.R;
import com.mikethegeek.notificationappmtg.beans.User;
import com.mikethegeek.notificationappmtg.tools.FirebaseMethods;

public class ActivityRegister extends AppCompatActivity {

    private EditText etEmail, etPasswd, etConfirmPasswd, etUsername;
    private TextView txtGoToLogin;
    private Button btnRegister;
    private FirebaseMethods firebaseMethods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();


        //PULSAR BOTON REGISTRO
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });


        //PULSAR BOTON YA REGISTRADO
        txtGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(ActivityRegister.this, ActivityLogin.class);
                ActivityRegister.this.startActivity(intentLogin);
            }
        });

    }




    private void registrar(){

        final User nuevoUser = new User("", etUsername.getText().toString(), etUsername.getText().toString(), etEmail.getText().toString(), "https://cdn.dribbble.com/users/299266/screenshots/1871250/materialme.jpg", "");


        if (etPasswd.getText().toString().equals(etConfirmPasswd.getText().toString())) {

            //6 es el minimo de seguridad de firebase
            if (etPasswd.getText().toString().length() >= 6) {
                //registrar

                final ProgressDialog progressDialog = new ProgressDialog(ActivityRegister.this, R.style.Theme_AppCompat_DayNight_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Iniciando...");
                progressDialog.show();


                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                firebaseMethods.registerUser(nuevoUser, etPasswd.getText().toString(), getApplicationContext());
                                progressDialog.dismiss();
                            }
                        }, 1000);


            } else {
//                        La contraseña minimo son 6 caracteres para firebase
                Toast.makeText(getApplicationContext(), "Minimo 6 carácteres", Toast.LENGTH_SHORT).show();
            }

        } else {
//                        La contraseñas no coinciden
            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }

    }

    private void initComponents() {

        firebaseMethods = new FirebaseMethods(getApplicationContext());
        btnRegister = findViewById(R.id.btnRegister);
        etEmail = findViewById(R.id.txtRegisterEmail);
        etPasswd = findViewById(R.id.txtRegisterPasswd);
        etConfirmPasswd = findViewById(R.id.txtRegisterConfirmPasswd);
        etUsername = findViewById(R.id.txtRegisterUserName);
        txtGoToLogin = findViewById(R.id.txtGoToLogin);

    }
}