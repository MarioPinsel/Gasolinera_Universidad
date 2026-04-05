package efm.gasolina.ui.operator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import efm.gasolina.model.Sale;
import efm.gasolina.model.SaleRequest;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperatorViewModel extends ViewModel {

    private final ApiService apiService;
    private final MutableLiveData<List<String>> vehicleTypes = new MutableLiveData<>();
    private final MutableLiveData<String> actionResult = new MutableLiveData<>();
    private final MutableLiveData<Sale> lastSale = new MutableLiveData<>();
    private final MutableLiveData<Integer> currentPrice = new MutableLiveData<>();

    public OperatorViewModel() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<List<String>> getVehicleTypes() { return vehicleTypes; }
    public LiveData<String> getActionResult() { return actionResult; }
    public LiveData<Sale> getLastSale() { return lastSale; }
    public LiveData<Integer> getCurrentPrice() { return currentPrice; }

    public void loadVehicleTypes() {
        apiService.getVehicleTypes().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call,
                                   Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    vehicleTypes.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                actionResult.setValue("ERROR:Sin conexión");
            }
        });
    }

    public void consultarPrecio(String email, String fuelType, String vehicleType) {
        apiService.getPrice(email, fuelType, vehicleType)
                .enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call,
                                           Response<Integer> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            currentPrice.setValue(response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        actionResult.setValue("ERROR:Sin conexión");
                    }
                });
    }

    public void registerSale(String fuelType, String vehicleType,
                             Integer volume, String plate,
                             String operatorEmail) {

        if (volume == null || volume <= 0) {
            actionResult.setValue("ERROR:Ingresa un volumen válido");
            return;
        }

        SaleRequest request = new SaleRequest(
                fuelType, vehicleType, volume, plate, operatorEmail
        );

        apiService.registerSale(request).enqueue(new Callback<Sale>() {
            @Override
            public void onResponse(Call<Sale> call, Response<Sale> response) {
                if (response.isSuccessful() && response.body() != null) {
                    lastSale.setValue(response.body());
                    actionResult.setValue("OK:Venta registrada exitosamente");
                } else if (response.code() == 409) {
                    actionResult.setValue("ERROR:Combustible insuficiente en la estación");
                } else if (response.code() == 404) {
                    actionResult.setValue("ERROR:Operador o estación no encontrado");
                } else {
                    actionResult.setValue("ERROR:Error del servidor " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Sale> call, Throwable t) {
                actionResult.setValue("ERROR:Sin conexión");
            }
        });
    }
}