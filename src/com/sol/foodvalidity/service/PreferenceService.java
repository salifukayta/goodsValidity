package com.sol.foodvalidity.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sol.foodvalidity.R;

public class PreferenceService {

	public static String getSortByOption(Context context) {
		SharedPreferences prefs = PreferenceManager .getDefaultSharedPreferences(context);
		return prefs.getString(context.getString(R.string.pref_sorting_by), 
				context.getString(R.string.pref_sort_by_name));
	}

	public static void editSortByOption(Context context, String chosenSort) {
		SharedPreferences prefs = PreferenceManager .getDefaultSharedPreferences(context);
		prefs.edit().putString(context.getString(R.string.pref_sorting_by),  chosenSort).commit();
	}
	
}
