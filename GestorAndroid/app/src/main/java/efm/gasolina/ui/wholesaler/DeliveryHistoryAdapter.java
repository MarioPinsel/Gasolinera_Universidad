package efm.gasolina.ui.wholesaler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import efm.gasolina.R;
import efm.gasolina.model.Delivery;

public class DeliveryHistoryAdapter extends RecyclerView.Adapter<DeliveryHistoryAdapter.ViewHolder> {

    private final List<Delivery> deliveries;

    public DeliveryHistoryAdapter(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_delivery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Delivery d = deliveries.get(position);

        holder.tvVehicle.setText("🚛 " + d.getVehicle());
        holder.tvConductor.setText("Conductor: " + d.getConductor());
        holder.tvVolume.setText(d.getVolume() + " gal");
        holder.tvFuelType.setText(d.getFuelType());
        holder.tvDate.setText(d.getDate());
        holder.tvDistributor.setText("📦 " + (d.getDistributor() != null
                ? d.getDistributor().getName() : "—"));

        // 🔥 ocultamos botones porque es historial
        holder.btnAccept.setVisibility(View.GONE);
        holder.btnReject.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return deliveries.size();
    }

    public void updateList(List<Delivery> newList) {
        deliveries.clear();
        deliveries.addAll(newList);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvVehicle, tvConductor, tvVolume, tvFuelType, tvDistributor, tvDate;
        MaterialButton btnAccept, btnReject;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVehicle     = itemView.findViewById(R.id.tv_vehicle);
            tvConductor   = itemView.findViewById(R.id.tv_conductor);
            tvVolume      = itemView.findViewById(R.id.tv_volume);
            tvFuelType    = itemView.findViewById(R.id.tv_fuel_type);
            tvDistributor = itemView.findViewById(R.id.tv_distributor);
            tvDate        = itemView.findViewById(R.id.tv_date);
            btnAccept     = itemView.findViewById(R.id.btn_accept);
            btnReject     = itemView.findViewById(R.id.btn_reject);
        }
    }
}