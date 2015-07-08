package com.taxiking.driver.model;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderHistory {
	
	public String transaction_id = "";
	public String price = "";
	public String time_created = "";
	public String address = "";
	public String rating = "";
	
	public static OrderHistory fromJSON(JSONObject obj) {
		OrderHistory object = new OrderHistory();

		try {
			object.transaction_id	= obj.getString("transaction_id");
			object.price			= obj.getString("price");
			object.time_created		= obj.getString("time_created");
			object.address			= obj.getString("address_string");
			object.rating			= obj.getString("rating");
		} catch (JSONException e) {
		}

		return object;
	}
}
