package com.example.appgastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MenuTarjetas extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    FirebaseAuth mAuth;
    DatabaseReference userReference;

    EditText Textnombre, Textcantidad;

    Tarjeta tarjetSelected;

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

        listV_Cards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tarjetSelected =  (Tarjeta) parent.getItemAtPosition(position);
                Textnombre.setText(tarjetSelected.getNombre());
                Textcantidad.setText(tarjetSelected.getCantidad());
            }
        });

    }

    private void inicializarFirebase(){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabase.setPersistenceEnabled(true);
        userReference = mDatabase.getReference();
    }

    private void listarDatos(){
        userReference.child("Users").child(mAuth.getUid()).child("Tarjetas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCard.clear();
                for(DataSnapshot objSnapshot : snapshot.getChildren()){
                    Tarjeta t = objSnapshot.getValue(Tarjeta.class);
                    listCard.add(t);

                    arrayAdapterTarjet = new ArrayAdapter<Tarjeta>(MenuTarjetas.this, android.R.layout.simple_list_item_1, listCard);
                    listV_Cards.setAdapter(arrayAdapterTarjet);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.manu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void add(View v)
    {
        String nombre = Textnombre.getText().toString();
        String cantidad = Textcantidad.getText().toString();
        if(!nombre.isEmpty() && !cantidad.isEmpty()){
            if (nombre.length() > 0 ){

                Tarjeta t = new Tarjeta();
                t.setNombre(nombre);
                t.setCantidad(cantidad);
                t.setUid(UUID.randomUUID().toString());
                userReference.child("Users").child(mAuth.getUid()).child("Tarjetas").child(t.getUid()).setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    }

    public void modify(View v)
    {
        Tarjeta t = new Tarjeta();
        t.setUid(tarjetSelected.getUid());
        t.setNombre(Textnombre.getText().toString().trim());
        t.setCantidad(Textcantidad.getText().toString().trim());

        userReference.child("Users").child(mAuth.getUid()).child("Tarjetas").child(tarjetSelected.getUid()).setValue(t);
        Toast.makeText(this, "¡Tarjeta Actualizada!", Toast.LENGTH_LONG).show();
        limpiarCajas();
    }

    public void deleteT(View v)
    {
        Tarjeta t = new Tarjeta();
        t.setUid(tarjetSelected.getUid());
        userReference.child("Users").child(mAuth.getUid()).child("Tarjetas").child(t.getUid()).removeValue();
        Toast.makeText(this, "¡Tarjeta eliminada!", Toast.LENGTH_LONG).show();
        limpiarCajas();
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
                        t.setNombre(nombre);
                        t.setCantidad(cantidad);
                        t.setUid(UUID.randomUUID().toString());
                        userReference.child("Users").child(mAuth.getUid()).child("Tarjetas").child(t.getUid()).setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                Tarjeta t = new Tarjeta();
                t.setUid(tarjetSelected.getUid());
                t.setNombre(Textnombre.getText().toString().trim());
                t.setCantidad(Textcantidad.getText().toString().trim());

                userReference.child("Users").child(mAuth.getUid()).child("Tarjetas").child(tarjetSelected.getUid()).setValue(t);
                Toast.makeText(this, "¡Tarjeta Actualizada!", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }

            case R.id.icon_delete:{
                Tarjeta t = new Tarjeta();
                t.setUid(tarjetSelected.getUid());
                userReference.child("Users").child(mAuth.getUid()).child("Tarjetas").child(t.getUid()).removeValue();
                Toast.makeText(this, "¡Tarjeta eliminada!", Toast.LENGTH_LONG).show();
                limpiarCajas();
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