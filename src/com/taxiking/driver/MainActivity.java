package com.taxiking.driver;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.taxiking.driver.apiservice.HttpApi;
import com.taxiking.driver.apiservice.HttpApi.METHOD;
import com.taxiking.driver.base.BaseFragmentActivity;
import com.taxiking.driver.fragment.NewOrderFragment;
import com.taxiking.driver.fragment.OrderCheckFragment;
import com.taxiking.driver.fragment.OrderHistoryFragment;
import com.taxiking.driver.utils.AppConstants;
import com.taxiking.driver.utils.WaitDialog;
import com.taxiking.driver.view.dialog.SSMessageDialog;
import com.taxiking.driver.view.dialog.SSMessageDialog.MessageDilogListener;

public class MainActivity extends BaseFragmentActivity {

	private OrderHistoryFragment orderHistoryFragment;
	public static MainActivity instance; 
	private WaitDialog waitDlg;
	
	public int mCurrentFragmentIndex;
	public static boolean isBackPressed = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		orderHistoryFragment = OrderHistoryFragment.newInstance();
		
		instance = this;
		waitDlg = new WaitDialog(this);
		
		mCurrentFragmentIndex = AppConstants.SW_FRAGMENT_ORDER_HISTORY;
		showFragment(orderHistoryFragment, false, false);
	}
	
	@Override
	public void onBackPressed() {
		onBackButtonPressed();
	}

	boolean isBackAllowed = false;
	private void onBackButtonPressed() {
	
		if (mCurrentFragmentIndex != AppConstants.SW_FRAGMENT_ORDER_HISTORY) {
			SwitchContent(AppConstants.SW_FRAGMENT_ORDER_HISTORY, null);
			return;
		}

		logout();
	}
	
	public void logout() {
		SSMessageDialog alert = new SSMessageDialog(this, getString(R.string.msg_logout_confirm), "", getString(R.string.cancel),
				getString(R.string.confirm));
		alert.show();
		alert.setMessageDilogListener(new MessageDilogListener() {
			@Override
			public void onButtonClick(int id) {
				if (id == R.id.btn_1){
					new LogoutAsyncTask().execute();
				}
			}
		});
	}

	public void SwitchContent(int fragment_index, Bundle bundle) {
		// update the main content by replacing fragments
		
		Fragment fragment = null;
		if (mCurrentFragmentIndex != fragment_index) {
			if (fragment_index == AppConstants.SW_FRAGMENT_ORDER_HISTORY) {
				fragment = OrderHistoryFragment.newInstance();  
			} else if (fragment_index == AppConstants.SW_FRAGMENT_NEW_ORDER) {
				fragment = NewOrderFragment.newInstance();
			} else if (fragment_index == AppConstants.SW_FRAGMENT_CONFIRM_ORDER) {
				fragment = OrderCheckFragment.newInstance();
			}

			if (fragment != null) {
				if (fragment_index<mCurrentFragmentIndex) {
					popFragment(fragment, true);
				} else {
					showFragment(fragment, false, true);
				}
				
				mCurrentFragmentIndex = fragment_index;
			}
		}
	}

	public void showWaitView() {
		waitDlg.show();
	}
	
	public void hideWaitView() {
		waitDlg.cancel();
	}
	
	public class LogoutAsyncTask extends AsyncTask<String, String, JSONObject> {

		@Override
		protected void onPreExecute() {
			showWaitView();
			super.onPreExecute();
		}
		
		@Override
		protected JSONObject doInBackground(String... args) {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("session_token", prefs.getSession()));

			return HttpApi.callToJson(AppConstants.HOST_LOGOUT, METHOD.POST, params, null);
		}

		@Override
		protected void onPostExecute(JSONObject res) {
			MainActivity.instance.hideWaitView();
			try {
				String result = res.getString("result");
	
				if (result.equalsIgnoreCase("success")) {
					prefs.setSession("");
					Intent intent = new Intent(MainActivity.instance, LoginActivity.class);
					startActivity(intent);
					MainActivity.instance.finish();
				} else {
					try {
						String errorMsg = res.getString("error");
						Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
					}catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
}