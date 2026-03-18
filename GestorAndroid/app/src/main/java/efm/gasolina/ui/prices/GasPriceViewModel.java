package efm.gasolina.ui.prices;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import efm.gasolina.model.consults.PricesRequest;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GasPriceViewModel extends ViewModel {

    private final ApiService apiService;

    // Lista completa de precios
    private final MutableLiveData<List<PricesRequest>> pricesList = new MutableLiveData<>();

    // Para mensajes de estado y errores
    private final MutableLiveData<String> statusMessage = new MutableLiveData<>();

    // Para indicar si está cargando
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public GasPriceViewModel() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    // Getter para la lista de precios
    public LiveData<List<PricesRequest>> getPricesList() {
        return pricesList;
    }

    // Getter para mensajes de estado
    public LiveData<String> getStatusMessage() {
        return statusMessage;
    }

    // Getter para indicador de loading
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    /**
     * Obtener precios por zona y tipo de combustible
     */
    public void getPricesByZoneAndType(String zone, String type) {

        // VALIDACIONES
        if (zone.isEmpty() && type.isEmpty()) {
            statusMessage.setValue("ERROR: Todos los campos son necesarios");
            return;
        }

        if (zone.isEmpty()) {
            statusMessage.setValue("ERROR: La zona es requerida");
            return;
        }

        if (type.isEmpty()) {
            statusMessage.setValue("ERROR: El tipo de combustible es requerido");
            return;
        }

        // Mostrar que está cargando
        isLoading.setValue(true);

        // LLAMADA AL API
        apiService.getPrices(zone, type).enqueue(new Callback<List<PricesRequest>>() {
            @Override
            public void onResponse(Call<List<PricesRequest>> call, Response<List<PricesRequest>> response) {
                isLoading.setValue(false);

                if (response.isSuccessful()) {
                    if (response.body() != null && !response.body().isEmpty()) {
                        // ✅ Retornar la lista completa (no procesar aquí)
                        pricesList.setValue(response.body());
                        statusMessage.setValue("OK");
                    } else {
                        statusMessage.setValue("ERROR: No hay resultados disponibles");
                    }
                } else {
                    statusMessage.setValue("ERROR: No se pudo buscar la información");
                }
            }

            @Override
            public void onFailure(Call<List<PricesRequest>> call, Throwable t) {
                isLoading.setValue(false);
                statusMessage.setValue("ERROR: No hay conexión a internet");
            }
        });
    }
}
