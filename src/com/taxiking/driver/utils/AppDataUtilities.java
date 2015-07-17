package com.taxiking.driver.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.taxiking.driver.model.CurrentStatus;
import com.taxiking.driver.model.Driver;
import com.taxiking.driver.model.OrderHistory;

public class AppDataUtilities {
	
	private static AppDataUtilities instance;
	
	// drivers parse
	public ArrayList <OrderHistory> orderHistoryArray;
	public CurrentStatus status;
	public Driver driver;
	public String transaction_id;
	
	public AppDataUtilities() {
		
		orderHistoryArray = new ArrayList<OrderHistory>();
		transaction_id	= "";
	}
	
	public static AppDataUtilities sharedInstance() {
		if (instance == null) {
			instance = new AppDataUtilities();
		}
		return instance;
	}
	
	public void setDataFromLoginJsonData(JSONObject res) {
		try {
			// past history
			orderHistoryArray = new ArrayList<OrderHistory>();
			JSONArray hostoryJsonArray = res.getJSONArray("past_rides");
			for (int i=0; i<hostoryJsonArray.length(); i++) {
				OrderHistory orderHistory = OrderHistory.fromJSON(hostoryJsonArray.getJSONObject(i));
				orderHistoryArray.add(orderHistory);
			}
			
			// current status
			JSONObject new_order = res.getJSONObject("new_order");
			status = CurrentStatus.fromJSON(new_order);
			
			// driver
			JSONObject driverInfo = res.getJSONObject("accoint_Info");
			driver = Driver.fromJSON(driverInfo);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
}
