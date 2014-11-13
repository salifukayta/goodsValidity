package com.sol.foodvalidity.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sol.foodvalidity.activity.R;
import com.sol.foodvalidity.activity.goods.AddGoodActivity;
import com.sol.foodvalidity.activity.goods.ViewGoodsListActivity;
import com.sol.foodvalidity.activity.register.RegisterActivity;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		Button btnGoInscription = (Button) findViewById(R.id.goRegister);
		btnGoInscription.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), RegisterActivity.class));		
			}
		});
		
		Button btnGoAddGoods = (Button) findViewById(R.id.txvAddGoodsTitle);
		btnGoAddGoods.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), AddGoodActivity.class));		
			}
		});
		Button btnViewGoodsValidity = (Button) findViewById(R.id.viewGoodsValidity);
		btnViewGoodsValidity.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), ViewGoodsListActivity.class));		
			}
		});
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("EXIT", true);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
