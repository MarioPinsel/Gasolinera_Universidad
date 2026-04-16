package efm.gasolina.ui.operator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import efm.gasolina.R;

public class AvailabilityFragment extends Fragment {

    private TextView tvBrand, tvZone;
    private TextView tvRegularQuantity, tvRegularCapacity, tvRegularPercent;
    private TextView tvDieselQuantity, tvDieselCapacity, tvDieselPercent;
    private ProgressBar progressRegular, progressDiesel;
    private OperatorViewModel viewModel;
    private String operatorEmail;

    public static AvailabilityFragment newInstance(String email) {
        AvailabilityFragment fragment = new AvailabilityFragment();
        Bundle args = new Bundle();
        args.putString("email", email);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_availability, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            operatorEmail = getArguments().getString("email");
        }

        tvBrand           = view.findViewById(R.id.tvBrand);
        tvZone            = view.findViewById(R.id.tvZone);
        tvRegularQuantity = view.findViewById(R.id.tvRegularQuantity);
        tvRegularCapacity = view.findViewById(R.id.tvRegularCapacity);
        tvRegularPercent  = view.findViewById(R.id.tvRegularPercent);
        tvDieselQuantity  = view.findViewById(R.id.tvDieselQuantity);
        tvDieselCapacity  = view.findViewById(R.id.tvDieselCapacity);
        tvDieselPercent   = view.findViewById(R.id.tvDieselPercent);
        progressRegular   = view.findViewById(R.id.progressRegular);
        progressDiesel    = view.findViewById(R.id.progressDiesel);

        viewModel = new ViewModelProvider(requireActivity())
                .get(OperatorViewModel.class);

        viewModel.getAvailability().observe(getViewLifecycleOwner(), station -> {
            tvBrand.setText(station.getBrand());
            tvZone.setText("Zona: " + station.getZone());

            int regularPct = (station.getRegularCapacity() > 0)
                    ? (station.getRegularQuantity() * 100 / station.getRegularCapacity())
                    : 0;
            tvRegularQuantity.setText("Disponible: " + station.getRegularQuantity() + " galones");
            tvRegularCapacity.setText("Capacidad: " + station.getRegularCapacity() + " galones");
            tvRegularPercent.setText(regularPct + "% lleno");
            progressRegular.setProgress(regularPct);

            int dieselPct = (station.getDieselCapacity() > 0)
                    ? (station.getDieselQuantity() * 100 / station.getDieselCapacity())
                    : 0;
            tvDieselQuantity.setText("Disponible: " + station.getDieselQuantity() + " galones");
            tvDieselCapacity.setText("Capacidad: " + station.getDieselCapacity() + " galones");
            tvDieselPercent.setText(dieselPct + "% lleno");
            progressDiesel.setProgress(dieselPct);
        });

        viewModel.loadAvailability(operatorEmail);

    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadAvailability(operatorEmail);
    }
}