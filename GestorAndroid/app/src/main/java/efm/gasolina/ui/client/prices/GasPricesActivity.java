package efm.gasolina.ui.client.prices;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import efm.gasolina.R;
import efm.gasolina.model.consults.PricesRequest;

public class GasPricesActivity extends AppCompatActivity {

    private Spinner zoneSpinner;
    private Spinner typeSpinner;

    private Spinner vehicleSpinner;
    private Button launchBtn;

    // Componentes para mostrar resultados
    private ScrollView resultsContainer;
    private LinearLayout othersContainer;

    // TextViews para best option
    private TextView bestOptionType;
    private TextView bestOptionPrice;
    private TextView bestOptionZone;

    private GasPriceViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_prices);

        // Referencias a elementos del layout
        zoneSpinner = findViewById(R.id.zoneSpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        vehicleSpinner = findViewById(R.id.vehicleSpinner);
        launchBtn = findViewById(R.id.button);

        resultsContainer = findViewById(R.id.resultsContainer);
        othersContainer = findViewById(R.id.othersContainer);

        bestOptionType = findViewById(R.id.bestOptionType);
        bestOptionPrice = findViewById(R.id.bestOptionPrice);

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(this).get(GasPriceViewModel.class);

        // ═══════════════════════════════════════════════════════════
        // OBSERVER 1: Observar la LISTA de precios
        // ═══════════════════════════════════════════════════════════
        viewModel.getPricesList().observe(this, pricesList -> {
            if (pricesList != null && !pricesList.isEmpty()) {
                mostrarResultados(pricesList);
            }
        });

        // ═══════════════════════════════════════════════════════════
        // OBSERVER 2: Observar mensajes de estado/error
        // ═══════════════════════════════════════════════════════════
        viewModel.getStatusMessage().observe(this, statusMessage -> {
            if (statusMessage != null) {
                if (!statusMessage.equals("OK")) {
                    if (statusMessage.startsWith("ERROR")) {
                        // Mostrar toast de error
                        String errorMsg = statusMessage.substring(7); // Quitar "ERROR: "
                        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // ═══════════════════════════════════════════════════════════
        // OBSERVER 3: Observar loading
        // ═══════════════════════════════════════════════════════════
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                launchBtn.setText("BUSCANDO...");
                launchBtn.setEnabled(false);
            } else {
                launchBtn.setText("BUSCAR PRECIOS");
                launchBtn.setEnabled(true);
            }
        });

        // ═══════════════════════════════════════════════════════════
        // CLICK EN BOTÓN DE BÚSQUEDA
        // ═══════════════════════════════════════════════════════════
        launchBtn.setOnClickListener(v -> {
            String zone = zoneSpinner.getSelectedItem().toString().trim();
            String type = typeSpinner.getSelectedItem().toString().trim();
            String vehicle = vehicleSpinner.getSelectedItem().toString().trim();

            viewModel.getPricesByZoneAndType(zone, type, vehicle);
        });
    }


    /**
     * Mostrar resultados cuando llega la lista del API
     */
    private void mostrarResultados(List<PricesRequest> prices) {
        // Mostrar contenedor
        resultsContainer.setVisibility(View.VISIBLE);

        // PRIMERA OPCIÓN = BEST OPTION
        if (prices.size() > 0) {
            rellenarBestOption(prices.get(0));
        }

        // RESTO = OTRAS OPCIONES (crear dinámicamente)
        if (prices.size() > 1) {
            othersContainer.removeAllViews();
            for (int i = 1; i < prices.size(); i++) {
                crearCeldaDinamica(othersContainer, prices.get(i));
            }
        }
    }

    /**
     * Rellenar la celda BEST OPTION (ya existe en el XML)
     */
    private void rellenarBestOption(PricesRequest pricesRequest) {
        bestOptionType.setText(pricesRequest.getFranchise());
        bestOptionPrice.setText(pricesRequest.getPrice());
        // Si tu modelo no tiene getZone(), comenta esta línea
        // bestOptionZone.setText(pricesRequest.getZone());
    }

    /**
     * Crear una celda dinámica para OTRAS OPCIONES
     */
    private void crearCeldaDinamica(LinearLayout container, PricesRequest pricesRequest) {

        // ═════════════════════════════════════════════════════════
        // 1. CREAR LA CELDA PRINCIPAL (LinearLayout)
        // ═════════════════════════════════════════════════════════
        LinearLayout cell = new LinearLayout(this);
        LinearLayout.LayoutParams cellParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cellParams.setMargins(0, 0, 0, dpToPx(12));
        cell.setLayoutParams(cellParams);
        cell.setOrientation(LinearLayout.VERTICAL);
        cell.setPadding(dpToPx(12), dpToPx(12), dpToPx(12), dpToPx(12));
        cell.setBackgroundColor(Color.parseColor("#f0f0f0"));

        // ═════════════════════════════════════════════════════════
        // 2. FILA 1: COMBUSTIBLE
        // ═════════════════════════════════════════════════════════
        LinearLayout row1 = new LinearLayout(this);
        row1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams row1Params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        row1Params.setMargins(0, 0, 0, dpToPx(8));
        row1.setLayoutParams(row1Params);

        // Label "Combustible"
        TextView label1 = new TextView(this);
        label1.setText("Combustible:");
        label1.setTextSize(16);
        label1.setTypeface(null, android.graphics.Typeface.BOLD);
        LinearLayout.LayoutParams label1Params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.4f
        );
        label1.setLayoutParams(label1Params);

        // Valor del combustible
        TextView value1 = new TextView(this);
        value1.setText(pricesRequest.getFranchise());  // ← DEL API
        value1.setTextSize(16);
        value1.setGravity(Gravity.END);
        LinearLayout.LayoutParams value1Params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.6f
        );
        value1.setLayoutParams(value1Params);

        row1.addView(label1);
        row1.addView(value1);
        cell.addView(row1);

        // ═════════════════════════════════════════════════════════
        // 3. FILA 2: PRECIO
        // ═════════════════════════════════════════════════════════
        LinearLayout row2 = new LinearLayout(this);
        row2.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams row2Params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        row2Params.setMargins(0, 0, 0, dpToPx(8));
        row2.setLayoutParams(row2Params);

        // Label "Precio"
        TextView label2 = new TextView(this);
        label2.setText("Precio:");
        label2.setTextSize(16);
        label2.setTypeface(null, android.graphics.Typeface.BOLD);
        LinearLayout.LayoutParams label2Params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.4f
        );
        label2.setLayoutParams(label2Params);

        // Valor del precio
        TextView value2 = new TextView(this);
        value2.setText(pricesRequest.getPrice());  // ← DEL API
        value2.setTextSize(14);
        value2.setTypeface(null, android.graphics.Typeface.BOLD);
        value2.setTextColor(Color.parseColor("#FF9800"));
        value2.setGravity(Gravity.END);
        LinearLayout.LayoutParams value2Params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.6f
        );
        value2.setLayoutParams(value2Params);

        row2.addView(label2);
        row2.addView(value2);
        cell.addView(row2);

        // ═════════════════════════════════════════════════════════
        // 4. AGREGAR LA CELDA AL CONTENEDOR
        // ═════════════════════════════════════════════════════════
        container.addView(cell);
    }
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}