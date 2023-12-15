package com.example.vocafe12.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vocafe12.R;

import java.util.List;

public class RecyclerViewPedidoAdaptador extends RecyclerView.Adapter<RecyclerViewPedidoAdaptador.PedidoViewHolder> {
    private Context mCtx;
    private List<ComidaPedido> pedidos;
    RecyclerViewPedidoAdaptador.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(ComidaPedido item);
    }

    public RecyclerViewPedidoAdaptador(Context mCtx,List<ComidaPedido> pedidos,RecyclerViewPedidoAdaptador.OnItemClickListener listener){
        this.mCtx = mCtx;
        this.pedidos = pedidos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewPedidoAdaptador.PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_comidapedido,null);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewPedidoAdaptador.PedidoViewHolder holder, int position) {
        ComidaPedido comidaPedido = pedidos.get(position);
        holder.nombreComidaPedido.setText(comidaPedido.getNombreP());
        holder.precioComidaPedido.setText(comidaPedido.getPrecioP());
        Glide.with(mCtx)
                .load(comidaPedido.getFotoP())
                .into(holder.imgComidaPedido);
        holder.binData(pedidos.get(position));
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }
    class PedidoViewHolder extends RecyclerView.ViewHolder{
        TextView nombreComidaPedido,precioComidaPedido;
        ImageView imgComidaPedido;
        public PedidoViewHolder(View itemview){
            super(itemview);
            nombreComidaPedido = itemview.findViewById(R.id.nombreComidaPedido);
            precioComidaPedido = itemview.findViewById(R.id.precioComidaPedido);
            imgComidaPedido = itemview.findViewById(R.id.imgComidaPedido);
        }
        void binData(final ComidaPedido item){
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    listener.onItemClick(item);
                }
            });
        }
    }
}
