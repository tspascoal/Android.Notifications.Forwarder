package tspascoal.android.notificationsforwarder;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotificationListener extends NotificationListenerService {

    private static final String TAG = "NotificationListener";
    private static final String WEB_ENDPOINT_URL = "https://your-web-endpoint.com/notifications";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private OkHttpClient client = new OkHttpClient();
    private Moshi moshi = new Moshi.Builder().build();
    private JsonAdapter<Map<String, Object>> jsonAdapter = moshi.adapter(Map.class);

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String appName = sbn.getPackageName();
        String notificationTitle = sbn.getNotification().extras.getString("android.title");
        String notificationText = sbn.getNotification().extras.getString("android.text");
        long timestamp = sbn.getPostTime();
        String notificationId = String.valueOf(sbn.getId());

        Map<String, Object> notificationData = new HashMap<>();
        notificationData.put("appName", appName);
        notificationData.put("notificationTitle", notificationTitle);
        notificationData.put("notificationText", notificationText);
        notificationData.put("timestamp", timestamp);
        notificationData.put("notificationId", notificationId);

        String json = jsonAdapter.toJson(notificationData);

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(WEB_ENDPOINT_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                Log.e(TAG, "Failed to forward notification: " + response);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error forwarding notification", e);
        }
    }
}
