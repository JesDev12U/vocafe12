package com.example.vocafe12;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.vocafe12.ui.PedidoPendiente;
import com.example.vocafe12.ui.RecyclerViewPedidoPendienteAdaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PedidosPendientes extends AppCompatActivity {
    private RecyclerView recyclerViewPedidosPendientes;
    private RecyclerViewPedidoPendienteAdaptador adaptadorPP;
    String correoE,idCliente;
    RequestQueue requestQueue;
    List<PedidoPendiente> pp = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_pendientes);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoE = extras.getString("correoE");
        }
        recyclerViewPedidosPendientes=findViewById(R.id.recyclerPedidosPendientes);
        recyclerViewPedidosPendientes.setHasFixedSize(true);
        recyclerViewPedidosPendientes.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        traerIdCliente();
    }
    private void traerIdCliente(){
        final ProgressDialog loading = ProgressDialog.show(this,"Abriendo tus pedidos pendientes...","Espere por favor");
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
                        traerPedidosPendientes(dato);
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
                                    Intent intent = new Intent(PedidosPendientes.this,MenuClientes.class);
                                    intent.putExtra("correoE",correoE);
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
    private void traerPedidosPendientes(String id){
        String URL = "http://192.168.1.76/vocafe12/traerPedidosPendientes.php?IdCliente="+id;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                (Response.Listener<String>)response -> {
                    try{
                        JSONArray array = new JSONArray(response);
                        for(int i=0;i<array.length();i++){
                            JSONObject pedidosp = array.getJSONObject(i);
                            pp.add(new PedidoPendiente(
                               pedidosp.getString("CodigoPedido"),pedidosp.getString("Fecha"),
                               pedidosp.getString("Hora"),pedidosp.getString("statusPedido")
                            ));
                        }
                        adaptadorPP = new RecyclerViewPedidoPendienteAdaptador(PedidosPendientes.this, pp, new RecyclerViewPedidoPendienteAdaptador.OnItemClickListener() {
                            @Override
                            public void onItemClick(PedidoPendiente item) { irDetalles(item); }
                        });
                        recyclerViewPedidosPendientes.setAdapter(adaptadorPP);
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        );
        requestQueue.add(stringRequest);
    }
    private void irDetalles(PedidoPendiente item){
        Intent intent = new Intent(this,detallePedidosPendientes.class);
        intent.putExtra("pp",item);
        intent.putExtra("correoE",correoE);
        startActivity(intent);
    }
    public void onBackPressed(){
        Intent intent = new Intent(PedidosPendientes.this,MenuClientes.class);
        intent.putExtra("correoE",correoE);
        startActivity(intent);
    }
}