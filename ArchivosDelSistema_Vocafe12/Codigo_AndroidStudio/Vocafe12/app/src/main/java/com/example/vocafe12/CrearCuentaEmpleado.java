package com.example.vocafe12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CrearCuentaEmpleado extends AppCompatActivity implements View.OnClickListener{
    EditText editTextNombreE,editTextApellidoPaternoE,editTextApellidoMaternoE,editTextCorreoEE,editTextPasswordE,editTextCargoE;
    Button btn_menuCE,btnCrearCuentaE;
    RequestQueue requestQueue;
    String correo;

    boolean nombre,apellidoP,apellidoM,correoE,bpasswordE,bcargoE;
    private static final String URL1 = "http://192.168.1.76/vocafe12/guardarEmpleado.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearcuenta_empleado);
        requestQueue = Volley.newRequestQueue(this);
        inicializar();
        btn_menuCE.setOnClickListener(this);
        btnCrearCuentaE.setOnClickListener(this);
    }
    private void inicializar(){
        btn_menuCE = findViewById(R.id.btn_menuCE);
        btnCrearCuentaE = findViewById(R.id.btnCrearCuentaE);
        editTextNombreE = findViewById(R.id.editTextNombreE);
        editTextApellidoPaternoE = findViewById(R.id.editTextApellidoPaternoE);
        editTextApellidoMaternoE = findViewById(R.id.editTextApellidoMaternoE);
        editTextCorreoEE = findViewById(R.id.editTextCorreoEE);
        editTextPasswordE = findViewById(R.id.editTextPasswordE);
        editTextCargoE = findViewById(R.id.editTextCargoE);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.btn_menuCE){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        } else if(id==R.id.btnCrearCuentaE){
            String nombreE = editTextNombreE.getText().toString().trim();
            String apellidoPaternoE = editTextApellidoPaternoE.getText().toString().trim();
            String apelldioMaternoE = editTextApellidoMaternoE.getText().toString().trim();
            String correoEE = editTextCorreoEE.getText().toString().trim();
            String passwordE = editTextPasswordE.getText().toString().trim();
            String cargoE = editTextCargoE.getText().toString().trim();
            if(nombreE.equals("")){
                nombre=false;
            } else{
                nombre=true;
            }
            if(apellidoPaternoE.equals("")){
                apellidoP=false;
            } else{
                apellidoP=true;
            }
            if(apelldioMaternoE.equals("")){
                apellidoM=false;
            } else{
                apellidoM=true;
            }
            if(correoEE.equals("")){
                correoE=false;
            } else{
                correoE=true;
            }
            if(passwordE.equals("")){
                bpasswordE=false;
            } else{
                bpasswordE=true;
            }
            if(cargoE.equals("")){
                bcargoE=false;
            } else{
                bcargoE=true;
            }
            if((nombre)&&(apellidoP)&&(apellidoM)&&(correoE)&&(bpasswordE)&&(bcargoE)){
                String URL2 = "http://192.168.1.76/vocafe12/checarCorreoEmpleado.php?CorreoElectronico="+correoEE;
                if(validarCorreoElectronico(editTextCorreoEE)){
                    checarCorreo(URL2);
                }
            } else{
                AlertDialog.Builder alertaCampos = new AlertDialog.Builder(this);
                alertaCampos.setMessage("¡Llena los campos!").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
    private boolean validarCorreoElectronico(EditText editTextCorreoEE){
        String entradaCorreo = editTextCorreoEE.getText().toString();
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
    public void checarCorreo(String URL2){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL2,
                null,
                response -> {
                    String correoEE = editTextCorreoEE.getText().toString().trim();
                    try{
                        correo=response.getString("CorreoElectronico");
                        if(correo.equals(correoEE)){
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
                },
                error -> {
                    char[] mayusculas = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
                    char[] minusculas = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
                    char[] numeros = {'1','2','3','4','5','6','7','8','9','0'};
                    StringBuilder caracteres = new StringBuilder();
                    caracteres.append(mayusculas);
                    caracteres.append(minusculas);
                    caracteres.append(numeros);
                    StringBuilder codigo = new StringBuilder();
                    for (int i = 0; i <= 5; i++) {
                        int cantidadCaracteres = caracteres.length();
                        int numeroRandom = (int)(Math.random()*cantidadCaracteres);
                        codigo.append((caracteres.toString()).charAt(numeroRandom));
                    }
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("Antes de generar la cuenta, se le mandará un código al administrador del sistema; " +
                            "pídele al administrador del sistema que te proporcione el código para poder continuar")
                    .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AlertDialog.Builder alerta1 = new AlertDialog.Builder(CrearCuentaEmpleado.this);
                                    alerta1.setCancelable(false);
                                    alerta1.setTitle("Ingresa el código");
                                    final EditText codigoIngresado = new EditText(CrearCuentaEmpleado.this);
                                    codigoIngresado.setInputType(InputType.TYPE_CLASS_TEXT);
                                    alerta1.setView(codigoIngresado);
                                    alerta1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if(codigoIngresado.getText().toString().equals(codigo.toString())){
                                                String nombreE = editTextNombreE.getText().toString().trim();
                                                String apellidoPaternoE = editTextApellidoPaternoE.getText().toString().trim();
                                                String apelldioMaternoE = editTextApellidoMaternoE.getText().toString().trim();
                                                String correoEE = editTextCorreoEE.getText().toString().trim();
                                                String passwordE = editTextPasswordE.getText().toString().trim();
                                                String cargoE = editTextCargoE.getText().toString().trim();
                                                crearEmpleado(nombreE,apellidoPaternoE,apelldioMaternoE,correoEE,passwordE,cargoE);
                                            } else{
                                                AlertDialog.Builder alerta = new AlertDialog.Builder(CrearCuentaEmpleado.this);
                                                alerta.setMessage("Código Incorrecto")
                                                        .setCancelable(false)
                                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Intent intent = new Intent(CrearCuentaEmpleado.this,InicioSesionEmpleados.class);
                                                                startActivity(intent);
                                                            }
                                                        });
                                                AlertDialog titulo = alerta.create();
                                                titulo.setTitle("Error");
                                                titulo.show();
                                            }
                                        }
                                    });
                                    alerta1.show();
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Aviso");
                    titulo.show();
                    if(ActivityCompat.checkSelfPermission(CrearCuentaEmpleado.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(CrearCuentaEmpleado.this,new String[]{Manifest.permission.SEND_SMS},1);
                    }
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("5582416449",null,codigo.toString(),null,null);
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    private void crearEmpleado(final String nombreE, final String apellidoPaternoE, final String apelldioMaternoE, final String correoEE, final String passwordE, final String cargoE) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                response -> {
                    AlertDialog.Builder alertaEmpleadoCreacion = new AlertDialog.Builder(this);
                    alertaEmpleadoCreacion.setMessage("El empleado ha sido creado satisfactoriamente")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(CrearCuentaEmpleado.this,InicioSesionEmpleados.class);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog tituloEmpleado = alertaEmpleadoCreacion.create();
                    tituloEmpleado.setTitle("Aviso");
                    tituloEmpleado.show();
                },
                error -> Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
        ){
            @Override
                    protected Map<String,String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("NombreE",nombreE);
                    params.put("ApellidoPaternoE",apellidoPaternoE);
                    params.put("ApellidoMaternoE",apelldioMaternoE);
                    params.put("CorreoElectronico",correoEE);
                    params.put("PasswordE",passwordE);
                    params.put("Cargo",cargoE);
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
                        Intent menuPrincipal = new Intent(CrearCuentaEmpleado.this,MainActivity.class);
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