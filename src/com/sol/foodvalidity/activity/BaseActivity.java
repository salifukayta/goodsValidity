package com.sol.foodvalidity.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.sol.foodvalidity.R;
import com.sol.foodvalidity.activity.main.SettingActivity;
import com.sol.foodvalidity.activity.main.fragment.HelpFragment;

public abstract class BaseActivity extends Activity{

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_setting:
				startActivity(new Intent(getApplicationContext(), SettingActivity.class));
				return true;
			case R.id.action_exit:
				exitApp();
				return true;
			case R.id.action_help:
				HelpFragment helpFragment = new HelpFragment();
				helpFragment.show(getFragmentManager(), "help");
				return true;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected void exitApp() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("EXIT", true);
		startActivity(intent);
	}
}
