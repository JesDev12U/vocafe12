package com.example.vocafe12;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class InicioSesionEmpleados extends AppCompatActivity implements View.OnClickListener{
    Button btn_iniciarsesionE,btn_menuE,btn_registrarE;
    EditText editTextCorreoEInicio,editTextPasswordEInicio;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion_empleados);
        requestQueue = Volley.newRequestQueue(this);
        inicializar();
        btn_iniciarsesionE.setOnClickListener(this);
        btn_menuE.setOnClickListener(this);
        btn_registrarE.setOnClickListener(this);
    }

    private void inicializar(){
        btn_iniciarsesionE = findViewById(R.id.btn_iniciarsesionE);
        btn_menuE = findViewById(R.id.btn_menuE);
        btn_registrarE = findViewById(R.id.btn_registrarE);
        editTextCorreoEInicio = findViewById(R.id.editTextCorreoEInicio);
        editTextPasswordEInicio = findViewById(R.id.editTextPasswordEInicio);
    }
    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.btn_menuE){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        } else if(id==R.id.btn_registrarE){
            Intent intent = new Intent(this,CrearCuentaEmpleado.class);
            startActivity(intent);
        } else if(id==R.id.btn_iniciarsesionE){
            validarEmpleado("http://192.168.1.76/vocafe12/validar_empleado.php");
        }
    }
    private void validarEmpleado(String URL){
        final ProgressDialog loading = ProgressDialog.show(this,"Iniciando sesión...","Espere por favor");
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    loading.dismiss();
                    if(!response.isEmpty()){
                        Intent intent = new Intent(this,MenuEmpleados.class);
                        intent.putExtra("correoEE",editTextCorreoEInicio.getText().toString().trim());
                        startActivity(intent);
                    } else{
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
                }, error -> {
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
                params.put("CorreoElectronico",editTextCorreoEInicio.getText().toString());
                params.put("PasswordE",editTextPasswordEInicio.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(InicioSesionEmpleados.this,MainActivity.class);
        startActivity(intent);
    }
}