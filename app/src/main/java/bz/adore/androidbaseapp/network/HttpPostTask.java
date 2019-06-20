package bz.adore.androidbaseapp.network;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import bz.adore.androidbaseapp.R;
import okhttp3.Call;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpPostTask extends AsyncTask<Object, Void, Object> {

    private final WeakReference<Activity> w_Activity;

    public HttpPostTask(Activity activity) {
        this.w_Activity = new WeakReference<>(activity);
    }

    @Override
    protected Object doInBackground(Object[] data) {

        String url = (String)data[0];
        String jsonString = (String)data[1];

        okhttp3.MediaType mediaTypeJson = okhttp3.MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaTypeJson, jsonString);

        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

         String result = "";
        try {
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                result = body.string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        Log.i("onPostExecute", (String)result);

        String version = "";
        try {
            JSONObject json = new JSONObject((String)result);
            version = json.getString("version");
        } catch (JSONException je) {
            je.getStackTrace();
        }

        Activity activity = w_Activity.get();
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        TextView tv = activity.findViewById(R.id.messageText);
        String showText = "API Version: " + version;
        tv.setText(showText);
    }
}
