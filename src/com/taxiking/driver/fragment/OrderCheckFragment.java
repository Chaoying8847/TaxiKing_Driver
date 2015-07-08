package com.taxiking.driver.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.taxiking.driver.MainActivity;
import com.taxiking.driver.R;
import com.taxiking.driver.apiservice.HttpApi;
import com.taxiking.driver.apiservice.HttpApi.METHOD;
import com.taxiking.driver.base.BaseFragment;
import com.taxiking.driver.fragment.OrderHistoryFragment.CurrentAsyncTask;
import com.taxiking.driver.model.CurrentStatus;
import com.taxiking.driver.utils.AppConstants;
import com.taxiking.driver.utils.AppDataUtilities;

public class OrderCheckFragment extends BaseFragment implements View.OnClickListener {

	private TextView txtOrderAddress;
	private TextView txtOrderPrice;
	private TextView txtOrderId;
	private TextView txtOrderTime;
	
	private Button btnCallToCustomer;
	
	private CurrentStatus status;

	public static OrderCheckFragment newInstance() {
		OrderCheckFragment fragment = new OrderCheckFragment();

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootview = inflater.inflate(R.layout.fragment_order_check, null);

		status = AppDataUtilities.sharedInstance().status;

		txtOrderAddress = (TextView)rootview.findViewById(R.id.txt_address);
		txtOrderPrice = (TextView)rootview.findViewById(R.id.txt_order_total_price);
		txtOrderId = (TextView)rootview.findViewById(R.id.txt_order_id);
		txtOrderTime = (TextView)rootview.findViewById(R.id.txt_order_time);
		
		btnCallToCustomer = (Button)rootview.findViewById(R.id.btn_call_customer);
		btnCallToCustomer.setOnClickListener(this);

		txtOrderAddress.setText(status.order_address);
		txtOrderPrice.setText(String.format("%.0f¥", status.price));
		txtOrderId.setText(status.transaction_id);
		txtOrderTime.setText(status.order_time);
		
		callCurrentStatus();
		
		return rootview;
	}

	private void callCurrentStatus() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				new CurrentAsyncTask().execute();
			}
		}, 5000);
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
			case R.id.btn_call_customer:
//			if (status.driver_phone!=null && !status.driver_phone.equalsIgnoreCase("")) {
//				// call
//			}
			break;
		default:
			break;
		}
	}
	
	public class CurrentAsyncTask extends AsyncTask<String, String, JSONObject> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected JSONObject doInBackground(String... args) {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("session_token", prefs.getSession()));

			return HttpApi.callToJson(AppConstants.HOST_CURRENT_STATUS, METHOD.POST, params, null);
		}

		@Override
		protected void onPostExecute(JSONObject res) {
			try {
				String result = res.getString("result");
	
				if (result.equalsIgnoreCase("success")) {
					if (res.has("new_order")) {
						CurrentStatus status = CurrentStatus.fromJSON(res.getJSONObject("new_order"));
						if (!status.state.equalsIgnoreCase("accepted")) {
							AppDataUtilities.sharedInstance().status = status;
							MainActivity.instance.SwitchContent(AppConstants.SW_FRAGMENT_ORDER_HISTORY, null);
						} else {
							callCurrentStatus();
						}						
					} else {
						callCurrentStatus();
					}
				} else {
					try {
						String errorMsg = res.getString("error");
						Toast.makeText(parent, errorMsg, Toast.LENGTH_LONG).show();
					}catch (JSONException e) {
						e.printStackTrace();
					}
					callCurrentStatus();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
