package com.example.vocafe12;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vocafe12.ui.ComidaPedido;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class DescripcionActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imagenComidaPedida;
    TextView nombreProductoPedido,txtContador,txtTotal;
    Button btnMas,btnMenos,btnAgregarCarrito,btnAgregarDetalles;
    int contador;
    String nombreP;
    String correoE;
    String idcliente;
    String detallesString;
    String foto;
    RequestQueue requestQueue;
    double precio;
    double total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);
        ComidaPedido elemento = (ComidaPedido) getIntent().getSerializableExtra("ComidaPedido");
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoE = extras.getString("correoE");
        }
        inicializar();
        nombreProductoPedido.setText(elemento.getNombreP());
        nombreP = elemento.getNombreP();
        foto = elemento.getFotoP();
        Picasso.get()
                .load(elemento.getFotoP())
                .error(R.mipmap.ic_launcher_round)
                .into(imagenComidaPedida);
        btnMas.setOnClickListener(this);
        btnMenos.setOnClickListener(this);
        btnAgregarCarrito.setOnClickListener(this);
        btnAgregarDetalles.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
        contador = 2;
        traerPrecio();
        calcularTotal();
        comprobarCategoria();
        obtenerIdCliente();
    }
    private void inicializar(){
        imagenComidaPedida = findViewById(R.id.imagenComidaPedida);
        nombreProductoPedido = findViewById(R.id.nombreProductoPedido);
        btnMas = findViewById(R.id.btnMas);
        btnMenos = findViewById(R.id.btnMenos);
        btnAgregarCarrito = findViewById(R.id.btnAgregarCarrito);
        btnAgregarDetalles = findViewById(R.id.btnAgregarDetalles);
        txtContador = findViewById(R.id.txtContador);
        txtTotal = findViewById(R.id.txtTotal);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.btnMas){
            if(contador>0){
                btnMenos.setEnabled(true);
            }
            contador++;
            txtContador.setText(String.valueOf(contador));
            calcularTotal();
        } else if(id==R.id.btnMenos){
           if(contador>1){
               contador--;
           }
           if(contador==1){
               btnMenos.setEnabled(false);
           }else{
               btnMenos.setEnabled(true);
           }
            txtContador.setText(String.valueOf(contador));
           calcularTotal();
        } else if(id==R.id.btnAgregarDetalles){
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setCancelable(false);
            alerta.setTitle("¿Cómo quieres que te preparen este guisado?");
            final EditText detalles = new EditText(this);
            detalles.setInputType(InputType.TYPE_CLASS_TEXT);
            alerta.setView(detalles);
            alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    detallesString = detalles.getText().toString();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(DescripcionActivity.this);
                    alerta.setMessage("Los detalles se han registrado, para ser guardados, agrega el producto al carrito")
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
                }
            });
            alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alerta.show();
        } else if(id==R.id.btnAgregarCarrito){
            if(detallesString==null){
                detallesString = "-";
            }
            guardarAlCarrito();
        }
    }
    private void traerPrecio(){
        final ProgressDialog loading = ProgressDialog.show(this,"Abriendo los detalles del producto...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/traerPrecioProducto.php?NombreP="+nombreP;
        @SuppressLint("SetTextI18n") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    loading.dismiss();
                    String precioString;
                    double total;
                    try{
                        precioString = response.getString("Precio");
                        precio = Double.parseDouble(precioString);
                        total = contador * precio;
                        txtTotal.setText(String.valueOf(total));
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
                                    Intent intent = new Intent(DescripcionActivity.this,ListaCarrito.class);
                                    intent.putExtra("correE",correoE);
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
    @SuppressLint("SetTextI18n")
    private void calcularTotal(){
        total = contador * precio;
        txtTotal.setText(String.valueOf(total));
    }
    private void comprobarCategoria(){
        String URL = "http://192.168.1.76/vocafe12/traerPrecioProducto.php?NombreP="+nombreP;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    String categoria;
                    try{
                        categoria = response.getString("Categoria");
                        //Toast.makeText(this,categoria,Toast.LENGTH_SHORT).show();
                        if(categoria.equals("Guisado")){
                            btnAgregarDetalles.setEnabled(true);
                        } else{
                            btnAgregarDetalles.setEnabled(false);
                            detallesString = "-";
                        }
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    public void obtenerIdCliente(){
        String URL = "http://192.168.1.76/vocafe12/checarCorreoCliente.php?CorreoE="+correoE;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    try {
                        idcliente = response.getString("IdCliente");
                        //Toast.makeText(this,idcliente,Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    public void guardarAlCarrito(){
        final ProgressDialog loading = ProgressDialog.show(this,"Agregando al carrito","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/agregarProductoCarrito.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    loading.dismiss();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("El producto se ha añadido al carrito")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(DescripcionActivity.this,ListaCarrito.class);
                                    intent.putExtra("correoE",correoE);
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
                Map<String,String> params = new Hashtable<>();
                params.put("IdCliente",idcliente);
                params.put("NombreProducto",nombreP);
                params.put("Detalles",detallesString);
                params.put("Cantidad",String.valueOf(contador));
                params.put("Total",txtTotal.getText().toString());
                params.put("Foto",foto);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}