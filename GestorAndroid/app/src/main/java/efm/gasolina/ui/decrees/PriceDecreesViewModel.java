package efm.gasolina.ui.decrees;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriceDecreesViewModel extends ViewModel {

    private final ApiService apiService;

    private final MutableLiveData<String> statusMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public PriceDecreesViewModel() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<String> getStatusMessage() {
        return statusMessage;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void guardarDecreto(String name, String type, Integer value) {

        isLoading.setValue(true);

        Map<String, Object> body = new HashMap<>();
        body.put("name", name);
        body.put("typeOfGas", type);
        body.put("value", value);

        apiService.guardarDecreto(body)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        isLoading.setValue(false);

                        if (response.isSuccessful()) {
                            statusMessage.setValue("OK");
                        } else {
                            statusMessage.setValue("ERROR: No se pudo guardar (" + response.code() + ")");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        isLoading.setValue(false);
                        statusMessage.setValue("ERROR: " + t.getMessage());
                    }
                });
    }
}