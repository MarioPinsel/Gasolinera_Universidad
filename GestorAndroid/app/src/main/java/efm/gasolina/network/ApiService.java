package efm.gasolina.network;
import java.util.List;
import java.util.Map;

import efm.gasolina.model.Delivery;
import efm.gasolina.model.DeliveryRequest;
import efm.gasolina.model.LoginRequest;
import efm.gasolina.model.PasswordRequest;
import efm.gasolina.model.LoginResponse;
import efm.gasolina.model.Sale;
import efm.gasolina.model.SaleRequest;
import efm.gasolina.model.Station;
import efm.gasolina.model.TokenResponse;
import efm.gasolina.model.User;
import efm.gasolina.model.consults.PricesRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("/sesion/register")
    Call<Void> registerUser(@Body User user);
    @POST("/sesion/forgotPassword")
    Call<TokenResponse> forgotPassword(@Body Map<String,String> request);

    @POST("/sesion/codeVerifier")
    Call<Void> codeVerifier(@Body Map<String,String> request);

    @PATCH("/sesion/changePassword")
    Call<Void> changePassword(@Body PasswordRequest request);

    @POST("/sesion/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("developer/pending")
    Call<List<User>> getPendingUsers();

    @PUT("developer/approve/{id}")
    Call<Void> approveUser(@Path("id") Long id);

    @PUT("developer/reject/{id}")
    Call<Void> rejectUser(@Path("id") Long id);

    @GET("/consult/prices/{zone}/{type}/{vehicle}")
    Call<List<PricesRequest>> getPrices(
            @Path("zone") String zone,
            @Path("type") String type,
            @Path("vehicle") String vehicle);

    @GET("station/all")
    Call<List<Station>> getAllStations();

    @POST("delivery/register")
    Call<Delivery> registerDelivery(@Body DeliveryRequest request);

    @GET("delivery/history/{email}")
    Call<List<Delivery>> getDeliveryHistory(@Path("email") String email);

    @POST("sale/register")
    Call<Sale> registerSale(@Body SaleRequest request);

    @GET("sale/vehicles")
    Call<List<String>> getVehicleTypes();
    @GET("delivery/station/{stationId}/pending")
    Call<List<Delivery>> getPendingDeliveries(@Path("stationId") Long stationId);

    @PUT("delivery/{id}/accept")
    Call<Void> acceptDelivery(@Path("id") Long id);

    @PUT("delivery/{id}/reject")
    Call<Void> rejectDelivery(@Path("id") Long id);

}