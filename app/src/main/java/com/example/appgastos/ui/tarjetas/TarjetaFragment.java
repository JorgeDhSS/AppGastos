package com.example.appgastos.ui.tarjetas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appgastos.Login;
import com.example.appgastos.MenuTarjetas;
import com.example.appgastos.R;
import com.example.appgastos.Tarjeta;
import com.example.appgastos.databinding.FragmentPaginaPrincipalBinding;
import com.example.appgastos.databinding.FragmentTarjetasBinding;
import com.example.appgastos.ui.PaginaPrincipal.PaginaPrincipalViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TarjetaFragment extends Fragment {
    private FragmentTarjetasBinding binding;
    FirebaseDatabase mDatabase;
    FirebaseAuth mAuth;
    DatabaseReference userReference;
    EditText Textnombre, Textcantidad;

    Tarjeta tarjetSelected;

    ListView listV_Cards;

    private List<Tarjeta> listCard = new ArrayList<Tarjeta>();
    ArrayAdapter<Tarjeta> arrayAdapterTarjet;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TarjetaViewModel TarjetaViewModel = new ViewModelProvider(this).get(TarjetaViewModel.class);

        binding = FragmentTarjetasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Textnombre = root.findViewById(R.id.nameCard);
        Textcantidad = root.findViewById(R.id.cantCard);
        listV_Cards = root.findViewById(R.id.listviewTarjetas);

        Button btn_add = root.findViewById(R.id.button20);
        Button btn_save = root.findViewById(R.id.button19);
        Button btn_delete = root.findViewById(R.id.button18);

        inicializarFirebase();
        listarDatos();
        btn_add.setOnClickListener(view -> {
            this.add();
        });
        btn_save.setOnClickListener(View -> {
            this.modify();
        });
        btn_delete.setOnClickListener(View -> {
            this.deleteT();
        });
        listV_Cards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tarjetSelected =  (Tarjeta) parent.getItemAtPosition(position);
                Textnombre.setText(tarjetSelected.getNombre());
                Textcantidad.setText(tarjetSelected.getCantidad());
            }
        });

        return root;
    }
    private void inicializarFirebase(){
        mAuth = FirebaseAuth.getInstance();
        //mDatabase.setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance();
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

                    arrayAdapterTarjet = new ArrayAdapter<Tarjeta>(getContext(), android.R.layout.simple_list_item_1, listCard);
                    listV_Cards.setAdapter(arrayAdapterTarjet);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void add()
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
                        Toast.makeText(getContext(), "Tarjeta agregada", Toast.LENGTH_LONG).show();
                        limpiarCajas();
                    }
                });
            }else{
                Toast.makeText(getContext(), "El nombre debe tener al menos 1 caracter", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), "Ningun dato debe estar vacio", Toast.LENGTH_SHORT).show();
        }
    }

    public void modify()
    {
        Tarjeta t = new Tarjeta();
        t.setUid(tarjetSelected.getUid());
        t.setNombre(Textnombre.getText().toString().trim());
        t.setCantidad(Textcantidad.getText().toString().trim());

        userReference.child("Users").child(mAuth.getUid()).child("Tarjetas").child(tarjetSelected.getUid()).setValue(t);
        Toast.makeText(getContext(), "¡Tarjeta Actualizada!", Toast.LENGTH_LONG).show();
        limpiarCajas();
    }

    public void deleteT()
    {
        Tarjeta t = new Tarjeta();
        t.setUid(tarjetSelected.getUid());
        userReference.child("Users").child(mAuth.getUid()).child("Tarjetas").child(t.getUid()).removeValue();
        Toast.makeText(getContext(), "¡Tarjeta eliminada!", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getContext(), "Tarjeta agregada", Toast.LENGTH_LONG).show();
                                limpiarCajas();
                            }
                        });
                    }else{
                        Toast.makeText(getContext(), "El nombre debe tener al menos 1 caracter", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Ningun dato debe estar vacio", Toast.LENGTH_SHORT).show();
                }
                break;
            }

            case R.id.icon_save:{
                Tarjeta t = new Tarjeta();
                t.setUid(tarjetSelected.getUid());
                t.setNombre(Textnombre.getText().toString().trim());
                t.setCantidad(Textcantidad.getText().toString().trim());

                userReference.child("Users").child(mAuth.getUid()).child("Tarjetas").child(tarjetSelected.getUid()).setValue(t);
                Toast.makeText(getContext(), "¡Tarjeta Actualizada!", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }

            case R.id.icon_delete:{
                Tarjeta t = new Tarjeta();
                t.setUid(tarjetSelected.getUid());
                userReference.child("Users").child(mAuth.getUid()).child("Tarjetas").child(t.getUid()).removeValue();
                Toast.makeText(getContext(), "¡Tarjeta eliminada!", Toast.LENGTH_LONG).show();
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
