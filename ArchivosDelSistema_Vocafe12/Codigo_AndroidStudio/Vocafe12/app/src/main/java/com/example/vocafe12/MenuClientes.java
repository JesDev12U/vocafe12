package com.example.vocafe12;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class MenuClientes extends AppCompatActivity implements View.OnClickListener{
    TextView textViewnombreCliente;
    Button btnMenuComida,btnCerrarSesionC,btnHacerPedido,btnVerCarrito,btnVerPP;
    String correoE;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_clientes);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoE = extras.getString("correoE");
        }
        requestQueue = Volley.newRequestQueue(this);
        inicializar();
        traerNombre();
        btnMenuComida.setOnClickListener(this);
        btnCerrarSesionC.setOnClickListener(this);
        btnHacerPedido.setOnClickListener(this);
        btnVerCarrito.setOnClickListener(this);
        btnVerPP.setOnClickListener(this);
    }
    private void inicializar(){
        textViewnombreCliente = findViewById(R.id.textViewNombreCliente);
        btnMenuComida = findViewById(R.id.btnMenuComida);
        btnCerrarSesionC = findViewById(R.id.btnCerrarSesionC);
        btnHacerPedido = findViewById(R.id.btnHacerPedido);
        btnVerCarrito  =findViewById(R.id.btnVerCarrito);
        btnVerPP = findViewById(R.id.btnVerPP);
    }
    private void traerNombre(){
        final ProgressDialog loading = ProgressDialog.show(this,"Cargando...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/traerNombreCliente.php?CorreoE="+correoE;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    loading.dismiss();
                    String nombreCliente;
                    try{
                        nombreCliente = response.getString("NombreC");
                        textViewnombreCliente.setText(nombreCliente);
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                },
                error -> {
                    loading.dismiss();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("No tienes conexión a internet, se cerrará la sesión")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MenuClientes.this,InicioSesionClientes.class);
                                    startActivity(intent);
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
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.btnMenuComida){
            Intent intent = new Intent(this,MenuComida.class);
            startActivity(intent);
        } else if(id==R.id.btnCerrarSesionC){
            Intent intent = new Intent(this,InicioSesionClientes.class);
            startActivity(intent);
        } else if(id==R.id.btnHacerPedido){
            Intent intent = new Intent(this,HacerPedido.class);
            intent.putExtra("correoE",correoE);
            startActivity(intent);
        } else if(id==R.id.btnVerCarrito){
            Intent intent = new Intent(this,ListaCarrito.class);
            intent.putExtra("correoE",correoE);
            startActivity(intent);
        } else if(id==R.id.btnVerPP){
            Intent intent = new Intent(this,PedidosPendientes.class);
            intent.putExtra("correoE",correoE);
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
                        Intent menuPrincipal = new Intent(MenuClientes.this,MainActivity.class);
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