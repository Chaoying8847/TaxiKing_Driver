package com.taxiking.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

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
					prefs.setSession("");
					Intent intent = new Intent(MainActivity.instance, LoginActivity.class);
					startActivity(intent);
					MainActivity.instance.finish();
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
	
}