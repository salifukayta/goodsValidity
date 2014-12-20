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
	private static final String FOOD_EXTRA_NAME = "food";
	private static final int REQUEST_ALARM = 0;

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

	public void setAlarm(Activity currentActivity, Food food, Class<AlarmValidityReceiver> receiverClass) {					    
		Intent alarmValidityReceiverIntent = new Intent(currentActivity.getApplicationContext(), receiverClass);
		alarmValidityReceiverIntent.putExtra(FOOD_EXTRA_NAME, food);
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(currentActivity.getApplicationContext(), REQUEST_ALARM, 
				alarmValidityReceiverIntent, PendingIntent.FLAG_CANCEL_CURRENT);

		AlarmManager alarmManager = (AlarmManager) currentActivity.getSystemService(Context.ALARM_SERVICE);

//		Calendar systemCalendar = Calendar.getInstance();
//		systemCalendar.setTimeInMillis(System.currentTimeMillis());			
//		systemCalendar.set(Calendar.MINUTE, (systemCalendar.get(Calendar.MINUTE) + 1));
//		alarmManager.set(AlarmManager.RTC_WAKEUP, systemCalendar.getTimeInMillis(), pendingIntent);
		
		alarmManager.set(AlarmManager.RTC_WAKEUP, food.getRemindBefore().getTimeInMillis(), pendingIntent);
		Log.i("alarmSet", food.getRemindBefore().get(Calendar.HOUR_OF_DAY) + "h" 
		+ food.getRemindBefore().get(Calendar.HOUR_OF_DAY) + "min");
	}
}
