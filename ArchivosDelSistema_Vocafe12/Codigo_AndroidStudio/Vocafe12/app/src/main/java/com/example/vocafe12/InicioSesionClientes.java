package com.example.vocafe12;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InicioSesionClientes extends AppCompatActivity implements View.OnClickListener{
    Button btnIniciarSesionC,btnRegistroC,btn_menuC;
    EditText editTextCorreoInicioC,editTextPasswordInicioC;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion_clientes);
        requestQueue = Volley.newRequestQueue(this);
        inicializar();
        btnIniciarSesionC.setOnClickListener(this);
        btnRegistroC.setOnClickListener(this);
        btn_menuC.setOnClickListener(this);
    }
    private void inicializar(){
        btnIniciarSesionC=findViewById(R.id.btnIniciarSesionC);
        btnRegistroC = findViewById(R.id.btnRegistroC);
        btn_menuC = findViewById(R.id.btn_menuC);
        editTextCorreoInicioC=findViewById(R.id.editTextCorreoInicioC);
        editTextPasswordInicioC=findViewById(R.id.editTextPasswordInicioC);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.btnRegistroC){
            Intent intent = new Intent(this,CrearCuentaClientes.class);
            startActivity(intent);
        } else if(id==R.id.btn_menuC){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        } else if(id==R.id.btnIniciarSesionC){
            validarCliente("http://192.168.1.76/vocafe12/validar_cliente.php");
        }
    }

    private void validarCliente(String URL) {
        final ProgressDialog loading = ProgressDialog.show(this,"Iniciando sesión...","Entrando al sistema");
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    if(!response.isEmpty()){
                        loading.dismiss();
                        String estado;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            estado = jsonObject.getString("Estado");
                            if(estado.equals("Habilitado")){
                                Intent intent = new Intent(this,MenuClientes.class);
                                intent.putExtra("correoE",editTextCorreoInicioC.getText().toString().trim());
                                startActivity(intent);
                            } else{
                                AlertDialog.Builder alerta = new AlertDialog.Builder(InicioSesionClientes.this);
                                alerta.setMessage("¡Tu cuenta está bloqueada!, comunicate con los empleados de la cafetería")
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else{
                        loading.dismiss();
                        AlertDialog.Builder alertaInicioIncorrecto = new AlertDialog.Builder(this);
                        alertaInicioIncorrecto.setMessage("Usuario o contraseña incorrecto")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                        AlertDialog tituloInicioIncorrecto = alertaInicioIncorrecto.create();
                        tituloInicioIncorrecto.setTitle("Error");
                        tituloInicioIncorrecto.show();
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
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("CorreoE",editTextCorreoInicioC.getText().toString());
                params.put("PasswordC",editTextPasswordInicioC.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(InicioSesionClientes.this,MainActivity.class);
        startActivity(intent);
    }
}