package com.taxiking.driver.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taxiking.driver.MainActivity;
import com.taxiking.driver.R;
import com.taxiking.driver.base.BaseFragment;

public class MainAboutFragment extends BaseFragment {
	public static MainAboutFragment newInstance() {
		MainAboutFragment fragment = new MainAboutFragment();

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (MainActivity.instance != null)
			MainActivity.instance.setTitle(getActivity().getString(R.string.app_name));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootview = inflater.inflate(R.layout.fragment_terms, null);

		return rootview;
	}
}
