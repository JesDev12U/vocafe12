package com.example.vocafe12;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class AgregarProducto extends AppCompatActivity implements View.OnClickListener{

    EditText editTextnombreProducto,editTextDescripcionProducto,editTextPrecioProducto,editTextCostoProducto;
    ImageView imagenProducto;
    Button btnSubirFoto,btnGuardarProducto,btnProductosMenuEmpleados;
    RequestQueue requestQueue;
    Bitmap bitmap;
    String nombreP;
    String correoEE;
    private Spinner categorias;

    boolean bnombre,bdescripcion,bprecio,bcosto,bimagen;
    String URL = "http://192.168.1.76/vocafe12/guardarProducto.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);
        inicializar();
        requestQueue = Volley.newRequestQueue(this);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoEE=extras.getString("correoEE");
        }
        btnSubirFoto.setOnClickListener(this);
        btnGuardarProducto.setOnClickListener(this);
        btnProductosMenuEmpleados.setOnClickListener(this);
        String [] opcionesCategorias = {"Guisado","Producto"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,opcionesCategorias);
        categorias.setAdapter(adapter);
    }
    private void inicializar(){
        editTextnombreProducto=findViewById(R.id.editTextNombreProducto);
        editTextDescripcionProducto=findViewById(R.id.editTextDescripcionProducto);
        editTextPrecioProducto=findViewById(R.id.editTextPrecioProducto);
        editTextCostoProducto=findViewById(R.id.editTextCostoProducto);
        imagenProducto=findViewById(R.id.imagenProducto);
        btnSubirFoto=findViewById(R.id.btnSubirFoto);
        btnGuardarProducto=findViewById(R.id.btnGuardarProducto);
        btnProductosMenuEmpleados=findViewById(R.id.btnProductosMenuEmpleados);
        categorias = findViewById(R.id.spinnerCategorias);
    }
    public String getStringImagen(Bitmap bmp){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
            return encodedImage;
        } catch(Exception e){
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setMessage("¡Sube una imagen!")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Error");
            titulo.show();
            return "Error";
        }
    }
    public void uploadImage(){
        final ProgressDialog loading = ProgressDialog.show(this,"Agregando el producto...","Espere por favor");
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    loading.dismiss();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("El producto se ha agregado a la base de datos")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(AgregarProducto.this,MenuEmpleados.class);
                                    intent.putExtra("correoEE",correoEE);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Aviso");
                    titulo.show();
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
                String imagen=getStringImagen(bitmap);
                String nombreProducto = editTextnombreProducto.getText().toString().trim();
                String descripcion = editTextDescripcionProducto.getText().toString().trim();
                String precio = editTextPrecioProducto.getText().toString().trim();
                String costo = editTextCostoProducto.getText().toString().trim();
                Map<String,String> params = new Hashtable<>();
                params.put("NombreP",nombreProducto);
                params.put("Descripcion",descripcion);
                params.put("Precio",precio);
                params.put("Costo",costo);
                params.put("Foto",imagen);
                params.put("Categoria",categorias.getSelectedItem().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Selecciona la imagen"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode==RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imagenProducto.setImageBitmap(bitmap);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.btnSubirFoto){
            showFileChooser();
        }
        else if(id==R.id.btnGuardarProducto){
            String imagen=getStringImagen(bitmap);
            String nombreProducto = editTextnombreProducto.getText().toString().trim();
            String descripcion = editTextDescripcionProducto.getText().toString().trim();
            String precio = editTextPrecioProducto.getText().toString().trim();
            String costo = editTextCostoProducto.getText().toString().trim();
            if(nombreProducto.equals("")){
                bnombre=false;
            } else{
                bnombre=true;
            }
            if(descripcion.equals("")){
                bdescripcion=false;
            } else{
                bdescripcion=true;
            }
            if(precio.equals("")){
                bprecio=false;
            } else{
                bprecio=true;
            }
            if(costo.equals("")){
                bcosto=false;
            } else{
                bcosto=true;
            }
            if(bnombre && bdescripcion && bprecio && bcosto){
                String URL2 = "http://192.168.1.76/vocafe12/checarNombreProducto.php?NombreP="+nombreProducto;
                checarNombreProducto(URL2);
            } else{
                AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                alerta.setMessage("¡Llena todos los campos!")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Error");
                titulo.show();
            }
        } else if(id==R.id.btnProductosMenuEmpleados){
            Intent intent = new Intent(this,MenuEmpleados.class);
            intent.putExtra("correoEE",correoEE);
            startActivity(intent);
        }
    }
    private void checarNombreProducto(String URL2){
        final ProgressDialog loading = ProgressDialog.show(this,"Checando el nombre del producto...","Espere por favor");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL2,
                null,
                response -> {
                    loading.dismiss();
                    String nombre = editTextnombreProducto.getText().toString().trim();
                    try{
                        nombreP = response.getString("NombreP");
                        if(nombreP.equals(nombre)){
                            AlertDialog.Builder alertaCorreo = new AlertDialog.Builder(this);
                            alertaCorreo.setMessage("¡Este producto ya existe!")
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
                    loading.dismiss();
                    uploadImage();
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this,MenuEmpleados.class);
        intent.putExtra("correoEE",correoEE);
        startActivity(intent);
    }
}