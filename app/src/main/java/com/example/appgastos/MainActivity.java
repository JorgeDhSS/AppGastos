package com.example.appgastos;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.example.appgastos.ui.category.CategoryFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appgastos.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_categories, R.id.nav_rutine, R.id.nav_report, R.id.nav_cards, R.id.paginaPricipal)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onClickBtnAlimentos(View v)
    {
        Intent activityChangeIntent = new Intent(MainActivity.this, ModifyCategoriesFragment.class);
        activityChangeIntent.putExtra("Category", "Alimentos");
        startActivity(activityChangeIntent);
    }

    public void onClickBtnTransporte(View v)
    {
        Intent activityChangeIntent = new Intent(MainActivity.this, ModifyCategoriesFragment.class);
        activityChangeIntent.putExtra("Category", "Transportes");
        startActivity(activityChangeIntent);
    }

    public void onClickBtnMedicamentos(View v)
    {
        Intent activityChangeIntent = new Intent(MainActivity.this, ModifyCategoriesFragment.class);
        activityChangeIntent.putExtra("Category", "Medicamentos");
        startActivity(activityChangeIntent);
    }

    public void onClickBtnVivienda(View v)
    {
        Intent activityChangeIntent = new Intent(MainActivity.this, ModifyCategoriesFragment.class);
        activityChangeIntent.putExtra("Category", "Vivienda");
        startActivity(activityChangeIntent);
    }

    public void onClickBtnOcio(View v)
    {
        Intent activityChangeIntent = new Intent(MainActivity.this, ModifyCategoriesFragment.class);
        activityChangeIntent.putExtra("Category", "Ocio");
        startActivity(activityChangeIntent);
    }

    public void onClickBtnOtros(View v)
    {
        Intent activityChangeIntent = new Intent(MainActivity.this, ModifyCategoriesFragment.class);
        activityChangeIntent.putExtra("Category", "Otros");
        startActivity(activityChangeIntent);
    }

    public void onClickBtnDiario(View v)
    {
        Intent activityChangeIntent = new Intent(MainActivity.this, RutinaCategorias.class);
        activityChangeIntent.putExtra("periodo", "Diario");
        startActivity(activityChangeIntent);
                /*this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_container,RutinaGastos.newInstance("dg", "ew"))
                        .addToBackStack(null)
                        .commit();*/

        /*FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_container, RutinaGastos.newInstance("er","er"));
        transaction.addToBackStack(null);

        // Commit a la transacción
        transaction.commit();*/
    }
    public void onClickBtnSemanal(View v)
    {
        Intent activityChangeIntent = new Intent(MainActivity.this, RutinaCategorias.class);
        activityChangeIntent.putExtra("periodo", "Semanal");
        startActivity(activityChangeIntent);
                /*this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_container,RutinaGastos.newInstance("dg", "ew"))
                        .addToBackStack(null)
                        .commit();*/

        /*FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_container, RutinaGastos.newInstance("er","er"));
        transaction.addToBackStack(null);

        // Commit a la transacción
        transaction.commit();*/
    }
    public void onClickBtnMensual(View v)
    {
        Intent activityChangeIntent = new Intent(MainActivity.this, RutinaCategorias.class);
        activityChangeIntent.putExtra("periodo", "Mensual");
        startActivity(activityChangeIntent);
                /*this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_container,RutinaGastos.newInstance("dg", "ew"))
                        .addToBackStack(null)
                        .commit();*/

        /*FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_container, RutinaGastos.newInstance("er","er"));
        transaction.addToBackStack(null);

        // Commit a la transacción
        transaction.commit();*/
    }
}