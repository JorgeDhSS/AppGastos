package com.example.appgastos;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appgastos.ui.category.CategoryViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


public class ModifyCategoriesFragment extends AppCompatActivity {
    String category;
    EditText editTextNameCategory, editTextGastoCategory;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_modify_categories);

        Intent intent = getIntent();
        category = intent.getStringExtra("Category");

        editTextNameCategory = findViewById(R.id.editTextNameCategory);
        editTextGastoCategory = findViewById(R.id.editTextGastoCategory);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recuperarDatos();
    }
    public void recuperarDatos()
    {
        mDatabase.child("Users").child(mAuth.getUid()).child("Categories").child(category).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    if(String.valueOf(task.getResult().getValue()) != "null")
                    {
                        editTextGastoCategory.setText(String.valueOf(task.getResult().getValue()));
                    }
                    else
                    {
                        editTextGastoCategory.setText("0.00");
                    }
                }
            }
        });

        editTextNameCategory.setText(category);
    }
    public void updateCategory(View v)
    {
        Map<String, Object> categorias = new HashMap<>();
        categorias.put(category, editTextGastoCategory.getText().toString());
        mDatabase.child("Users").child(mAuth.getUid()).child("Categories").updateChildren(categorias);
        Intent intent = new Intent(ModifyCategoriesFragment.this, MainActivity.class);
        startActivity(intent);
    }
    public void deleteCategory(View v)
    {
        Intent intent = new Intent(ModifyCategoriesFragment.this, CategoryDelete.class);
        intent.putExtra("category", category);
        intent.putExtra("gasto", editTextGastoCategory.getText().toString());
        startActivity(intent);
    }
}