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
        setContentView(R.layout.fragment_pagina_principal);

    }

    public void onclicGastos(View v){
        Intent intent = new Intent(PaginaPrincipal.this, IngresarGasto.class);
        startActivity(intent);
    }
}