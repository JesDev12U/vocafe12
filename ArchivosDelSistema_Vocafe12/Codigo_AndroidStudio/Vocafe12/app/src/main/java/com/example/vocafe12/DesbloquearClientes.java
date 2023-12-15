package com.example.vocafe12;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vocafe12.ui.BloquearClientesClass;
import com.example.vocafe12.ui.DesbloquearClientesClass;
import com.example.vocafe12.ui.RecyclerViewDesbloquearClientesAdaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DesbloquearClientes extends AppCompatActivity {
    private RecyclerView recyclerDesbloquearClientes;
    private RecyclerViewDesbloquearClientesAdaptador adaptadorDesbloquearClientes;
    RequestQueue requestQueue;
    List<DesbloquearClientesClass> desbloquearClientesClassList = new ArrayList<>();
    String correoEE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desbloquear_clientes);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoEE = extras.getString("correoEE");
        }
        recyclerDesbloquearClientes = findViewById(R.id.recyclerDesbloquearClientes);
        recyclerDesbloquearClientes.setHasFixedSize(true);
        recyclerDesbloquearClientes.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        traerClientesBloqueados();
    }
    private void traerClientesBloqueados(){
        final ProgressDialog loading = ProgressDialog.show(this,"Abriendo los clientes bloqueados...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/traerClientesBloqueados.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                (Response.Listener<String>) response ->{
                    loading.dismiss();
                    try{
                        JSONArray array = new JSONArray(response);
                        for(int i=0;i<array.length();i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            String nombreCompleto = jsonObject.getString("NombreC")+" "+
                                    jsonObject.getString("ApellidoPaternoC")+" "+
                                    jsonObject.getString("ApellidoMaternoC");
                            desbloquearClientesClassList.add(new DesbloquearClientesClass(
                                    jsonObject.getString("IdCliente"),nombreCompleto,
                                    jsonObject.getString("CorreoE"), jsonObject.getString("PasswordC")
                            ));
                        }
                        adaptadorDesbloquearClientes = new RecyclerViewDesbloquearClientesAdaptador(DesbloquearClientes.this, desbloquearClientesClassList, new RecyclerViewDesbloquearClientesAdaptador.OnItemClickListener() {
                            @Override
                            public void onItemClick(DesbloquearClientesClass item) {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(DesbloquearClientes.this);
                                alerta.setMessage("¿Está seguro de desbloquear el cliente?")
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
                        recyclerDesbloquearClientes.setAdapter(adaptadorDesbloquearClientes);
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
        Intent intent = new Intent(DesbloquearClientes.this,MenuEmpleados.class);
        intent.putExtra("correoEE",correoEE);
        startActivity(intent);
    }
    private void cambiarEstado(DesbloquearClientesClass item){
        final ProgressDialog loading = ProgressDialog.show(this,"Desbloqueando al cliente...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/desbloquearCliente.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    loading.dismiss();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(DesbloquearClientes.this);
                    alerta.setMessage("El cliente ha sido desbloqueado")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(DesbloquearClientes.this,MenuEmpleados.class);
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
                    AlertDialog.Builder alerta = new AlertDialog.Builder(DesbloquearClientes.this);
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
                params.put("IdCliente",item.getIDDC());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}