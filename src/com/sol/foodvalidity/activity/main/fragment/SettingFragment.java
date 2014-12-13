package com.sol.foodvalidity.activity.main.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.sol.foodvalidity.R;

public class SettingFragment extends PreferenceFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

}
