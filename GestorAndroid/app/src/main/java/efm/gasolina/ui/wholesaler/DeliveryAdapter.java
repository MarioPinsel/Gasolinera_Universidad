package efm.gasolina.ui.wholesaler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import efm.gasolina.R;
import efm.gasolina.model.Delivery;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder> {

    private List<Delivery> deliveries;

    public DeliveryAdapter(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    @NonNull
    @Override
    public DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_delivery, parent, false);
        return new DeliveryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryViewHolder holder, int position) {
        Delivery delivery = deliveries.get(position);

        holder.tvVehicle.setText("Vehiculo: " + delivery.getVehicle());
        holder.tvConductor.setText("Conductor: " + delivery.getConductor());
        holder.tvVolume.setText("Cantidad: " + delivery.getVolume() + " Galon(s)");
        holder.tvFuelType.setText("Combustible: " + delivery.getFuelType());
        holder.tvStation.setText("Estación: " + delivery.getStation().getFranchise()
                + " - " + delivery.getStation().getZone());
        holder.tvDate.setText("Fecha: " + delivery.getDate());
        holder.tvDistributor.setText("Distributor: " + delivery.getDistributor().getName());
    }

    @Override
    public int getItemCount() { return deliveries.size(); }

    public void updateList(List<Delivery> newList) {
        this.deliveries = newList;
        notifyDataSetChanged();
    }

    static class DeliveryViewHolder extends RecyclerView.ViewHolder {
        TextView tvVehicle, tvConductor, tvVolume, tvFuelType, tvStation, tvDate,tvDistributor;

        DeliveryViewHolder(View itemView) {
            super(itemView);
            tvVehicle   = itemView.findViewById(R.id.tvVehicle);
            tvConductor = itemView.findViewById(R.id.tvConductor);
            tvVolume    = itemView.findViewById(R.id.tvVolume);
            tvFuelType  = itemView.findViewById(R.id.tvFuelType);
            tvStation   = itemView.findViewById(R.id.tvStation);
            tvDate      = itemView.findViewById(R.id.tvDate);
            tvDistributor = itemView.findViewById(R.id.tvDistributor);
        }
    }
}
