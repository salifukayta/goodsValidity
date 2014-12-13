package com.sol.foodvalidity.activity.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.sol.foodvalidity.activity.main.fragment.SettingFragment;

public class SettingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingFragment()).commit();
//		displaySharedPreferences();
	}
	
	@SuppressWarnings("unused")
	private void displaySharedPreferences() {
		   SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SettingActivity.this);
		 
		   String username = prefs.getString("username", "Default NickName");
		   String passw = prefs.getString("password", "Default Password");
		   boolean checkBox = prefs.getBoolean("checkBox", false);
		   String listPrefs = prefs.getString("listpref", "Default list prefs");
		 
		   Log.i("Username: ", username);
		   Log.i("Password: ", passw);
		   Log.i("Keep me logged in: ", String.valueOf(checkBox));
		   Log.i("List preference: ", listPrefs);
	}

}
