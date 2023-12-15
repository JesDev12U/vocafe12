package com.example.vocafe12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vocafe12.ui.Menu;
import com.example.vocafe12.ui.RecyclerViewMenuAdaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MenuComida extends AppCompatActivity {
    private RecyclerView recyclerViewComida;
    private RecyclerViewMenuAdaptador adaptadorComida;
    RequestQueue requestQueue;
    List<Menu> menuC = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_comida);
        recyclerViewComida=findViewById(R.id.recyclerComidaMenu);
        recyclerViewComida.setHasFixedSize(true);
        recyclerViewComida.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        consulta();
    }
    private void consulta(){
        final ProgressDialog loading = ProgressDialog.show(this,"Abriendo el menú...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/menuComida.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                (Response.Listener<String>) response -> {
                    loading.dismiss();
                    try{
                        JSONArray array = new JSONArray(response);
                        for(int i=0;i<array.length();i++){
                            JSONObject comidas = array.getJSONObject(i);
                            menuC.add(new Menu(
                                    comidas.getString("NombreP"),
                                    comidas.getString("Descripcion"),
                                    "Precio: $"+comidas.getString("Precio"),
                                    comidas.getString("Foto")
                            ));
                        }
                        adaptadorComida = new RecyclerViewMenuAdaptador(MenuComida.this,menuC);
                        recyclerViewComida.setAdapter(adaptadorComida);
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
}