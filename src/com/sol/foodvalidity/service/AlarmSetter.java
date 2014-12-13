package com.sol.foodvalidity.service;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sol.foodvalidity.model.Food;
import com.sol.foodvalidity.receiver.AlarmValidityReceiver;

public class AlarmSetter {
	
	private static AlarmSetter CURRENT_INSTANCE;
	private static final String EXTRA_KEY_GOODS = "goods";

	private AlarmSetter() {
		super();
	}
	
	public static AlarmSetter getInstance() {
		if (CURRENT_INSTANCE == null) {
			synchronized (AlarmSetter.class) {
				if (CURRENT_INSTANCE == null) {
					CURRENT_INSTANCE = new AlarmSetter();
				}
			}
		}		
		return CURRENT_INSTANCE;
	}

	public void setAlarm(Activity currentActivity, Food goods, Class<AlarmValidityReceiver> receiverClass) {					    
	    Log.i("alarm", "before setAlarm");
		Intent alarmValidityReceiverIntent = new Intent(currentActivity.getApplicationContext(), receiverClass);
		alarmValidityReceiverIntent.putExtra(EXTRA_KEY_GOODS, goods);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(currentActivity.getApplicationContext(), 0, 
				alarmValidityReceiverIntent, 0);

		AlarmManager alarmManager = (AlarmManager) currentActivity.getSystemService(Context.ALARM_SERVICE);
		
		Calendar systemCalendar = Calendar.getInstance();
		systemCalendar.setTimeInMillis(System.currentTimeMillis());			
		systemCalendar.set(Calendar.MINUTE, (systemCalendar.get(Calendar.MINUTE) + 1));
		
		alarmManager.set(AlarmManager.RTC_WAKEUP, goods.getRemindBefore().getTimeInMillis(), pendingIntent);
		Log.i("alarmSet", systemCalendar.get(Calendar.HOUR_OF_DAY) + "h" + systemCalendar.get(Calendar.MINUTE)
				+ "min");
}
}
