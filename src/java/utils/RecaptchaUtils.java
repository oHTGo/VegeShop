package utils;

import configurations.EnvConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecaptchaUtils {

    public static boolean checkToken(String token) {
        try {
            String url = "https://www.google.com/recaptcha/api/siteverify"
                    + "?secret=" + EnvConfig.getRECAPTCHA_SECRET()
                    + "&response=" + token;

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(null, new byte[0]);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            if (responseBody == null) {
                return false;
            }

            if (responseBody.isEmpty()) {
                return false;
            }

            return responseBody.contains("\"success\": true");
        } catch (Exception ex) {
            return false;
        }
    }
}
