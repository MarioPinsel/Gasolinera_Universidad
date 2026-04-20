package efm.gasolina.ui.wholesaler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import efm.gasolina.R;
import efm.gasolina.model.sation.Station;

public class RegisterDeliveryFragment extends Fragment {

    private EditText etVehicle, etConductor, etVolume, etPrice;;
    private Spinner spinnerFuelType, spinnerStation;
    private Button btnRegister;
    private WholesalerViewModel viewModel;
    private List<Station> stationList = new ArrayList<>();
    private String distributorEmail;

    public static RegisterDeliveryFragment newInstance(String email) {
        RegisterDeliveryFragment fragment = new RegisterDeliveryFragment();
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
        return inflater.inflate(R.layout.fragment_register_delivery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            distributorEmail = getArguments().getString("email");
        }

        etVehicle   = view.findViewById(R.id.etVehicle);
        etConductor = view.findViewById(R.id.etConductor);
        etVolume    = view.findViewById(R.id.etVolume);
        spinnerFuelType = view.findViewById(R.id.spinnerFuelType);
        spinnerStation  = view.findViewById(R.id.spinnerStation);
        btnRegister     = view.findViewById(R.id.btnRegister);
        etPrice = view.findViewById(R.id.etPrice);

        viewModel = new ViewModelProvider(requireActivity())
                .get(WholesalerViewModel.class);

        viewModel.getStations().observe(getViewLifecycleOwner(), stations -> {
            stationList = stations;
            List<String> stationNames = new ArrayList<>();
            List<Station> uniqueStations = new ArrayList<>();

            for (Station s : stations) {
                String name = s.getBrand() + " - " + s.getZone();
                if (!stationNames.contains(name)) {
                    stationNames.add(name);
                    uniqueStations.add(s);
                }
            }

            stationList = uniqueStations;

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    stationNames
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerStation.setAdapter(adapter);
        });

        viewModel.getActionResult().observe(getViewLifecycleOwner(), result -> {
            if (result.startsWith("OK:")) {
                Toast.makeText(requireContext(),
                        result.substring(3), Toast.LENGTH_SHORT).show();

                etVehicle.setText("");
                etConductor.setText("");
                etVolume.setText("");

                viewModel.loadHistory(distributorEmail);
            } else {
                Toast.makeText(requireContext(),
                        result.substring(7), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.loadStations();

        btnRegister.setOnClickListener(v -> {
            String vehicle   = etVehicle.getText().toString().trim().toUpperCase();
            String conductor = etConductor.getText().toString().trim().toUpperCase();
            String volumeStr = etVolume.getText().toString().trim();
            String fuelType  = spinnerFuelType.getSelectedItem().toString();
            String priceStr = etPrice.getText().toString().trim();

            if (!vehicle.toUpperCase().matches("[A-Z]{3}[0-9]{3}|[A-Z]{3}[0-9]{2}[A-Z]{1}")) {
                Toast.makeText(requireContext(),
                        "Placa invalida — formato: ABC123 (carro) o ABC12D (moto)",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (conductor.length() < 3) {
                Toast.makeText(requireContext(),
                        "El nombre del conductor debe tener al menos 3 carcteres", Toast.LENGTH_SHORT).show();
                return;
            }

            if (volumeStr.isEmpty()) {
                Toast.makeText(requireContext(),
                        "Ingresa la cantidad", Toast.LENGTH_SHORT).show();
                return;
            }

            Integer volume = Integer.parseInt(volumeStr);

            if (volume <= 0) {
                Toast.makeText(requireContext(),
                        "La cantidad debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                return;
            }

            if (priceStr.isEmpty()) {
                Toast.makeText(requireContext(),
                        "Ingresa el precio", Toast.LENGTH_SHORT).show();
                return;
            }
            Integer price = Integer.parseInt(priceStr);
            if (price <= 0) {
                Toast.makeText(requireContext(),
                        "El precio debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                return;
            }


            Long stationId = stationList.get(
                    spinnerStation.getSelectedItemPosition()).getId();

            viewModel.registerDelivery(
                    vehicle, conductor, volume,
                    fuelType,price, stationId, distributorEmail
            );
        });
    }
}