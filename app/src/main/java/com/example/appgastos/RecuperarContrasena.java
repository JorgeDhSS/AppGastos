package com.example.appgastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

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
        emailText = findViewById(R.id.correo);
        setContentView(R.layout.activity_recuperar_contrasena);
    }

    public void onClickVerify(View v) {
        /*Map<String, Object> map = new HashMap<>();
        map.put("date", new Date());
        //TODO auto-generate code
        map.put("code", 1425);
        map.put("email", emailText.getText().toString());

        String _id = mDatabase.push().getKey();

        mDatabase.child("PasswordRetrieve").child(_id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if (task2.isSuccessful()){
                    //TODO send email
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
}