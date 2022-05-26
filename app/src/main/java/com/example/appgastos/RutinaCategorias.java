package com.example.appgastos;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.appgastos.ui.category.CategoryFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RutinaCategorias extends AppCompatActivity {


    EditText alimentosGasto, transporteGatos, medicamentosGasto, ocioGasto, otrosGasto, viviendaGasto;
    String periodo;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase, userReference, updateReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rutina_categorias);
        alimentosGasto = findViewById(R.id.editTextGastoAlimentos);
        transporteGatos = findViewById(R.id.editTextGastoTransporte);
        medicamentosGasto = findViewById(R.id.editTextGastoMedicamentos);
        ocioGasto = findViewById(R.id.editTextGastoOcio);
        otrosGasto = findViewById(R.id.editTextGastoOtros);
        viviendaGasto = findViewById(R.id.editTextGastoVivienda);

        Intent intent = getIntent();
        periodo = intent.getStringExtra("periodo");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userReference =  mDatabase.child("Users").child(mAuth.getUid());
        FirebaseApp.initializeApp(this);
    }
    public void onclickBtnNext(View v)
    {

        Map<String, Object> categorias = new HashMap<>();
        categorias.put("Transportes", transporteGatos.getText().toString());
        categorias.put("Alimentos", alimentosGasto.getText().toString());
        categorias.put("Vivienda", viviendaGasto.getText().toString());
        categorias.put("Medicamentos", medicamentosGasto.getText().toString());
        categorias.put("Ocio", ocioGasto.getText().toString());
        categorias.put("Otros", otrosGasto.getText().toString());


        userReference.child("Periodo").setValue(periodo);
        userReference.child("Categories").updateChildren(categorias);
        Intent activityChangeIntent = new Intent(RutinaCategorias.this, MainActivity.class);

       /* activityChangeIntent.putExtra("periodo", periodo);
        activityChangeIntent.putExtra("Transportes", transporteGatos.getText().toString());
        activityChangeIntent.putExtra("Alimentos", alimentosGasto.getText().toString());
        activityChangeIntent.putExtra("Vivienda", viviendaGasto.getText().toString());
        activityChangeIntent.putExtra("Medicantos", medicamentosGasto.getText().toString());
        activityChangeIntent.putExtra("Ocio", ocioGasto.getText().toString());
        activityChangeIntent.putExtra("Otros", otrosGasto.getText().toString());*/

        startActivity(activityChangeIntent);
    }

}