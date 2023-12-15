package com.example.vocafe12;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class IntervaloFechas extends AppCompatActivity implements View.OnClickListener{
    String correoEE;
    TextView textViewFechaInicio,textViewFechaFinal;
    Button btnFechaInicio,btnFechaFinal,btnCV,btnRegresarMenuEmp;
    int dia,mes,year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervalo_fechas);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoEE = extras.getString("correoEE");
        }
        inicializar();
        btnFechaInicio.setOnClickListener(this);
        btnFechaFinal.setOnClickListener(this);
        btnCV.setOnClickListener(this);
        btnRegresarMenuEmp.setOnClickListener(this);
    }
    private void inicializar(){
        textViewFechaInicio = findViewById(R.id.textViewFechaInicio);
        textViewFechaFinal = findViewById(R.id.textViewFechaFinal);
        btnFechaInicio = findViewById(R.id.btnFechaInicio);
        btnFechaFinal = findViewById(R.id.btnFechaFinal);
        btnCV = findViewById(R.id.btnCV);
        btnRegresarMenuEmp = findViewById(R.id.btnRegresarMenuEmp);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.btnFechaInicio){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    textViewFechaInicio.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                }
            },dia,mes,year);
            datePickerDialog.show();
        } else if(id == R.id.btnFechaFinal){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    textViewFechaFinal.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                }
            },dia,mes,year);
            datePickerDialog.show();
        } else if(id == R.id.btnCV){
            boolean fechaI,fechaF;
            if(textViewFechaInicio.getText().equals("dd/m/aaaa")){
                fechaI = false;
            } else{
                fechaI = true;
            }
            if(textViewFechaFinal.getText().equals("dd/m/aaaa")){
                fechaF = false;
            } else{
                fechaF = true;
            }
            if(!fechaI){
                AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                alerta.setMessage("¡No haz puesto la fecha de inicio!");
                alerta.setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Error");
                titulo.show();
            }
            if(!fechaF){
                AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                alerta.setMessage("¡No haz puesto la fecha final!");
                alerta.setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Error");
                titulo.show();
            }
            if(fechaI && fechaF){
                Intent intent = new Intent(this,VentasPedidos.class);
                intent.putExtra("correoEE",correoEE);
                intent.putExtra("fechaInicio",textViewFechaInicio.getText());
                intent.putExtra("fechaFinal",textViewFechaFinal.getText());
                startActivity(intent);
            }
        } else if(id==R.id.btnRegresarMenuEmp){
            Intent intent = new Intent(this,MenuEmpleados.class);
            intent.putExtra("correoEE",correoEE);
            startActivity(intent);
        }
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(IntervaloFechas.this,MenuEmpleados.class);
        intent.putExtra("correoEE",correoEE);
        startActivity(intent);
    }
}