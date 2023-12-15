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

public class RecyclerViewMenuAdaptador extends RecyclerView.Adapter<RecyclerViewMenuAdaptador.MenuViewHolder> {
    private Context mCtx;
    private List<Menu> menuC;
    public RecyclerViewMenuAdaptador(Context mCtx,List<Menu> menuC){
        this.mCtx = mCtx;
        this.menuC = menuC;
    }

    @NonNull
    @Override
    public RecyclerViewMenuAdaptador.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_menucomida,null);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu menu = menuC.get(position);
        holder.mnombre.setText(menu.getNombreM());
        holder.mdescripcion.setText(menu.getDescripcionM());
        holder.mprecio.setText(menu.getPrecioM());
        Glide.with(mCtx)
                .load(menu.getFotoM())
                .into(holder.imgComida);
    }

    @Override
    public int getItemCount() {
        return menuC.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder{
        TextView mnombre,mdescripcion,mprecio;
        ImageView imgComida;
        public MenuViewHolder(View itemView){
            super(itemView);
            mnombre = itemView.findViewById(R.id.mnombre);
            mdescripcion = itemView.findViewById(R.id.mdescripcion);
            mprecio = itemView.findViewById(R.id.mprecio);
            imgComida = itemView.findViewById(R.id.imgComida);
        }
    }

}
