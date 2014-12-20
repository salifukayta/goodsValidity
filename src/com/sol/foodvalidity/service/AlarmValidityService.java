package com.sol.foodvalidity.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.util.Log;

import com.sol.foodvalidity.R;
import com.sol.foodvalidity.activity.food.ViewFoodsListActivity;
import com.sol.foodvalidity.model.Food;
import com.sol.foodvalidity.utils.DateUtils;

public class AlarmValidityService extends Service {

	private String notification_title_reminder;

	private static final String ANDROID_RESOURCE = "android.resource://";
	private static final String FOOD_EXTRA_NAME = "food";

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		notification_title_reminder = getResources().getString(R.string.notification_title_reminder);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.i("alarmService", "begin onStart Service");
		Intent intentNextView = new Intent(this.getApplicationContext(), ViewFoodsListActivity.class);
		intentNextView.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, 
				intentNextView, PendingIntent.FLAG_UPDATE_CURRENT);

		if (intent == null || !intent.hasExtra(FOOD_EXTRA_NAME)) {
			return;
		}
		
		Food food = (Food) intent.getExtras().getSerializable(FOOD_EXTRA_NAME);
		Bitmap foodPicture = food.getPictureBitMap();
		
		Notification.Builder builder = new Notification.Builder(getApplicationContext()).setAutoCancel(true)
				.setTicker(prepareTickerText(food))
				.setContentTitle(notification_title_reminder)
				.setContentText(prepareContentText(food))
//				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
				//TODO android 5 => notification appear on locked screen
//				.setVisibility(VISIBILITY_PUBLIC)
				.setContentIntent(pendingNotificationIntent)
				.setLargeIcon(foodPicture)
				.setDefaults(Notification.DEFAULT_SOUND)
				.setSmallIcon(R.drawable.fruit);

//		try {
//			builder.setSound(Uri.parse(ANDROID_RESOURCE + getApplicationContext().getPackageName() + "/" + R.raw.darara));
//		} catch (Exception e) {
//			Log.e("alarmService", "setSound exception");
//		}
		
				Notification notification = builder.build();

		NotificationManager notifManager = (NotificationManager) this.getApplicationContext()
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notifManager.notify(food.getId().intValue(), notification);
		Log.i("alarmService", "end onStart Service");
	}
	
	private String prepareTickerText(Food food) {
		return getString(R.string.notification_ticker_reminder_before) + food.getName() 
				+getString( R.string.notification_ticker_reminder_after);
	}

	private String prepareContentText(Food food) {
		return getString(R.string.notification_content_reminder_before) + food.getName() 
				+ getString(R.string.notification_content_reminder_after)
		+ DateUtils.simpleShortDateFormatter(food.getDateValidity());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
