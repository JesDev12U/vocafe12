package com.example.vocafe12;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vocafe12.ui.PDeshabilitados;
import com.example.vocafe12.ui.RecyclerViewPDeshabilitadosAdaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeshabilitarProductos extends AppCompatActivity {
    private RecyclerView recyclerViewHabilitados;
    private RecyclerViewPDeshabilitadosAdaptador adaptadorDeshabilitados;
    RequestQueue requestQueue;
    List<PDeshabilitados> pDeshabilitadosList = new ArrayList<>();
    String correoEE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deshabilitar_productos);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoEE = extras.getString("correoEE");
        }
        recyclerViewHabilitados = findViewById(R.id.recyclerViewHabilitados);
        recyclerViewHabilitados.setHasFixedSize(true);
        recyclerViewHabilitados.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        consulta();
    }
    private void consulta(){
        final ProgressDialog loading = ProgressDialog.show(this,"Abriendo los productos deshabilitados","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/traerPDeshabilitados.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                (Response.Listener<String>)  response -> {
                    loading.dismiss();
                    try{
                        JSONArray array = new JSONArray(response);
                        for(int i=0;i<array.length();i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            pDeshabilitadosList.add(new PDeshabilitados(
                                    jsonObject.getString("NombreP"),jsonObject.getString("Foto")
                            ));
                        }
                        adaptadorDeshabilitados = new RecyclerViewPDeshabilitadosAdaptador(DeshabilitarProductos.this, pDeshabilitadosList, new RecyclerViewPDeshabilitadosAdaptador.OnItemClickListener() {
                            @Override
                            public void onItemClick(PDeshabilitados item) {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(DeshabilitarProductos.this);
                                alerta.setMessage("¿Desea habilitar el producto?")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                habilitarP(item);
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
                        recyclerViewHabilitados.setAdapter(adaptadorDeshabilitados);
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                },
                error -> {
                    loading.dismiss();
                    android.app.AlertDialog.Builder alerta = new android.app.AlertDialog.Builder(this);
                    alerta.setMessage("Revisa tu conexión")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    android.app.AlertDialog titulo = alerta.create();
                    titulo.setTitle("Error");
                    titulo.show();
                }
        );
        requestQueue.add(stringRequest);
    }
    private void habilitarP(PDeshabilitados item){
        final ProgressDialog loading = ProgressDialog.show(this,"Habilitando el producto...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/habilitarProducto.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    loading.dismiss();
                    android.app.AlertDialog.Builder alerta = new android.app.AlertDialog.Builder(this);
                    alerta.setMessage("El producto ha sido habilitado")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(DeshabilitarProductos.this,MenuEmpleados.class);
                                    intent.putExtra("correoEE",correoEE);
                                    startActivity(intent);
                                }
                            });
                    android.app.AlertDialog titulo = alerta.create();
                    titulo.setTitle("Aviso");
                    titulo.show();
                },
                error -> {
                    loading.dismiss();
                    android.app.AlertDialog.Builder alerta = new android.app.AlertDialog.Builder(this);
                    alerta.setMessage("Revisa tu conexión")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    android.app.AlertDialog titulo = alerta.create();
                    titulo.setTitle("Error");
                    titulo.show();
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("NombreP",item.getNombrePDeshabilitado());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void onBackPressed(){
        Intent intent = new Intent(DeshabilitarProductos.this,MenuEmpleados.class);
        intent.putExtra("correoEE",correoEE);
        startActivity(intent);
    }
}