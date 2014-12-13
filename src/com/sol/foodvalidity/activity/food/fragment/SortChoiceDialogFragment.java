package com.sol.foodvalidity.activity.food.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;

import com.sol.foodvalidity.R;
import com.sol.foodvalidity.activity.food.ViewFoodsListActivity;
import com.sol.foodvalidity.comparator.ByDateValidityComparator;
import com.sol.foodvalidity.comparator.ByNameComparator;
import com.sol.foodvalidity.service.PreferenceService;

public class SortChoiceDialogFragment extends DialogFragment {

	private ViewFoodsListActivity parentActivity;

	@Override	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.parentActivity = (ViewFoodsListActivity) activity;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		String foodName = parentActivity.getString(R.string.food_name);
		String foodDateValidity = parentActivity.getString(R.string.food_date_validity);

		final CharSequence[] items = {foodName, foodDateValidity};
		
		String sortOption = PreferenceService.getSortByOption(getActivity().getApplicationContext());
		int selectedChoice = -1;
		if (sortOption.equals(parentActivity.getString(R.string.pref_sort_by_name))) {
			selectedChoice = 0;
		}
		else if (sortOption.equals(parentActivity.getString(R.string.pref_sort_by_datevalidity))) {
			selectedChoice = 1;
		}
		
		Log.i("sort fragment", sortOption+", "+selectedChoice);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle(R.string.sort_by)
				.setSingleChoiceItems(items, selectedChoice, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.i("sort choice: which", which+"");
				String chosenSort = parentActivity.getString(R.string.pref_sort_by_name);
				switch (which) {
					case 0:
						chosenSort = parentActivity.getString(R.string.pref_sort_by_name);
						parentActivity.getFoodsAdapter().sort(new ByNameComparator());
						break;
					case 1:
						parentActivity.getFoodsAdapter().sort(new ByDateValidityComparator());
						chosenSort = parentActivity.getString(R.string.pref_sort_by_datevalidity);
						break;
				}

				PreferenceService.editSortByOption(getActivity().getApplicationContext(), chosenSort);
				dismiss();
			}
		});
		
		AlertDialog dialogSortChoice = builder.create();
		dialogSortChoice.show();
		return dialogSortChoice;
	}
		
}
