package com.example.gestionvehiculos.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionvehiculos.Activities.FormularioActivity;
import com.example.gestionvehiculos.R;
import com.example.gestionvehiculos.clases.AutoNuevo;
import com.example.gestionvehiculos.clases.Moto;
import com.example.gestionvehiculos.clases.Vehiculo;
import com.example.gestionvehiculos.clases.MotoModificada;
import com.example.gestionvehiculos.clases.VehiculoHolder;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.VehiculoViewHolder> {
    private List<Vehiculo> vehiculos;
    private Context context;
    private OnItemCrudListener listener;

    public interface OnItemCrudListener {
        void onDeleteClick(Vehiculo vehiculo, int position);
        void onEditClick(Vehiculo vehiculo, int position);
    }

    public RecyclerViewAdapter(List<Vehiculo> vehiculos, Context context, OnItemCrudListener listener) {
        this.vehiculos = vehiculos;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VehiculoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_crud, parent, false);
        return new VehiculoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehiculoViewHolder holder, int position) {
        Vehiculo vehiculo = vehiculos.get(position);
        holder.bind(vehiculo);

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(vehiculo, holder.getAdapterPosition());
            }
        });

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, FormularioActivity.class);
            intent.putExtra("vehiculoEditar", vehiculos.get(position));
            intent.putExtra("posicion", position);
            listener.onEditClick(vehiculo, position);
        });
    }
    public void removerVehiculo(int position) {
        vehiculos.remove(position);
        VehiculoHolder.setListaVehiculos(vehiculos);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return vehiculos.size();
    }

    public static class VehiculoViewHolder extends RecyclerView.ViewHolder {

        TextView modelo, id, marca, ano, precioBase, kilometraje, garantia, descuento, cilindrada, tipoManejo, tipoModificacion;
        TextView esperanza, dato1, dato2;
        ImageButton btnEdit, btnDelete;
        LinearLayout linearAutoNuevo, linearMoto, linearMotoModificada;
        public VehiculoViewHolder(@NonNull View itemView) {
            super(itemView);
            modelo = itemView.findViewById(R.id.tvModelo);
            id = itemView.findViewById(R.id.tvId);
            marca = itemView.findViewById(R.id.tvMarca);
            ano = itemView.findViewById(R.id.tvAno);
            precioBase = itemView.findViewById(R.id.tvPrecioBase);
            kilometraje = itemView.findViewById(R.id.tvKilometraje);
            garantia = itemView.findViewById(R.id.tvGarantia);
            descuento = itemView.findViewById(R.id.tvDescuento);
            cilindrada = itemView.findViewById(R.id.tvCilindrada);
            tipoManejo = itemView.findViewById(R.id.tvTipoManejo);
            tipoModificacion = itemView.findViewById(R.id.tvTipoModificacion);
            esperanza = itemView.findViewById(R.id.tvEsperanzaVida);
            dato1 = itemView.findViewById(R.id.tvDato1);
            dato2 = itemView.findViewById(R.id.tvDato2);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            linearAutoNuevo = itemView.findViewById(R.id.linearAutoNuevo);
            linearMoto = itemView.findViewById(R.id.linearMoto);
            linearMotoModificada = itemView.findViewById(R.id.linearMotoModificada);
        }

        public void bind(Vehiculo vehiculo) {
            modelo.setText(vehiculo.getModelo());
            id.setText(String.valueOf("#" + vehiculo.getId()));
            marca.setText("Marca: " + vehiculo.getMarca());
            ano.setText(String.valueOf("Año de fabricación: " + vehiculo.getAnoFabricacion()));
            precioBase.setText(String.valueOf("Precio base: " + vehiculo.getPrecioBase()));
            kilometraje.setText(String.valueOf("Kilometraje: " + vehiculo.getKilometraje()));
            esperanza.setText(String.valueOf("Esperanza de vida: " + vehiculo.getEsperanzaVida()));

            // Determinar el tipo de vehiculo y mostrar información específica
            if (vehiculo instanceof AutoNuevo) {
                linearAutoNuevo.setVisibility(View.VISIBLE);
                linearMoto.setVisibility(View.GONE);
                linearMotoModificada.setVisibility(View.GONE);
                dato1.setText("Consumo de combustible: " + vehiculo.getPotencia());
                dato2.setText("Potencia de motor: " + vehiculo.getModificacion());
                garantia.setText(String.valueOf("Garantía en años: " + ((AutoNuevo) vehiculo).getGarantiaAnos()));
                descuento.setText(String.valueOf("Descuento promocional: " + ((AutoNuevo) vehiculo).getDescuentoPromocional()));
            } else if (vehiculo instanceof MotoModificada) {
                linearMoto.setVisibility(View.VISIBLE);
                linearAutoNuevo.setVisibility(View.GONE);
                cilindrada.setText(String.valueOf("Cilindrada Cc: " + ((Moto) vehiculo).getCilindradaCc()));
                tipoManejo.setText(String.valueOf("Tipo de manejo: " + ((Moto) vehiculo).getTipoManejo()));
                linearMotoModificada.setVisibility(View.VISIBLE);
                dato1.setText(vehiculo.getPotencia());
                dato2.setText(vehiculo.getModificacion());
                tipoModificacion.setText("Tipo de modificación: " + ((MotoModificada) vehiculo).getTipoModificacion());
            } else if (vehiculo instanceof Moto) {
                linearMoto.setVisibility(View.VISIBLE);
                linearAutoNuevo.setVisibility(View.GONE);
                linearMotoModificada.setVisibility(View.GONE);
                dato1.setText("Autonomía: " + vehiculo.getPotencia());
                dato2.setText("Peso: " + vehiculo.getModificacion());
                cilindrada.setText(String.valueOf("Cilindrada Cc: " + ((Moto) vehiculo).getCilindradaCc()));
                tipoManejo.setText(String.valueOf("Tipo de manejo: " + ((Moto) vehiculo).getTipoManejo()));
            }
        }
    }
}
