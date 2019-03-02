package com.simon.fcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mBroadcastReceiver;
    private TextView tvTitle, tvMessage;
    private final String TOPIC_GDG_DEVFEST = "gdg_devfest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvMessage = (TextView) findViewById(R.id.tvMessage);

        if(getIntent().getStringExtra("title") != null)
            tvTitle.setText(getIntent().getStringExtra("title"));

        if(getIntent().getStringExtra("message") != null)
            tvMessage.setText(getIntent().getStringExtra("message"));

        registerBroadcastReceiver();
    }

    private void registerBroadcastReceiver(){
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Check if token is generated or refreshed
                if (intent.getAction().equals(MyFirebaseInstanceId.TOKEN_GENERATED)) {
                    //Subscribe to a topic
                    FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_GDG_DEVFEST);

                    Log.e(TAG, "SUBSCRIBED TO A TOPIC");

                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Register receiver for token generated
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                new IntentFilter(MyFirebaseInstanceId.TOKEN_GENERATED));

    }

    @Override
    protected void onPause() {
        //Unregister all broadcast receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }
}
