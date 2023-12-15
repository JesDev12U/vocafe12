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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vocafe12.ui.BloquearClientesClass;
import com.example.vocafe12.ui.RecyclerViewBloquearClientesAdaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BloquearClientes extends AppCompatActivity {
    private RecyclerView recyclerBloquearClientes;
    private RecyclerViewBloquearClientesAdaptador adaptadorBloquearClientes;
    RequestQueue requestQueue;
    List<BloquearClientesClass> bloquearClientesClassList = new ArrayList<>();
    String correoEE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloquear_clientes);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            correoEE = extras.getString("correoEE");
        }
        recyclerBloquearClientes = findViewById(R.id.recyclerBloquearClientes);
        recyclerBloquearClientes.setHasFixedSize(true);
        recyclerBloquearClientes.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        traerClientesDesbloqueados();
    }
    private void traerClientesDesbloqueados(){
        final ProgressDialog loading = ProgressDialog.show(this,"Abriendo los clientes desbloqueados...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/traerClientesDesbloqueados.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                (Response.Listener<String>) response -> {
                    loading.dismiss();
                    try{
                        JSONArray array = new JSONArray(response);
                        for(int i=0;i<array.length();i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            String nombreCompleto = jsonObject.getString("NombreC")+" "+jsonObject.getString("ApellidoPaternoC")+" "+jsonObject.getString("ApellidoMaternoC");
                            bloquearClientesClassList.add(new BloquearClientesClass(
                                    jsonObject.getString("IdCliente"),nombreCompleto,jsonObject.getString("CorreoE"),jsonObject.getString("PasswordC")
                            ));
                        }
                        adaptadorBloquearClientes = new RecyclerViewBloquearClientesAdaptador(BloquearClientes.this, bloquearClientesClassList, new RecyclerViewBloquearClientesAdaptador.OnItemClickListener() {
                            @Override
                            public void onItemClick(BloquearClientesClass item) {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(BloquearClientes.this);
                                alerta.setMessage("¿Está seguro de bloquear el cliente?")
                                        .setCancelable(false)
                                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                cambiarEstado(item);
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
                        });
                        recyclerBloquearClientes.setAdapter(adaptadorBloquearClientes);
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
    public void onBackPressed(){
        Intent intent = new Intent(BloquearClientes.this,MenuEmpleados.class);
        intent.putExtra("correoEE",correoEE);
        startActivity(intent);
    }
    private void cambiarEstado(BloquearClientesClass item){
        final ProgressDialog loading = ProgressDialog.show(this,"Bloqueando al cliente...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/bloquearCliente.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    loading.dismiss();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(BloquearClientes.this);
                    alerta.setMessage("El cliente ha sido bloqueado")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(BloquearClientes.this,MenuEmpleados.class);
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
                    AlertDialog.Builder alerta = new AlertDialog.Builder(BloquearClientes.this);
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
                params.put("IdCliente",item.getIDBC());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}