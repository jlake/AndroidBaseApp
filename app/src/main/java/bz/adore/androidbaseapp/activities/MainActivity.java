package bz.adore.androidbaseapp.activities;

import android.content.Intent;
import android.util.Log;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.WindowCompat;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import bz.adore.androidbaseapp.R;
import bz.adore.androidbaseapp.network.HttpGetTask;
import bz.adore.androidbaseapp.network.HttpPostTask;
import bz.adore.androidbaseapp.network.HttpImageTask;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static String LOG_TAG = "MainActivity";
    public static final int MENU_SELECT_A = 0;
    public static final int MENU_SELECT_B = 1;

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("HH:mm:ss");
    private TextView mTimerText;
    private TextView mMessageText;
    private ImageView mNetImageView;

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
        mNetImageView = (ImageView) findViewById(R.id.netImageView);

        findViewById(R.id.httpTest).setOnClickListener(this);
        findViewById(R.id.netImage).setOnClickListener(this);

        Runnable runnable = new IntervalRunner();
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

    class IntervalRunner implements Runnable {
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
            case R.id.httpTest:
                httpGetTest();
                break;
            case R.id.netImage:
                httpImageTest();
                break;
        }
    }

     private void httpGetTest() {
        String apiUrl = "https://dev.adore.bz/nandemo/api/v1/index";
        String queryString = "?t=abcd";

        Object[] getParams = new Object[2];
        getParams[0] = apiUrl;
        getParams[1] = queryString;

        new HttpGetTask(this).execute(getParams);
    }

    private void httpPostTest() {
        String apiUrl = "https://dev.adore.bz/nandemo/api/v1/index";
        String jsonString = "";

        Object[] postParams = new Object[2];
        postParams[0] = apiUrl;
        postParams[1] = jsonString;

        new HttpPostTask(this).execute(postParams);
    }

    private void httpImageTest() {
        String imageUrl = "https://dev.adore.bz/nandemo/img/sample.jpg";

        Object[] reqParams = new Object[1];
        reqParams[0] = imageUrl;

        new HttpImageTask(this).execute(reqParams);
    }
}
