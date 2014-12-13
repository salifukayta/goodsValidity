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

public class ViewGoodsDialogFragment extends DialogFragment {
    
	private IOnFoodPass<Food, TypeOperation> dataPass;
	private FoodDao goodsDao;
	private Food goods;
	private FormEditText etxQuantityGoods;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		dataPass = (IOnFoodPass<Food, TypeOperation>) activity;
	}
	
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		goodsDao = FoodDao.getInstance(getActivity().getApplicationContext());
		goods = dataPass.getClicked();
		View rootView = getActivity().getLayoutInflater().inflate(R.layout.view_food, null);
		etxQuantityGoods = (FormEditText) rootView.findViewById(R.id.updateQuantityGoods);
		etxQuantityGoods.setText(goods.getQuantity());

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(rootView)
				.setTitle(R.string.view_goods_dialog_title)
				.setMessage(getResources().getString(R.string.view_goods_dialog_message_before) + goods.getName() 
						+ getResources().getString(R.string.view_goods_dialog_message_after) 
						+ DateUtils.simpleDateFormatter(goods.getDateValidity(), SimpleDateFormat.LONG))
				.setPositiveButton(R.string.btn_update_goods_quantity, null)
				.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						Log.i("dialog fragment", "cancel click");
					}
				});

		AlertDialog dialogViewGoods = builder.create();
		dialogViewGoods.show();
		setButtonPickerLaf(dialogViewGoods);
		setPositiveButtonListener(dialogViewGoods.getButton(DialogInterface.BUTTON_POSITIVE));
		return dialogViewGoods;
	}

	private void setPositiveButtonListener(Button positifButton) {
		positifButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (etxQuantityGoods.testValidity()) {
					Log.i("dialog fragment", "update :" + etxQuantityGoods.getText().toString());
					goods.setQuantity(etxQuantityGoods.getText().toString());
					goodsDao.updateOnly(goods);
					dataPass.onDataPass(goods, TypeOperation.update);
					dismiss();
				}
			}
		});
	}

	private void setButtonPickerLaf(AlertDialog dialogViewGoods) {
		dialogViewGoods.getButton(DialogInterface.BUTTON_POSITIVE).setBackground(getResources().getDrawable(R.drawable.button_selector));
		dialogViewGoods.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.WHITE);
		dialogViewGoods.getButton(DialogInterface.BUTTON_NEGATIVE).setBackground(getResources().getDrawable(R.drawable.button_selector));
		dialogViewGoods.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
	}

}
