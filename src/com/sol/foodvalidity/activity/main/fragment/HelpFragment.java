package com.sol.foodvalidity.activity.main.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.sol.foodvalidity.R;

public class HelpFragment extends DialogFragment {

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.setTitle(getString(R.string.how_to_use_the_application))
				.setMessage(getString(R.string.help_msg))
				.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						Log.i("dialog fragment", "cancel click");
					}
				}).create();

		alertDialog.show();
		setButtonPickerLaf(alertDialog);
		return alertDialog;
	}
	
	private void setButtonPickerLaf(AlertDialog dialogViewFood) {
		dialogViewFood.getButton(DialogInterface.BUTTON_NEGATIVE).setBackground(getResources().getDrawable(R.drawable.button_selector));
		dialogViewFood.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
	}

	
}
