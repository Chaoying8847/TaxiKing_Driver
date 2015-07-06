package com.taxiking.driver;

import android.os.Bundle;

import com.taxiking.driver.base.BaseFragmentActivity;
import com.taxiking.driver.fragment.LoginOptionFragment;
import com.taxiking.driver.utils.WaitDialog;

public class LoginActivity extends BaseFragmentActivity {

	private LoginOptionFragment loginOptionFragment;
	public static LoginActivity instance; 
	private WaitDialog waitDlg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		loginOptionFragment = LoginOptionFragment.newInstance();
		
		instance = this;
		waitDlg = new WaitDialog(this);
		
		showFragment(loginOptionFragment, false, false);
	}
	
	public void showWaitView() {
		waitDlg.show();
	}
	
	public void hideWaitView() {
		waitDlg.cancel();
	}
}
