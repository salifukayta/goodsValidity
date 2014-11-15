package com.sol.foodvalidity.activity.goods;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sol.foodvalidity.activity.R;
import com.sol.foodvalidity.activity.goods.adapter.FoodListAdapter;
import com.sol.foodvalidity.activity.goods.fragment.ConfirmationDeleteGoodsFragment;
import com.sol.foodvalidity.activity.goods.fragment.ViewGoodsDialogFragment;
import com.sol.foodvalidity.activity.goods.i.OnDataPass;
import com.sol.foodvalidity.commun.TypeOperation;
import com.sol.foodvalidity.dao.GoodsDao;
import com.sol.foodvalidity.model.Food;

public class ViewGoodsListActivity extends ListActivity implements OnDataPass<Food, TypeOperation> {

	private static final String TAG_VIEW_GOODS_DETAILS = "View Goods Details";
	private FoodListAdapter foodsAdapter;
	private GoodsDao goodsDao;
	private Food clickedFood;
	private List<Food> foodsList;
	private ListView livFoods;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		goodsDao = GoodsDao.getInstance(getApplicationContext());
		foodsList = goodsDao.getAllOnly();
		livFoods = getListView();
		foodsAdapter = new FoodListAdapter(this, foodsList);
		livFoods.setAdapter(foodsAdapter);
		TextView txvEmptyMsg = new TextView(getApplicationContext());
		txvEmptyMsg.setText(getString(R.string.no_foods_added));
		livFoods.setEmptyView(txvEmptyMsg);
		
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
	
}
