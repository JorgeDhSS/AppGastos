package com.example.appgastos.ui.PaginaPrincipal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appgastos.IngresarIngreso;
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
    private PaginaPrincipalViewModel paginaPrincipalViewModel;
    private FragmentPaginaPrincipalBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference ingresosDB;
    TextView totalIngresos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        paginaPrincipalViewModel =
                new ViewModelProvider(this).get(PaginaPrincipalViewModel.class);

        binding = FragmentPaginaPrincipalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button btn_ingreso = root.findViewById(R.id.btn_ingreso);
        totalIngresos = root.findViewById(R.id.txtTotalIngresos);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
