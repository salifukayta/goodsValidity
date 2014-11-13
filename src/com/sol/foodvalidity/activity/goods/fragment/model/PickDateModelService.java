package com.sol.foodvalidity.activity.goods.fragment.model;

import com.sol.foodvalidity.activity.R;
import com.sol.foodvalidity.activity.goods.model.ModelDatePicker;
import com.sol.foodvalidity.commun.TypeDate;

public class PickDateModelService {
	
	private static PickDateModelService CURRENT_INSTANCE;
	
	private PickDateModelService () {
		super();
	}
	
	public static PickDateModelService getInstance () {
		if (CURRENT_INSTANCE == null) {
			synchronized (PickDateModelService.class) {
				if (CURRENT_INSTANCE == null) {
					CURRENT_INSTANCE = new PickDateModelService();
				}
			}
		}
		return CURRENT_INSTANCE;		
	}

	public ModelDatePicker initModelDatePicker(TypeDate typeDate) {
		ModelDatePicker modelDatePicker;
		switch (typeDate) {
			case dateValidity:
				modelDatePicker = new ModelDatePicker(R.id.etxDateValidity, R.id.etxDateReminder, 
						R.string.error_message_data_validity_after_date_reminder, R.id.etxDateValidity, 
						R.string.message_content_picker_date_end_validity_goods);
				break;
			case dateReminder:
				modelDatePicker = new ModelDatePicker(R.id.etxDateReminder, R.id.etxDateValidity, 
						R.string.error_message_data_reminder_after_date_validity, R.id.etxDateReminder,
						R.string.pick_reminder_for_goods_validity);
				break;
		default:
			modelDatePicker = new ModelDatePicker();
			break;
		}
		return modelDatePicker;
	}	
}
