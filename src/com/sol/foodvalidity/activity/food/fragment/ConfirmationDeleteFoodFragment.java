package com.sol.foodvalidity.activity.food.fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.sol.foodvalidity.R;
import com.sol.foodvalidity.activity.food.i.IOnFoodPass;
import com.sol.foodvalidity.commun.TypeOperation;
import com.sol.foodvalidity.dao.FoodDao;
import com.sol.foodvalidity.model.Food;
import com.sol.foodvalidity.utils.DateUtils;

public class ConfirmationDeleteFoodFragment extends DialogFragment {

	private IOnFoodPass<Food, TypeOperation> dataPass;

	@SuppressWarnings("unchecked")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		dataPass = (IOnFoodPass<Food, TypeOperation>) activity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Food food = dataPass.getClicked();
		final FoodDao foodDao = FoodDao.getInstance(getActivity().getApplicationContext());

		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
				.setTitle(R.string.title_delete_food)
				.setMessage(
						getResources().getString(R.string.delete_food_message_1) + food.getName() + getResources().getString(R.string.delete_food_message_2)
								+ food.getQuantity() + getResources().getString(R.string.delete_food_message_3)
								+ DateUtils.simpleDateFormatter(food.getDateValidity(), SimpleDateFormat.LONG)
								+ getResources().getString(R.string.delete_food_message_4))
				.setPositiveButton(R.string.btn_delete_food, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						Log.i("dialog fragment", "deleting food " + food.toString());
						int nbRowAffedted = foodDao.deleteOnly(food.getId());
						if(nbRowAffedted == 1 && food.getPictureUrl() != null) {
							deletePictureImg(food.getPictureUrl());
						}
						dataPass.onDataPass(food, TypeOperation.delete);
					}

					private void deletePictureImg(String pictureUrl) {
						if (pictureUrl != null) {
							File file = new File(pictureUrl);
							file.delete();
							if(file.exists()){
							      try {
									file.getCanonicalFile().delete();
									if(file.exists()){
										getActivity().getApplicationContext().deleteFile(file.getName());
								     }
								} catch (IOException e) {
									Log.e("delete food", "img deleting");
								}
							}
						}
					}
				}).setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						Log.i("dialog fragment", "cancel delte");
					}
				});

		AlertDialog dialogDeleteConfirm = builder.create();
		dialogDeleteConfirm.show();
		setButtonPickerLaf(dialogDeleteConfirm);
		return dialogDeleteConfirm;
	}

	private void setButtonPickerLaf(AlertDialog dialogDeleteConfirm) {
		dialogDeleteConfirm.getButton(DialogInterface.BUTTON_POSITIVE).setBackground(getResources().getDrawable(R.drawable.button_selector));
		dialogDeleteConfirm.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.WHITE);
		dialogDeleteConfirm.getButton(DialogInterface.BUTTON_NEGATIVE).setBackground(getResources().getDrawable(R.drawable.button_selector));
		dialogDeleteConfirm.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
	}
}
