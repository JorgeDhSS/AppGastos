package com.example.appgastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IngresarGastoFinal extends AppCompatActivity {
    TextView textViewCategoryName;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase, userReference;
    String category, cantidadGasto, tarjetName;
    int categoryImage;
    EditText gasto, nota;
    ImageView imgCategory;
    Spinner spinner;
    ArrayList<String> tarjetasArrayList;
    ArrayAdapter<String> adapter;
    private boolean isUserInteracting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_gasto_final);

        Intent intent = getIntent();
        category = intent.getStringExtra("categoryName");
        categoryImage = intent.getIntExtra("categoryImage", 0);
        cantidadGasto = intent.getStringExtra("cantidadGasto");

        textViewCategoryName = findViewById(R.id.textViewCategoryNameGastoFinal);
        imgCategory = findViewById(R.id.imageViewCategoryGastoFinal);
        gasto = findViewById(R.id.editTextNumberGastoAmount);
        nota = findViewById(R.id.editTextNotaGasto);
        spinner = findViewById(R.id.spinnerTarjetaGasto);
        tarjetasArrayList = new ArrayList();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userReference =  mDatabase.child("Users").child(mAuth.getUid());
        setData();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                tarjetName = tarjetasArrayList.get(i);
                Log.d("tarjeta", tarjetName);
            }

            @Override
            public void onNothingSelected(AdapterView adapterView) {

            }
        });
    }

    public void setData()
    {
        textViewCategoryName.setText(category);
        imgCategory.setImageResource(categoryImage);
        if (cantidadGasto != "")
        {
            gasto.setText(cantidadGasto);
            gasto.setEnabled(true);
        }
        else
        {
            gasto.setEnabled(false);
        }

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tarjetasArrayList);
        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mDatabase.child("Users").child(mAuth.getUid()).child("Tarjetas").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    if(String.valueOf(task.getResult().getValue()) != "null")
                    {
                        //tarjetasArrayList.add(String.valueOf(task.getResult().getValue()));
                        for(DataSnapshot ds: task.getResult().getChildren()){
                            tarjetasArrayList.add(String.valueOf(ds.child("nombre").getValue()));
                        }

                    }
                    else
                    {
                        //editTextGastoCategory.setText("0.00");
                    }
                }
            }
        });
    }

    public void guardarGasto(View v)
    {
        Gasto gastoObject = new Gasto(gasto.getText().toString(), new Date(), category, tarjetName, nota.getText().toString());
        userReference.child("Gastos").push().setValue(gastoObject).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                Toast.makeText(IngresarGastoFinal.this, "Gasto agregado", Toast.LENGTH_LONG).show();
                Intent activityChangeIntent = new Intent(IngresarGastoFinal.this, MainActivity.class);
                startActivity(activityChangeIntent);
            }
        });
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        isUserInteracting = true;
    }
}