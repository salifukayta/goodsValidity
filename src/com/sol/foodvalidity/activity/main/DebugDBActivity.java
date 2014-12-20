package com.sol.foodvalidity.activity.main;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;

import com.sol.foodvalidity.R;
import com.sol.foodvalidity.dao.FoodDao;
import com.sol.foodvalidity.dao.UserDao;
import com.sol.foodvalidity.model.Food;
import com.sol.foodvalidity.model.User;

public class DebugDBActivity extends Activity {
	
	private static final String EXTRA_VALUE_FOOD_CLASS_TYPE = "food";
	private static final String EXTRA_VALUE_USER_CLASS_TYPE = "user";
	private static final String EXTRA_KEY_CLASS_TYPE = "classType";
	private FoodDao foodDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.debug_db);
		
		GridLayout gridLayout= (GridLayout) findViewById(R.id.rootList);
		String classType = getIntent().getExtras().getString(EXTRA_KEY_CLASS_TYPE);
		
		if (classType.equals(EXTRA_VALUE_USER_CLASS_TYPE)) {
			printUsersList(gridLayout);
			
		} 
		else if (classType.equals(EXTRA_VALUE_FOOD_CLASS_TYPE)){
			printFoodList(gridLayout);			
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
	
	protected void printFoodList(GridLayout gridLayout) {
		foodDao = FoodDao.getInstance(getApplicationContext());
		foodDao.open();
		List<Food> foodList = foodDao.getAll();		
		gridLayout.setRowCount(foodList.size());
		int i = 0;
		for (Food food : foodList) {
			TextView textView = new TextView(getApplicationContext());
			textView.setText(food.getName()+" "+food.getQuantity()+" "+food.getDateValidity());
			gridLayout.addView(textView ,i);
			i++;
		}
	}

}
