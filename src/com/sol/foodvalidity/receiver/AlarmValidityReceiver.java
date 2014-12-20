package com.sol.foodvalidity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sol.foodvalidity.service.AlarmValidityService;


public class AlarmValidityReceiver extends BroadcastReceiver {

	private static final String FOOD_EXTRA_NAME = "food";

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent alarmValidityServiceIntent = new Intent(context, AlarmValidityService.class);
		alarmValidityServiceIntent.putExtra(FOOD_EXTRA_NAME, intent.getExtras().getSerializable(FOOD_EXTRA_NAME));
		context.startService(alarmValidityServiceIntent);
		Log.i("alarm", "end onReceive");
	}
}
