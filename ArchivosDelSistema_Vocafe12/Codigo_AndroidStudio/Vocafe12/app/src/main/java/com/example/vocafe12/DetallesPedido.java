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
import com.example.vocafe12.ui.Carrito;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class DetallesPedido extends AppCompatActivity implements View.OnClickListener{
    RequestQueue requestQueue;
    ImageView imgProductoDetalles;
    TextView txtNomPDetalles,txtContadorDetalles,txtTotalDetalles;
    Button btnMasDetalles,btnMenosDetalles,btnModificarPD,btnModificarDetallesP,btnEliminarPDetalles;
    String correoE;
    String nombreP;
    String detallesString;
    String idcliente;
    int contador;
    double precio;
    double total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pedido);
        inicializar();
        Carrito elemento = (Carrito) getIntent().getSerializableExtra("Carrito");
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoE = extras.getString("correoE");
        }
        txtNomPDetalles.setText(elemento.getNombreProducto());
        txtContadorDetalles.setText(elemento.getCantidad());
        txtTotalDetalles.setText(elemento.getTotal());
        Picasso.get()
                .load(elemento.getFoto())
                .error(R.mipmap.ic_launcher_round)
                .into(imgProductoDetalles);
        btnMasDetalles.setOnClickListener(this);
        btnMenosDetalles.setOnClickListener(this);
        btnModificarPD.setOnClickListener(this);
        btnModificarDetallesP.setOnClickListener(this);
        btnEliminarPDetalles.setOnClickListener(this);
        contador = Integer.parseInt(elemento.getCantidad());
        nombreP= elemento.getNombreProducto();
        detallesString = elemento.getDetalles();
        requestQueue = Volley.newRequestQueue(this);
        traerPrecio();
        calcularTotal();
        comprobarCategoria();
        obtenerIdCliente();
        inicioTotal();
    }
    private void inicializar(){
        imgProductoDetalles = findViewById(R.id.imgProductoDetalles);
        txtNomPDetalles = findViewById(R.id.txtNomPDetalles);
        txtContadorDetalles = findViewById(R.id.txtContadorDetalles);
        txtTotalDetalles = findViewById(R.id.txtTotalDetalles);
        btnMasDetalles = findViewById(R.id.btnMasDetalles);
        btnMenosDetalles = findViewById(R.id.btnMenosDetalles);
        btnModificarPD = findViewById(R.id.btnModificarPD);
        btnModificarDetallesP = findViewById(R.id.btnModificarDetallesP);
        btnEliminarPDetalles = findViewById(R.id.btnEliminarPDetalles);
    }
    private void inicioTotal(){
        if(contador>0){
            btnMenosDetalles.setEnabled(true);
        }
        if(contador==1){
            btnMenosDetalles.setEnabled(false);
        }else{
            btnMenosDetalles.setEnabled(true);
        }
        calcularTotal();
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.btnMasDetalles){
            if(contador>0){
                btnMenosDetalles.setEnabled(true);
            }
            contador++;
            txtContadorDetalles.setText(String.valueOf(contador));
            calcularTotal();
        } else if(id == R.id.btnMenosDetalles){
            if(contador>1){
                contador--;
            }
            if(contador==1){
                btnMenosDetalles.setEnabled(false);
            }else{
                btnMenosDetalles.setEnabled(true);
            }
            txtContadorDetalles.setText(String.valueOf(contador));
            calcularTotal();
        } else if(id==R.id.btnModificarPD){
            modificar();
        } else if(id==R.id.btnModificarDetallesP){
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
                    AlertDialog.Builder alerta = new AlertDialog.Builder(DetallesPedido.this);
                    alerta.setMessage("Los detalles se han registrado, para ser guardados, modifica el pedido")
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
        } else if(id==R.id.btnEliminarPDetalles){
            eliminar();
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
                        txtTotalDetalles.setText(String.valueOf(total));
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
                                    Intent intent = new Intent(DetallesPedido.this,MenuClientes.class);
                                    intent.putExtra("correoE",correoE);
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
    private void calcularTotal(){
        total = contador * precio;
        txtTotalDetalles.setText(String.valueOf(total));
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
                            btnModificarDetallesP.setEnabled(true);
                        } else{
                            btnModificarDetallesP.setEnabled(false);
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
    private void modificar(){
        final ProgressDialog loading = ProgressDialog.show(this,"Modificando el producto...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/modificarPedido.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    loading.dismiss();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("Pedido actualizado")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                   Intent intent = new Intent(DetallesPedido.this,ListaCarrito.class);
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
                                    Intent intent = new Intent(DetallesPedido.this,ListaCarrito.class);
                                    intent.putExtra("correoE",correoE);
                                    startActivity(intent);
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
                String idCliente = idcliente;
                String nombreProducto = nombreP;
                String detalles = detallesString;
                String cantidad = String.valueOf(contador);
                String totalP = txtTotalDetalles.getText().toString();
                params.put("IdCliente",idCliente);
                params.put("NombreProducto",nombreProducto);
                params.put("Detalles",detalles);
                params.put("Cantidad",cantidad);
                params.put("Total",totalP);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void eliminar(){
        final ProgressDialog loading = ProgressDialog.show(this,"Eliminando el producto...","Espere por favor");
        String URL = "http://192.168.1.76/vocafe12/eliminarPedido.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    loading.dismiss();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setMessage("Producto eliminado")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(DetallesPedido.this,ListaCarrito.class);
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
                                    Intent intent = new Intent(DetallesPedido.this,ListaCarrito.class);
                                    intent.putExtra("correoE",correoE);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Error");
                    titulo.show();
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("IdCliente",idcliente);
                params.put("NombreProducto",nombreP);
                params.put("Detalles",detallesString);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void onBackPressed(){
        Intent intent = new Intent(this,ListaCarrito.class);
        intent.putExtra("correoE",correoE);
        startActivity(intent);
    }
}