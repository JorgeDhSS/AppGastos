package com.example.appgastos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class IngresarGastoCategoria extends AppCompatActivity {

    String[] spinnerTitles;
    int[] spinnerImages;
    Spinner spinner;
    private boolean isUserInteracting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_gasto_categoria);
        spinnerTitles = new String[]{"Alimentos", "Medicamentos", "Ocio", "Transportes", "Vivienda", "Otros"};
        spinnerImages = new int[]{R.mipmap.ic_category_alimentos_foreground
                , R.mipmap.ic_category_medicamentos_foreground
                , R.mipmap.ic_category_ocio_foreground
                , R.mipmap.ic_category_transporte_foreground
                , R.mipmap.ic_category_vivienda_foreground
                , R.mipmap.ic_category_other_foreground};
        spinner = (Spinner) findViewById(R.id.spinnerCategory);

        SpinnerOptionAdapter spinnerAdapter = new SpinnerOptionAdapter(IngresarGastoCategoria.this, spinnerTitles, spinnerImages);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                if (isUserInteracting) {
                    Toast.makeText(IngresarGastoCategoria.this, spinnerTitles[i], Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(IngresarGastoCategoria.this, IngresarGasto.class);
                    intent.putExtra("category", spinnerTitles[i]);
                    intent.putExtra("categoryImage", spinnerImages[i]);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView adapterView) {

            }
        });
    }
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        isUserInteracting = true;
    }
}