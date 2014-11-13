package com.sol.foodvalidity.activity.goods.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import android.widget.DatePicker;

import com.sol.foodvalidity.activity.R;
import com.sol.foodvalidity.activity.goods.fragment.model.PickDateModelService;
import com.sol.foodvalidity.activity.goods.i.OnDataPass;
import com.sol.foodvalidity.activity.goods.model.ModelDatePicker;
import com.sol.foodvalidity.commun.TypeDate;
import com.sol.foodvalidity.utils.DateUtils;

public class DatePickerDialogFragment extends DialogFragment {
	
	private OnDataPass<TypeDate, Calendar> dataPass;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		dataPass = (OnDataPass<TypeDate, Calendar>) activity;
	}

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final ModelDatePicker modelDatePicker = PickDateModelService.getInstance().initModelDatePicker(dataPass.getClicked());
		View rootView = getActivity().getLayoutInflater().inflate(R.layout.date_picker, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(rootView)
				.setTitle(R.string.title_date_picker).setMessage(modelDatePicker.getViewMsg());
		final DatePicker datePicker = (DatePicker) rootView.findViewById(R.id.datePickerId);
		
		builder.setPositiveButton(R.string.btn_choose_date_in_picker, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				try {
					Calendar calendarPicked = DateUtils.getDateFromPicker(datePicker);
					Log.i("dialog fragment", "update :" + DateUtils.simpleDateFormatter(calendarPicked, SimpleDateFormat.LONG));
					
					dataPass.onDataPass(dataPass.getClicked(), calendarPicked);
				}
				catch(ClassCastException e) {
					Log.e("class cast exeption", e.getMessage());
				}
			}				
		})
		.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				Log.i("dialog fragment", "cancel click");
			}
		});
		AlertDialog dialogDatePicker = builder.create();
		dialogDatePicker.show();
		setButtonPickerLaf(dialogDatePicker);
		return dialogDatePicker;
	}

	private void setButtonPickerLaf(AlertDialog dialogDatePicker) {
		dialogDatePicker.getButton(DialogInterface.BUTTON_POSITIVE).setBackground(getResources().getDrawable(R.drawable.button_selector));
		dialogDatePicker.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.WHITE);
		dialogDatePicker.getButton(DialogInterface.BUTTON_NEGATIVE).setBackground(getResources().getDrawable(R.drawable.button_selector));
		dialogDatePicker.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
	}
	
}
