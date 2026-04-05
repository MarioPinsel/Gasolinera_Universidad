package efm.gasolina.ui.operator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import efm.gasolina.R;

public class RegisterSaleFragment extends Fragment {

    private Spinner spinnerFuelType, spinnerVehicleType;
    private EditText etVolume, etPlate;
    private Button btnRegisterSale;
    private TextView tvPrice, tvTotal;
    private OperatorViewModel viewModel;
    private String operatorEmail;
    private boolean vehicleTypesLoaded = false;

    public static RegisterSaleFragment newInstance(String email) {
        RegisterSaleFragment fragment = new RegisterSaleFragment();
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
        return inflater.inflate(R.layout.fragment_register_sale, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            operatorEmail = getArguments().getString("email");
        }

        spinnerFuelType    = view.findViewById(R.id.spinnerFuelType);
        spinnerVehicleType = view.findViewById(R.id.spinnerVehicleType);
        etVolume           = view.findViewById(R.id.etVolume);
        etPlate            = view.findViewById(R.id.etPlate);
        btnRegisterSale    = view.findViewById(R.id.btnRegisterSale);
        tvPrice            = view.findViewById(R.id.tvPrice);
        tvTotal            = view.findViewById(R.id.tvTotal);

        viewModel = new ViewModelProvider(requireActivity())
                .get(OperatorViewModel.class);

        viewModel.loadVehicleTypes();

        viewModel.getVehicleTypes().observe(getViewLifecycleOwner(), types -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    types
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerVehicleType.setAdapter(adapter);
            vehicleTypesLoaded = true;
            consultarPrecio();
        });

        AdapterView.OnItemSelectedListener spinnerListener =
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View v,
                                               int position, long id) {
                        if (vehicleTypesLoaded) consultarPrecio();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                };

        spinnerFuelType.setOnItemSelectedListener(spinnerListener);
        spinnerVehicleType.setOnItemSelectedListener(spinnerListener);

        etVolume.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {}
            @Override
            public void afterTextChanged(android.text.Editable s) {
                calcularTotal();
            }
        });

        viewModel.getCurrentPrice().observe(getViewLifecycleOwner(), precio -> {
            tvPrice.setText("Precio por galón: $" + precio);
            calcularTotal();
        });

        viewModel.getActionResult().observe(getViewLifecycleOwner(), result -> {
            if (result.startsWith("OK:")) {
                Toast.makeText(requireContext(),
                        result.substring(3), Toast.LENGTH_SHORT).show();
                etVolume.setText("");
                etPlate.setText("");
                tvTotal.setText("Total: -");
            } else {
                Toast.makeText(requireContext(),
                        result.substring(7), Toast.LENGTH_SHORT).show();
            }
        });

        btnRegisterSale.setOnClickListener(v -> {
            String volumeStr   = etVolume.getText().toString().trim();
            String plate       = etPlate.getText().toString().trim().toUpperCase();
            String fuelType    = spinnerFuelType.getSelectedItem().toString();
            String vehicleType = spinnerVehicleType.getSelectedItem().toString();

            if (plate.isEmpty()) {
                Toast.makeText(requireContext(),
                        "Ingresa la placa", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!plate.matches("[A-Z]{3}[0-9]{3}|[A-Z]{3}[0-9]{2}[A-Z]{1}")) {
                Toast.makeText(requireContext(),
                        "Placa inválida — formato: ABC123 o ABC12D",
                        Toast.LENGTH_SHORT).show();
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

            viewModel.registerSale(fuelType, vehicleType, volume, plate, operatorEmail);
        });
    }

    private void consultarPrecio() {
        if (spinnerFuelType.getSelectedItem() == null ||
                spinnerVehicleType.getSelectedItem() == null) return;

        String fuelType    = spinnerFuelType.getSelectedItem().toString();
        String vehicleType = spinnerVehicleType.getSelectedItem().toString();

        viewModel.consultarPrecio(operatorEmail, fuelType, vehicleType);
    }

    private void calcularTotal() {
        String volStr = etVolume.getText().toString().trim();
        Integer precio = viewModel.getCurrentPrice().getValue();

        if (!volStr.isEmpty() && precio != null) {
            int vol = Integer.parseInt(volStr);
            tvTotal.setText("Total: $" + (vol * precio));
        } else {
            tvTotal.setText("Total: -");
        }
    }
}