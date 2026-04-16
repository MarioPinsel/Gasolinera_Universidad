package efm.gasolina.ui.client.prices;

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

    private final MutableLiveData<List<PricesRequest>> pricesList = new MutableLiveData<>();

    private final MutableLiveData<String> statusMessage = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public GasPriceViewModel() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<List<PricesRequest>> getPricesList() {
        return pricesList;
    }

    public LiveData<String> getStatusMessage() {
        return statusMessage;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    /**
     * Obtener precios por zona y tipo de combustible
     */
    public void getPricesByZoneAndType(String zone, String type, String vehicle) {

        if (zone == null && type == null) {
            statusMessage.setValue("ERROR: Todos los campos son necesarios");
            return;
        }

        if (zone == null) {
            statusMessage.setValue("ERROR: La zona es requerida");
            return;
        }

        if (type == null) {
            statusMessage.setValue("ERROR: El tipo de combustible es requerido");
            return;
        }

        if(vehicle == null){
            statusMessage.setValue("ERROR: El vehiculo es requerido");
            return;
        }

        isLoading.setValue(true);
        apiService.getPrices(zone, type, vehicle).enqueue(new Callback<List<PricesRequest>>() {
            @Override
            public void onResponse(Call<List<PricesRequest>> call, Response<List<PricesRequest>> response) {
                isLoading.setValue(false);

                if (response.isSuccessful()) {
                    if (response.body() != null && !response.body().isEmpty()) {
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
