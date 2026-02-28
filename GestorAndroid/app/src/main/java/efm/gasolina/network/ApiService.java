package efm.gasolina.network;
import java.util.Map;

import efm.gasolina.model.LoginRequest;
import efm.gasolina.model.NewPassword;
import efm.gasolina.model.LoginResponse;
import efm.gasolina.model.TokenResponse;
import efm.gasolina.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/sesion/register")
    Call<Void> registerUser(@Body User user);


    @POST("/sesion/forgotPassword")
    Call<TokenResponse> forgotPassword(@Body Map<String,String> request);

    @POST("api/session/codeVerifier")
    Call<Void> codeVerifier(@Body String request);

    @POST("api/session/changePassword")
    Call<NewPassword> changePassword(@Body NewPassword request);

    @POST("/sesion/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}