package com.example.appgastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IngresarGasto extends AppCompatActivity {

    TextView textCantidadGasto, textViewCategoryName;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String category, cantidadGasto;
    int categoryImage;
    ImageView imgCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_gasto);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        textCantidadGasto = findViewById(R.id.textViewGastoCantidad);
        textViewCategoryName = findViewById(R.id.textViewGastoCategoryName);
        imgCategory = findViewById(R.id.imageViewCategoryGasto);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        categoryImage = intent.getIntExtra("categoryImage", 0);

        recuperarDatos();
    }

    public void recuperarDatos()
    {
        textViewCategoryName.setText(category);
        imgCategory.setImageResource(categoryImage);
        mDatabase.child("Users").child(mAuth.getUid()).child("Categories").child(category).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    if(String.valueOf(task.getResult().getValue()) != "null")
                    {
                        cantidadGasto = String.valueOf(task.getResult().getValue());
                        textCantidadGasto.setText("$"+String.valueOf(task.getResult().getValue())+" pesos");
                    }
                    else
                    {
                        textCantidadGasto.setText("No se tienen datos sobre tus gastos en esta categoría, completa tu rutina de gastos");
                    }
                }
            }
        });
    }

    public void gastoExactoOnClick(View v)
    {
        Intent intent = new Intent(IngresarGasto.this, IngresarGastoFinal.class);
        intent.putExtra("categoryName", category);
        intent.putExtra("categoryImage", categoryImage);
        intent.putExtra("cantidadGasto", cantidadGasto);
        startActivity(intent);
    }

    public void otroGastoOnClick(View v)
    {
        Intent intent = new Intent(IngresarGasto.this, IngresarGastoFinal.class);
        intent.putExtra("categoryName", category);
        intent.putExtra("categoryImage", categoryImage);
        startActivity(intent);
    }
}