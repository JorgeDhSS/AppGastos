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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistroTarjeta extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private EditText Ednombre;
    private EditText Edcantidad;
    private Button botonRegistro;

    //VARIBALES DE LOS DATOS QUE SE REGISTRARAN
    private String nombre = " ";
    private String cantidad = " ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_tarjeta);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        Ednombre = findViewById(R.id.nombreTarjeta);
        Edcantidad = findViewById(R.id.saldo);
        botonRegistro = findViewById(R.id.buttonRegistrar);

        botonRegistro.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                nombre = Ednombre.getText().toString();
                cantidad = Edcantidad.getText().toString();


                if(!nombre.isEmpty() && !cantidad.isEmpty()){

                    if (nombre.length() >0 ){
                        registrarTarjeta();

                    }else{
                        Toast.makeText(RegistroTarjeta.this, "El nombre debe tener al menos 1 caracter", Toast.LENGTH_SHORT).show();

                    }

                }else{
                    Toast.makeText(RegistroTarjeta.this, "Ningun dato debe estar vacio", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    public void registrarTarjeta(){
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombre);
        map.put("saldo", cantidad);


        mDatabase.child("Tarjetas").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if (task2.isSuccessful()){
                    startActivity(new Intent(RegistroTarjeta.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(RegistroTarjeta.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}