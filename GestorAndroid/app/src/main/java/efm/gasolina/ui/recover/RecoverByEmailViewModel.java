package efm.gasolina.ui.recover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import efm.gasolina.model.TokenResponse;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecoverByEmailViewModel extends ViewModel {

    private final ApiService apiService;

    private final MutableLiveData<String> requestRecoverResult = new MutableLiveData<>();

    public RecoverByEmailViewModel() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }
    public LiveData<String> getRequestRecoverResult() {
        return requestRecoverResult;
    }

    public void sendMail(String correo) {

        if (correo.isEmpty()) {
            requestRecoverResult.setValue("ERROR:Ingresa correo para enviar el codigo");
            return;
        }

        String request = correo;
        // primero creas el objeto LoginRequest
        // luego se lo mandas a la API

        apiService.forgotPassword(request).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful() && response.body() != null) {
                    requestRecoverResult.setValue("OK");
                } else if (response.code() == 404) {
                    requestRecoverResult.setValue("ERROR:Correo no encontrado");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                requestRecoverResult.setValue("ERROR:Sin conexión");
            }
        });
    }



}
