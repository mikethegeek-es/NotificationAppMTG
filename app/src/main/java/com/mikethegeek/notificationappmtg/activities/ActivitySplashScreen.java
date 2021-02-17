package com.mikethegeek.notificationappmtg.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mikethegeek.notificationappmtg.MainActivity;
import com.mikethegeek.notificationappmtg.R;

import java.util.Objects;

public class ActivitySplashScreen extends AppCompatActivity {

    //instancia de firestore
    private FirebaseFirestore firestore;
    private static int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        firestore = FirebaseFirestore.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //capturamos el usuario
                getUserInfo();
            }
        }, SPLASH_TIME);


    }

    private void getUserInfo() {

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        //si está logueado
        if (firebaseUser != null) {

            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {

                                return;
                            }

                            // Obtenemos el token del dispositivo
                            String token = task.getResult().getToken();
                            String msg = "mensaje";

                            Log.d("ON GET TOKEN: ", msg);


                            //actualizamos el token del usuario en firebase
                            updateToken(firebaseUser, token);


                        }
                    });

            Intent myIntent = new Intent(ActivitySplashScreen.this, MainActivity.class);
            ActivitySplashScreen.this.startActivity(myIntent);
            finish();

        } else {
            //no logueado - le mandamos al login
            Intent myIntent = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
            ActivitySplashScreen.this.startActivity(myIntent);
            finish();
        }
    }


    private void updateToken(final FirebaseUser firebaseUser, String newToken) {

        firestore.collection("Users").document(firebaseUser.getUid()).update("token", newToken).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Token Guardado", Toast.LENGTH_SHORT).show();
                //si es correcto, llamamos a getUserInfo para pintar la info actualizada
                initApp(firebaseUser);

            }
        });

    }

    private void initApp(FirebaseUser firebaseUser) {


        firestore.collection("Users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(new Intent(ActivitySplashScreen.this, MainActivity.class));
                        finish();
                    }
                }, 2000);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                Log.d("Get data", "onFailure: " + e.getMessage());

            }
        });
    }
}