package com.sol.foodvalidity.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.sol.foodvalidity.activity.R;
import com.sol.foodvalidity.activity.goods.ViewFoodsListActivity;
import com.sol.foodvalidity.model.Food;
import com.sol.foodvalidity.utils.DateUtils;

public class AlarmValidityService extends Service {
	
	private String notification_title_reminder;

	private static final String ANDROID_RESOURCE = "android.resource://";
	private static final String GOODS_EXTRA_NAME = "goods";
	private static final int ID_NOTIFICATION = 500;

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
		Log.i("alarm", "begin onStart Service");
		Intent intentNextView = new Intent(this.getApplicationContext(), ViewFoodsListActivity.class);
		intentNextView.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, 
				intentNextView, PendingIntent.FLAG_UPDATE_CURRENT);
		
		Food goods = (Food) intent.getExtras().getSerializable(GOODS_EXTRA_NAME);
		
		Notification notification = new Notification.Builder(getApplicationContext()).setAutoCancel(true)
				.setTicker(prepareTickerText(goods))
				.setContentTitle(notification_title_reminder)
				.setContentText(prepareContentText(goods))
//				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
				.setSound(Uri.parse(ANDROID_RESOURCE + getApplicationContext().getPackageName() 
						+ "/" + R.raw.darara))
				//TODO android 5 => notification appear on locked screen
//				.setVisibility(VISIBILITY_PUBLIC)
				.setContentIntent(pendingNotificationIntent).setSmallIcon(R.drawable.ic_launcher).build();

		NotificationManager notifManager = (NotificationManager) this.getApplicationContext()
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notifManager.notify(ID_NOTIFICATION, notification);
		Log.i("alarm", "end onStart Service");
	}

	private String prepareTickerText(Food goods) {
		return getString(R.string.notification_ticker_reminder_before) + goods.getName() 
				+getString( R.string.notification_ticker_reminder_after);
	}

	private String prepareContentText(Food goods) {
		return getString(R.string.notification_content_reminder_before) + goods.getName() 
				+ getString(R.string.notification_content_reminder_after)
		+ DateUtils.simpleShortDateFormatter(goods.getDateValidity());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
