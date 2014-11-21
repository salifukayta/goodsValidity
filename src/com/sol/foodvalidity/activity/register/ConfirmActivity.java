package com.sol.foodvalidity.activity.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sol.foodvalidity.R;
import com.sol.foodvalidity.activity.main.DebugDBActivity;
import com.sol.foodvalidity.model.User;

public class ConfirmActivity extends Activity {

	private static final String EXTRA_VALUE_CLASS_TYPE = "user";
	private static final String EXTRA_KEY_CLASS_TYPE = "classType";
	private static final String USER_EXTRAT_NAME = "userData";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm);
		
		User user = (User) getIntent().getExtras().getSerializable(USER_EXTRAT_NAME);		

		TextView confirmMsg = (TextView) findViewById(R.id.confirmMsg);
		confirmMsg.setText(confirmMsg.getText()+"\n"+user.toString());
		
		Button btnGoDebugDB = (Button) findViewById(R.id.goDebugDB);
		btnGoDebugDB.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), DebugDBActivity.class);
				intent.putExtra(EXTRA_KEY_CLASS_TYPE, EXTRA_VALUE_CLASS_TYPE);
				startActivity(intent);
			}
		});
	}

}
