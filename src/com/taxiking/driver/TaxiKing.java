package com.taxiking.driver;

import android.app.Application;

import com.taxiking.driver.service.GPSTracker;

public class TaxiKing extends Application {

	public GPSTracker gpsTracker;
	@Override
	public void onCreate() {
		super.onCreate();
		gpsTracker = new GPSTracker(this);
		gpsTracker.startTracking();
		
	}
}
