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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vocafe12.ui.ComidaPedido;
import com.example.vocafe12.ui.RecyclerViewPedidoAdaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HacerPedido extends AppCompatActivity {
    private RecyclerView recyclerComidaPedido;
    private RecyclerViewPedidoAdaptador adaptadorPedidos;
    RequestQueue requestQueue;
    List<ComidaPedido> pedidos = new ArrayList<>();
    String correoE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hacer_pedido);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoE = extras.getString("correoE");
        }
        recyclerComidaPedido=findViewById(R.id.recyclerComidaPedido);
        recyclerComidaPedido.setHasFixedSize(true);
        recyclerComidaPedido.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        traerComidas();
    }
    private void traerComidas(){
        final ProgressDialog loading = ProgressDialog.show(this,"Abriendo el menú...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/menuPedidos.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                (Response.Listener<String>)  response -> {
                    try{
                        loading.dismiss();
                        JSONArray array = new JSONArray(response);
                        for(int i=0;i<array.length();i++){
                            JSONObject comidas = array.getJSONObject(i);
                            pedidos.add(new ComidaPedido(
                                    comidas.getString("NombreP"),
                                    "$"+comidas.getString("Precio"),
                                    comidas.getString("Foto")
                            ));
                        }
                        adaptadorPedidos = new RecyclerViewPedidoAdaptador(HacerPedido.this, pedidos, new RecyclerViewPedidoAdaptador.OnItemClickListener() {
                            @Override
                            public void onItemClick(ComidaPedido item) {
                                moveToDescription(item);
                            }
                        });
                        recyclerComidaPedido.setAdapter(adaptadorPedidos);
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

    public void moveToDescription(ComidaPedido item){
        final ProgressDialog loading = ProgressDialog.show(this,"Abriendo la descripción del producto...","Espere por favor");
        Intent intent = new Intent(this,DescripcionActivity.class);
        intent.putExtra("ComidaPedido",item);
        intent.putExtra("correoE",correoE);
        startActivity(intent);
        loading.dismiss();
    }
}