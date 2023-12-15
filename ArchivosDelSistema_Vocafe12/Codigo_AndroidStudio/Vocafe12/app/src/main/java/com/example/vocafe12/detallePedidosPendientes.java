package com.example.vocafe12;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vocafe12.ui.DetallePP;
import com.example.vocafe12.ui.PedidoPendiente;
import com.example.vocafe12.ui.RecyclerViewDetallePPAdaptador;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class detallePedidosPendientes extends AppCompatActivity {
    private RecyclerView recyclerViewDetallesPP;
    private RecyclerViewDetallePPAdaptador adaptadorDetallesPP;
    RequestQueue requestQueue;
    String codigoPedido,nombre,correoE;
    TextView textViewTotalPPC;
    List<DetallePP> detallePPList = new ArrayList<>();
    double total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedidos_pendientes);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoE = extras.getString("correoE");
        }
        textViewTotalPPC = findViewById(R.id.textViewTotalPPC);
        recyclerViewDetallesPP=findViewById(R.id.recyclerViewDetallePP);
        recyclerViewDetallesPP.setHasFixedSize(true);
        recyclerViewDetallesPP.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        PedidoPendiente pedidoPendiente = (PedidoPendiente) getIntent().getSerializableExtra("pp");
        codigoPedido = pedidoPendiente.getCodigoP();
        consulta();
    }

    private void consulta(){
        final ProgressDialog loading = ProgressDialog.show(this,"Abriendo los detalles del pedido...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/traerDetallesPedido.php?CodigoPedido="+codigoPedido;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                (Response.Listener<String>) response -> {
                    loading.dismiss();
                    try{
                        JSONArray array = new JSONArray(response);
                        for(int i=0;i<array.length();i++){
                            JSONObject pedidos = array.getJSONObject(i);
                            nombre = pedidos.getString("NombreProducto");
                            total += Double.parseDouble(pedidos.getString("Importe"));
                            textViewTotalPPC.setText("Total a pagar: $"+total);
                            detallePPList.add(new DetallePP(
                               pedidos.getString("NombreProducto"),pedidos.getString("Cantidad"),
                               pedidos.getString("Importe"),pedidos.getString("Detalles"),pedidos.getString("Foto")
                            ));
                        }
                        adaptadorDetallesPP = new RecyclerViewDetallePPAdaptador(detallePedidosPendientes.this,detallePPList);
                        recyclerViewDetallesPP.setAdapter(adaptadorDetallesPP);
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
                                    Intent intent = new Intent(detallePedidosPendientes.this,PedidosPendientes.class);
                                    intent.putExtra("correoE",correoE);
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