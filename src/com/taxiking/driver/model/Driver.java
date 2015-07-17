package com.taxiking.driver.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Driver {
	public String phone = "";
	
	public static Driver fromJSON(JSONObject obj) {
		Driver object = new Driver();

		try {
			object.phone	= obj.getString("phone_number");
			
		} catch (JSONException e) {
		}
		return object;
	}

}
