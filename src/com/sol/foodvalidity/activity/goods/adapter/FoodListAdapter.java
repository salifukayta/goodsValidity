package com.sol.foodvalidity.activity.goods.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sol.foodvalidity.activity.R;
import com.sol.foodvalidity.model.Food;
import com.sol.foodvalidity.utils.DateUtils;

public class FoodListAdapter extends ArrayAdapter<Food> {
	
	private final List<Food> foodsList;
	private final Activity context;

    public FoodListAdapter(Activity context, List<Food> foodsList) {
    	super(context, R.layout.row_food, foodsList);
    	this.context = context;
    	this.foodsList = foodsList;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		Food food = foodsList.get(position);
   		LayoutInflater inflator = context.getLayoutInflater();
   		View view = inflator.inflate(R.layout.row_food, null);
   		TextView txvFirstLine = (TextView) view.findViewById(R.id.firstLineList);
   		txvFirstLine.setText(food.getName());
   		TextView txvSecondLine = (TextView) view.findViewById(R.id.secondLineList);
   		txvSecondLine.setText(context.getString(R.string.quantity_food) + food.getQuantity());
   		TextView txvThirdLine = (TextView) view.findViewById(R.id.thirdLineList);
   		txvThirdLine.setText(context.getString(R.string.date_validity_food)
   				+ DateUtils.simpleShortDateFormatter(food.getDateValidity()));
   		ImageView iconList  = (ImageView) view.findViewById(R.id.iconList);
   		iconList.setImageResource(R.drawable.ic_launcher);
   		//TODO à remettre après avoir fait les bons images
// 		iconList.setImageURI(Uri.parse(food.getImageUrl()));
    	return view;
    }

}