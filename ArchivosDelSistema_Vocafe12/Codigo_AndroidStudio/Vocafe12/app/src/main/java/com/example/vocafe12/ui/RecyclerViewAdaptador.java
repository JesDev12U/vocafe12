package com.example.vocafe12.ui;

import android.content.Context;
import android.renderscript.RenderScript;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.example.vocafe12.R;

import java.util.List;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ProductosViewHolder>{
    private Context mCtx;
    private List<Producto> productos;
    public RecyclerViewAdaptador(Context mCtx, List<Producto> productos){
        this.mCtx = mCtx;
        this.productos = productos;
    }
    class ProductosViewHolder extends RecyclerView.ViewHolder{
        TextView vnombreP,vdescripcionP,vprecioP,vcostoP,vcategoria;
        ImageView imagenProductoVisualizacion;
        public ProductosViewHolder(View itemView){
            super(itemView);
            vnombreP = itemView.findViewById(R.id.vnombreP);
            vdescripcionP = itemView.findViewById(R.id.vdescripcionp);
            vprecioP = itemView.findViewById(R.id.vprecio);
            vcostoP = itemView.findViewById(R.id.vcosto);
            vcategoria = itemView.findViewById(R.id.vcategoria);
            imagenProductoVisualizacion = itemView.findViewById(R.id.imagenProductoVisualizacion);
        }
    }
    @NonNull
    @Override
    public RecyclerViewAdaptador.ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_productos,null);
        return new ProductosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdaptador.ProductosViewHolder holder, int position) {
        Producto producto = productos.get(position);
        holder.vnombreP.setText(producto.getNombre());
        holder.vdescripcionP.setText(producto.getDescripcion());
        holder.vprecioP.setText(producto.getPrecio());
        holder.vcostoP.setText(producto.getCosto());
        holder.vcategoria.setText(producto.getCategoria());
        Glide.with(mCtx)
                .load(producto.getFoto())
                .into(holder.imagenProductoVisualizacion);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

}
