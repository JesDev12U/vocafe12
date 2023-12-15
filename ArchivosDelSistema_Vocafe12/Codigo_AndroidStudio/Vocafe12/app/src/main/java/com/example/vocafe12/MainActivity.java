package com.example.vocafe12;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn_cliente,btn_empleado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializar();
        btn_cliente.setOnClickListener(this);
        btn_empleado.setOnClickListener(this);
    }

    public void inicializar(){
        //Botones
        btn_cliente= findViewById(R.id.btn_cliente);
        btn_empleado = findViewById(R.id.btn_empleado);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.btn_cliente){
            Intent intent = new Intent(this,InicioSesionClientes.class);
            startActivity(intent);
        } else if(id==R.id.btn_empleado){
            Intent intent = new Intent(this,InicioSesionEmpleados.class);
            startActivity(intent);
        }
    }
    @Override
    public void onBackPressed(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setMessage("¿Desea salir de Vocafe 12?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent menuPrincipal = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(menuPrincipal);
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        androidx.appcompat.app.AlertDialog titulo = builder.create();
        titulo.setTitle("Advertencia");
        titulo.show();
    }
}
