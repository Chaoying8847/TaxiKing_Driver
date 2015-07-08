package com.taxiking.driver.utils;

import java.text.DecimalFormat;


public class AppConstants {
	
	public static final String HOST = "http://184.73.117.45/android/driver_api";
	public static final String HOST_REGISTER_1		= HOST + "/register_1";
	public static final String HOST_REGISTER_2 		= HOST + "/register_2";
	public static final String HOST_REGISTER_3 		= HOST + "/register_3";
	public static final String HOST_LOGIN 			= HOST + "/login";
	public static final String HOST_CURRENT_STATUS 	= HOST + "/current_status";
	public static final String HOST_ACCEPT_ORDER 	= HOST + "/accept_order";
	public static final String HOST_LOGOUT	 		= HOST + "/logout";
	
	public static final String API_KEY = "xxx";
	public static final String API_SECRET = "xxx";

//	public static final SimpleDateFormat exportDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//	public static final SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	public static final SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd.MM.yyyy");
//	public static final SimpleDateFormat deductionDateFormat = new SimpleDateFormat("yy-M-d");
//	public static final SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DecimalFormat priceFormat = new DecimalFormat("0.00");
	
	/*
	 * Fragment IDs
	 */
	public static final int SW_FRAGMENT_ORDER_HISTORY 	= 0x100;
	public static final int SW_FRAGMENT_NEW_ORDER 		= 0x110;
	public static final int SW_FRAGMENT_CONFIRM_ORDER 	= 0x120;
	
}
