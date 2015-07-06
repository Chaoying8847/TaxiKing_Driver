package com.taxiking.driver.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

import com.taxiking.driver.R;
import com.taxiking.driver.view.dialog.SSMessageDialog;
import com.taxiking.driver.view.dialog.SSMessageDialog.MessageDilogListener;


public class CommonUtil {
	/*
	 * Hide keyboard
	 */
	public static void hideKeyboard(Context context, IBinder binder) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(binder, 0);
	}

	/*
	 * network connection
	 */
	public static boolean isNetworkAvailable(Context context) {
		boolean status=false;
		try{
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getNetworkInfo(0);
			if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
				status= true;

			}else {
				netInfo = cm.getNetworkInfo(1);
				if (netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
					status= true;
			}

		} catch(Exception e) {
			e.printStackTrace();  
			return false;
		}

		return status;
	}

	public static void showMessageDialog(final Context context, String title, String message) {
		SSMessageDialog alert = new SSMessageDialog(context,
				title, message,
				context.getString(R.string.confirm));
		alert.show();
		alert.setMessageDilogListener(new MessageDilogListener() {

			@Override
			public void onButtonClick(int id) {
//				Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
//				context.startActivity(intent);
			}
		});
	}
	
	public static void showWaringDialog(final Context context, String title, String message) {
		SSMessageDialog alert = new SSMessageDialog(context,
				title, message,
				context.getString(R.string.confirm));
		alert.show();
		alert.setMessageDilogListener(new MessageDilogListener() {

			@Override
			public void onButtonClick(int id) {
				Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
				context.startActivity(intent);
			}
		});
	}
	
}
