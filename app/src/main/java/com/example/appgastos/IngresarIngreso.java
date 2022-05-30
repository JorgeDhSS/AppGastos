package com.example.appgastos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IngresarIngreso extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference ref;
    private DatabaseReference usersRef;
    private String id_usuarioR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_ingreso);
        ref = FirebaseDatabase.getInstance().getReference();
        id_usuarioR = this.mAuth.getCurrentUser().getUid();
        usersRef = ref.child("Users").child(this.id_usuarioR).child("Ingreso");
        Button guardarIngreso = findViewById(R.id.btnGuardarIngreso);
        this.nuevoIngreso = findViewById(R.id.nuevoIngreso);
        this.efectivo = findViewById(R.id.efectivo);
        this.nota = findViewById(R.id.nota);
        guardarIngreso.setOnClickListener(view -> {
            this.guardarIngresos();
        });
    }

    private EditText nuevoIngreso;
    private Spinner efectivo;
    private EditText nota;

    private void guardarIngresos(){
        if(this.validarNuevoIngreso() && this.validarEfectivo()){
            HashMap<String, Object> nuevoRegistro = new HashMap<>();
            String ingreso = this.nuevoIngreso.getText().toString();
            nuevoRegistro.put("Ingreso", Float.parseFloat(ingreso));
            nuevoRegistro.put("TipoIngreso", this.efectivo.getSelectedItem());
            nuevoRegistro.put("Nota", this.nota.getText().toString());
            nuevoRegistro.put("Fecha", this.generarFecha());

            String id = usersRef.push().getKey();
            usersRef.child(id).setValue(nuevoRegistro);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else{
            Toast.makeText(this, "Falta ingresar unos datos", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarNuevoIngreso(){
        boolean validacion = false;
        String ingreso = this.nuevoIngreso.getText().toString();
        try {
            if(!ingreso.isEmpty()){
                Float.parseFloat(ingreso);
                validacion = true;
            }
        }catch (NumberFormatException e){

        }
        return validacion;
    }

    private boolean validarEfectivo(){
        boolean validacion = false;
        if(this.efectivo.getSelectedItem()!=null && !this.efectivo.getSelectedItem().equals("Tipo de Ingreso")){
            validacion = true;
        }
        return validacion;
    }

    @SuppressLint("SimpleDateFormat")
    private String generarFecha(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

}