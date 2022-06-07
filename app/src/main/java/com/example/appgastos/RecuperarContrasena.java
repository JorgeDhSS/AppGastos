package com.example.appgastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RecuperarContrasena extends AppCompatActivity {

    public EditText emailText;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);
        emailText = findViewById(R.id.correo);
    }

    public void onClickVerify(View v) {
        String email = emailText.getText().toString().trim();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Correo inválido");
            return;
        }
        sendConfirmationEmail(email);
        /*Map<String, Object> map = new HashMap<>();
        map.put("date", new Date());

        map.put("code", 1425);
        map.put("email", emailText.getText().toString());

        String _id = mDatabase.push().getKey();

        mDatabase.child("PasswordRetrieve").child(_id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if (task2.isSuccessful()){

                    Intent intent = new Intent(RecuperarContrasena.this, RecuperarContrasenaValidacion.class);
                    intent.putExtra("code", 1425);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(RecuperarContrasena.this, "No se pudo crear el código de confirmación", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        Intent activityChangeIntent = new Intent(RecuperarContrasena.this, RecuperarContrasenaValidacion.class);
        startActivity(activityChangeIntent);
    }

    private void sendConfirmationEmail(String mail)
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(mail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(RecuperarContrasena.this, "Correo enviado", Toast.LENGTH_LONG);
                            startActivity(new Intent(RecuperarContrasena.this, Login.class));
                        }
                        else
                        {
                            Toast.makeText(RecuperarContrasena.this, "Error al enviar correo, favor de volver intentarlo", Toast.LENGTH_LONG);
                        }
                    }
                });
    }
}