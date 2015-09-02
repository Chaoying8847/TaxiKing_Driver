package com.taxiking.driver.push;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class PushReceiver extends WakefulBroadcastReceiver {

    @Override
	public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String message = extras != null ? extras.getString("com.parse.Data") : "";
        Log.d("message ", " " + message);

        try {
        	ComponentName comp = new ComponentName(context.getPackageName(),
        			PushIntentService.class.getName());
            // Start the service, keeping the device awake while it is launching.
            startWakefulService(context, (intent.setComponent(comp)));
            setResultCode(Activity.RESULT_OK);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
