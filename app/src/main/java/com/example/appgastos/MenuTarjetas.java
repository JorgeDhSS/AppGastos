package com.example.appgastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MenuTarjetas extends AppCompatActivity {

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    DatabaseReference userReference;

    EditText Textnombre, Textcantidad;

    String nameCard = " ";
    String cantidadCard = " ";

    ListView listV_Cards;

    private List<Tarjeta> listCard = new ArrayList<Tarjeta>();
    ArrayAdapter<Tarjeta> arrayAdapterTarjet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tarjetas);

        Textnombre = findViewById(R.id.nameCard);
        Textcantidad = findViewById(R.id.cantCard);
        listV_Cards = findViewById(R.id.listviewTarjetas);


        inicializarFirebase();
        listarDatos();
        //userReference = mDatabase.child("Users").child(mAuth.getUid()).child("Tarjetas");
        //userReference = mDatabase.child("Users").child(mAuth.getUid());

    }

    private void inicializarFirebase(){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userReference =  mDatabase.child("Users").child(mAuth.getUid());
    }

    private void listarDatos(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.manu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        String nombre = Textnombre.getText().toString();
        String cantidad = Textcantidad.getText().toString();

        switch(item.getItemId()){
            case R.id.icon_add:{
                if(!nombre.isEmpty() && !cantidad.isEmpty()){
                    if (nombre.length() > 0 ){

                        Tarjeta t = new Tarjeta();
                        t.setCantidad(cantidad);
                        t.setNombre(nombre);
                        mDatabase.child("Users").child(mAuth.getUid()).child("Tarjetas").push().setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                Toast.makeText(MenuTarjetas.this, "Tarjeta agregada", Toast.LENGTH_LONG).show();
                                limpiarCajas();
                            }
                        });



                    }else{
                        Toast.makeText(this, "El nombre debe tener al menos 1 caracter", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Ningun dato debe estar vacio", Toast.LENGTH_SHORT).show();
                }
                break;
            }

            case R.id.icon_save:{
                Toast.makeText(this, "Actualizar", Toast.LENGTH_LONG).show();
                break;
            }

            case R.id.icon_delete:{
                Toast.makeText(this, "Eliminar", Toast.LENGTH_LONG).show();
                break;
            }
            default:break;
        }
        return true;
    }

    private void limpiarCajas(){
        Textnombre.setText("");
        Textcantidad.setText("");
    }



}