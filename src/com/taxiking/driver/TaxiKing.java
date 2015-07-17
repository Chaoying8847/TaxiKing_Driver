package com.taxiking.driver;

import android.app.Application;

import com.parse.Parse;
import com.taxiking.driver.service.GPSTracker;

public class TaxiKing extends Application {

	public GPSTracker gpsTracker;
	@Override
	public void onCreate() {
		super.onCreate();
		
		Parse.initialize(this, "9wvAGHzEjnKQ7KjG8mJjLFwQYDlB1ityliXxGAl7", "4p6xJzoF9EPAlRGYhjP3zuJKMNbsbxcyBDLcgNA9");
//		PushService.setDefaultPushCallback(this, MainActivity.class);
		
//		gpsTracker = new GPSTracker(this);
//		gpsTracker.startTracking();
		
	}
}
