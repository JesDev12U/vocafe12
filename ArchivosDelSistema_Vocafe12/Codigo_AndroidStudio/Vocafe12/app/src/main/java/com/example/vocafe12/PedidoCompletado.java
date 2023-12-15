package com.example.vocafe12;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class PedidoCompletado extends AppCompatActivity implements View.OnClickListener{
    TextView txtCodigoPedido;
    Button btnHecho;
    String codigoPedido,correoE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_completado);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            codigoPedido = extras.getString("codigoPedido");
            correoE = extras.getString("correoE");
        }
        inicializar();
        btnHecho.setOnClickListener(this);
        txtCodigoPedido.setText(codigoPedido);
    }
    private void inicializar(){
        txtCodigoPedido = findViewById(R.id.txtCodigoPedido);
        btnHecho = findViewById(R.id.btnHecho);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.btnHecho){
            Intent intent = new Intent(this,MenuClientes.class);
            intent.putExtra("correoE",correoE);
            startActivity(intent);
        }
    }
}