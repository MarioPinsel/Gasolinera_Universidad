package efm.gasolina.repository;

import efm.gasolina.model.LoginRequest;
import efm.gasolina.model.LoginResponse;
import efm.gasolina.model.User;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;

public class UserRepository {

    private final ApiService apiService;

    public UserRepository() {
        apiService =
                ApiClient.getClient()
                        .create(ApiService.class);
    }

    public Call<Void> register(User user) {
        return apiService.registerUser(user);
    }

    public Call<LoginResponse> login(LoginRequest request) {
        return apiService.login(request);
    }
}