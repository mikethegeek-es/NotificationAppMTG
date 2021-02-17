package com.mikethegeek.notificationappmtg.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikethegeek.notificationappmtg.MainActivity;
import com.mikethegeek.notificationappmtg.beans.User;

public class FirebaseMethods extends Activity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private Context context;
    private DatabaseReference reference;


    public FirebaseMethods(Context context) {
        this.context = context;
    }


    //METODO PARA INICIAR SESION EN FIREBASE
    public void logIn(String email, String password, final Context loginContext) {
        final FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        try {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        //LOGIN CORRECTO
                        //capturamos el usuario
                        FirebaseUser user = mAuth.getCurrentUser();

//                        String urlAvatar = user.getPhotoUrl().toString();

                        Toast.makeText(loginContext, "Bienvenido de nuevo " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                        goToInicio(user.getDisplayName());


                    } else {
                        //LOGIN INCORRECTO
                        Toast.makeText(loginContext, "Error en el inicio de sesión ", Toast.LENGTH_SHORT).show();


                    }

                }
            });
        } catch (IllegalArgumentException e) {
            Toast.makeText(context, "Campos de texto vacios", Toast.LENGTH_SHORT).show();
        }

    }


    //METODO PARA REGISTRAR UN USUARIO EN FIREBASE
    public void registerUser(final User userToRegister, String passwd, final Context context) {

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        String usernametocreate = userToRegister.getEmail();
        final String urlimagetocreate = userToRegister.getUrlImage();
        mAuth.createUserWithEmailAndPassword(usernametocreate, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    //si se ha registrado, obtenemos el ID
                    final FirebaseUser myUser = mAuth.getCurrentUser();

                    //creamos profileUpdates para añadirle info como la imagen de perfil
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(userToRegister.getUsername()).setPhotoUri(Uri.parse(urlimagetocreate)).build();

                    myUser.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> taskDatos) {
                                    if (taskDatos.isSuccessful()) {
                                        //DATOS ACTUALIZADOS
                                        String OK = "OK";


                                        firebaseFirestore = FirebaseFirestore.getInstance();

//                                        User createdUser = new User();
                                        User createdUser = userToRegister;

                                        createdUser.setUserId(myUser.getUid());
                                        createdUser.setEmail(myUser.getEmail());
                                        createdUser.setName(myUser.getDisplayName());
                                        createdUser.setUsername(myUser.getDisplayName());


                                        firebaseFirestore.collection("Users").document(myUser.getUid()).set(createdUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //si ha ido bien, redirigimos a inicio
                                                goToInicio(userToRegister.getUsername());
                                            }
                                        });


                                    } else {
                                        //no se han actualizado los datos
                                        String NOOK = "NOOK";
                                        Toast.makeText(context, "No se han podido actualizar los datos", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                } else {

                    //no se ha podido crear el usuario
                    Toast.makeText(context, "Error en el registro", Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                    Exception e = task.getException();

                }
            }
        });
    }


    //METODO PARA REDIRECCIONAR A INICIO
    private void goToInicio(String displayName) {

//        Toast.makeText(context,  "Bienvenido " + displayName, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(context, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);


    }


}


