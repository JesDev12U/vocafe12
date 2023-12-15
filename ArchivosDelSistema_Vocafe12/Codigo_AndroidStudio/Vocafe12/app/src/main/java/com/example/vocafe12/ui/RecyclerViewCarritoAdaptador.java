package com.example.vocafe12.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vocafe12.R;

import java.util.List;

public class RecyclerViewCarritoAdaptador extends RecyclerView.Adapter<RecyclerViewCarritoAdaptador.CarritoViewHolder>{
    private Context mCtx;
    private List<Carrito> carrito;
    RecyclerViewCarritoAdaptador.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(Carrito item);
    }

    public RecyclerViewCarritoAdaptador(Context mCtx, List<Carrito> carrito, RecyclerViewCarritoAdaptador.OnItemClickListener listener) {
        this.mCtx = mCtx;
        this.carrito = carrito;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewCarritoAdaptador.CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_carrito,null);
        return new CarritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCarritoAdaptador.CarritoViewHolder holder, int position) {
        Carrito car = carrito.get(position);
        holder.txtNombreP.setText(car.getNombreProducto());
        holder.txtDetalles.setText(car.getDetalles());
        holder.txtCantidad.setText(car.getCantidad());
        holder.txtTotalP.setText(car.getTotal());
        Glide.with(mCtx)
                .load(car.getFoto())
                .into(holder.imgProductoCarrito);
        holder.binDataCarrito(carrito.get(position));
    }

    @Override
    public int getItemCount() {
        return carrito.size();
    }
    class CarritoViewHolder extends RecyclerView.ViewHolder{
        TextView txtNombreP,txtDetalles,txtCantidad,txtTotalP;
        ImageView imgProductoCarrito;
        public CarritoViewHolder(View itemView){
            super(itemView);
            txtNombreP = itemView.findViewById(R.id.txtNomP);
            txtDetalles = itemView.findViewById(R.id.txtDetalles);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
            txtTotalP = itemView.findViewById(R.id.txtTotalP);
            imgProductoCarrito = itemView.findViewById(R.id.imgProductoCarrito);
        }
        void binDataCarrito(final Carrito item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
