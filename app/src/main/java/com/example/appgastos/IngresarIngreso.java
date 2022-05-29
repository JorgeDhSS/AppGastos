package com.example.appgastos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class IngresarIngreso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_ingreso);
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