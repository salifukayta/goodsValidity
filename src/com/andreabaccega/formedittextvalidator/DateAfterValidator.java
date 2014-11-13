package com.andreabaccega.formedittextvalidator;

import java.util.Calendar;

import android.widget.EditText;

import com.sol.foodvalidity.utils.DateUtils;

public class DateAfterValidator extends Validator {

	private Calendar calendarBefore;

	public DateAfterValidator(String errorMessage) {
		super(errorMessage);
	}

	@Override
	public boolean isValid(EditText etxAfter) {
		String stringDateAfter = etxAfter.getText().toString();

		Calendar calendarAfter = DateUtils.parseToCalendar(stringDateAfter);
		
		if (calendarBefore == null || calendarAfter == null || calendarBefore.before(calendarAfter) 
				|| DateUtils.equals(calendarBefore, calendarAfter)) {
			return true;
		}
		return false;
	}
	
	public void setCalendarBefore(Calendar calendarBefore) {
		this.calendarBefore = calendarBefore;
	}
}
