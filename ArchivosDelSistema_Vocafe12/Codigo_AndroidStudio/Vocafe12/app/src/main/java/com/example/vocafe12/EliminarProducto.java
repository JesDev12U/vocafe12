package com.example.vocafe12;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EliminarProducto extends AppCompatActivity implements View.OnClickListener{
    EditText editTextNomProducto;
    Button btnEliminar, btnRegresar;
    String correoEE;
    RequestQueue requestQueue;
    boolean bnombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_producto);
        inicializar();
        btnEliminar.setOnClickListener(this);
        btnRegresar.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoEE=extras.getString("correoEE");
        }
    }
    private void inicializar(){
        editTextNomProducto = findViewById(R.id.editTextNomProducto);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnRegresar = findViewById(R.id.btnRegresar);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.btnEliminar){
            String nomP;
            nomP=editTextNomProducto.getText().toString().trim();
            if(nomP.equals("")){
                bnombre = false;
            } else{
                bnombre=true;
            }
            if(bnombre){
                verificacion();
            } else{
                AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                alerta.setMessage("¡Llena los campos!")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Error");
                titulo.show();
            }
        } else if(id==R.id.btnRegresar){
            Intent intent = new Intent(this,MenuEmpleados.class);
            intent.putExtra("correoEE",correoEE);
            startActivity(intent);
        }
    }
    private void verificacion(){
        final ProgressDialog loading = ProgressDialog.show(this,"Buscando el producto...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/buscarProducto.php?NombreP="+editTextNomProducto.getText().toString().trim();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    loading.dismiss();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("¿Está seguro de eliminar este producto?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    eliminarProducto();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Advertencia");
                    titulo.show();
                },
                error -> {
                    loading.dismiss();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("Ese producto no existe en la base de datos")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Error");
                    titulo.show();
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    private void eliminarProducto(){
        final ProgressDialog loading = ProgressDialog.show(this,"Eliminando el producto...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/eliminarProducto.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    loading.dismiss();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(EliminarProducto.this,MenuEmpleados.class);
                                    intent.putExtra("correoEE",correoEE);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Aviso");
                    titulo.show();
                },
                error -> {
                    loading.dismiss();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("Revisa tu conexión")
                            .setCancelable(false)
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
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("NombreP",editTextNomProducto.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this,MenuEmpleados.class);
        intent.putExtra("correoEE",correoEE);
        startActivity(intent);
    }
}