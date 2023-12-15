package com.example.vocafe12;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class CrearCuentaClientes extends AppCompatActivity implements View.OnClickListener{
    Button btn_crearCuentaCliente,btn_menuCC;
    EditText editTextNombreCliente,editTextAPCliente,editTextAMCliente,editTextCorreoCliente,editTextPasswordCliente;
    boolean bnombre,bapellidoP,bapellidoM,bcorreo,bpassword;
    String correo;
    RequestQueue requestQueue;
    private static final String URL = "http://192.168.1.76/vocafe12/guardarCliente.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearcuenta_clientes);
        requestQueue = Volley.newRequestQueue(this);
        inicializar();
        btn_crearCuentaCliente.setOnClickListener(this);
        btn_menuCC.setOnClickListener(this);
    }
    private void inicializar(){
        btn_crearCuentaCliente=findViewById(R.id.btn_crearCuentaCliente);
        btn_menuCC=findViewById(R.id.btn_menuCC);
        editTextNombreCliente=findViewById(R.id.editTextNombreCliente);
        editTextAPCliente=findViewById(R.id.editTextAPCliente);
        editTextAMCliente=findViewById(R.id.editTextAMCliente);
        editTextCorreoCliente=findViewById(R.id.editTextCorreoCliente);
        editTextPasswordCliente=findViewById(R.id.editTextPasswordCliente);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.btn_menuCC){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        } else if(id==R.id.btn_crearCuentaCliente){
            String nombreC=editTextNombreCliente.getText().toString().trim();
            String apellidoPaternoC=editTextAPCliente.getText().toString().trim();
            String apellidoMaternoC=editTextAMCliente.getText().toString().trim();
            String correoEC=editTextCorreoCliente.getText().toString().trim();
            String passwordC=editTextPasswordCliente.getText().toString().trim();
            if(nombreC.equals("")){
                bnombre=false;
            } else{
                bnombre=true;
            }
            if(apellidoPaternoC.equals("")){
                bapellidoP=false;
            } else{
                bapellidoP=true;
            }
            if(apellidoMaternoC.equals("")){
                bapellidoM=false;
            } else{
                bapellidoM=true;
            }
            if(correoEC.equals("")){
                bcorreo=false;
            } else{
                bcorreo=true;
            }
            if(passwordC.equals("")){
                bpassword=false;
            } else{
                bpassword=true;
            }
            if((bnombre)&&(bapellidoP)&&(bapellidoM)&&(bcorreo)&&(bpassword)){
                String URL2 = "http://192.168.1.76/vocafe12/checarCorreoCliente.php?CorreoE="+correoEC;
                if(validarCorreoElectronico(editTextCorreoCliente)){
                    checarCorreo(URL2);
                }
            } else{
                AlertDialog.Builder alertaCampos = new AlertDialog.Builder(this);
                alertaCampos.setMessage("¡Llena todos los campos!")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog tituloCampos = alertaCampos.create();
                tituloCampos.setTitle("Error");
                tituloCampos.show();
            }
        }
    }

    private boolean validarCorreoElectronico(EditText editTextCorreoCliente){
        String entradaCorreo = editTextCorreoCliente.getText().toString();
        if(!entradaCorreo.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(entradaCorreo).matches()){
            return true;
        } else{
            AlertDialog.Builder alertaCorreoInvalido = new AlertDialog.Builder(this);
            alertaCorreoInvalido.setMessage("¡Email inválido!")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog tituloCorreoInvalido = alertaCorreoInvalido.create();
            tituloCorreoInvalido.setTitle("Error");
            tituloCorreoInvalido.show();
            return false;
        }
    }

    private void checarCorreo(String URL2) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL2,
                null,
                response -> {
                    String correoEC=editTextCorreoCliente.getText().toString().trim();
                    try{
                        correo=response.getString("CorreoE");
                        if(correo.equals(correoEC)){
                            AlertDialog.Builder alertaCorreo = new AlertDialog.Builder(this);
                            alertaCorreo.setMessage("¡Este correo ya existe!, intenta con otro")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                            AlertDialog tituloCorreo = alertaCorreo.create();
                            tituloCorreo.setTitle("Error");
                            tituloCorreo.show();
                        }
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                }, error -> {
            String nombreC=editTextNombreCliente.getText().toString().trim();
            String apellidoPaternoC=editTextAPCliente.getText().toString().trim();
            String apellidoMaternoC=editTextAMCliente.getText().toString().trim();
            String correoEC=editTextCorreoCliente.getText().toString().trim();
            String passwordC=editTextPasswordCliente.getText().toString().trim();
            crearCliente(nombreC,apellidoPaternoC,apellidoMaternoC,correoEC,passwordC);
        }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void crearCliente(final String nombreC, final String apellidoPaternoC, final String apellidoMaternoC, final String correoEC, final String passwordC) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    AlertDialog.Builder alertaClienteCreado = new AlertDialog.Builder(this);
                    alertaClienteCreado.setMessage("El cliente ha sido creado satisfactoriamente")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog tituloClienteCreado = alertaClienteCreado.create();
                    tituloClienteCreado.setTitle("Aviso");
                    tituloClienteCreado.show();
                },
                error -> Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("NombreC",nombreC);
                params.put("ApellidoPaternoC",apellidoPaternoC);
                params.put("ApellidoMaternoC",apellidoMaternoC);
                params.put("CorreoE",correoEC);
                params.put("PasswordC",passwordC);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setMessage("¿Desea salir de Vocafe 12?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent menuPrincipal = new Intent(CrearCuentaClientes.this,MainActivity.class);
                        startActivity(menuPrincipal);
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        androidx.appcompat.app.AlertDialog titulo = builder.create();
        titulo.setTitle("Advertencia");
        titulo.show();
    }
}