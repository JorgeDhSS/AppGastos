package com.example.appgastos.ui.tarjetas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appgastos.Login;
import com.example.appgastos.R;
import com.example.appgastos.databinding.FragmentTarjetasBinding;
import com.example.appgastos.ui.PaginaPrincipal.PaginaPrincipalViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class TarjetaFragment extends Fragment {
    private FragmentTarjetasBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference ingresosDB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TarjetaViewModel TarjetaViewModel = new ViewModelProvider(this).get(TarjetaViewModel.class);

        binding = FragmentTarjetasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
}
