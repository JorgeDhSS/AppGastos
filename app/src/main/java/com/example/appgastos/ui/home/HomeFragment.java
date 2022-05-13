package com.example.appgastos.ui.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import com.example.appgastos.CategoryDelete;
import com.example.appgastos.ModifyCategoriesFragment;
import com.example.appgastos.R;
import com.example.appgastos.RutinaCategorias;
import com.example.appgastos.RutinaGastos;
import com.example.appgastos.databinding.FragmentHomeBinding;
import com.example.appgastos.ui.category.CategoryFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        /*Button btnDiario = root.findViewById(R.id.btnDiario);
        btnDiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RutinaCategorias rutinaCategorias = new RutinaCategorias();
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_container,rutinaCategorias.getTargetFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });*/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /*public void onClickBtnMensual(View v)
    {
        Intent activityChangeIntent = new Intent(HomeFragment.this.getContext(), RutinaCategorias.class);
        HomeFragment.this.startActivity(activityChangeIntent);
    }
    public void onClickBtnDiario(View v)
    {
        RutinaGastos rutinaGastos = new RutinaGastos();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_container,rutinaGastos)
                .addToBackStack(null)
                .commit();
    }
    public void onClickBtnSemanal(View v)
    {
        Intent activityChangeIntent = new Intent(HomeFragment.this.getContext(), RutinaCategorias.class);
        HomeFragment.this.startActivity(activityChangeIntent);
    }*/
}