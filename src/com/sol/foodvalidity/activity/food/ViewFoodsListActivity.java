package com.sol.foodvalidity.activity.food;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sol.foodvalidity.R;
import com.sol.foodvalidity.activity.food.adapter.FoodListAdapter;
import com.sol.foodvalidity.activity.food.fragment.ConfirmationDeleteGoodsFragment;
import com.sol.foodvalidity.activity.food.fragment.ViewGoodsDialogFragment;
import com.sol.foodvalidity.activity.food.i.IBaseActivity;
import com.sol.foodvalidity.activity.food.i.IOnDataPass;
import com.sol.foodvalidity.activity.main.fragment.HelpFragment;
import com.sol.foodvalidity.commun.TypeOperation;
import com.sol.foodvalidity.dao.FoodDao;
import com.sol.foodvalidity.model.Food;

public class ViewFoodsListActivity extends ListActivity 
		implements IOnDataPass<Food, TypeOperation>, IBaseActivity {

	private static final String TAG_VIEW_GOODS_DETAILS = "View Goods Details";
	private FoodListAdapter foodsAdapter;
	private FoodDao goodsDao;
	private Food clickedFood;
	private List<Food> foodsList;
	private ListView livFoods;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		goodsDao = FoodDao.getInstance(getApplicationContext());
		foodsList = goodsDao.getAllOnly();
		livFoods = getListView();

//		TextView txvEmptyMsg = (TextView) findViewById(R.id.emptyMsgId);
		TextView txvEmptyMsg = new TextView(getApplicationContext());
		txvEmptyMsg.setText(getString(R.string.no_foods_added));
		txvEmptyMsg.setTextSize(24);
		txvEmptyMsg.setTextColor(Color.BLACK);
		txvEmptyMsg.setGravity(Gravity.CENTER);
				
		((ViewGroup)livFoods.getParent()).addView(txvEmptyMsg);
		livFoods.setEmptyView(txvEmptyMsg);
		
		foodsAdapter = new FoodListAdapter(this, foodsList);
		livFoods.setAdapter(foodsAdapter);
		Log.i("on create food list", foodsList.isEmpty()+"!");
		
		livFoods.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				ConfirmationDeleteGoodsFragment confirmationDelteGoodsFragment = new ConfirmationDeleteGoodsFragment();
				clickedFood = (Food) parent.getItemAtPosition(position);
				confirmationDelteGoodsFragment.show(getFragmentManager(), getString(R.string.message_confirm_delete_goods));
				return true;
			}
		});
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		clickedFood = (Food) lv.getItemAtPosition(position);
		ViewGoodsDialogFragment viewGoodsDialogFragment = new ViewGoodsDialogFragment();
		viewGoodsDialogFragment.show(getFragmentManager(), TAG_VIEW_GOODS_DETAILS);
	}
	
	public void confirmUpdate(Food food) {
		int position = foodsAdapter.getPosition(food);
		foodsAdapter.remove(food);
		foodsAdapter.insert(food, position);
		Toast.makeText(getApplicationContext(), R.string.confirmation_goods_updated_successfully, Toast.LENGTH_SHORT).show();

	}
	
	public void confirmDelete(Food food) {
		clickedFood = null;
		foodsAdapter.remove(food);
		Toast.makeText(getApplicationContext(), R.string.confirmation_goods_updated_successfully, Toast.LENGTH_SHORT).show();
	}

	@Override
	public Food getClicked() {
		return clickedFood;
	}
	
	@Override
	public void onDataPass(final Food food, TypeOperation operation) {
		switch (operation) {
		case delete:
					confirmDelete(food);
			break;
		case update:
					confirmUpdate(food);
			break;
		default:
			break;
		}
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_exit) {
			exitApp();
			return true;
		}
		if (id == R.id.action_help) {
			HelpFragment helpFragment = new HelpFragment();
			helpFragment.show(getFragmentManager(), "help");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_goods_list, menu);
		return true;
	}

	@Override
	public void exitApp() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("EXIT", true);
		startActivity(intent);
	}
	
}
