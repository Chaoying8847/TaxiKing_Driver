package com.taxiking.driver.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.taxiking.driver.R;
import com.taxiking.driver.base.BaseFragment;
import com.taxiking.driver.model.CurrentStatus;
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
		
		return rootview;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
			case R.id.btn_call_customer:
			if (status.driver_phone!=null && !status.driver_phone.equalsIgnoreCase("")) {
				// call
			}
			break;
		default:
			break;
		}
	}
}
