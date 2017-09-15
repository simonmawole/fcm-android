package com.simon.fcm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Simon on 15-Sep-17.
 */

public class MyFirebaseInstanceId extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceId.class.getSimpleName();
    public static final String TOKEN_GENERATED = "TOKEN GENERATED";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Send token to your backend
        sendRegistrationToServer(refreshedToken);

        //Notify listeners that token is generated
        Intent tokenGenerated = new Intent(TOKEN_GENERATED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(tokenGenerated);
    }

    private void sendRegistrationToServer(final String token) {
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

}
