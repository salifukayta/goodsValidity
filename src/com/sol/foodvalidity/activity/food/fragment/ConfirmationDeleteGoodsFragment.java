package com.sol.foodvalidity.activity.food.fragment;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.sol.foodvalidity.activity.R;
import com.sol.foodvalidity.activity.food.i.OnDataPass;
import com.sol.foodvalidity.commun.TypeOperation;
import com.sol.foodvalidity.dao.FoodDao;
import com.sol.foodvalidity.model.Food;
import com.sol.foodvalidity.utils.DateUtils;

public class ConfirmationDeleteGoodsFragment extends DialogFragment {

	private OnDataPass<Food, TypeOperation> dataPass;

	@SuppressWarnings("unchecked")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		dataPass = (OnDataPass<Food, TypeOperation>) activity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Food goods = dataPass.getClicked();
		final FoodDao goodsDao = FoodDao.getInstance(getActivity().getApplicationContext());

		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
				.setTitle(R.string.title_delete_goods)
				.setMessage(
						getResources().getString(R.string.delete_goods_message_1) + goods.getName() + getResources().getString(R.string.delete_goods_message_2)
								+ goods.getQuantity() + getResources().getString(R.string.delete_goods_message_3)
								+ DateUtils.simpleDateFormatter(goods.getDateValidity(), SimpleDateFormat.LONG)
								+ getResources().getString(R.string.delete_goods_message_4))
				.setPositiveButton(R.string.btn_delete_goods, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						Log.i("dialog fragment", "deleting goods " + goods.toString());
						goodsDao.deleteOnly(goods.getId());
						dataPass.onDataPass(goods, TypeOperation.delete);
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
