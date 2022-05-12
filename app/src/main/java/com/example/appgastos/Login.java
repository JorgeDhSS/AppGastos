package com.example.appgastos;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText Eduser;
    private EditText Edpassword;
    private FirebaseAuth mAuth;
    private Button botonInicio;
    private Button botonRecuperar;
    private String user = " ";
    private String password = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Eduser = findViewById(R.id.user);
        Edpassword = findViewById(R.id.contraseña);
        mAuth = FirebaseAuth.getInstance();
        botonInicio = findViewById(R.id.buttonIniciar);
        botonRecuperar = findViewById(R.id.Recuperar);

        botonInicio.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                user = Eduser.getText().toString();
                password = Edpassword.getText().toString();

                if(!user.isEmpty() && !password.isEmpty() && !password.isEmpty()){
                    if (password.length() >=8 ){
                        iniciarSesion();
                    }else{
                        Toast.makeText(Login.this, "El password no tiene los 8 caracteres necesarios", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Login.this, "Ningun campo puede estar vacio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botonRecuperar.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, RecuperarContrasena.class));
                finish();
            }
        });
    }

    public void onStart(){
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void iniciarSesion(){
        mAuth.signInWithEmailAndPassword(Eduser.getText().toString().trim(), Edpassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Autenticación correcta",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Autenticación fallida, correo o contraseña" +
                                            "incorrectas",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}