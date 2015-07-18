package com.taxiking.driver.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.taxiking.driver.MainActivity;
import com.taxiking.driver.R;
import com.taxiking.driver.base.BaseFragment;
import com.taxiking.driver.model.OrderHistory;
import com.taxiking.driver.utils.AppDataUtilities;

public class OrderHistoryFragment extends BaseFragment {

	private ListView listView;
	private OrderHistoryAdapter adapter;
	private List<OrderHistory> arrOrders;
	private Button btnLogout;
	
	public static OrderHistoryFragment newInstance() {
		OrderHistoryFragment fragment = new OrderHistoryFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootview = inflater.inflate(R.layout.fragment_order_history, null);

		listView = (ListView)rootview.findViewById(R.id.order_list);
		adapter = new OrderHistoryAdapter(parent, R.layout.view_order_history_item);
		listView.setAdapter(adapter);
		
		btnLogout = (Button)rootview.findViewById(R.id.btn_logout);
		btnLogout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.instance.logout();
			}
		});
		
		arrOrders = AppDataUtilities.sharedInstance().orderHistoryArray;
		
		return rootview;
	}
	
	public void setOrderData(List<OrderHistory> orders) {
		if (arrOrders != null)
			arrOrders.clear();
		else
			arrOrders = new ArrayList<OrderHistory>();
		
		arrOrders = orders;

		if (adapter != null)
			adapter.notifyDataSetChanged();
	}
	
	class OrderHistoryAdapter extends ArrayAdapter<OrderHistory> {
		LayoutInflater inflater;
		
		public OrderHistoryAdapter(Context context, int resource) {
			super(context, resource);
			
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			convertView = inflater.inflate(R.layout.view_order_history_item, null, false);
		
			holder = new ViewHolder();
			holder.txtLocation = (TextView) convertView.findViewById(R.id.txt_location);
			holder.txtTime = (TextView) convertView.findViewById(R.id.txt_order_time);
			holder.txtOrderId = (TextView) convertView.findViewById(R.id.txt_order_id);
			holder.txtPrice = (TextView) convertView.findViewById(R.id.txt_price);
			
			OrderHistory orderItem = arrOrders.get(position);
			
			if (orderItem != null) {
				holder.txtLocation.setText(orderItem.address);
				holder.txtTime.setText(orderItem.time_created);
				holder.txtOrderId.setText(orderItem.transaction_id);				
				holder.txtPrice.setText(orderItem.price + getResources().getString(R.string.money_unit));
			}
			
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
//					OrderHistory orderHistoryItem = arrOrders.get(position);
//					if (orderHistoryItem.rating == null || orderHistoryItem.rating.equalsIgnoreCase("")) {
//						AppDataUtilities.sharedInstance().status.transaction_id = orderHistoryItem.order_id;
//						MainActivity.instance.SwitchContent(AppConstants.SW_FRAGMENT_RATING, null);
//					}
				}
			});
			
			return convertView;
		}

		class ViewHolder {
			TextView txtLocation;
			TextView txtTime;
			TextView txtOrderId;
			TextView txtPrice;
		}
		
		@Override
		public int getCount () {
			if (arrOrders == null)
				return 0;
			return arrOrders.size();
		}
	}
}