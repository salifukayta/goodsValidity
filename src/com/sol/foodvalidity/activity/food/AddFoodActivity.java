package com.sol.foodvalidity.activity.food;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.andreabaccega.formedittextvalidator.DateAfterValidator;
import com.andreabaccega.widget.FormEditText;
import com.sol.foodvalidity.R;
import com.sol.foodvalidity.activity.BaseActivity;
import com.sol.foodvalidity.activity.food.fragment.DatePickerDialogFragment;
import com.sol.foodvalidity.activity.food.i.IOnFoodPass;
import com.sol.foodvalidity.activity.main.HomeActivity;
import com.sol.foodvalidity.commun.TypeDate;
import com.sol.foodvalidity.dao.FoodDao;
import com.sol.foodvalidity.model.Food;
import com.sol.foodvalidity.receiver.AlarmValidityReceiver;
import com.sol.foodvalidity.service.AlarmSetter;
import com.sol.foodvalidity.utils.DateUtils;

public class AddFoodActivity extends BaseActivity implements IOnFoodPass<TypeDate, Calendar>{
	
	private static final String TAG_PICK_DATE_REMAINDER = "Pick date remainder";
	private static final String TAG_PICK_DATE_VALIDITY = "Pick date validity";
	private static final String EXTRA_KEY_GOODS_DATA = "goodsData";
	private Calendar calendarValidity;
	private Calendar calendarReminder;
	private FoodDao goodsDao;
	private TypeDate typeDate;
	
	private FormEditText etxGoodName;
	private FormEditText etxQuantityRemaining;
	private FormEditText etxDateValidity;
	private FormEditText etxDateReminder;
	private DateAfterValidator dateAfterValidator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_food);
		
		final Activity thisActivity = this;
        goodsDao = FoodDao.getInstance(getApplicationContext());		
        Button btnAddGoods = (Button) findViewById(R.id.btnAddGoods);
        Button btnCancelAddGoods = (Button) findViewById(R.id.btnCancelAddGoods);
        ImageButton imgBtnTakeFoodPicture = (ImageButton) findViewById(R.id.imgBtnTakeFoodPicture);
		
        etxGoodName = (FormEditText) findViewById(R.id.etxGoodName);
        etxQuantityRemaining = (FormEditText) findViewById(R.id.etxQuantityRemaining);
        etxDateReminder = (FormEditText) findViewById(R.id.etxDateReminder);
        etxDateValidity = (FormEditText) findViewById(R.id.etxDateValidity);

        dateAfterValidator = new DateAfterValidator(getString(R.string.error_message_data_validity_after_date_reminder));
        etxDateValidity.addValidator(dateAfterValidator);
        
        etxDateValidity.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Log.i("add good", "set date validity");
				showPickDate(TypeDate.dateValidity, TAG_PICK_DATE_VALIDITY);
			}
		});
        etxDateReminder.setOnClickListener(new OnClickListener() {			
        	@Override
        	public void onClick(View v) {
				Log.i("add good", "set date validity");
				showPickDate(TypeDate.dateReminder, TAG_PICK_DATE_REMAINDER);
        	}
        });
        
        imgBtnTakeFoodPicture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("add good", "imgBtnTakeFoodPicture");
				//TODO open camera, take picture, save it and print it
			}
		});
        
        btnCancelAddGoods.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), HomeActivity.class));
			}
		});
                
        btnAddGoods.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		        if (validateAll()) {
		        	Food goods = new Food(etxGoodName.getText().toString(), etxQuantityRemaining.getText().toString(), 
		        	calendarValidity, calendarReminder);
					
					goodsDao.addOnly(goods);
					AlarmSetter.getInstance().setAlarm(thisActivity, goods, AlarmValidityReceiver.class);
					
					Intent nextIntent = new Intent(getApplicationContext(), HomeActivity.class);
					nextIntent.putExtra(EXTRA_KEY_GOODS_DATA, goods);
					startActivity(nextIntent);
					Toast.makeText(getApplicationContext(), R.string.confirmation_goods_saved_successfully, 
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private boolean validateAll() {
		return (etxGoodName.testValidity() && etxQuantityRemaining.testValidity() 
				&& etxDateValidity.testValidity() && etxDateReminder.testValidity());
	}

	protected void showPickDate(TypeDate typeDate, String msg) {
		this.typeDate = typeDate;
    	DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
    	datePickerDialogFragment.show(getFragmentManager(), msg);
    }
	    
	@Override
	public TypeDate getClicked() {
		return typeDate;
	}

	@Override
	public void onDataPass(TypeDate typeDate, Calendar calendarPicked) {
		if (typeDate.equals(TypeDate.dateReminder)) {
			this.calendarReminder = calendarPicked;	
			etxDateReminder.setText(DateUtils.simpleShortDateFormatter(this.calendarReminder));
			dateAfterValidator.setCalendarBefore(calendarReminder);
		}
		else if (typeDate.equals(TypeDate.dateValidity)) {
			this.calendarValidity = calendarPicked;		
			etxDateValidity.setText(DateUtils.simpleShortDateFormatter(this.calendarValidity));
		}
	}
	
}
