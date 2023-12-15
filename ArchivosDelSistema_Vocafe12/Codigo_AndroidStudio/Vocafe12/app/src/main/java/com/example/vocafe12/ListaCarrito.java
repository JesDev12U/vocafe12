package com.example.vocafe12;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vocafe12.ui.Carrito;
import com.example.vocafe12.ui.RecyclerViewCarritoAdaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;

public class ListaCarrito extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerCarrito;
    private RecyclerViewCarritoAdaptador adaptadorCarrito;
    int sizeCarrito;
    List<Carrito> carritoList = new ArrayList<>();
    String correoE,idCliente,fecha,hora;
    TextView txtTotalPagar;
    RequestQueue requestQueue;
    Button btnComprar;
    String [] nombreProducto;
    String [] cantidad;
    String [] importe;
    String [] detalles;
    String [] foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_carrito);
        btnComprar = findViewById(R.id.btnComprar);
        btnComprar.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoE = extras.getString("correoE");
        }
        requestQueue = Volley.newRequestQueue(this);
        recyclerCarrito=findViewById(R.id.recyclerCarrito);
        recyclerCarrito.setHasFixedSize(true);
        recyclerCarrito.setLayoutManager(new LinearLayoutManager(this));
        txtTotalPagar = findViewById(R.id.txtTotalPagar);
        btnComprar.setEnabled(false);
        traerIdCliente();
    }
    public void traerIdCliente(){
        final ProgressDialog loading = ProgressDialog.show(this,"Abriendo el carrito...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/checarCorreoCliente.php?CorreoE="+correoE;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    loading.dismiss();
                    String dato;
                    try {
                        dato = response.getString("IdCliente");
                        idCliente=dato;
                        traerCarrito(dato);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
        );
        requestQueue.add(jsonObjectRequest);
    }
    public void traerCarrito(String id){
        String URL = "http://192.168.1.76/vocafe12/listaCarrito.php?IdCliente="+id;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                (Response.Listener<String>) response->{
                    try{
                        JSONArray array = new JSONArray(response);
                        sizeCarrito = array.length();
                        double total=0;
                        nombreProducto = new String[array.length()];
                        cantidad = new String[array.length()];
                        importe = new String[array.length()];
                        detalles = new String[array.length()];
                        foto = new String[array.length()];
                        if(sizeCarrito==0){
                            txtTotalPagar.setText("¡No tienes nada en el carrito!");
                            btnComprar.setEnabled(false);
                        } else{
                            btnComprar.setEnabled(true);
                        }
                        for(int i=0;i<array.length();i++){
                            JSONObject productos = array.getJSONObject(i);
                            String totalString;
                            totalString = productos.getString("Total");
                            total+=Double.parseDouble(totalString);
                            nombreProducto[i]=productos.getString("NombreProducto");
                            cantidad[i]=productos.getString("Cantidad");
                            importe[i]=productos.getString("Total");
                            detalles[i]=productos.getString("Detalles");
                            foto[i]=productos.getString("Foto");
                            txtTotalPagar.setText("Total a pagar: $"+total);
                            carritoList.add(new Carrito(
                                productos.getString("NombreProducto"),productos.getString("Detalles"),
                                    productos.getString("Cantidad"),
                                    productos.getString("Total"),productos.getString("Foto")
                            ));
                        }
                        adaptadorCarrito = new RecyclerViewCarritoAdaptador(ListaCarrito.this, carritoList, new RecyclerViewCarritoAdaptador.OnItemClickListener() {
                            @Override
                            public void onItemClick(Carrito item) {
                                moverDetalles(item);
                            }
                        });
                        recyclerCarrito.setAdapter(adaptadorCarrito);
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        );
        requestQueue.add(stringRequest);
    }
    private void moverDetalles(Carrito item){
        final ProgressDialog loading = ProgressDialog.show(this,"Moviendose a los detalles del pedido...","Espere por favor");
        Intent intent  = new Intent(this,DetallesPedido.class);
        intent.putExtra("Carrito",item);
        intent.putExtra("correoE",correoE);
        startActivity(intent);
        loading.dismiss();
    }
    public void onBackPressed(){
        Intent intent = new Intent(this,MenuClientes.class);
        intent.putExtra("correoE",correoE);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.btnComprar){
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setMessage("¿Está seguro de comprar estos productos?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            marcarSiOcupado();
                            generarPedido();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Advertencia");
            titulo.show();
        }
    }
    private void generarPedido(){
        final ProgressDialog loading = ProgressDialog.show(this,"Generando código de pedido...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/agregarPedido.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    loading.dismiss();
                    obtenerCodigoPedido();
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
                Time hoy = new Time(Time.getCurrentTimezone());
                hoy.setToNow();
                int dia = hoy.monthDay;
                int mes = hoy.month;
                mes++;
                int year = hoy.year;
                fecha = dia+"/"+mes+"/"+year;
                Date date = new Date();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formato = new SimpleDateFormat("h:mm:ss a");
                hora = formato.format(date);
                params.put("Fecha",fecha);
                params.put("Hora",hora);
                params.put("statusPedido","Pendiente");
                params.put("IdCliente",idCliente);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void marcarSiOcupado(){
        for(int i=0;i<sizeCarrito;i++){
            String URL = "http://192.168.1.76/vocafe12/cambiarOcupado.php";
            int finalI = i;
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    URL,
                    response -> {},
                    error -> {}
            ){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("NombreP",nombreProducto[finalI]);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
    private void detallesPedido(String codigoPedido){
        for(int i=0;i<sizeCarrito;i++){
            String URL = "http://192.168.1.76/vocafe12/detallesPedido.php";
                int finalI = i;
                StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    URL,
                    response -> {
                        vaciarCarrito();
                        Intent intent = new Intent(this,PedidoCompletado.class);
                        intent.putExtra("codigoPedido",codigoPedido);
                        intent.putExtra("correoE",correoE);
                        startActivity(intent);
                    },
                    error -> {

                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("CodigoPedido",codigoPedido);
                    params.put("IdCliente",idCliente);
                    params.put("NombreProducto",nombreProducto[finalI]);
                    params.put("Cantidad",cantidad[finalI]);
                    params.put("Importe",importe[finalI]);
                    params.put("Detalles",detalles[finalI]);
                    params.put("Foto",foto[finalI]);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
    private void obtenerCodigoPedido(){
        String URL = "http://192.168.1.76/vocafe12/buscarCodigoPedido.php?Fecha="+fecha+"&Hora="+hora+"&IdCliente="+idCliente;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    String dato;
                    try {
                        dato = response.getString("CodigoPedido");
                        detallesPedido(dato);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    private void vaciarCarrito(){
        String URL = "http://192.168.1.76/vocafe12/vaciarCarrito.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {},
                error -> {}
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("IdCliente",idCliente);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}