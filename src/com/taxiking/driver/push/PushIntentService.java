package com.taxiking.driver.push;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

public class PushIntentService extends IntentService {

	public PushIntentService() {
		super("PushIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		String data = extras != null ? extras.getString("com.parse.Data") : "";

		if (!TextUtils.isEmpty(data)) {
			NotificationService service = NotificationService.instance;
			service.showNotification(data);
		}
		
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		PushReceiver.completeWakefulIntent(intent);
	}
}