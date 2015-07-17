package com.taxiking.driver.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CurrentStatus {

	public static final String TAG = "JSON Parse Err In User";
	
	public String state;
	public String transaction_id;
	public String order_time;
	public String order_address;
	public double price;
	public String phone_number;
	public String error;
	
	public static CurrentStatus fromJSON(JSONObject obj) {
		CurrentStatus status = new CurrentStatus();
	
		try {
			status.state			= obj.getString("state");
			status.transaction_id	= obj.getString("transaction_id");
			status.order_time		= obj.getString("time_created");
			status.order_address	= obj.getString("address_string");
			status.price			= obj.getDouble("price");
			status.phone_number		= obj.getString("phone_number");
			status.error			= "";
		} catch (JSONException e) {
			Log.d(TAG, e.toString());
		}
	
		return status;
	}

}
