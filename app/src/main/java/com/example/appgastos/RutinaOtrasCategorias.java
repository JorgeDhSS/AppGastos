package com.example.appgastos;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appgastos.ui.category.CategoryFragment;


public class RutinaOtrasCategorias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rutina_otras_categorias);
    }
    public void onclickBtnNext(View v)
    {
        Intent activityChangeIntent = new Intent(RutinaOtrasCategorias.this, RutinaGastos.class);
        startActivity(activityChangeIntent);
    }
}