package com.example.vocafe12;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vocafe12.ui.PHabilitados;
import com.example.vocafe12.ui.RecyclerViewPHabilitadosAdaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HabilitarProductos extends AppCompatActivity {
    private RecyclerView recyclerProductosHabilitados;
    private RecyclerViewPHabilitadosAdaptador adaptadorPHabilitados;
    RequestQueue requestQueue;
    List<PHabilitados> pHabilitadosList = new ArrayList<>();
    String correoEE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habilitar_productos);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoEE = extras.getString("correoEE");
        }
        recyclerProductosHabilitados = findViewById(R.id.recyclerProductosHabilitados);
        recyclerProductosHabilitados.setHasFixedSize(true);
        recyclerProductosHabilitados.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        traerPHabilitados();
    }
    private void traerPHabilitados(){
        final ProgressDialog loading = ProgressDialog.show(this,"Abriendo los productos habilitados","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/traerPHabilitados.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                (Response.Listener<String>) response -> {
                    loading.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            pHabilitadosList.add(new PHabilitados(
                                    jsonObject.getString("NombreP"),jsonObject.getString("Foto")
                            ));
                        }
                        adaptadorPHabilitados = new RecyclerViewPHabilitadosAdaptador(HabilitarProductos.this, pHabilitadosList, new RecyclerViewPHabilitadosAdaptador.OnItemClickListener() {
                            @Override
                            public void onItemClick(PHabilitados item) {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(HabilitarProductos.this);
                                alerta.setMessage("¿Desea deshabilitar este producto?")
                                        .setCancelable(false)
                                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                deshabilitarP(item);
                                            }
                                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                AlertDialog titulo = alerta.create();
                                titulo.setTitle("Advertencia");
                                titulo.show();
                            }
                        });
                        recyclerProductosHabilitados.setAdapter(adaptadorPHabilitados);
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
        requestQueue.add(stringRequest);
    }
    private void deshabilitarP(PHabilitados item){
        final ProgressDialog loading = ProgressDialog.show(this,"Deshabilitando el producto...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/deshabilitarProducto.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    loading.dismiss();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("El producto ha sido deshabilitado")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(HabilitarProductos.this,MenuEmpleados.class);
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
                params.put("NombreP",item.getNombrePHabilitado());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void onBackPressed(){
        Intent intent = new Intent(HabilitarProductos.this,MenuEmpleados.class);
        intent.putExtra("correoEE",correoEE);
        startActivity(intent);
    }
}