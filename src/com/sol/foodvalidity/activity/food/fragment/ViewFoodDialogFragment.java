package com.sol.foodvalidity.activity.food.fragment;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.andreabaccega.widget.FormEditText;
import com.sol.foodvalidity.R;
import com.sol.foodvalidity.activity.food.i.IOnFoodPass;
import com.sol.foodvalidity.commun.TypeOperation;
import com.sol.foodvalidity.dao.FoodDao;
import com.sol.foodvalidity.model.Food;
import com.sol.foodvalidity.utils.DateUtils;

public class ViewFoodDialogFragment extends DialogFragment {
    
	private IOnFoodPass<Food, TypeOperation> dataPass;
	private FoodDao foodDao;
	private Food food;
	private FormEditText etxQuantityFood;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		dataPass = (IOnFoodPass<Food, TypeOperation>) activity;
	}
	
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		foodDao = FoodDao.getInstance(getActivity().getApplicationContext());
		food = dataPass.getClicked();
		View rootView = getActivity().getLayoutInflater().inflate(R.layout.view_food, null);
		etxQuantityFood = (FormEditText) rootView.findViewById(R.id.updateQuantityFood);
		etxQuantityFood.setText(food.getQuantity());

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(rootView)
				.setTitle(R.string.view_food_dialog_title)
				.setMessage(getResources().getString(R.string.view_food_dialog_message_before) + food.getName() 
						+ getResources().getString(R.string.view_food_dialog_message_after) 
						+ DateUtils.simpleDateFormatter(food.getDateValidity(), SimpleDateFormat.LONG))
				.setPositiveButton(R.string.btn_update_food_quantity, null)
				.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						Log.i("dialog fragment", "cancel click");
					}
				});

		AlertDialog dialogViewFood = builder.create();
		dialogViewFood.show();
		setButtonPickerLaf(dialogViewFood);
		setPositiveButtonListener(dialogViewFood.getButton(DialogInterface.BUTTON_POSITIVE));
		return dialogViewFood;
	}

	private void setPositiveButtonListener(Button positifButton) {
		positifButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (etxQuantityFood.testValidity()) {
					Log.i("dialog fragment", "update :" + etxQuantityFood.getText().toString());
					food.setQuantity(etxQuantityFood.getText().toString());
					foodDao.updateOnly(food);
					dataPass.onDataPass(food, TypeOperation.update);
					dismiss();
				}
			}
		});
	}

	private void setButtonPickerLaf(AlertDialog dialogViewFood) {
		dialogViewFood.getButton(DialogInterface.BUTTON_POSITIVE).setBackground(getResources().getDrawable(R.drawable.button_selector));
		dialogViewFood.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.WHITE);
		dialogViewFood.getButton(DialogInterface.BUTTON_NEGATIVE).setBackground(getResources().getDrawable(R.drawable.button_selector));
		dialogViewFood.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
	}

}
