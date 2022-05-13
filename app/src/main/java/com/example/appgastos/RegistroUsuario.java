package com.example.appgastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistroUsuario extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    private EditText Edemail;
    private EditText Edpassword;
    private EditText Ednombre;
    private EditText Ededad;
    private Button botonRegistro;

    //VARIBALES DE LOS DATOS QUE SE REGISTRARAN
    private String email = " ";
    private String nombre = " ";
    private String edad = " ";
    private String password = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        Edemail = findViewById(R.id.email);
        Edpassword = findViewById(R.id.password);
        Ednombre = findViewById(R.id.nombre);
        Ededad = findViewById(R.id.edad);
        botonRegistro = findViewById(R.id.buttonRegistro);


        botonRegistro.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                nombre = Ednombre.getText().toString();
                email = Edemail.getText().toString();
                password = Edpassword.getText().toString();
                edad= Ededad.getText().toString();


                if(!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty()){

                    if (password.length() >=8 ){
                        registrarUsuario();

                    }else{
                        Toast.makeText(RegistroUsuario.this, "El password debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();

                    }

                }else{
                    Toast.makeText(RegistroUsuario.this, "Ningun dato debe estar vacio", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }



    public void registrarUsuario(){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", nombre);
                    map.put("edad", edad);
                    map.put("email", email);
                    map.put("password", password);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(RegistroUsuario.this, MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(RegistroUsuario.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }else{
                    Toast.makeText(RegistroUsuario.this, "No se pudo registrar el usuario, intente nuevamente", Toast.LENGTH_SHORT).show();


                }
            }
        });


    }
}