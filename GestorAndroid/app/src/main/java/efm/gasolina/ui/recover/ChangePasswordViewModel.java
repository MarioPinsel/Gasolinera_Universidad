package efm.gasolina.ui.recover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import efm.gasolina.model.NewPassword;
import efm.gasolina.model.TokenResponse;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordViewModel extends ViewModel {
    private final ApiService apiService;

    private final MutableLiveData<String> requestRecoverResult = new MutableLiveData<>();

    public ChangePasswordViewModel() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }
    public LiveData<String> getRequestRecoverResult() {
        return requestRecoverResult;
    }

    public void changePassword(String password, String passwordB) {

        if (password.isEmpty() || passwordB.isEmpty()) {
            requestRecoverResult.setValue("ERROR:Ingresa correo para enviar el codigo");
            return;
        }

        NewPassword request = new NewPassword(password, passwordB);

        apiService.changePassword(request).enqueue(new Callback<NewPassword>() {

            @Override
            public void onResponse(Call<NewPassword> call,
                                   Response<NewPassword> response) {
                if (response.isSuccessful()) {
                    requestRecoverResult.setValue("Su contraseña ha sido cambiada");
                } else  {
                    requestRecoverResult.setValue("ERROR:No se puedo completar la operacion");
                }
            }
            @Override
            public void onFailure(Call<NewPassword> call, Throwable t) {
                requestRecoverResult.setValue("ERROR:Sin conexión");
            }
        });
    }
}
