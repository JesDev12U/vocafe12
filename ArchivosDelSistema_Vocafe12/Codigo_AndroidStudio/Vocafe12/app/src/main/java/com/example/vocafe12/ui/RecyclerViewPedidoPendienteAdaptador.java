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

public class RecyclerViewPedidoPendienteAdaptador extends RecyclerView.Adapter<RecyclerViewPedidoPendienteAdaptador.PedidoPendienteViewHolder>{
    private Context mCtx;
    private List<PedidoPendiente> pedidosPendientes;
    RecyclerViewPedidoPendienteAdaptador.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(PedidoPendiente item);
    }
    public RecyclerViewPedidoPendienteAdaptador(Context mCtx, List<PedidoPendiente> pedidosPendientes,RecyclerViewPedidoPendienteAdaptador.OnItemClickListener listener){
        this.mCtx = mCtx;
        this.pedidosPendientes = pedidosPendientes;
        this.listener = listener;
    }
    @NonNull
    @Override
    public RecyclerViewPedidoPendienteAdaptador.PedidoPendienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_pedidospendientes,null);
        return new PedidoPendienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewPedidoPendienteAdaptador.PedidoPendienteViewHolder holder, int position) {
        PedidoPendiente pp = pedidosPendientes.get(position);
        holder.txtCodigoPedidoPendiente.setText(pp.getCodigoP());
        holder.txtFechaPP.setText(pp.getFecha());
        holder.txtHoraPP.setText(pp.getHora());
        holder.txtStatusPP.setText(pp.getStatusPedido());
        holder.binDataPedidoPendiente(pp);
    }

    @Override
    public int getItemCount() {
        return pedidosPendientes.size();
    }
    class PedidoPendienteViewHolder extends RecyclerView.ViewHolder{
        TextView txtCodigoPedidoPendiente,txtFechaPP,txtHoraPP,txtStatusPP;
        public PedidoPendienteViewHolder(View itemView){
            super(itemView);
            txtCodigoPedidoPendiente = itemView.findViewById(R.id.txtCodigoPedidoPendiente);
            txtFechaPP = itemView.findViewById(R.id.txtFechaPP);
            txtHoraPP = itemView.findViewById(R.id.txtHoraPP);
            txtStatusPP = itemView.findViewById(R.id.txtStatusPP);
        }
        void binDataPedidoPendiente(final PedidoPendiente item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { listener.onItemClick(item); }
            });
        }
    }
}
