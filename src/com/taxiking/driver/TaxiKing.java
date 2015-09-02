package com.taxiking.driver;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.taxiking.driver.service.GPSTracker;

public class TaxiKing extends Application {

	public GPSTracker gpsTracker;
	@Override
	public void onCreate() {
		super.onCreate();
		
		// Initialize the Parse SDK.
		Parse.initialize(this, "OTMXdlHNn3DCxC7XhrfDYGEeuEdnLvruIdWhPwkw", "Hl2PHCJYMjzRZNrxSgMCQgxGx9AAWQz6rDwmiD4q"); 
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
		gpsTracker = new GPSTracker(this);
		gpsTracker.startTracking();
		
	}
}
