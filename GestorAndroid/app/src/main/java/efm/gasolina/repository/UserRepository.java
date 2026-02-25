package efm.gasolina.repository;

import efm.gasolina.model.User;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import retrofit2.Call;

public class UserRepository {

    private ApiService apiService;

    public UserRepository() {
        apiService =
                ApiClient.getClient()
                        .create(ApiService.class);
    }

    public Call<Void> register(User user) {
        return apiService.registerUser(user);
    }
}