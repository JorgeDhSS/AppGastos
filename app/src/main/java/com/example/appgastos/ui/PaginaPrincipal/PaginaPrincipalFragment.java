package com.example.appgastos.ui.PaginaPrincipal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appgastos.IngresarIngreso;
import com.example.appgastos.Login;
import com.example.appgastos.MainActivity;
import com.example.appgastos.R;
import com.example.appgastos.databinding.FragmentPaginaPrincipalBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PaginaPrincipalFragment extends Fragment {
    private FragmentPaginaPrincipalBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference ingresosDB;
    private TextView totalIngresos;
    private TextView totalGastos;
    private TextView totalSaldo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PaginaPrincipalViewModel paginaPrincipalViewModel = new ViewModelProvider(this).get(PaginaPrincipalViewModel.class);

        binding = FragmentPaginaPrincipalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button btn_ingreso = root.findViewById(R.id.btn_ingreso);
        totalIngresos = root.findViewById(R.id.txtTotalIngresos);
        totalGastos = root.findViewById(R.id.txtGastos);
        totalSaldo = root.findViewById(R.id.saldoTotal);
        ImageView btn_session = root.findViewById(R.id.btn_cerrar);
        btn_session.setOnClickListener(view -> {
            Intent activityChangeIntent = new Intent(getActivity(), Login.class);
            startActivity(activityChangeIntent);
        });
        this.sumarIngresos();
        btn_ingreso.setOnClickListener(view -> {
            this.ingresarIngreso();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void ingresarIngreso(){
        Intent ingreso = new Intent(getActivity(), IngresarIngreso.class);
        startActivity(ingreso);
    }

    private void sumarIngresos (){
        this.ingresosDB = FirebaseDatabase.getInstance().getReference();

        Query query = this.ingresosDB.child("Users").child(mAuth.getCurrentUser().getUid()).child("Ingreso");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    float total = 0;
                    for(DataSnapshot ingreso : snapshot.getChildren()){
                        total += Float.parseFloat(ingreso.child("Ingreso").getValue().toString());
                    }
                    totalIngresos.setText(String.valueOf(total));
                }
                sumarGastos();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sumarGastos () {
        this.ingresosDB = FirebaseDatabase.getInstance().getReference();

        Query query = this.ingresosDB.child("Users").child(mAuth.getCurrentUser().getUid()).child("Gastos");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    float total = 0;
                    for(DataSnapshot ingreso : snapshot.getChildren()){
                        total += Float.parseFloat(ingreso.child("amount").getValue().toString());
                    }
                    totalGastos.setText(String.valueOf(total));
                }
                calcularTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void calcularTotal () {
        float total = Float.parseFloat(totalIngresos.getText().toString()) - Float.parseFloat(totalGastos.getText().toString());
        totalSaldo.setText(String.valueOf(total));
    }
}
