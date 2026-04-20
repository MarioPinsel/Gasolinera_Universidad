package efm.gasolina.ui.wholesaler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import efm.gasolina.model.delivery.Delivery;
import efm.gasolina.model.delivery.DeliveryRequest;
import efm.gasolina.model.sation.Station;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WholesalerViewModel extends ViewModel {

    private final ApiService apiService;
    private final MutableLiveData<List<Station>> stations = new MutableLiveData<>();
    private final MutableLiveData<List<Delivery>> deliveries = new MutableLiveData<>();
    private final MutableLiveData<String> actionResult = new MutableLiveData<>();

    public WholesalerViewModel() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<List<Station>> getStations() { return stations; }
    public LiveData<List<Delivery>> getDeliveries() { return deliveries; }
    public LiveData<String> getActionResult() { return actionResult; }

    public void loadStations() {
        apiService.getAllStations().enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call,
                                   Response<List<Station>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    stations.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                actionResult.setValue("ERROR: No internet connection");
            }
        });
    }

    public void loadHistory(String email) {
        apiService.getDeliveryHistory(email).enqueue(new Callback<List<Delivery>>() {
            @Override
            public void onResponse(Call<List<Delivery>> call,
                                   Response<List<Delivery>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    deliveries.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Delivery>> call, Throwable t) {
                actionResult.setValue("ERROR: No internet connection");
            }
        });
    }

    public void registerDelivery(String vehicle, String conductor,
                                 Integer volume, String fuelType,Integer price,
                                 Long stationId, String email) {

        if (vehicle.isEmpty() || conductor.isEmpty() || volume == null) {
            actionResult.setValue("ERROR: Completa todos los campos");
            return;
        }

        DeliveryRequest request = new DeliveryRequest(
                vehicle, conductor, volume, fuelType, price, stationId, email
        );

        apiService.registerDelivery(request).enqueue(new Callback<Delivery>() {
            @Override
            public void onResponse(Call<Delivery> call,
                                   Response<Delivery> response) {
                if (response.isSuccessful()) {
                    actionResult.setValue("Entrega solicitada correctamente");
                } else if (response.code() == 409) {
                    actionResult.setValue("ERROR: Capacidad de volumen excedida");
                } else {
                    actionResult.setValue("ERROR: Server error " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Delivery> call, Throwable t) {
                actionResult.setValue("ERROR: No internet connection");
            }
        });
    }
}
