package com.example.appgastos.ui.reporte;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appgastos.R;
import com.example.appgastos.databinding.FragmentReporteBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class ReporteFragment extends Fragment {
    private FragmentReporteBinding binding;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    final Calendar calendarioInicio= Calendar.getInstance();
    final Calendar calendarioFin= Calendar.getInstance();
    private EditText fechaInicio;
    private EditText fechaFin;
    private HashMap<String,Float> datosBarras;
    private BarChart graficaBarras;
    private PieChart graficaPastel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReporteViewModel reporteViewModel = new ViewModelProvider(this).get(ReporteViewModel.class);

        binding = FragmentReporteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        this.fechaInicio = root.findViewById(R.id.fechaInicio);
        this.fechaFin = root.findViewById(R.id.fechaFin);
        this.graficaBarras = root.findViewById(R.id.grafica_barras);
        this.graficaPastel = root.findViewById(R.id.grafica_pastel);
        Button btnGraficasBarras = root.findViewById(R.id.btn_barras);
        Button btnGraficasPastel = root.findViewById(R.id.btn_pastel);

        btnGraficasBarras.setOnClickListener(view -> {
            this.obtenerGastos(true);
        });

        btnGraficasPastel.setOnClickListener(view -> {
            this.obtenerGastos(false);
        });

        DatePickerDialog.OnDateSetListener dateInicio = (view, year, month, day) -> {
            calendarioInicio.set(Calendar.YEAR, year);
            calendarioInicio.set(Calendar.MONTH,month);
            calendarioInicio.set(Calendar.DAY_OF_MONTH,day);
            updateLabelInicio();
        };

        DatePickerDialog.OnDateSetListener dateFin = (view, year, month, day) -> {
            calendarioFin.set(Calendar.YEAR, year);
            calendarioFin.set(Calendar.MONTH,month);
            calendarioFin.set(Calendar.DAY_OF_MONTH,day);
            updateLabelFin();
        };

        fechaInicio.setOnClickListener(view -> new DatePickerDialog(getActivity(),dateInicio,calendarioInicio.get(Calendar.YEAR),calendarioInicio.get(Calendar.MONTH),calendarioInicio.get(Calendar.DAY_OF_MONTH)).show());
        fechaFin.setOnClickListener(view -> new DatePickerDialog(getActivity(),dateFin,calendarioFin.get(Calendar.YEAR),calendarioFin.get(Calendar.MONTH),calendarioFin.get(Calendar.DAY_OF_MONTH)).show());

        return root;
    }

    private void updateLabelInicio() {
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        fechaInicio.setText(dateFormat.format(calendarioInicio.getTime()));
    }

    private void updateLabelFin() {
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        fechaFin.setText(dateFormat.format(calendarioFin.getTime()));
    }

    private void obtenerGastos(boolean isBarras){
        if(!fechaInicio.getText().toString().isEmpty() && !fechaFin.getText().toString().isEmpty()){
            DatabaseReference ingresosDB = FirebaseDatabase.getInstance().getReference();
            long tiempoInicio = this.calendarioInicio.getTimeInMillis();
            long tiempoFin = this.calendarioFin.getTimeInMillis();
            this.datosBarras = new HashMap<>();
            Query query = ingresosDB.child("Users").child(mAuth.getCurrentUser().getUid()).child("Gastos");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for(DataSnapshot gasto : snapshot.getChildren()){
                            Calendar fechaRegistro = Calendar.getInstance();
                            String categoria = gasto.child("Category").getValue().toString();
                            float cantidad = Float.parseFloat(gasto.child("amount").getValue().toString());

                            fechaRegistro.set(Calendar.YEAR,Integer.parseInt(gasto.child("date").child("year").getValue().toString()));
                            fechaRegistro.set(Calendar.MONTH,Integer.parseInt(gasto.child("date").child("month").getValue().toString()));
                            fechaRegistro.set(Calendar.DAY_OF_MONTH,Integer.parseInt(gasto.child("date").child("date").getValue().toString()));

                            long tiempo = fechaRegistro.getTimeInMillis();
                            if(tiempo <= tiempoFin && tiempo >= tiempoInicio){
                                if(datosBarras.containsKey(categoria)){
                                    datosBarras.replace(categoria,datosBarras.get(categoria) + cantidad);
                                }else{
                                    datosBarras.put(categoria,cantidad);
                                }
                            }
                        }
                        if(isBarras){
                            llenarGraficaBarras();
                        }else {
                            llenarGraficaPastel();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    private void llenarGraficaPastel(){
        this.graficaBarras.setVisibility(View.GONE);
        ArrayList<PieEntry> visitors = new ArrayList<>();
        for(String key : this.datosBarras.keySet()){
            visitors.add(new PieEntry(this.datosBarras.get(key),key));
        }

        PieDataSet pieDataSet = new PieDataSet(visitors,"Reporte gastos");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        graficaPastel.setData(pieData);
        graficaPastel.getDescription().setEnabled(false);
        graficaPastel.setCenterText("Reporte gastos");
        graficaPastel.animate();
    }

    private void llenarGraficaBarras(){
        this.graficaBarras.setVisibility(View.VISIBLE);;
        ArrayList<BarEntry> visitors = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        int count = 0;
        for(String key : this.datosBarras.keySet()){
            visitors.add(new BarEntry(count,this.datosBarras.get(key)));
            labels.add(key);
            count++;
        }

        BarDataSet barDataSet = new BarDataSet(visitors,"Reporte de gastos");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description = new Description();
        description.setText("Categorias");
        graficaBarras.setDescription(description);
        BarData barData = new BarData(barDataSet);
        graficaBarras.setData(barData);

        XAxis xAxis = graficaBarras.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());
        xAxis.setLabelRotationAngle(270);
        graficaBarras.animateY(2000);
        graficaBarras.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
