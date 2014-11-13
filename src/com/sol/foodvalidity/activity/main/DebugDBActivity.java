package com.sol.foodvalidity.activity.main;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.TextView;

import com.sol.foodvalidity.activity.R;
import com.sol.foodvalidity.dao.GoodsDao;
import com.sol.foodvalidity.dao.UserDao;
import com.sol.foodvalidity.model.Food;
import com.sol.foodvalidity.model.User;

public class DebugDBActivity extends Activity {
	
	private static final String EXTRA_VALUE_GOODS_CLASS_TYPE = "goods";
	private static final String EXTRA_VALUE_USER_CLASS_TYPE = "user";
	private static final String EXTRA_KEY_CLASS_TYPE = "classType";
	private GoodsDao goodsDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.debug_db);
		
		GridLayout gridLayout= (GridLayout) findViewById(R.id.rootList);
		String classType = getIntent().getExtras().getString(EXTRA_KEY_CLASS_TYPE);
		
		if (classType.equals(EXTRA_VALUE_USER_CLASS_TYPE)) {
			printUsersList(gridLayout);
			
		} 
		else if (classType.equals(EXTRA_VALUE_GOODS_CLASS_TYPE)){
			printGoodsList(gridLayout);			
		}			
		else {
			printOk(gridLayout);
		}
	}

	protected void printOk(GridLayout gridLayout) {
		TextView textView = new TextView(getApplicationContext());
		textView.setText(R.string.error_message_none_class_detected);
		gridLayout.addView(textView ,0);
	}

	protected void printUsersList(GridLayout gridLayout) {
		UserDao userDao = new UserDao(getApplicationContext());
		userDao.open();
		List<User> users = userDao.getAll();		
		gridLayout.setRowCount(users.size());
		int i = 0;
		for (User user : users) {
			TextView textView = new TextView(getApplicationContext());
			textView.setText(user.getName()+" "+user.getPassword());
			gridLayout.addView(textView ,i);
			i++;
		}
	}
	
	protected void printGoodsList(GridLayout gridLayout) {
		goodsDao = GoodsDao.getInstance(getApplicationContext());
		goodsDao.open();
		List<Food> goodsList = goodsDao.getAll();		
		gridLayout.setRowCount(goodsList.size());
		int i = 0;
		for (Food goods : goodsList) {
			TextView textView = new TextView(getApplicationContext());
			textView.setText(goods.getName()+" "+goods.getQuantity()+" "+goods.getDateValidity());
			gridLayout.addView(textView ,i);
			i++;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.debug_db, menu);
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
