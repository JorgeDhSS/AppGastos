package com.example.appgastos;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CategoryDelete extends AppCompatActivity {
    TextView category, gasto;
    String categoryString;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_category_delete);

        Intent intent = getIntent();
        category = findViewById(R.id.textViewCategory);
        category.setText(intent.getStringExtra("category"));
        categoryString = intent.getStringExtra("category");
        gasto = findViewById(R.id.textViewGasto);
        gasto.setText(intent.getStringExtra("gasto"));

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public void delete(View v)
    {
        mDatabase.child("Users").child(mAuth.getUid()).child("Categories").child(categoryString).removeValue();
        Intent intent = new Intent(CategoryDelete.this, MainActivity.class);
        startActivity(intent);
    }
}