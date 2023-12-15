package com.example.vocafe12;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vocafe12.ui.DetallesVenta;
import com.example.vocafe12.ui.RecyclerViewDetallesVentaAdaptador;
import com.example.vocafe12.ui.VPedidos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DetallePedidosVenta extends AppCompatActivity {
    private RecyclerView recyclerDetallePedidosVenta;
    private RecyclerViewDetallesVentaAdaptador adaptadorDetallesVenta;
    String codigoPedido,idCliente;
    RequestQueue requestQueue;
    List<DetallesVenta> detallesVentaList = new ArrayList<>();
    String correoEE;
    TextView txtNombreClienteVenta,txtTotalPedidoVendido;
    double total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedidos_venta);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoEE = extras.getString("correoEE");
        }
        recyclerDetallePedidosVenta = findViewById(R.id.recyclerDetallePedidosVenta);
        recyclerDetallePedidosVenta.setHasFixedSize(true);
        recyclerDetallePedidosVenta.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        VPedidos vPedidos = (VPedidos) getIntent().getSerializableExtra("VentasPedidos");
        codigoPedido = vPedidos.getCodigoP();
        idCliente = vPedidos.getIdCliente();
        txtTotalPedidoVendido = findViewById(R.id.txtTotalPedidoVendido);
        consulta();
        txtNombreClienteVenta = findViewById(R.id.txtNomClienteVenta);
        traerNombreCliente();
    }
    private void consulta(){
        final ProgressDialog loading = ProgressDialog.show(this,"Cargando los detalles del pedido...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/traerDetallesPedido.php?CodigoPedido="+codigoPedido;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                (Response.Listener<String>) response -> {
                    loading.dismiss();
                    try{
                        JSONArray array = new JSONArray(response);
                        for(int i=0;i<array.length();i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            total+=Double.parseDouble(jsonObject.getString("Importe"));
                            txtTotalPedidoVendido.setText("Total: $"+total);
                            detallesVentaList.add(new DetallesVenta(
                                    jsonObject.getString("NombreProducto"),jsonObject.getString("Cantidad"),
                                    jsonObject.getString("Importe"),jsonObject.getString("Detalles"),
                                    jsonObject.getString("Foto")
                            ));
                        }
                        adaptadorDetallesVenta = new RecyclerViewDetallesVentaAdaptador(DetallePedidosVenta.this,detallesVentaList);
                        recyclerDetallePedidosVenta.setAdapter(adaptadorDetallesVenta);
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
                                    Intent intent = new Intent(DetallePedidosVenta.this,IntervaloFechas.class);
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
    private void traerNombreCliente(){
        String URL = "http://192.168.1.76/vocafe12/NomCPorID.php?IdCliente="+idCliente;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    String nombre,apellidoP,apellidoM,nombreCompleto;
                    try{
                        nombre = response.getString("NombreC");
                        apellidoP = response.getString("ApellidoPaternoC");
                        apellidoM = response.getString("ApellidoMaternoC");
                        nombreCompleto = nombre+" "+apellidoP+" "+apellidoM;
                        txtNombreClienteVenta.setText(nombreCompleto);
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}