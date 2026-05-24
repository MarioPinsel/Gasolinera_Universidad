package efm.gasolina.service;

import android.Manifest;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import efm.gasolina.R;
import efm.gasolina.model.pqrs.Pqrs;
import efm.gasolina.network.ApiClient;
import efm.gasolina.network.ApiService;
import efm.gasolina.ui.client.ClientActivity;
import retrofit2.Response;

public class PqrsNotificationService extends IntentService {

    public static final String EXTRA_EMAIL = "email";
    private static final String CHANNEL_ID = "pqrs_responses";
    private static final String PREFS_NAME = "pqrs_notifications";
    private static final String PREF_NOTIFIED_IDS = "notified_ids";
    private static final long CHECK_INTERVAL_MS = 30000L;

    public PqrsNotificationService() {
        super("PqrsNotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) return;

        String email = intent.getStringExtra(EXTRA_EMAIL);
        if (email == null || email.isEmpty()) return;

        createNotificationChannel();
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        while (isClientSessionActive(email)) {
            checkResponses(apiService, email);
            sleepBeforeNextCheck();
        }
    }

    private boolean isClientSessionActive(String email) {
        SharedPreferences session = getSharedPreferences("sesion", MODE_PRIVATE);
        return "CLIENTE".equals(session.getString("rol", ""))
                && email.equals(session.getString("email", ""));
    }

    private void checkResponses(ApiService apiService, String email) {
        try {
            Response<List<Pqrs>> response = apiService.getRespondedPqrs(email).execute();
            if (!response.isSuccessful() || response.body() == null) return;

            Set<String> notifiedIds = getNotifiedIds();
            boolean changed = false;

            for (Pqrs pqrs : response.body()) {
                if (pqrs.getId() == null) continue;

                String id = String.valueOf(pqrs.getId());
                if (!notifiedIds.contains(id)) {
                    showNotification(pqrs);
                    notifiedIds.add(id);
                    changed = true;
                }
            }

            if (changed) {
                saveNotifiedIds(notifiedIds);
            }
        } catch (IOException ignored) {
            // El servicio volverá a consultar en el siguiente intervalo.
        }
    }

    private Set<String> getNotifiedIds() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return new HashSet<>(prefs.getStringSet(PREF_NOTIFIED_IDS, new HashSet<>()));
    }

    private void saveNotifiedIds(Set<String> ids) {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putStringSet(PREF_NOTIFIED_IDS, ids)
                .apply();
    }

    private void showNotification(Pqrs pqrs) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Intent intent = new Intent(this, ClientActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                pqrs.getId().intValue(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        String title = "Respuesta a tu PQRS";
        String text = pqrs.getRespuesta() != null && !pqrs.getRespuesta().isEmpty()
                ? pqrs.getRespuesta()
                : "Tu solicitud ya fue respondida";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(pqrs.getId().intValue(), builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return;

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Respuestas PQRS",
                NotificationManager.IMPORTANCE_HIGH
        );
        channel.setDescription("Notificaciones cuando una PQRS recibe respuesta");

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }

    private void sleepBeforeNextCheck() {
        try {
            Thread.sleep(CHECK_INTERVAL_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
