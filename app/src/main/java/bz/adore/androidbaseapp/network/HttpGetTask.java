package bz.adore.androidbaseapp.network;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import bz.adore.androidbaseapp.R;

public class HttpGetTask extends AsyncTask<Object, Void, Object> {

    private final WeakReference<Activity> w_Activity;

    public HttpGetTask(Activity activity) {
        this.w_Activity = new WeakReference<>(activity);
    }

    @Override
    protected Object doInBackground(Object[] data) {

        String url = (String)data[0];
        String queryString = (String)data[1];

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url + queryString).get().build();

        Call call = client.newCall(request);
        String result = "";

        try {
            Response response = call.execute();
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
