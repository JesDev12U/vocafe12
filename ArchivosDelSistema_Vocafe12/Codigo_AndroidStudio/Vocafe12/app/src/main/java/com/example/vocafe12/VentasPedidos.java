package com.example.vocafe12;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.job.JobScheduler;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vocafe12.ui.DetallesVenta;
import com.example.vocafe12.ui.RecyclerViewVentasAdaptador;
import com.example.vocafe12.ui.VPedidos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VentasPedidos extends AppCompatActivity {
    private RecyclerView recyclerViewVentas;
    private RecyclerViewVentasAdaptador adaptadorVentas;
    RequestQueue requestQueue;
    String correoEE,fechaInicio,fechaFinal;
    List<VPedidos> vPedidosList = new ArrayList<>();
    TextView txtTotalVentas;
    double total=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas_pedidos);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoEE = extras.getString("correoEE");
            fechaInicio = extras.getString("fechaInicio");
            fechaFinal = extras.getString("fechaFinal");
        }
        recyclerViewVentas=findViewById(R.id.recyclerVentasPedidos);
        recyclerViewVentas.setHasFixedSize(true);
        recyclerViewVentas.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        txtTotalVentas = findViewById(R.id.txtTotalVentas);
        consulta();
    }
    private void consulta(){
        final ProgressDialog loading = ProgressDialog.show(this,"Cargando las ventas...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/PedidosPorFechas.php?FechaInicio="+fechaInicio+"&FechaFinal="+fechaFinal;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                (Response.Listener<String>) response -> {
                    loading.dismiss();
                       try{
                           String dato;
                           JSONArray array = new JSONArray(response);
                           for(int i=0;i<array.length();i++){
                               JSONObject jsonObject = array.getJSONObject(i);
                               dato = jsonObject.getString("CodigoPedido");
                               calcularTotal(dato);
                               vPedidosList.add(new VPedidos(
                                       jsonObject.getString("CodigoPedido"),jsonObject.getString("IdCliente"),
                                       jsonObject.getString("Fecha"), jsonObject.getString("Hora")
                               ));
                           }
                           adaptadorVentas = new RecyclerViewVentasAdaptador(VentasPedidos.this, vPedidosList, new RecyclerViewVentasAdaptador.OnItemClickListener() {
                               @Override
                               public void OnItemClick(VPedidos item) {
                                    irDetallesVenta(item);
                               }
                           });
                           recyclerViewVentas.setAdapter(adaptadorVentas);
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
                                    Intent intent = new Intent(VentasPedidos.this,IntervaloFechas.class);
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
    private void calcularTotal(String codigoPedido){
        String URL = "http://192.168.1.76/vocafe12/traerImporte.php?CodigoPedido="+codigoPedido;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        for(int i=0;i<array.length();i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            total += Double.parseDouble(jsonObject.getString("Importe"));
                            txtTotalVentas.setText("Total: $"+total);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {

                }
        );
        requestQueue.add(stringRequest);
    }
    private void irDetallesVenta(VPedidos item){
        Intent intent = new Intent(this,DetallePedidosVenta.class);
        intent.putExtra("VentasPedidos",item);
        intent.putExtra("correoEE",correoEE);
        startActivity(intent);
    }
    public void onBackPressed(){
        Intent intent = new Intent(this,IntervaloFechas.class);
        intent.putExtra("correoEE",correoEE);
        startActivity(intent);
    }
}