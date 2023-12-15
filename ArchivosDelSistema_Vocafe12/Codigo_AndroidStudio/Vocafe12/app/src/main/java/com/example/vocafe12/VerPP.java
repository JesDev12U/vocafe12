package com.example.vocafe12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vocafe12.ui.RecyclerViewVerPPAdaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VerPP extends AppCompatActivity {
    private RecyclerView recyclerViewVerPP;
    private RecyclerViewVerPPAdaptador adaptadorVerPP;
    RequestQueue requestQueue;
    String correoEE;
    List<com.example.vocafe12.ui.VerPP> verPPList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pp);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoEE = extras.getString("correoEE");
        }
        recyclerViewVerPP=findViewById(R.id.recyclerVerPP);
        recyclerViewVerPP.setHasFixedSize(true);
        recyclerViewVerPP.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        consulta();
    }
    private void consulta(){
        final ProgressDialog loading = ProgressDialog.show(this,"Cargando los pedidos pendientes...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/PP.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                (Response.Listener<String>) response -> {
                    loading.dismiss();
                    try{
                        JSONArray array = new JSONArray(response);
                        for(int i=0;i< array.length();i++){
                            JSONObject pps = array.getJSONObject(i);
                            verPPList.add(new com.example.vocafe12.ui.VerPP(
                               pps.getString("CodigoPedido"),pps.getString("IdCliente"),
                                    pps.getString("Fecha"), pps.getString("Hora")
                            ));
                        }
                        adaptadorVerPP = new RecyclerViewVerPPAdaptador(VerPP.this, verPPList, new RecyclerViewVerPPAdaptador.OnItemClickListener() {
                            @Override
                            public void onItemClick(com.example.vocafe12.ui.VerPP item) {
                                irDetalles(item);
                            }
                        });
                        recyclerViewVerPP.setAdapter(adaptadorVerPP);
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
                                    Intent intent = new Intent(VerPP.this,MenuEmpleados.class);
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
    private void irDetalles(com.example.vocafe12.ui.VerPP item){
        Intent intent = new Intent(this,DetallesPP.class);
        intent.putExtra("PP",item);
        intent.putExtra("correoEE",correoEE);
        startActivity(intent);
    }
    public void onBackPressed(){
        Intent intent = new Intent(this,MenuEmpleados.class);
        intent.putExtra("correoEE",correoEE);
        startActivity(intent);
    }
}