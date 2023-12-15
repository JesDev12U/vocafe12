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

public class RecyclerViewDetallesVentaAdaptador extends RecyclerView.Adapter<RecyclerViewDetallesVentaAdaptador.DetallesVentaViewHolder> {
    private Context mCtx;
    private List<DetallesVenta> detallesVentaList;
    public RecyclerViewDetallesVentaAdaptador(Context mCtx,List<DetallesVenta> detallesVentaList){
        this.mCtx = mCtx;
        this.detallesVentaList = detallesVentaList;
    }

    @NonNull
    @Override
    public RecyclerViewDetallesVentaAdaptador.DetallesVentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_detalle_pedidos_venta,null);
        return new DetallesVentaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewDetallesVentaAdaptador.DetallesVentaViewHolder holder, int position) {
        DetallesVenta detallesVenta = detallesVentaList.get(position);
        holder.txtNomPDetalleVenta.setText(detallesVenta.getNombreProductoV());
        holder.txtCantidadDetalleVenta.setText(detallesVenta.getCantidadV());
        holder.txtImporteDetalleVenta.setText(detallesVenta.getImporteV());
        holder.txtDetallesDetalleVenta.setText(detallesVenta.getDetallesV());
        Glide.with(mCtx)
                .load(detallesVenta.getFotoV())
                .into(holder.imgPDetalleVenta);
    }

    @Override
    public int getItemCount() {
        return detallesVentaList.size();
    }
    class DetallesVentaViewHolder extends RecyclerView.ViewHolder{
        TextView txtNomPDetalleVenta,txtCantidadDetalleVenta,txtImporteDetalleVenta,txtDetallesDetalleVenta;
        ImageView imgPDetalleVenta;
        public DetallesVentaViewHolder(View itemView){
            super(itemView);
            txtNomPDetalleVenta = itemView.findViewById(R.id.txtNomPDetalleVenta);
            txtCantidadDetalleVenta = itemView.findViewById(R.id.txtCantidadDetalleVenta);
            txtImporteDetalleVenta = itemView.findViewById(R.id.txtImporteDetalleVenta);
            txtDetallesDetalleVenta = itemView.findViewById(R.id.txtDetallesDetalleVenta);
            imgPDetalleVenta = itemView.findViewById(R.id.imgPDetalleVenta);
        }
    }
}
