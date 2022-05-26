package com.example.appgastos;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.appgastos.ui.category.CategoryFragment;

import java.util.ArrayList;


public class RutinaOtrasCategorias extends AppCompatActivity {
    ListView listViewCategories;
    ArrayList<String> listCategories;
    ArrayAdapter<String> categoriesAdaptador;
    EditText newCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rutina_otras_categorias);

        listViewCategories = (ListView) findViewById(R.id.listViewCategories);
        listCategories = new ArrayList<String>(0);
    }
    public void onclickBtnAddCategory(View v)
    {
        newCategory = findViewById(R.id.editTextNameCateExtra);
        listCategories.add(newCategory.getText().toString().trim());
        categoriesAdaptador = new  ArrayAdapter<String>(RutinaOtrasCategorias.this, android.R.layout.simple_list_item_1, listCategories);
        listViewCategories.setAdapter(categoriesAdaptador);
    }
    public void onclickBtnNext(View v)
    {
        Intent activityChangeIntent = new Intent(RutinaOtrasCategorias.this, RutinaGastos.class);
        activityChangeIntent.putExtra("otherCategories", listCategories);
        startActivity(activityChangeIntent);
    }
}