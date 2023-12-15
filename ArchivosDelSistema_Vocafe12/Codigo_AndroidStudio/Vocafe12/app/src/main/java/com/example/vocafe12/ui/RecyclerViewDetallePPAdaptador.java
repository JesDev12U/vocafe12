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

public class RecyclerViewDetallePPAdaptador extends RecyclerView.Adapter<RecyclerViewDetallePPAdaptador.DetallePPViewHolder> {
    private Context mCtx;
    private List<DetallePP> detallesPP;
    public RecyclerViewDetallePPAdaptador(Context mCtx,List<DetallePP>detallesPP){
        this.mCtx = mCtx;
        this.detallesPP = detallesPP;
    }
    @NonNull
    @Override
    public RecyclerViewDetallePPAdaptador.DetallePPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_detallepp,null);
        return new DetallePPViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewDetallePPAdaptador.DetallePPViewHolder holder, int position) {
        DetallePP detallePP = detallesPP.get(position);
        holder.txtNomPDPP.setText(detallePP.getNombreProducto());
        holder.txtCantidadDPP.setText(detallePP.getCantidad());
        holder.txtImporteDPP.setText(detallePP.getImporte());
        holder.txtDetallesDPP.setText(detallePP.getDetalles());
        Glide.with(mCtx)
                .load(detallePP.getFoto())
                .into(holder.imgPDPP);
    }

    @Override
    public int getItemCount() { return detallesPP.size(); }
    class DetallePPViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomPDPP,txtCantidadDPP,txtImporteDPP,txtDetallesDPP;
        ImageView imgPDPP;
        public DetallePPViewHolder(View itemView){
            super(itemView);
            txtNomPDPP = itemView.findViewById(R.id.txtNomPDPP);
            txtCantidadDPP = itemView.findViewById(R.id.txtCantidadDPP);
            txtImporteDPP = itemView.findViewById(R.id.txtImporteDPP);
            txtDetallesDPP = itemView.findViewById(R.id.txtDetallesDPP);
            imgPDPP = itemView.findViewById(R.id.imgPDPP);
        }
    }
}
