package com.example.appgastos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaginaInicio extends AppCompatActivity {
    private Button botonRegistro;
    private Button botonInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicio);

        botonRegistro = findViewById(R.id.Registrarse);
        botonInicio = findViewById(R.id.iniciarsesion);

        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaginaInicio.this, RegistroUsuario.class);
                startActivity(intent);
            }
        });

        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaginaInicio.this, Login.class);
                startActivity(intent);
            }
        });
    }



}