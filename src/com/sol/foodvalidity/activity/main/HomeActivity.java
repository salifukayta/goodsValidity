package com.sol.foodvalidity.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sol.foodvalidity.R;
import com.sol.foodvalidity.activity.BaseActivity;
import com.sol.foodvalidity.activity.food.AddFoodActivity;
import com.sol.foodvalidity.activity.food.ViewFoodsListActivity;
import com.sol.foodvalidity.activity.register.RegisterActivity;

public class HomeActivity extends BaseActivity {

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
		
		Button btnGoAddFood = (Button) findViewById(R.id.txvAddFoodTitle);
		btnGoAddFood.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), AddFoodActivity.class));		
			}
		});
		Button btnViewFoodsValidity = (Button) findViewById(R.id.viewFoodsValidity);
		btnViewFoodsValidity.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), ViewFoodsListActivity.class));
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		super.exitApp();
	}
}
