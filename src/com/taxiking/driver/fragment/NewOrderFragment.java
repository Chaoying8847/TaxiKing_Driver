package com.taxiking.driver.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taxiking.driver.MainActivity;
import com.taxiking.driver.R;
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
		MainActivity.instance.SwitchContent(AppConstants.SW_FRAGMENT_CONFIRM_ORDER, null);
	}

	@Override
	public void onRightHandleTriggered() {
//		decline();
	}
}
