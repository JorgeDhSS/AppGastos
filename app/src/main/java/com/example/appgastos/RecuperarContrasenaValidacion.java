package com.example.appgastos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RecuperarContrasenaValidacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena_validacion);
    }
    public void onClickValidate(View v) {
        /*String code = (String) getIntent().getExtras().getSerializable("code");
        EditText apodoTextView = findViewById(R.id.editTextPersonName);
        if(apodoTextView.getText().toString() == code)
        {
            Intent activityChangeIntent = new Intent(RecuperarContrasenaValidacion.this, NuevaContrasena.class);
            startActivity(activityChangeIntent);
        }
        else
        {
            Toast.makeText(RecuperarContrasenaValidacion.this, "Código erróneo", Toast.LENGTH_SHORT).show();
        }*/
        Intent activityChangeIntent = new Intent(RecuperarContrasenaValidacion.this, NuevaContrasena.class);
        startActivity(activityChangeIntent);
    }
}