package bz.adore.androidbaseapp.network;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import bz.adore.androidbaseapp.R;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpImageTask extends AsyncTask<Object, Void, Object> {

    private final WeakReference<Activity> w_Activity;

    public HttpImageTask(Activity activity) {
        this.w_Activity = new WeakReference<>(activity);
    }

    @Override
    protected Bitmap doInBackground(Object[] data) {

        String url = (String)data[0];

        Request request = new Request.Builder().url(url).get().build();
        final OkHttpClient client = new OkHttpClient();

        Bitmap mImageData = null;

        try {
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                mImageData = BitmapFactory.decodeStream(body.byteStream());
            }
         } catch (IOException e) {
            e.printStackTrace();
        }

        return mImageData;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);


        Activity activity = w_Activity.get();
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        ImageView iv = activity.findViewById(R.id.netImageView);
        iv.setImageBitmap((Bitmap)result);
    }
}
