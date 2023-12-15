package com.example.vocafe12;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vocafe12.ui.Producto;
import com.example.vocafe12.ui.RecyclerViewAdaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VisualizarProductos extends AppCompatActivity {
    private RecyclerView recyclerViewProductos;
    private RecyclerViewAdaptador adaptadorProductos;
    RequestQueue requestQueue;
    List<Producto> producto = new ArrayList<>();
    String correoEE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_productos);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoEE = extras.getString("correoEE");
        }
        recyclerViewProductos=findViewById(R.id.recyclerProductos);
        recyclerViewProductos.setHasFixedSize(true);
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        consulta();
    }

    public void consulta(){
        final ProgressDialog loading = ProgressDialog.show(this,"Buscando los productos...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/buscarProductoporID.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                (Response.Listener<String>) response -> {
                    loading.dismiss();
                    try{
                        JSONArray array = new JSONArray(response);
                        for(int i=0;i<array.length();i++){
                            JSONObject productos = array.getJSONObject(i);
                            producto.add(new Producto(
                                    productos.getString("NombreP"),
                                    productos.getString("Descripcion"),
                                    "Precio: $"+productos.getString("Precio"),
                                    "Costo: $"+productos.getString("Costo"),
                                    productos.getString("Foto"),
                                    "Categoria: "+productos.getString("Categoria")
                            ));
                        }
                        adaptadorProductos = new RecyclerViewAdaptador(VisualizarProductos.this,producto);
                        recyclerViewProductos.setAdapter(adaptadorProductos);
                    } catch(JSONException e){
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
                                    Intent intent = new Intent(VisualizarProductos.this,MenuEmpleados.class);
                                    intent.putExtra("correoEE",correoEE);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Error");
                    titulo.show();
                }
        );
        requestQueue.add(stringRequest);
    }
}