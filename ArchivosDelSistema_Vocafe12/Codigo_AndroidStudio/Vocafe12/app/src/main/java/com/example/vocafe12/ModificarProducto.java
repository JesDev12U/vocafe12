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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class ModificarProducto extends AppCompatActivity implements View.OnClickListener{
    EditText editTextNombreProductoEdit,editTextDescripcionEdit,editTextPrecioEdit,editTextCostoEdit;
    Button btnSubirFotoEdit,btnModificarProducto,btnBuscarOtroProducto;
    ImageView imagenProductoEdit;
    String nombreProducto;
    String correoEE;
    String idProducto;
    RequestQueue requestQueue;
    Bitmap bitmap;
    boolean bnombre,bdescripcion,bprecio,bcosto;
    private Spinner categorias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_producto);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            nombreProducto = extras.getString("nombreProducto");
            correoEE = extras.getString("correoEE");
        }
        inicializar();
        requestQueue = Volley.newRequestQueue(this);
        btnSubirFotoEdit.setOnClickListener(this);
        btnModificarProducto.setOnClickListener(this);
        btnBuscarOtroProducto.setOnClickListener(this);
        String [] opcionesCategorias = {"Guisado","Producto"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,opcionesCategorias);
        categorias.setAdapter(adapter);
        buscarProducto();
        uploadImageInicio();
    }
    private void inicializar(){
        editTextNombreProductoEdit = findViewById(R.id.editTextNombreProductoEdit);
        editTextDescripcionEdit = findViewById(R.id.editTextDescripcionEdit);
        editTextPrecioEdit = findViewById(R.id.editTextPrecioEdit);
        editTextCostoEdit = findViewById(R.id.editTextCostoEdit);
        btnSubirFotoEdit = findViewById(R.id.btnSubirFotoEdit);
        btnModificarProducto = findViewById(R.id.btnModificarProducto);
        btnBuscarOtroProducto = findViewById(R.id.btnBuscarOtroProducto);
        imagenProductoEdit = findViewById(R.id.imagenProductoEdit);
        categorias = findViewById(R.id.spinnerCategoriasMod);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.btnSubirFotoEdit){
            showFileChooser();
        } else if(id==R.id.btnModificarProducto){
            String nombre = editTextNombreProductoEdit.getText().toString().trim();
            String descripcion = editTextDescripcionEdit.getText().toString().trim();
            String precio = editTextPrecioEdit.getText().toString().trim();
            String costo = editTextCostoEdit.getText().toString().trim();
            if(nombre.equals("")){
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
                uploadImage();
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
        } else if(id==R.id.btnBuscarOtroProducto){
            Intent intent = new Intent(this,BuscarProducto.class);
            intent.putExtra("correoEE",correoEE);
            startActivity(intent);
        }
    }
    private void buscarProducto(){
        String URL = "http://192.168.1.76/vocafe12/buscarProducto.php?NombreP="+nombreProducto;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    String nombre,descripcion,precio,costo;
                    try{
                        nombre=response.getString("NombreP");
                        descripcion = response.getString("Descripcion");
                        precio = response.getString("Precio");
                        costo = response.getString("Costo");
                        editTextNombreProductoEdit.setText(nombre);
                        editTextDescripcionEdit.setText(descripcion);
                        editTextPrecioEdit.setText(precio);
                        editTextCostoEdit.setText(costo);
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                    try{
                        ImageRequest imageRequest = new ImageRequest(
                                response.getString("Foto"),
                                response1 -> imagenProductoEdit.setImageBitmap(response1),
                                0,
                                0,
                                null,
                                null,
                                error -> {

                                }
                        );
                        requestQueue.add(imageRequest);
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    public void obtenerId(){
        final ProgressDialog loading = ProgressDialog.show(this,"Buscando el ID del producto...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/buscarProducto.php?NombreP="+nombreProducto;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    loading.dismiss();
                    try {
                        idProducto = response.getString("IdProducto");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    loading.dismiss();
                }
        );
        requestQueue.add(jsonObjectRequest);
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
            return "Error con la imagen";
        }
    }
    private void modificarProductoSinFoto(){
        String URL = "http://192.168.1.76/vocafe12/modificarProductoSinFoto.php";
        final ProgressDialog loading = ProgressDialog.show(this,"Modificando...","Espere por favor");
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    loading.dismiss();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("Producto modificado!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(ModificarProducto.this,BuscarProducto.class);
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
                Map<String,String> params = new HashMap<>();
                String nombreProducto = editTextNombreProductoEdit.getText().toString().trim();
                String descripcion = editTextDescripcionEdit.getText().toString().trim();
                String precio = editTextPrecioEdit.getText().toString().trim();
                String costo = editTextCostoEdit.getText().toString().trim();
                params.put("IdProducto",idProducto);
                params.put("NombreP",nombreProducto);
                params.put("Descripcion",descripcion);
                params.put("Precio",precio);
                params.put("Costo",costo);
                params.put("Categoria",categorias.getSelectedItem().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void uploadImage(){
        obtenerId();
        String URL = "http://192.168.1.76/vocafe12/modificarProducto.php";
        final ProgressDialog loading = ProgressDialog.show(this,"Modificando...","Espere por favor");
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    loading.dismiss();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("Producto modificado!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(ModificarProducto.this,BuscarProducto.class);
                                    intent.putExtra("correoEE",correoEE);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Aviso");
                    titulo.show();
                    //Toast.makeText(this,response, Toast.LENGTH_SHORT).show();
                },
                error -> {
                    loading.dismiss();
                    modificarProductoSinFoto();
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String imagen=getStringImagen(bitmap);
                String nombreProducto = editTextNombreProductoEdit.getText().toString().trim();
                String descripcion = editTextDescripcionEdit.getText().toString().trim();
                String precio = editTextPrecioEdit.getText().toString().trim();
                String costo = editTextCostoEdit.getText().toString().trim();
                Map<String,String> params = new Hashtable<>();
                params.put("IdProducto",idProducto);
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

    public void uploadImageInicio(){
        obtenerId();
        String URL = "http://192.168.1.76/vocafe12/modificarProducto.php";
        final ProgressDialog loading = ProgressDialog.show(this,"Modificando...","Espere por favor");
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    loading.dismiss();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("Producto modificado!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Aviso");
                    titulo.show();
                    //Toast.makeText(this,response, Toast.LENGTH_SHORT).show();
                },
                error -> {
                    loading.dismiss();

                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String imagen=getStringImagen(bitmap);
                String nombreProducto = editTextNombreProductoEdit.getText().toString().trim();
                String descripcion = editTextDescripcionEdit.getText().toString().trim();
                String precio = editTextPrecioEdit.getText().toString().trim();
                String costo = editTextCostoEdit.getText().toString().trim();
                Map<String,String> params = new Hashtable<>();
                params.put("IdProducto",idProducto);
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
                imagenProductoEdit.setImageBitmap(bitmap);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ModificarProducto.this,BuscarProducto.class);
        intent.putExtra("correoEE",correoEE);
        startActivity(intent);
    }
}