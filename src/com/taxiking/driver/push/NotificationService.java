package com.taxiking.driver.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.taxiking.driver.MainActivity;
import com.taxiking.driver.R;

public class NotificationService extends Service {
	
	public static NotificationService instance;
	private static final String TAG = "NotificationService";
	
	private static boolean isRunning = false;
	
	// data
	public static int mTimelineTabCount = 0;
	public static int mNotificationTabCount = 0;

	public NotificationService() {}

	public static boolean isRunning() {
		return isRunning;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		isRunning = true;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;
	}
	
	@Override
	public void onDestroy() {
		isRunning = false;
		super.onDestroy();
		
	}
	
	public void showNotification(String message) {
		Context appContext = getBaseContext();
				
		Intent notificationIntent = new Intent(appContext, MainActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent intent = PendingIntent.getActivity(appContext, 0, notificationIntent, 0);
		
		Notification notification = new Notification.Builder(appContext)
		.setContentIntent(intent)
		.setContentTitle(appContext.getString(R.string.app_name))
		.setContentText(message)
		.setSmallIcon(R.drawable.ic_launcher)
		.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND  | Notification.DEFAULT_LIGHTS)
		.build();
		
		NotificationManager notificationManager = (NotificationManager) appContext.getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
	}
}

