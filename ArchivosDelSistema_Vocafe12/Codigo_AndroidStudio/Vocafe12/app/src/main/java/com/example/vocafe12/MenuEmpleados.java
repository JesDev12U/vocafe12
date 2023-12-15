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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class MenuEmpleados extends AppCompatActivity implements View.OnClickListener{
    TextView textViewNombreE;
    Button btnAgregarProducto,btnModificarProductoMenu,btnCerrarSesion, btnEliminarProducto, btnVerProductos;
    Button btnVerPPS, btnConsultarVentas, btnDeshabilitarP, btnHabilitarP,btnBloquearClientes,btnDesbloquearClientes;
    String correoEE;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_empleados);
        requestQueue = Volley.newRequestQueue(this);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoEE=extras.getString("correoEE");
        }
        inicializar();
        traerNombre();
        btnAgregarProducto.setOnClickListener(this);
        btnModificarProductoMenu.setOnClickListener(this);
        btnCerrarSesion.setOnClickListener(this);
        btnEliminarProducto.setOnClickListener(this);
        btnVerProductos.setOnClickListener(this);
        btnVerPPS.setOnClickListener(this);
        btnConsultarVentas.setOnClickListener(this);
        btnDeshabilitarP.setOnClickListener(this);
        btnHabilitarP.setOnClickListener(this);
        btnBloquearClientes.setOnClickListener(this);
        btnDesbloquearClientes.setOnClickListener(this);
    }
    private void inicializar(){
        textViewNombreE=findViewById(R.id.textViewNombreE);
        btnAgregarProducto = findViewById(R.id.btnAgregarProducto);
        btnModificarProductoMenu = findViewById(R.id.btnModificarProductoMenu);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnEliminarProducto = findViewById(R.id.btnEliminarProducto);
        btnVerProductos = findViewById(R.id.btnVerProductos);
        btnVerPPS = findViewById(R.id.btnVerPPS);
        btnConsultarVentas = findViewById(R.id.btnConsultarVentas);
        btnDeshabilitarP = findViewById(R.id.btnDeshabilitarP);
        btnHabilitarP = findViewById(R.id.btnHabilitarProductos);
        btnBloquearClientes = findViewById(R.id.btnBloquearClientes);
        btnDesbloquearClientes = findViewById(R.id.btnDesbloquearClientes);
    }
    private void traerNombre(){
        final ProgressDialog loading = ProgressDialog.show(this,"Cargando...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/traerNombreEmpleado.php?CorreoElectronico="+correoEE;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    loading.dismiss();
                    String nombreEmpleado;
                    try{
                        nombreEmpleado = response.getString("NombreE");
                        textViewNombreE.setText(nombreEmpleado);
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                },
                error -> {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("No tienes conexión a internet, se cerrará la sesión")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MenuEmpleados.this,InicioSesionEmpleados.class);
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
        int id=view.getId();
        if(id==R.id.btnAgregarProducto){
            Intent intent = new Intent(this,AgregarProducto.class);
            intent.putExtra("correoEE",correoEE);
            startActivity(intent);
        } else if(id==R.id.btnCerrarSesion){
            Intent intent = new Intent(this,InicioSesionEmpleados.class);
            startActivity(intent);
        } else if(id==R.id.btnModificarProductoMenu){
            Intent intent = new Intent(this,BuscarProducto.class);
            intent.putExtra("correoEE",correoEE);
            startActivity(intent);
        } else if(id==R.id.btnEliminarProducto){
            Intent intent = new Intent(this,EliminarProducto.class);
            intent.putExtra("correoEE",correoEE);
            startActivity(intent);
        } else if(id==R.id.btnVerProductos){
            Intent intent = new Intent(this,VisualizarProductos.class);
            intent.putExtra("correoEE",correoEE);
            startActivity(intent);
        } else if(id==R.id.btnVerPPS){
            Intent intent = new Intent(this,VerPP.class);
            intent.putExtra("correoEE",correoEE);
            startActivity(intent);
        } else if(id == R.id.btnConsultarVentas){
            Intent intent = new Intent(this,IntervaloFechas.class);
            intent.putExtra("correoEE",correoEE);
            startActivity(intent);
        } else if(id == R.id.btnDeshabilitarP){
            Intent intent = new Intent(this,HabilitarProductos.class);
            intent.putExtra("correoEE",correoEE);
            startActivity(intent);
        } else if(id == R.id.btnHabilitarProductos){
            Intent intent = new Intent(this,DeshabilitarProductos.class);
            intent.putExtra("correoEE",correoEE);
            startActivity(intent);
        } else if(id == R.id.btnBloquearClientes){
            Intent intent = new Intent(this,BloquearClientes.class);
            intent.putExtra("correoEE",correoEE);
            startActivity(intent);
        } else if(id == R.id.btnDesbloquearClientes){
            Intent intent = new Intent(this,DesbloquearClientes.class);
            intent.putExtra("correoEE",correoEE);
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
                        Intent menuPrincipal = new Intent(MenuEmpleados.this,MainActivity.class);
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