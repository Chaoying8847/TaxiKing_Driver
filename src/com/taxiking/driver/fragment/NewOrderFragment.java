package com.taxiking.driver.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.taxiking.driver.LoginActivity;
import com.taxiking.driver.MainActivity;
import com.taxiking.driver.R;
import com.taxiking.driver.apiservice.HttpApi;
import com.taxiking.driver.apiservice.HttpApi.METHOD;
import com.taxiking.driver.base.BaseFragment;
import com.taxiking.driver.model.CurrentStatus;
import com.taxiking.driver.utils.AppConstants;
import com.taxiking.driver.utils.AppDataUtilities;
import com.taxiking.driver.view.Slider;
import com.taxiking.driver.view.Slider.SliderTriggered;

public class NewOrderFragment extends BaseFragment implements SliderTriggered {

	private TextView txtOrderAddress;
	private TextView txtOrderPrice;
	private TextView txtOrderId;
	
	private CurrentStatus status;
	private Slider mIncomingCallWidget;

	public static NewOrderFragment newInstance() {
		NewOrderFragment fragment = new NewOrderFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootview = inflater.inflate(R.layout.fragment_new_order, null);

		status = AppDataUtilities.sharedInstance().status;
		
		txtOrderAddress = (TextView)rootview.findViewById(R.id.txt_address);
		txtOrderPrice = (TextView)rootview.findViewById(R.id.txt_order_total_price);
		txtOrderId = (TextView)rootview.findViewById(R.id.txt_order_id);
		
		txtOrderAddress.setText(status.order_address);
		txtOrderPrice.setText(String.format("%.0f¥", status.price));
		txtOrderId.setText(status.transaction_id);
		
		mIncomingCallWidget = (Slider) rootview.findViewById(R.id.sliding_widget);
	    mIncomingCallWidget.setOnTriggerListener(this);

		return rootview;
	}
	
	@Override
	public void onLeftHandleTriggered() {
		new AcceptOrderAsyncTask().execute();
	}

	@Override
	public void onRightHandleTriggered() {
//		decline();
	}
	
	public class AcceptOrderAsyncTask extends AsyncTask<String, String, JSONObject> {

		@Override
		protected void onPreExecute() {
			MainActivity.instance.showWaitView();
			super.onPreExecute();
		}
		
		@Override
		protected JSONObject doInBackground(String... args) {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("transaction_id", status.transaction_id));
			params.add(new BasicNameValuePair("session_token", prefs.getSession()));

			return HttpApi.callToJson(AppConstants.HOST_ACCEPT_ORDER, METHOD.POST, params, null);
		}

		@Override
		protected void onPostExecute(JSONObject res) {
			MainActivity.instance.hideWaitView();
			try {
				String result = res.getString("result");
	
				if (result.equalsIgnoreCase("success")) {
					MainActivity.instance.SwitchContent(AppConstants.SW_FRAGMENT_CONFIRM_ORDER, null);
				} else {
					try {
						String errorMsg = res.getString("error");
						Toast.makeText(parent, errorMsg, Toast.LENGTH_LONG).show();
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
