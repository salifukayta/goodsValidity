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
		
//		Button b = (Button) findViewById(R.id.tooltip_container);
//		ToolTipLayout tipContainer = (ToolTipLayout) findViewById(R.id.tooltip_container);
//   		TextView toolTipMsg = new TextView(getApplicationContext());
//   		toolTipMsg.setText("Beware: food validity approches");
//   		ToolTip tip = new ToolTip.Builder(getApplicationContext())
//	        .anchor(b)      // The view to which the ToolTip should be anchored
//	        .gravity(Gravity.TOP)      // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
//	        .color(Color.RED)          // The color of the pointer arrow
//	        .pointerSize(20) // The size of the pointer
//	        .contentView(toolTipMsg)  // The actual contents of the ToolTip
//	        .build();
//
//   			// Add the ToolTip to the view, using the default animations
//   		tipContainer.addTooltip(tip);

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
