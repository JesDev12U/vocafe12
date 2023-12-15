package com.example.vocafe12.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vocafe12.R;

import java.util.List;

public class RecyclerViewVentasAdaptador extends RecyclerView.Adapter<RecyclerViewVentasAdaptador.VentasViewHolder> {
    private Context mCtx;
    private List<VPedidos> vPedidosList;
    RecyclerViewVentasAdaptador.OnItemClickListener listener;
    public interface OnItemClickListener{
        void OnItemClick(VPedidos item);
    }
    public RecyclerViewVentasAdaptador(Context mCtx,List<VPedidos> vPedidosList,RecyclerViewVentasAdaptador.OnItemClickListener listener){
        this.mCtx = mCtx;
        this.vPedidosList = vPedidosList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public RecyclerViewVentasAdaptador.VentasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_ventaspedidos,null);
        return new VentasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewVentasAdaptador.VentasViewHolder holder, int position) {
        VPedidos vPedidos = vPedidosList.get(position);
        holder.txtCodigoPVentas.setText(vPedidos.getCodigoP());
        holder.txtIdClienteVentas.setText(vPedidos.getIdCliente());
        holder.txtFechaVenta.setText(vPedidos.getFecha());
        holder.txtHoraVenta.setText(vPedidos.getHora());
        holder.binDataVentas(vPedidosList.get(position));
    }

    @Override
    public int getItemCount() {
        return vPedidosList.size();
    }
    class VentasViewHolder extends RecyclerView.ViewHolder{
        TextView txtCodigoPVentas,txtIdClienteVentas,txtFechaVenta,txtHoraVenta;
        public VentasViewHolder(View itemView){
            super(itemView);
            txtCodigoPVentas = itemView.findViewById(R.id.txtCodigoPVentas);
            txtIdClienteVentas = itemView.findViewById(R.id.txtIdClienteVentas);
            txtFechaVenta = itemView.findViewById(R.id.txtFechaVenta);
            txtHoraVenta = itemView.findViewById(R.id.txtHoraVenta);
        }
        void binDataVentas(final VPedidos item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(item);
                }
            });
        }
    }
}
