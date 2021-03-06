package com.sol.foodvalidity.activity.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.sol.foodvalidity.R;
import com.sol.foodvalidity.dao.UserDao;
import com.sol.foodvalidity.model.User;


public class RegisterActivity extends Activity {

    private static final String EXTRA_USER_NAME = "userData";
    private UserDao userDao;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        // etx => EditText     |    btn => Button
        final EditText etxName = (EditText) findViewById(R.id.nameSubscription);
        final EditText etxPassword = (EditText) findViewById(R.id.passwordSubscription);
        final EditText etxEmail = (EditText) findViewById(R.id.emailSubscription);
        final EditText etxAddress = (EditText) findViewById(R.id.addressSubscription);
        Button btnSubscribe = (Button) findViewById(R.id.subscribeButton);
		userDao = new UserDao(getApplicationContext());
		
        btnSubscribe.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				User user = new User(etxName.getText().toString(), etxPassword.getText().toString(), 
						etxEmail.getText().toString(), etxAddress.getText().toString());
							
				userDao.addOnly(user);				
				Intent intent = new Intent(getApplicationContext(), ConfirmActivity.class);
				intent.putExtra(EXTRA_USER_NAME, user);
				startActivity(intent);
			}
		});
    }

}
