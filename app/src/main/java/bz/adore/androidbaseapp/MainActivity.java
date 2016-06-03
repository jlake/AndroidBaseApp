package bz.adore.androidbaseapp;

import android.content.Intent;
import android.util.Log;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.WindowCompat;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import bz.adore.androidbaseapp.util.BitmapCache;
//import com.android.volley.VolleyError;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static String LOG_TAG = "BaseApp";
    public static final int MENU_SELECT_A = 0;
    public static final int MENU_SELECT_B = 1;

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("HH:mm:ss");
    private TextView mTimerText;
    private TextView mMessageText;
    private NetworkImageView mNetImageView;
    private RequestQueue mRequestQueue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ActionBar表示を設定
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_main);

        // タイトル設定
        //getSupportActionBar().setTitle("Hello world App");

        mTimerText = (TextView) findViewById(R.id.timerText);
        mMessageText = (TextView) findViewById(R.id.messageText);
        mNetImageView = (NetworkImageView) findViewById(R.id.netImageView);

        findViewById(R.id.volleyTest).setOnClickListener(this);
        findViewById(R.id.netImage).setOnClickListener(this);

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this);
        } else {
            mRequestQueue.start();
        }

        Runnable runnable = new CountDownRunner();
        Thread myThread = new Thread(runnable);
        myThread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // メニューの要素を追加
        // menu.add("Normal item");
        menu.add(0, MENU_SELECT_A, Menu.NONE, "Another Activity");
        menu.add(0, MENU_SELECT_B, Menu.NONE, "Settings");

        // メニューの要素を追加して取得
        MenuItem actionItem = menu.add("Action Button");

        // SHOW_AS_ACTION_IF_ROOM:余裕があれば表示
        //actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setShowAsAction(actionItem, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);

        // アイコンを設定
        actionItem.setIcon(android.R.drawable.ic_menu_share);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case MENU_SELECT_A:
                Log.d(LOG_TAG,"Start Another Activity");
                Intent intent = new Intent(this, AnotherActivity.class);
                startActivity(intent);
                return true;

            case MENU_SELECT_B:
                Log.d(LOG_TAG,"Start Settings");
                return true;

        }
        return false;
        /*
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        return true;
        */
    }

    public void updateTimer() {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    String currentTime = mDateFormat.format(Calendar.getInstance().getTime());
                    //getSupportActionBar().setTitle(currentTime);
                    mTimerText.setText(currentTime);
                } catch (Exception e) {
                }
            }
        });
    }

    class CountDownRunner implements Runnable {
        // @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    updateTimer();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.volleyTest:
                requestVolley();
                break;
            case R.id.netImage:
                loadNetworkImageView();
                break;
        }
    }

    private void requestVolley() {
        // Volley でリクエスト
        String url = "http://192.168.1.129/phalcon-app/api/v1/";
        Log.d(LOG_TAG, "Json Request: " + url);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Method.GET,
                url,
                null,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOG_TAG, "Response: " + response.toString());
                        mMessageText.setText(response.toString());
                    }
                },
                null);
        mRequestQueue.add(jsonRequest);
        //mRequestQueue.start();
    }

    private void loadNetworkImageView() {
        String url = "http://192.168.1.129/phalcon-app/img/sample.jpg";
        Log.d(LOG_TAG, "Image Request: " + url);
        mNetImageView.setImageUrl(null, null);
        mNetImageView.setImageUrl(url, new ImageLoader(mRequestQueue, new BitmapCache()));
    }
}
