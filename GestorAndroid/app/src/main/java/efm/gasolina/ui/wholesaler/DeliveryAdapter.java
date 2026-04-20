package efm.gasolina.ui.wholesaler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import efm.gasolina.R;
import efm.gasolina.model.delivery.Delivery;

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

        holder.tvVehicle.setText("Placa del vehiculo: " + delivery.getVehicle());
        holder.tvConductor.setText("Nombre del conductor: " + delivery.getConductor());
        holder.tvVolume.setText("Cantidad: " + delivery.getVolume() + " Galon(s)");
        holder.tvFuelType.setText("Tipo de combustible: " + delivery.getFuelType());
        holder.tvPrice.setText("Precio: $" + delivery.getPrice());
        holder.tvStation.setText("Estación: " + delivery.getStation().getBrand()
                + " - " + delivery.getStation().getZone());
        holder.tvDistributor.setText("Nombre del distributor: " + delivery.getDistributor().getName());
        holder.tvStatus.setText("Estado: " + delivery.getStatus());
        String rawDate = delivery.getDate();
        try {
            java.text.SimpleDateFormat inputFormat =
                    new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
            java.text.SimpleDateFormat outputFormat =
                    new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
            java.util.Date date = inputFormat.parse(rawDate);
            holder.tvDate.setText("Fecha de solicitud: " + outputFormat.format(date));
        } catch (Exception e) {
            holder.tvDate.setText("Fecha de solicitud: " + rawDate);
        }
    }

    @Override
    public int getItemCount() { return deliveries.size(); }

    public void updateList(List<Delivery> newList) {
        this.deliveries = newList;
        notifyDataSetChanged();
    }

    static class DeliveryViewHolder extends RecyclerView.ViewHolder {
        TextView tvVehicle, tvConductor, tvVolume, tvFuelType,tvPrice, tvStation, tvDate,tvDistributor,tvStatus;

        DeliveryViewHolder(View itemView) {
            super(itemView);
            tvVehicle   = itemView.findViewById(R.id.tvVehicle);
            tvConductor = itemView.findViewById(R.id.tvConductor);
            tvVolume    = itemView.findViewById(R.id.tvVolume);
            tvFuelType  = itemView.findViewById(R.id.tvFuelType);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStation   = itemView.findViewById(R.id.tvStation);
            tvDate      = itemView.findViewById(R.id.tvDate);
            tvDistributor = itemView.findViewById(R.id.tvDistributor);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
