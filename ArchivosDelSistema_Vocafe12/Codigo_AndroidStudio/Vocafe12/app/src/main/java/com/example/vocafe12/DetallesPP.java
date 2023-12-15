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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vocafe12.ui.DetallesPPSEmp;
import com.example.vocafe12.ui.RecyclerViewDetallesPPSEmpAdaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetallesPP extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerViewDetallesPPSEmp;
    private RecyclerViewDetallesPPSEmpAdaptador adaptadorDetallesPPSEmp;
    RequestQueue requestQueue;
    String codigoPedido,idCliente;
    List<DetallesPPSEmp> detallesPPSEmpList = new ArrayList<>();
    TextView textViewNombreCompletoC,textViewTotalPagarC;
    Button btnCompletarPedido;
    String correoEE;
    double total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pp);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoEE = extras.getString("correoEE");
        }
        recyclerViewDetallesPPSEmp = findViewById(R.id.recyclerDetallesPP);
        recyclerViewDetallesPPSEmp.setHasFixedSize(true);
        recyclerViewDetallesPPSEmp.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        com.example.vocafe12.ui.VerPP verPPs = (com.example.vocafe12.ui.VerPP) getIntent().getSerializableExtra("PP");
        codigoPedido = verPPs.getCodigoP();
        idCliente = verPPs.getIDCliente();
        consulta();
        inicializar();
        btnCompletarPedido.setOnClickListener(this);
        traerNombreCliente();
    }
    private void inicializar(){
        textViewNombreCompletoC = findViewById(R.id.textViewNombreCompletoC);
        textViewTotalPagarC = findViewById(R.id.textViewTotalPagarC);
        btnCompletarPedido = findViewById(R.id.btnCompletarPedido);
    }
    private void consulta(){
        String URL = "http://192.168.1.76/vocafe12/traerDetallesPedido.php?CodigoPedido="+codigoPedido;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                (Response.Listener<String>) response -> {
                    try{
                        JSONArray array = new JSONArray(response);
                        for(int i=0;i<array.length();i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            total += Double.parseDouble(jsonObject.getString("Importe"));
                            textViewTotalPagarC.setText("Total: $"+total);
                            detallesPPSEmpList.add(new DetallesPPSEmp(
                                    jsonObject.getString("NombreProducto"),jsonObject.getString("Cantidad"),
                                    jsonObject.getString("Importe"),jsonObject.getString("Detalles"),
                                    jsonObject.getString("Foto")
                            ));
                        }
                        adaptadorDetallesPPSEmp = new RecyclerViewDetallesPPSEmpAdaptador(DetallesPP.this,detallesPPSEmpList);
                        recyclerViewDetallesPPSEmp.setAdapter(adaptadorDetallesPPSEmp);
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        );
        requestQueue.add(stringRequest);
    }
    private void traerNombreCliente(){
        final ProgressDialog loading = ProgressDialog.show(this,"Cargando los detalles del pedido...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/NomCPorID.php?IdCliente="+idCliente;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    loading.dismiss();
                    String nombreCliente,apellidoP,apellidoM,nombreCompleto;
                    try{
                        nombreCliente = response.getString("NombreC");
                        apellidoP = response.getString("ApellidoPaternoC");
                        apellidoM = response.getString("ApellidoMaternoC");
                        nombreCompleto = nombreCliente+" "+apellidoP+" "+apellidoM;
                        textViewNombreCompletoC.setText(nombreCompleto);
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
                                    Intent intent = new Intent(DetallesPP.this,VerPP.class);
                                    intent.putExtra("correoEE",correoEE);
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
    private void completarPedido(){
        String URL = "http://192.168.1.76/vocafe12/completarPedido.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("El pedido se ha marcado como completado")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Aviso");
                    titulo.show();
                },
                error -> {
                    error.printStackTrace();
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("CodigoPedido",codigoPedido);
                params.put("statusPedido","Completado");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnCompletarPedido){
            completarPedido();
        }
    }
    public void onBackPressed(){
        Intent intent = new Intent(this,VerPP.class);
        intent.putExtra("correoEE",correoEE);
        startActivity(intent);
    }
}