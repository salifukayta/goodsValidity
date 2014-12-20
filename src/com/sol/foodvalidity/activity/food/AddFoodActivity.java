package com.sol.foodvalidity.activity.food;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.andreabaccega.formedittextvalidator.DateAfterValidator;
import com.andreabaccega.widget.FormEditText;
import com.sol.foodvalidity.R;
import com.sol.foodvalidity.activity.food.fragment.DatePickerDialogFragment;
import com.sol.foodvalidity.activity.food.i.IOnFoodPass;
import com.sol.foodvalidity.activity.main.HomeActivity;
import com.sol.foodvalidity.commun.TypeDate;
import com.sol.foodvalidity.dao.FoodDao;
import com.sol.foodvalidity.model.Food;
import com.sol.foodvalidity.receiver.AlarmValidityReceiver;
import com.sol.foodvalidity.service.AlarmSetter;
import com.sol.foodvalidity.service.CameraManager;
import com.sol.foodvalidity.utils.DateUtils;

public class AddFoodActivity extends AbstractCameraActivity implements IOnFoodPass<TypeDate, Calendar>{
	
	private static final String TAG_PICK_DATE_REMAINDER = "Pick date remainder";
	private static final String TAG_PICK_DATE_VALIDITY = "Pick date validity";

//	private String currentPhotoPath;
    
	private Calendar calendarValidity;
	private Calendar calendarReminder;
	private FoodDao foodDao;
	private TypeDate typeDate;
	
	private FormEditText etxFoodName;
	private FormEditText etxQuantityRemaining;
	private FormEditText etxDateValidity;
	private FormEditText etxDateReminder;
	private DateAfterValidator dateAfterValidator;
	private ImageView imgBtnTakeFoodPicture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_food);
		
		final AbstractCameraActivity cameraActivity = this;
        foodDao = FoodDao.getInstance(getApplicationContext());		
        Button btnAddFood = (Button) findViewById(R.id.btnAddFood);
        Button btnCancelAddFood = (Button) findViewById(R.id.btnCancelAddFood);
        imgBtnTakeFoodPicture = (ImageView) findViewById(R.id.imgBtnTakeFoodPicture);
		
        etxFoodName = (FormEditText) findViewById(R.id.etxFoodName);
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
				CameraManager.openCamera(cameraActivity);
			}
		});
        
        btnCancelAddFood.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), HomeActivity.class));
			}
		});
                
        btnAddFood.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		        if (validateAll()) {
		        	Food food = addFood();
					AlarmSetter.getInstance().setAlarm(cameraActivity, food, AlarmValidityReceiver.class);
					
					Intent nextIntent = new Intent(getApplicationContext(), HomeActivity.class);
					startActivity(nextIntent);
					Toast.makeText(getApplicationContext(), R.string.confirmation_food_saved_successfully, 
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	/**
	 * 
	 * @return
	 */
	private Food addFood() {
		Food food = new Food(etxFoodName.getText().toString(), etxQuantityRemaining.getText().toString(), 
		calendarValidity, calendarReminder, CameraManager.getPicturePath());
		
		long idFood = foodDao.addOnly(food);
		if (idFood != -1) {
			food.setId(idFood);
		}
		return food;
	}
	
    /**
     * The activity returns with the photo.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CameraManager.REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
        	Food food = new Food();
        	food.setPictureUrl(CameraManager.getPicturePath());
            imgBtnTakeFoodPicture.setImageBitmap(food.getPictureBitMap());
        } 
        else {
            Toast.makeText(this, getString(R.string.picture_capture_failed), Toast.LENGTH_SHORT).show();
        }
    }
       	
	private boolean validateAll() {
		return (etxFoodName.testValidity() && etxQuantityRemaining.testValidity() 
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
	
	@Override
	public String getErrorCreatingFilePicture() {
		return getString(R.string.error_while_saving_picture);
	}

	
}
