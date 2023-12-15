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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class BuscarProducto extends AppCompatActivity implements View.OnClickListener{
    EditText editTextNombreProductoBusqueda;
    Button btnBuscarProducto,btnMenuEmpleadosBusqueda;
    RequestQueue requestQueue;
    String nombreProducto;
    String correoEE;
    boolean bnombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_producto);
        inicializar();
        requestQueue = Volley.newRequestQueue(this);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoEE=extras.getString("correoEE");
        }
        btnBuscarProducto.setOnClickListener(this);
        btnMenuEmpleadosBusqueda.setOnClickListener(this);
    }
    private void inicializar(){
        editTextNombreProductoBusqueda = findViewById(R.id.editTextNombreProductoBusqueda);
        btnBuscarProducto = findViewById(R.id.btnBuscarProducto);
        btnMenuEmpleadosBusqueda = findViewById(R.id.btnMenuEmpleadosBusqueda);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.btnBuscarProducto){
            nombreProducto = editTextNombreProductoBusqueda.getText().toString().trim();
            if(nombreProducto.equals("")){
                bnombre=false;
            } else{
                bnombre=true;
            }
            if(bnombre){
                buscarProducto();
            } else{
                AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                alerta.setMessage("¡Pon el nombre!")
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
        } else if(id==R.id.btnMenuEmpleadosBusqueda){
            Intent intent = new Intent(this,MenuEmpleados.class);
            intent.putExtra("correoEE",correoEE);
            startActivity(intent);
        }
    }
    private void buscarProducto(){
        final ProgressDialog loading = ProgressDialog.show(this,"Buscando el producto en la base de datos...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/buscarProducto.php?NombreP="+nombreProducto;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    loading.dismiss();
                    Intent intent = new Intent(this,ModificarProducto.class);
                    intent.putExtra("nombreProducto",nombreProducto);
                    intent.putExtra("correoEE",correoEE);
                    startActivity(intent);
                },
                error -> {
                    loading.dismiss();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("Este producto no existe en la base de datos")
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
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(BuscarProducto.this,MenuEmpleados.class);
        intent.putExtra("correoEE",correoEE);
        startActivity(intent);
    }
}