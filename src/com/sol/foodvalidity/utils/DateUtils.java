package com.sol.foodvalidity.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;
import android.widget.DatePicker;

import com.sol.foodvalidity.commun.TypeDate;

/**
 * 
 * @author salifukayta
 */
public class DateUtils {

	/**
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String simpleDateFormatter(Calendar date, int dateFormat) {
		DateFormat formatter = SimpleDateFormat.getDateInstance(dateFormat);
		return formatter.format((date!=null?date.getTime():""));
	}
	
	/**
	 * 
	 * @param calendar
	 * @param dateFormat
	 * @return
	 */
	public static String simpleShortDateFormatter(Calendar calendar) {
		DateFormat formatter = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT);
		if (calendar != null) {
			return formatter.format(calendar.getTime());
		}
		Log.e("dateUtils", "calendar is null");
		return "";
	}

	/**
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String simpleLongDateFormatter(Calendar date) {
		DateFormat formatter = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG);
		return formatter.format((date!=null?date.getTime():""));
	}
	
	/**
	 * 
	 * @param stringDateAfter
	 */
	public static Calendar parseToCalendar(String stringDateAfter) {
		DateFormat format = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT);
		Date dateAfter = null;
		try {
			dateAfter = format.parse(stringDateAfter);
			Calendar calendarAfter = Calendar.getInstance();
			calendarAfter.setTime(dateAfter);
			return calendarAfter;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param datePicker
	 * @return
	 */
	public static Calendar getDateFromPicker(DatePicker datePicker) {
		Calendar calendarPicked = Calendar.getInstance();
		calendarPicked.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
		return calendarPicked;
	}

	/**
	 * 
	 * @param viewPickerOtherDate
	 * @param datePicker
	 * @param typeDate
	 * @return
	 */
	public static boolean isDateBefore(DatePicker viewPickerOtherDate, DatePicker datePicker, TypeDate typeDate) {
		if (viewPickerOtherDate == null || datePicker == null) {
			return true;
		}

		Calendar validityCalendar;
		Calendar reminderCalendar;
		if (typeDate.equals(TypeDate.dateReminder)) {
			reminderCalendar = DateUtils.getDateFromPicker(datePicker);
			validityCalendar = DateUtils.getDateFromPicker(viewPickerOtherDate);
		}
		else {
			validityCalendar = DateUtils.getDateFromPicker(datePicker);
			reminderCalendar = DateUtils.getDateFromPicker(viewPickerOtherDate);
		}
		
		if (validityCalendar.after(reminderCalendar)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param calendarBefore
	 * @param calendarAfter
	 * @return
	 */
	public static boolean equals(Calendar calendarBefore, Calendar calendarAfter) {
		if (calendarBefore.get(Calendar.DAY_OF_MONTH) != calendarAfter.get(Calendar.DAY_OF_MONTH)
				|| calendarBefore.get(Calendar.MONTH) != calendarAfter.get(Calendar.MONTH)
				|| calendarBefore.get(Calendar.YEAR) != calendarAfter.get(Calendar.YEAR)) {
			return false;
		}
		return true;
	}
	
}
