package com.example.appgastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MenuTarjetas extends AppCompatActivity {

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    DatabaseReference userReference;

    EditText Textnombre, Textcantidad;

    String nameCard = " ";
    String cantidadCard = " ";

    ListView listV_Cards;

    ArrayList<String> tarjetasArrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tarjetas);

        Textnombre = findViewById(R.id.nameCard);
        Textcantidad = findViewById(R.id.cantCard);
        listV_Cards = findViewById(R.id.listviewTarjetas);
        tarjetasArrayList = new ArrayList();
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
                            adapter = new ArrayAdapter<String>(MenuTarjetas.this, android.R.layout.simple_list_item_1, tarjetasArrayList);
                            listV_Cards.setAdapter(adapter);
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
                        Map<String, Object> Tarjeta = new HashMap<>();
                        Tarjeta.put("Transportes", cantidad);
                        Tarjeta.put("Alimentos", nombre);
                        mDatabase.child("Users").child(mAuth.getUid()).child("Tarjetas").push().setValue(Tarjeta).addOnCompleteListener(new OnCompleteListener<Void>() {
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