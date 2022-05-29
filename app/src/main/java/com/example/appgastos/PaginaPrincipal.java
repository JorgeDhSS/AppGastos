package com.example.appgastos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaginaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);
        Button btn_ingreso = findViewById(R.id.btn_ingreso);
        btn_ingreso.setOnClickListener(view -> {
            this.ingresarIngreso();
        });
    }

    private void ingresarIngreso(){
        Intent ingreso = new Intent(this,IngresarIngreso.class);
        startActivity(ingreso);
    }
}