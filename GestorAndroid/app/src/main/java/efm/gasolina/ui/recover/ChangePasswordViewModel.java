package efm.gasolina.ui.recover;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

import efm.gasolina.model.PasswordRequest;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import efm.gasolina.util.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordViewModel extends AndroidViewModel {
    private final ApiService apiService;

    private final TokenManager tokenManager;
    private final MutableLiveData<String> requestRecoverResult = new MutableLiveData<>();

    public ChangePasswordViewModel(@NonNull Application app) {
        super(app);
        apiService = ApiClient.getClient().create(ApiService.class);
        tokenManager = new TokenManager(app.getApplicationContext());
    }
    public LiveData<String> getRequestRecoverResult() {
        return requestRecoverResult;
    }

    public void changePassword(String password, String passwordB) {

        if (password.isEmpty() || passwordB.isEmpty()) {
            requestRecoverResult.setValue("ERROR:Ingresa correo para enviar el codigo");
            return;
        }

        if (!password.equals(passwordB)) {
            requestRecoverResult.setValue("ERROR:las contraseñas deben coincidir");
            return;
        }


        PasswordRequest request = new PasswordRequest(
                tokenManager.getToken(),
                password
        );


        apiService.changePassword(request).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call,
                                   Response<Void> response) {
                if (response.isSuccessful()) {
                    requestRecoverResult.setValue("OK");
                } else if (response.code() == 404) {
                    requestRecoverResult.setValue("ERROR:No se puedo completar la operacion");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                requestRecoverResult.setValue("ERROR:Sin conexión");
            }
        });
    }
}
