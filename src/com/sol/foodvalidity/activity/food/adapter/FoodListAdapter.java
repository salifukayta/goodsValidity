package com.sol.foodvalidity.activity.food.adapter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryanharter.android.tooltips.ToolTip;
import com.ryanharter.android.tooltips.ToolTipContainer;
import com.ryanharter.android.tooltips.ToolTipLayout;
import com.sol.foodvalidity.R;
import com.sol.foodvalidity.model.Food;
import com.sol.foodvalidity.utils.DateUtils;

public class FoodListAdapter extends ArrayAdapter<Food> {

	private static final int POINTER_SIZE = 15;
	public List<Food> foodsList;
	public Activity context;
	public Map<Integer, ToolTipContainer> tooltipContainerMap;

	public FoodListAdapter(Activity context, List<Food> foodsList) {
		super(context, R.layout.row_food, foodsList);
		this.context = context;
		this.foodsList = foodsList;
		tooltipContainerMap = new HashMap<>();
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Food food = foodsList.get(position);
		LayoutInflater inflator = context.getLayoutInflater();

		View view;
		Calendar currentCalendar = Calendar.getInstance();
		if (food.getDateValidity().before(currentCalendar)) {
			// danger
			view = inflator.inflate(R.layout.row_food_alert, null);
			initView(food, view);
			setWarningTooltip(position, food, view, IconEnum.DANGER);
		} 
		else if (food.getRemindBefore().before(currentCalendar)) {
			// warning
			view = inflator.inflate(R.layout.row_food_alert, null);
			initView(food, view);
			setWarningTooltip(position, food, view, IconEnum.WARNING);
		}
		else {
			// safe
			view = inflator.inflate(R.layout.row_food, null);
			initView(food, view);
		}

		return view;
	}

	private void initView(Food food, View view) {
		TextView txvFirstLine = (TextView) view.findViewById(R.id.firstLineList);
		txvFirstLine.setText(food.getName());
		TextView txvSecondLine = (TextView) view.findViewById(R.id.secondLineList);
		txvSecondLine.setText(context.getString(R.string.quantity_food) + food.getQuantity());
		TextView txvThirdLine = (TextView) view.findViewById(R.id.thirdLineList);
		txvThirdLine.setText(context.getString(R.string.date_validity_food) + DateUtils.simpleShortDateFormatter(food.getDateValidity()));
		ImageView iconList = (ImageView) view.findViewById(R.id.iconList);

		// TODO set food icon
		iconList.setImageResource(R.drawable.ic_launcher);
	}

	private void setWarningTooltip(int position, Food food, View view, IconEnum iconEnum) {
		ImageView iconFoodState;
		ToolTipLayout tipContainer = (ToolTipLayout) view.findViewById(R.id.tooltip_container);
		iconFoodState = (ImageView) view.findViewById(R.id.iconState);
		iconFoodState.setImageResource(iconEnum.getImageSource());
		iconFoodState.setOnClickListener(new IconFoodStateListener(position));
		tooltipContainerMap.put(position, new ToolTipContainer(iconEnum, iconFoodState, tipContainer));
	}

	public View createToolTipView(String text, int textColor, int bgColor) {
		float density = context.getResources().getDisplayMetrics().density;
		int padding = (int) (8 * density);
		TextView contentView = new TextView(context.getApplicationContext());
		contentView.setPadding(padding, padding, padding, padding);
		contentView.setText(text);
		contentView.setBackgroundColor(bgColor);
		contentView.setTextAppearance(context.getApplicationContext(), R.style.toolTipColorSizeStyle);
		contentView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		return contentView;
	}

	/**
	 * Icon State Listener
	 * 
	 * @author salifukayta
	 */
	private class IconFoodStateListener implements View.OnClickListener {
		private final int position;

		public IconFoodStateListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			ToolTipLayout tipContainer = tooltipContainerMap.get(position).getTipContainer();
			if (tooltipContainerMap.get(position).isVisible()) {
				tipContainer.dismiss();
			} else {
				// icone warning
				int tipBgColor = context.getResources().getColor(android.R.color.holo_orange_light);
				String tooltipMsg = context.getString(R.string.beware_food_validity_approches);
				// icone danger
				if (tooltipContainerMap.get(position).getIconEnum().equals(IconEnum.DANGER)) {
					tipBgColor = context.getResources().getColor(android.R.color.holo_red_light);
					tooltipMsg = context.getString(R.string.attention_food_validity_is_passed);
				}
				View contentView = createToolTipView(tooltipMsg, Color.WHITE, tipBgColor);
				ImageView iconStateView = tooltipContainerMap.get(position).getIconState();
				
				// Add the ToolTip to the view, using the default animations
				ToolTip tip = new ToolTip.Builder(context.getApplicationContext())
						.anchor(iconStateView) // The view to which the ToolTip should be anchored
						.gravity(Gravity.START) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
						.color(tipBgColor) // The color of the pointer arrow
						.pointerSize(POINTER_SIZE) // The size of the pointer
						.contentView(contentView) // The actual contents of the ToolTip
						.dismissOnTouch(true).build();
				
				tipContainer.addTooltip(tip);
			}
			tooltipContainerMap.get(position).reverseVisibility();
		}
	}
}