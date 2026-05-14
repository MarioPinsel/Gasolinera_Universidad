package efm.gasolina.network;
import java.util.List;
import java.util.Map;

import efm.gasolina.model.delivery.Delivery;
import efm.gasolina.model.delivery.DeliveryRequest;
import efm.gasolina.model.auth.LoginRequest;
import efm.gasolina.model.auth.PasswordRequest;
import efm.gasolina.model.auth.LoginResponse;
import efm.gasolina.model.pqrs.PqrsRequest;
import efm.gasolina.model.sale.Sale;
import efm.gasolina.model.sale.SaleRequest;
import efm.gasolina.model.sation.Station;
import efm.gasolina.model.sation.StationAvailability;
import efm.gasolina.model.auth.TokenResponse;
import efm.gasolina.model.auth.User;
import efm.gasolina.model.consults.PricesRequest;
import efm.gasolina.model.sale.Movement;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Call<Void> approveUser(@Path("id") Long id,@Query("role") String role);

    @PUT("developer/reject/{id}")
    Call<Void> rejectUser(@Path("id") Long id, @Query("role") String role);

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
    @GET("station/availability/{email}")
    Call<StationAvailability> getAvailability(@Path("email") String email);
    @GET("sale/price/{email}/{fuelType}/{vehicleType}")
    Call<Integer> getPrice(
            @Path("email") String email,
            @Path("fuelType") String fuelType,
            @Path("vehicleType") String vehicleType
    );
    @GET("delivery/station/{stationId}/pending")
    Call<List<Delivery>> getPendingDeliveries(@Path("stationId") Long stationId);

    @PUT("delivery/{id}/accept")
    Call<Void> acceptDelivery(@Path("id") Long id);

    @PUT("delivery/{id}/reject")
    Call<Void> rejectDelivery(@Path("id") Long id);

    @GET("movimientos/{email}")
    Call<List<Movement>> getHistorialMovimientos(@Path("email") String email);

    @POST("/legal/newDecree")
    Call<Void>guardarDecreto(@Body Map<String, Object> body);

    @POST("/pqrs/send")
    Call<Void> enviarPqrs(@Body PqrsRequest request);
}